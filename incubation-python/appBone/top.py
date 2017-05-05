# -*- coding: utf-8 -*-

""" プロジェクトシステムのトップページ
    通常はboot.pyからブート後、ここに来ます。
"""

import os.path
from kivy.lang import Builder
from kivy.uix.screenmanager import Screen

import logging
logger = logging.getLogger(__name__)

file_path = os.path.abspath(os.path.join(os.path.dirname(__file__), 'top.kv'))
with open(file_path, 'r', encoding='utf8') as f:
    Builder.load_string(f.read())


class TopScreen(Screen):
    def __init__(self, **kwargs):
        super(TopScreen, self).__init__(**kwargs)

    def to_sys_password_scr(self):
        if self.manager is not None:
            self.manager.current = "sys_password"
            logger.info("システムパスワード入力画面へ")

    def to_sub_a_top_scr(self):
        if self.manager is not None:
            self.manager.current = "sub_a_top"
            logger.info("サブシステムA画面へ")
