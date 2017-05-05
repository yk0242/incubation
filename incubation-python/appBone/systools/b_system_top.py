# -*- coding: utf-8 -*-

"""test page with button to go to ShutdownScreen
   システム系サブシステムのルート、ここからブート可能
"""
import os.path

import pygame
from kivy.resources import resource_add_path
from kivy.app import App
from kivy.lang import Builder
from kivy.uix.screenmanager import Screen
from kivy.uix.screenmanager import ScreenManager

# --- フォントファイル等を階層依存無しに名称のみで呼び出せるように以下を実施
#     cf https://github.com/kivy/kivy/issues/4458 and watch for updates
resource_add_path(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'extlib', 'fonts', 'SourceHanSansJP')))
resource_add_path(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'extlib', 'fonts', 'SourceHanSerifJP')))
# ---
# --- import screens of this subsystem to be used here ---
from systools import shutdown_rel, lang_change
# ---
pygame.mixer.init()

import logging
logger = logging.getLogger(__name__)

file_path = os.path.abspath(os.path.join(os.path.dirname(__file__), 'b_system_top.kv'))
with open(file_path, 'r', encoding='utf8') as f:
    Builder.load_string(f.read())


class SystemScreen(Screen):
    def __init__(self, **kwargs):
        super(SystemScreen, self).__init__(**kwargs)

    def to_shutdown_rel_scr(self):
        if self.manager is not None:
            self.manager.current = "shutdown_rel"
            logger.info("シャットダウン関連メニューへ移動")

    def to_top_scr(self):
        if self.manager is not None:
            self.manager.current = "top"
            logger.info("トップ画面へ移動")

    def to_lang_change_scr(self):
        if self.manager is not None:
            self.manager.current = "lang_change"
            logger.info("使用言語選択メニューへ移動")


class SystemApp(App):
    def build(self):
        # --- 直接起動用にScreenManagerを設定しておく
        sm = ScreenManager()
        sm.add_widget(SystemScreen(name="system_top"))
        sm.add_widget(shutdown_rel.ShutdownScreen(name="shutdown_rel"))
        sm.add_widget(lang_change.LangChangeScreen(name="lang_change"))
        # ---
        return sm

if __name__ == "__main__":
    SystemApp().run()
