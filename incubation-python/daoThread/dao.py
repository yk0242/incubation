# -*- coding: utf-8 -*-

""" SQLite3 DAO Threading test
    refs: https://stackoverflow.com/a/19034408/
        https://stackoverflow.com/q/26767388/
"""
import queue
import sqlite3
import threading
from threading import current_thread, Thread


class DummyLogger:
    log = True

    @staticmethod
    def info(msg):
        if DummyLogger.log:
            print("[INFO]["+str(current_thread())+"] "+msg)

    @staticmethod
    def debug(msg):
        if DummyLogger.log:
            print("[DBG] [" + str(current_thread()) + "] " + msg)

logger = DummyLogger()
shutting_down = threading.Event()

class DaoTest:
    _db_name = 'test.sqlite3'
    _conn = None
    _cur = None
    _current_thread = None

    @staticmethod
    def initialize():
        try:
            DaoTest._conn = sqlite3.connect(DaoTest._db_name)
            DaoTest._conn.row_factory = sqlite3.Row
            DaoTest._cur = DaoTest._conn.cursor()
            DaoTest._current_thread = current_thread()
        except sqlite3.Error as err:
            logger.info("***** SQLite Error: " + str(err) + "\n")
            return False
        logger.info('【DaoTest】initialized. Thread:' + str(current_thread()))
        return True

    @staticmethod
    def execute(sql_str):
        logger.info('【DaoTest】execute: ' + sql_str + ' on thread:' + str(current_thread()))
        DaoTest._check_thread_id()
        try:
            DaoTest._cur.execute(sql_str)
            DaoTest._conn.commit()
        except sqlite3.Error as err:
            logger.info("***** SQLite Error: " + str(err) + "\n")
            return False
        return True

    @staticmethod
    def select_sql(sql_):
        logger.info('【DaoTest】select_sql: sql: ' + sql_ + ' on thread:' + str(current_thread()))
        DaoTest._check_thread_id()
        try:
            logger.debug("executing sql...")
            rows = DaoTest._cur.execute(sql_)
        except sqlite3.Error as err:
            logger.info("***** SQLite Error: "+str(err)+"\n")
            return False
        logger.debug("got result, repacking...")
        ret = DaoTest._repack_rows_to_dict_list(rows)
        logger.info('【DaoTest】select_sql: return: ' + str(ret))
        return ret

    @staticmethod
    def _repack_rows_to_dict_list(rows):
        """rowsの結果をdict形式に詰め直す"""
        ret = []
        for row in rows:
            ret.append(dict(zip(row.keys(), tuple(row))))
        return ret

    @staticmethod
    def _check_thread_id():
        if DaoTest._current_thread is not current_thread():
            logger.debug("!!!!! calling thread has changed; re-initializing !!!!!")
            DaoTest.initialize()

    @staticmethod
    def start():
        pass  # dummy to match new class

# ======================================================== addition from here on downwards


class DaoTestWrapper(Thread):
    def __init__(self):
        self.__q = queue.Queue()
        self.__wait_time = 0.1
        self.__return_dict = dict()
        self.__cv = threading.Condition() # condition lock for accessing return_dict
        super(DaoTestWrapper, self).__init__()

    def _on_thread(self, fn, *args, **kwargs):  # called ON THE SAME THREAD as calling thread
        # add request
        self.__q.put((fn, args, kwargs))

        # wait for response
        ret = None
        while ret is None:
            try:
                self.__cv.acquire()  # get lock
                ret = self.__return_dict.pop(current_thread().ident)  # throws KeyError if ident DNE
                self.__cv.notify()   # notifies waiting threads
            except KeyError:
                logger.debug("no result yet, will wait for dict change...")
                self.__cv.wait()     # wait for addition (release lock and regain on notify)
            finally:
                self.__cv.release()  # release lock
        logger.debug("got value, returning: "+str(ret))
        return ret

    def run(self):  # called ON THE DAO THREAD and NOT the calling thread
        while not shutting_down.is_set():
            try:
                fn, args, kwargs = self.__q.get(timeout=self.__wait_time)
                ret_thread_id = kwargs.pop("thread_id")
                logger.debug("got function, running...")
                logger.debug("   return_thread_id: "+str(ret_thread_id))
                logger.debug("   function: "+str(fn))
                logger.debug("   *args: "+str(args))
                logger.debug("   **kwargs: "+str(kwargs))
                ret = fn(*args, **kwargs)
                try:
                    self.__cv.acquire()  # get lock
                    logger.debug("adding result to dict...")
                    self.__return_dict[ret_thread_id] = ret
                    self.__cv.notify()   # notifies waiting threads
                finally:
                    self.__cv.release()  # release lock
            except queue.Empty:
                # pass
                logger.debug("...")


    def initialize(self):
        self._on_thread(DaoTest.initialize, thread_id=current_thread().ident)

    def execute(self, _sql):
        self._on_thread(DaoTest.execute, _sql, thread_id=current_thread().ident)

    def select_sql(self, sql_):
        logger.debug("Calling select SQL...")
        return self._on_thread(DaoTest.select_sql, sql_, thread_id=current_thread().ident)
