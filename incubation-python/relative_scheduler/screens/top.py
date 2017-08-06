# -*- coding: utf-8 -*-

""" トップページ
    通常はboot.pyからブート後、これが表示されます。
"""

import logging
import os.path

from kivy.clock import Clock
from kivy.lang import Builder
from kivy.uix.screenmanager import Screen

from modules.gmailapiusage import GmailApiUsage

logger = logging.getLogger(__name__)

file_path = os.path.abspath(os.path.join(os.path.dirname(__file__), 'top.kv'))
with open(file_path, 'r', encoding='utf8') as f:
    Builder.load_string(f.read())


class TopScreen(Screen):
    screen_id = 'top'
    screen_title = ''

    def __init__(self, **kwargs):
        super(TopScreen, self).__init__(**kwargs)

    def start_schedule(self):
        # TODO temp
        Clock.schedule_once(self.send_mail, 10)

    @staticmethod
    def send_mail(clock):  # TODO temp
        logger.info("sending mail after " + str(clock))
        GmailApiUsage.send_mail("これはKivyから送っています。", "test message from Kivy App", "yybtcbk@gmail.com")
