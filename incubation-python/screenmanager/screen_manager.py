# -*- coding: utf-8 -*-

# test using kivy to transition between different screens
# cf https://kivy.org/docs/api-kivy.uix.screenmanager.html

from kivy.app import App
from kivy.uix.screenmanager import ScreenManager, NoTransition

# --- import screens for tops of subsystems to be used here ---
import b_system_screen
import shutdown_screen
# ---

# --- add screens to sm, the first being the top screen ---
sm = ScreenManager(transition=NoTransition())
sm.add_widget(b_system_screen.SystemScreen(name="sys-top"))
sm.add_widget(shutdown_screen.ShutdownScreen(name="sys-shutdown"))
# ---


class ShutdownScreenApp(App):
    def build(self):
        return sm


if __name__ == "__main__":
    ShutdownScreenApp().run()
