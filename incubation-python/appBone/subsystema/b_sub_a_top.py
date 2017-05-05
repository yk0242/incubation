# -*- coding: utf-8 -*-

""" システム系サブシステムのルート
    ここからブート可能
"""
import os.path
import pygame
from kivy.resources import resource_add_path
from kivy.app import App
from kivy.lang import Builder
from kivy.uix.screenmanager import Screen
from kivy.uix.screenmanager import ScreenManager
import logging

# --- フォントファイル等を階層依存無しに名称のみで呼び出せるように以下を実施
#     cf https://github.com/kivy/kivy/issues/4458 and watch for updates
resource_add_path(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'extlib', 'fonts', 'SourceHanSansJP')))
resource_add_path(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'extlib', 'fonts', 'SourceHanSerifJP')))
# ---
# --- import screens of this subsystem to be used here ---
# from subsystema import
# ---
pygame.mixer.init()
logger = logging.getLogger(__name__)

file_path = os.path.abspath(os.path.join(os.path.dirname(__file__), 'b_sub_a_top.kv'))
with open(file_path, 'r', encoding='utf8') as f:
    Builder.load_string(f.read())


class SubATopScreen(Screen):
    def __init__(self, **kwargs):
        super(SubATopScreen, self).__init__(**kwargs)
        # self.ids.page_title.text = "システムメニューテスト\n（トップ画面）"

    def to_top_scr(self):
        if self.manager is not None:
            self.manager.current = "top"
            logger.info("トップ画面へ移動")


class SubATopApp(App):
    def build(self):
        # --- 直接起動用にScreenManagerを設定しておく
        sm = ScreenManager()
        sm.add_widget(SubATopScreen(name="sub_a_top"))
        # ---
        return sm

if __name__ == "__main__":
    SubATopApp().run()
