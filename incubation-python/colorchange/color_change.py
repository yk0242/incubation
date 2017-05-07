# -*- coding: utf-8 -*-

# test code to change background color on button press

from kivy.app import App
from kivy.properties import StringProperty, NumericProperty, ReferenceListProperty
from kivy.uix.screenmanager import Screen
import store


class ColorChange(Screen):
    color_theme_txt = StringProperty('white')
    bg_r = NumericProperty(1)
    bg_g = NumericProperty(1)
    bg_b = NumericProperty(1)
    bg_color = ReferenceListProperty(bg_r, bg_g, bg_b)
    txt_r = NumericProperty(0)
    txt_g = NumericProperty(0)
    txt_b = NumericProperty(0)
    txt_a = NumericProperty(1)
    txt_color = ReferenceListProperty(txt_r, txt_g, txt_b, txt_a)
    st = store.Store()

    def change_bg_color(self, storeinst, value):
        self.bg_color = storeinst.bg_color
        print("change")

    def change_col_text(self, storeinst, value):
        self.color_theme_txt = storeinst.bg_color_txt

    def change_txt_color(self, storeinst, value):
        self.txt_color = storeinst.txt_color

    def __init__(self, **kwargs):
        super(ColorChange, self).__init__(**kwargs)
        self.st.bind(bg_color=self.change_bg_color)
        self.st.bind(bg_color_txt=self.change_col_text)
        self.st.bind(txt_color=self.change_txt_color)

    def change_color(self):
        self.st.instance.bg_color = (0, 0, 0)
        self.st.instance.bg_color_txt = 'black'
        self.st.instance.txt_color = (1, 1, 1, 1)


class ColorChangeApp(App):
    def build(self):
        return ColorChange()

if __name__ == "__main__":
    ColorChangeApp().run()

