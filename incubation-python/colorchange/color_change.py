# -*- coding: utf-8 -*-

# test code to change background color on button press

from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.properties import StringProperty, NumericProperty, ReferenceListProperty


class ColorChange(BoxLayout):
    r = NumericProperty(1)
    g = NumericProperty(1)
    b = NumericProperty(1)
    bg_color = ReferenceListProperty(r, g, b)
    bg_color_txt = StringProperty('white')

    def __init__(self, **kwargs):
        super(ColorChange, self).__init__(**kwargs)

    def change_color(self):
        self.bg_color = (0, 0, 0)
        self.bg_color_txt = 'black'


class ColorChangeApp(App):
    def build(self):
        return ColorChange()

if __name__ == "__main__":
    ColorChangeApp().run()

