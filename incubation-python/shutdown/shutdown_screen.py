# -*- coding: utf-8 -*-

# test using kivy to display shutdown menu
# test shutdown, restart, exit python, turn screen on & off
# cf http://www.ridgesolutions.ie/index.php/2013/02/22/raspberry-pi-restart-shutdown-your-pi-from-python-code/
#    https://www.raspberrypi.org/forums/viewtopic.php?f=45&t=120296
#    http://stackoverflow.com/questions/84882/sudo-echo-something-etc-privilegedfile-doesnt-work-is-there-an-alterna

import sys
import subprocess
from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.lang import Builder


class ShutdownScreen(BoxLayout):
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
        # command  = "echo 1 | /usr/bin/sudo tee /sys/class/backlight/rpi_backlight/bl_power"
        command = "sudo sh -c \"echo 1 > /sys/class/backlight/rpi_backlight/bl_power\""
        process = subprocess.Popen(command.split(), stdout=subprocess.PIPE)
        output = process.communicate()[0]
        print(output)

    @staticmethod
    def screen_on():
        command = "echo 0 | /usr/bin/sudo tee /sys/class/backlight/rpi_backlight/bl_power"
        process = subprocess.Popen(command.split(), stdout=subprocess.PIPE)
        output = process.communicate()[0]
        print(output)

    @staticmethod
    def exit_python():
        sys.exit(0)


class ShutdownScreenApp(App):
    def build(self):
        Builder.load_file('ShutdownScreenKv.kv')
        return ShutdownScreen()


if __name__ == "__main__":
    ShutdownScreenApp().run()
