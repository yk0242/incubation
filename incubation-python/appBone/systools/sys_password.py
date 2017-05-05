# -*- coding: utf-8 -*-

""" システムメニューに入る前のパスワード画面
    正しいパスワードを入力しないと進めないようにし、ユーザーが間違えて入るのを防ぐ
"""
import os.path
import sys
import subprocess
from kivy.app import App
from kivy.lang import Builder
from kivy.uix.screenmanager import Screen, ScreenManager
import logging
logger = logging.getLogger(__name__)

file_path = os.path.abspath(os.path.join(os.path.dirname(__file__), 'sys_password.kv'))
with open(file_path, 'r', encoding='utf8') as f:
    Builder.load_string(f.read())


class SysPasswordScreen(Screen):
    def __init__(self, **kwargs):
        super(SysPasswordScreen, self).__init__(**kwargs)

    def to_top_scr(self):
        if self.manager is not None:
            self.manager.current = "top"
            logger.info("トップ画面へ移動")

    def check_to_system_top_scr(self):
        if self.ids.password.text == self.get_sys_pwd():
            self.to_system_top_scr()
        else:
            self.ids.warning.text = "パスワードが違います。"
            logger.warning("誤ったシステムパスワード入力： %s", self.ids.password.text)

    def to_system_top_scr(self):
        if self.manager is not None:
            self.manager.current = "system_top"
            logger.info("システムトップへ移動")

    @staticmethod
    def get_sys_pwd():
        # !! TODO stub -- test against proper password
        return ''


class TempApp(App):
    def build(self):
        global logger
        sm = ScreenManager()
        sm.add_widget(SysPasswordScreen(name="password"))
        return sm


if __name__ == "__main__":
    TempApp().run()
