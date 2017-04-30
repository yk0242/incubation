# -*- coding: utf-8 -*-

# test page with button to go to ShutdownScreen

from kivy.app import App
from kivy.lang import Builder
from kivy.uix.screenmanager import Screen
from kivy.uix.screenmanager import ScreenManager, NoTransition

# --- import screens of this subsystem to be used here ---
import shutdown_screen
# ---

Builder.load_file('SystemScreenKv.kv')


class SystemScreen(Screen):
    def __init__(self, **kwargs):
        super(SystemScreen, self).__init__(**kwargs)
        self.ids.page_title.text = "システムメニューテスト\n（トップ画面）"

    def tr_shutdown_scr(self):
        if self.manager is not None:  # need this to keep the button safe upon direct boot of this file
            self.manager.current = "sys-shutdown"


class SystemScreenApp(App):
    def build(self):
        # --- build sm of subsystem for when this file is called directly
        sm = ScreenManager(transition=NoTransition())
        sm.add_widget(SystemScreen(name="sys-top"))
        sm.add_widget(shutdown_screen.ShutdownScreen(name="sys-shutdown"))
        # ---
        return sm


if __name__ == "__main__":
    SystemScreenApp().run()
