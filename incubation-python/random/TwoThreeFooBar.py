# -*- coding: utf-8 -*-

# test using kivy to display output for 2-foo-3-bar-6-foobar program 
# 1. output to console first
# 2. output results progressively to Kivy App
# also serving as a test to separate MVC - delegate Model and View (and minimal Controls) to kv file

from kivy.app import App
# from kivy.uix.label import Label
from kivy.uix.boxlayout import BoxLayout
# from kivy.uix.slider import Slider
from kivy.lang import Builder

Builder.load_file('TwoThreeFooBarKv.kv')


class TwoThreeFooBarScreen(BoxLayout):
    def __init__(self, **kwargs):
        super(TwoThreeFooBarScreen, self).__init__(**kwargs)

        # # Test using Japanese text from py file to kv specified parts
        # self.ids.button_go.text = '実行'
        # self.ids.button_go.font_name = '../SourceHanSansJP/SourceHanSansJP-Medium.otf'

    def foobar_main_out(self, upper):
        outstr = ''
        for i in range(1, upper+1):
            div = False
            if i % 2 == 0:
                outstr += 'foo'
                div = True
            if i % 3 == 0:
                outstr += 'bar'
                div = True
            if not div:
                outstr += str(i)
            outstr += '\n'

            # output result
            print(outstr) # DEBUG
            self.ids.text_out.text = outstr

            # change some text just to be nice, and also to test multiple changes in one go...
            self.ids.inst_txt.text = 'Here you go; you can choose another value and press "GO again".'
            self.ids.button_go.text = 'GO again'

        return True


class TwoThreeFooBarApp(App):
    def build(self):
        return TwoThreeFooBarScreen()


if __name__ == "__main__":
    TwoThreeFooBarApp().run()
