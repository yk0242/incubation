# -*- coding: utf-8 -*-

""" システムシャットダウンと関連メニュー
    シャットダウン、再起動、Python終了、スクリーンの消灯・点灯
    参考にしたサイトは以下：
    http://www.ridgesolutions.ie/index.php/2013/02/22/raspberry-pi-restart-shutdown-your-pi-from-python-code/
    https://www.raspberrypi.org/forums/viewtopic.php?f=45&t=120296
    http://stackoverflow.com/questions/84882/sudo-echo-something-etc-privilegedfile-doesnt-work-is-there-an-alterna
    http://stackoverflow.com/questions/7389662/link-several-popen-commands-with-pipes
    https://raspberrypi.stackexchange.com/questions/66485/turning-rpi-official-displaytouchscreens-backlight-on-off
    """

import sys
import os.path
import subprocess
from kivy.lang import Builder
from kivy.uix.screenmanager import Screen
import logging
logger = logging.getLogger(__name__)

file_path = os.path.abspath(os.path.join(os.path.dirname(__file__), 'shutdown_rel.kv'))
with open(file_path, 'r', encoding='utf8') as f:
    Builder.load_string(f.read())


class ShutdownScreen(Screen):
    def __init__(self, **kwargs):
        super(ShutdownScreen, self).__init__(**kwargs)

    @staticmethod
    def restart():
        logger.warning("システム再起動")
        command = "/usr/bin/sudo /sbin/shutdown -r now"
        process = subprocess.Popen(command.split(), stdout=subprocess.PIPE)
        output = process.communicate()[0]
        logger.info(output)

    @staticmethod
    def shutdown():
        logger.warning("システムシャットダウン")
        command = "/usr/bin/sudo /sbin/shutdown -P now"
        process = subprocess.Popen(command.split(), stdout=subprocess.PIPE)
        output = process.communicate()[0]
        logger.info(output)

    @staticmethod
    def screen_off():
        logger.warning("スクリーン消灯")
        command1 = "echo 1"
        command2 = "/usr/bin/sudo tee /sys/class/backlight/rpi_backlight/bl_power"
        process1 = subprocess.Popen(command1.split(), stdout=subprocess.PIPE)
        process2 = subprocess.Popen(command2.split(), stdin=process1.stdout, stdout=subprocess.PIPE)
        output = process2.communicate()[0]
        logger.info(output)

    @staticmethod
    def screen_on():
        logger.warning("スクリーン点灯")
        command1 = "echo 0"
        command2 = "/usr/bin/sudo tee /sys/class/backlight/rpi_backlight/bl_power"
        process1 = subprocess.Popen(command1.split(), stdout=subprocess.PIPE)
        process2 = subprocess.Popen(command2.split(), stdin=process1.stdout, stdout=subprocess.PIPE)
        output = process2.communicate()[0]
        logger.info(output)

    @staticmethod
    def exit_python():
        logger.warning("プロジェクトを終了しOSに戻る")
        sys.exit(0)

    def tr_system_top_scr(self):
        if self.manager is not None:
            self.manager.current = "system_top"
            logger.info("システムツールトップ画面へ移動")
