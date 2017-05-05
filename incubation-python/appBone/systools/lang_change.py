# -*- coding: utf-8 -*-

""" 使用言語変更メニュー
    暫定的にFMsgChangerの仕組みをテスト運用するためのページ
"""


import pygame.mixer
import os.path
from kivy.lang import Builder
from kivy.uix.screenmanager import Screen

from common.fmsg import FMsg

import logging
logger = logging.getLogger(__name__)

file_path = os.path.abspath(os.path.join(os.path.dirname(__file__), 'lang_change.kv'))
with open(file_path, 'r', encoding='utf8') as f:
    Builder.load_string(f.read())


class LangChangeScreen(Screen):

    def __init__(self, **kwargs):
        super(LangChangeScreen, self).__init__(**kwargs)
        FMsg.init_screen(self)

    def set_view(self):
        FMsg.init_screen(self)
        pygame.mixer.music.load(FMsg.get_voice("msg_00000"))  # 言語変更メッセージ
        pygame.mixer.music.play()

    def tr_system_top_scr(self):
        if self.manager is not None:
            self.manager.current = "system_top"
            logger.info("システムツールトップ画面へ移動")

    @staticmethod
    def change_lang(lang_id):
        FMsg.set_lang(lang_id)
        logger.info("言語変更：%d", lang_id)

