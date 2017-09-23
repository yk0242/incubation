# -*- coding: utf-8 -*-

""" Test code to test multithread handling of DAO class
    The aim is to provide a DAO coherent with multithread access
"""
import threading

from dao import DaoTestWrapper, DummyLogger, shutting_down, DaoTest

logger = DummyLogger()
TEST_OLD_CLASS = False  # switch to True to test old class' action
THREAD_COUNT_3 = 9      # controls the number of threads started (given num * 3)

if not TEST_OLD_CLASS:
    dtw = DaoTestWrapper()  # common instance should be stored somewhere
else:
    dtw = DaoTest()  # old class


def select(id_):
    res = dtw.select_sql("SELECT * FROM pytest WHERE id="+str(id_))
    logger.info("【MAIN】Result:" + str(res))
    if res is False:  # presumably an SQLite Error
        logger.info("\n!!!!!  CHECK ABOVE FOR SQLITE ERROR  !!! (Thread id: "+str(threading.current_thread().ident)+")\n")
    return res


def select_1():
    ret = select(1)
    if ret != [{'id': 1, 'name': 'Name-A'}]:
        logger.info("(ERROR: expected [{'id': 1, 'name': 'Name-A'}] got "+str(ret)+")\n\n")


def select_2():
    ret = select(2)
    if ret != [{'id': 2, 'name': 'Name-B'}]:
        logger.info("(ERROR: expected [{'id': 2, 'name': 'Name-B'}] got "+str(ret)+")\n\n")


def select_3():
    ret = select(3)
    if ret != [{'id': 3, 'name': 'Name-C'}]:
        logger.info("(ERROR: expected [{'id': 3, 'name': 'Name-C'}] got "+str(ret)+")\n\n")

# ### main process start ###
DummyLogger.log = False
dtw.start()
dtw.initialize()
dtw.execute("DROP TABLE IF EXISTS pytest")
dtw.execute("create table pytest (id INTEGER PRIMARY KEY NOT NULL, name VARCHAR(64))")
dtw.execute("insert into pytest (id, name) values (1,\"Name-A\")")
dtw.execute("insert into pytest (id, name) values (2,\"Name-B\")")
dtw.execute("insert into pytest (id, name) values (3,\"Name-C\")")
DummyLogger.log = True

# SELECT from main thread
logger.info("【MAIN】Calling SELECT from main thread:")
select(1)

# SELECT from sub threads
thr_list = []
for i in range(1, THREAD_COUNT_3):
    thr_list.append(threading.Thread(target=select_1))
    thr_list.append(threading.Thread(target=select_2))
    thr_list.append(threading.Thread(target=select_3))
logger.info("【MAIN】Calling SELECT from sub threads:")
for thr in thr_list:
    thr.start()
for thr in thr_list:
    thr.join()

# shutdown
shutting_down.set()
