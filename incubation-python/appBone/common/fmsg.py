# -*- coding: utf-8 -*-

# test message processor
import os

import logging
logger = logging.getLogger(__name__)


class FMsg:
    _s_langid = 0
    # 0: 日本語
    # 1: 英語 etc.

    _S_MSG_FILELIST = [
        'msg/m_japanese.csv',
        'msg/m_english.csv'
    ]
    _S_MAX_LANGID = len(_S_MSG_FILELIST) - 1  # maximum langid allowed

    _s_msg_dict = {}  # dictionary of messages

    _SND_DIRLIST = [
        'voice/ja/',
        'voice/en/'
    ]
    _S_SND_EXT = '.ogg'

    def __init__(self, **kwargs):
        super(FMsg, self).__init__(**kwargs)

    @staticmethod
    def _read_mdict_from_file(langid):
        if langid > FMsg._S_MAX_LANGID:
            return False
        FMsg._s_msg_dict.clear()  # clear dict jic
        file_path = os.path.abspath(os.path.join(os.path.dirname(__file__), FMsg._S_MSG_FILELIST[langid]))
        with open(file_path, encoding='utf8') as fin:
            for line in fin:
                if ',' in line:
                    (key, val) = line.split(',')
                    FMsg._s_msg_dict[key.strip()] = val.strip()
                    logger.debug(FMsg._s_msg_dict)
        return True

    @staticmethod
    def set_lang(lang_id):
        """sets the current language"""
        if lang_id > FMsg._S_MAX_LANGID:
            return False
        FMsg._s_langid = lang_id
        FMsg._read_mdict_from_file(lang_id)
        return True

    @staticmethod
    def get_msg(msg_id):
        """returns corresponding message, or None if message not found"""
        return FMsg._s_msg_dict.get(msg_id)

    @staticmethod
    def get_voice(msg_id):
        """returns path to corresponding voice file
           NB does NOT guarantee the file actually exists"""
        return os.path.abspath(os.path.join(os.path.dirname(__file__),
                                            FMsg._SND_DIRLIST[FMsg._s_langid] + msg_id + FMsg._S_SND_EXT))

    @staticmethod
    def init_screen(self_):
        """initializes screen given the root widget as self_
           note that no replacement will occur if the corresponding message is not found in the 
           dict (based on the corresponding csv)"""
        for w_id, widget in self_.ids.items():
            if hasattr(widget, 'text_id') and FMsg.get_msg(widget.text_id) is not None:
                logger.debug(widget, widget.text_id)
                widget.text = FMsg.get_msg(widget.text_id)


FMsg.set_lang(0)  # run initialization for csv to load
