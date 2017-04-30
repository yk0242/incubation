# -*- coding: utf-8 -*-

# test using kivy to display shutdown menu
# test shutdown, restart, exit python, turn screen on & off
# cf http://www.ridgesolutions.ie/index.php/2013/02/22/raspberry-pi-restart-shutdown-your-pi-from-python-code/
#    https://www.raspberrypi.org/forums/viewtopic.php?f=45&t=120296
#    http://stackoverflow.com/questions/84882/sudo-echo-something-etc-privilegedfile-doesnt-work-is-there-an-alterna
#    http://stackoverflow.com/questions/7389662/link-several-popen-commands-with-pipes
#    https://raspberrypi.stackexchange.com/questions/66485/turning-rpi-official-displaytouchscreens-backlight-on-off

import sys
import subprocess
from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.lang import Builder
from kivy.uix.screenmanager import Screen

Builder.load_file('ShutdownScreenKv.kv')


class ShutdownScreen(Screen):
    def __init__(self, **kwargs):
        super(ShutdownScreen, self).__init__(**kwargs)
        self.ids.page_title.text = "システムメニューテスト\n（シャットダウン関係）"

    @staticmethod
    def restart():
        command = "/usr/bin/sudo /sbin/shutdown -r now"
        process = subprocess.Popen(command.split(), stdout=subprocess.PIPE)
        output = process.communicate()[0]
        print(output)

    @staticmethod
    def shutdown():
        command = "/usr/bin/sudo /sbin/shutdown -P now"
        process = subprocess.Popen(command.split(), stdout=subprocess.PIPE)
        output = process.communicate()[0]
        print(output)

    @staticmethod
    def screen_off():
        command1 = "echo 1"
        command2 = "/usr/bin/sudo tee /sys/class/backlight/rpi_backlight/bl_power"
        process1 = subprocess.Popen(command1.split(), stdout=subprocess.PIPE)
        process2 = subprocess.Popen(command2.split(), stdin=process1.stdout, stdout=subprocess.PIPE)
        output = process2.communicate()[0]
        print(output)

    @staticmethod
    def screen_on():
        command1 = "echo 0"
        command2 = "/usr/bin/sudo tee /sys/class/backlight/rpi_backlight/bl_power"
        process1 = subprocess.Popen(command1.split(), stdout=subprocess.PIPE)
        process2 = subprocess.Popen(command2.split(), stdin=process1.stdout, stdout=subprocess.PIPE)
        output = process2.communicate()[0]
        print(output)

    @staticmethod
    def exit_python():
        sys.exit(0)

    def tr_systop_scr(self):
        if self.manager is not None:  # need this to keep the button safe upon direct boot of this file
            self.manager.current = "sys-top"


class ShutdownScreenApp(App):
    def build(self):
        return ShutdownScreen()


if __name__ == "__main__":
    ShutdownScreenApp().run()
