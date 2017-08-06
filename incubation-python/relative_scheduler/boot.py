# -*- coding: utf-8 -*-

""" boot.py
    usage: python boot.py
    read the readme.txt for the prerequisites
"""
import logging.config
import logging.handlers
import os.path
from sys import stdout

from kivy.app import App
from kivy.lang import Builder
from kivy.properties import BooleanProperty, StringProperty
from kivy.resources import resource_add_path
import kivy.logger
from kivy.uix.boxlayout import BoxLayout

# --- フォントファイル等を階層依存無しに名称のみで呼び出せるように以下を実施
#     cf https://github.com/kivy/kivy/issues/4458 and watch for updates
resource_add_path(os.path.abspath(os.path.join(os.path.dirname(__file__), 'resources', 'SourceHanSansJP')))
# ---

logger = None  # _init_logging()内で設定される

# ### flags ###
DEBUG_MODE = True
# #############


def _init_logging():
    """ログ出力の初期設定を行う"""
    global logger

    # === ファイルログ出力設定 ### 他モジュールのインポート ＊前に＊ 行う必要がある ###
    logging.Logger.manager.root = kivy.logger.Logger  # cf http://stackoverflow.com/a/36109848/3799649
    logging.config.fileConfig('resources/logging.conf', disable_existing_loggers=not DEBUG_MODE)
    # 上の disable_existing_loggers を False にすると Kivy のログも出力
    logger = logging.getLogger(__name__)
    # ===

    # --- デバッグモードの設定
    if DEBUG_MODE:
        logging.basicConfig(level=logging.DEBUG)
        fh = _get_file_handler()
        fh.setLevel(logging.DEBUG)
        sh = logging.StreamHandler(stdout)
        sh.setFormatter(logging.Formatter('%(asctime)s %(name)s(%(lineno)s) [:%(levelname)s:] %(message)s'))
        sh.setLevel(logging.DEBUG)
        logging.getLogger().addHandler(sh)  # DEBUG_MODE でも stdout に出力できるようにする
        logger.info('------- DEBUG_MODE でログ出力中 -------')
    # ---

    logger.info('ログ出力の初期化を行いました。')
# end init_logging


def _get_file_handler():
    """ ルートロガーに接続されている 現在指定のFileHandler系統のものを取得する
        見つからないとReferenceErrorを上げる
    """
    fh = None
    logr = logging.getLogger()
    for h in logr.handlers:
        if type(h).__name__ == 'TimedRotatingFileHandler':
            fh = h
            break
    if fh is None:
        raise ReferenceError('目的のFile Handlerが見つかりませんでした。')
    return fh


class ScreenRoot(BoxLayout):
    show_back = BooleanProperty(False)

    def __init__(self, **kwargs):
        super(ScreenRoot, self).__init__(**kwargs)

    @staticmethod
    def back_button_pressed():
        pass  # TODO


class RelativeSchedulerApp(App):
    page_title = StringProperty('')

    def build(self):
        file_path = os.path.abspath(os.path.join(os.path.dirname(__file__), 'screens', 'boot.kv'))
        print(file_path)
        with open(file_path, 'r', encoding='utf8') as f:
            Builder.load_string(f.read())
        sr = ScreenRoot()
        sm = sr.ids.sm
        sm.add_widget(top.TopScreen(name='top'))
        # TODO add screens

        return sr


if __name__ == '__main__':
    _init_logging()
    from screens import *
    RelativeSchedulerApp().run()
