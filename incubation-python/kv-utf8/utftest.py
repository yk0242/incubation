# -*- coding: utf-8 -*-

""" Minimal case test using kivy KV File in UTF8 with Japanese text on Windows
    (note that a font set needs to be loaded separately for proper display of the text)
"""
from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.lang import Builder

# This will fail in Windows environment with the following stacktrace (Kivy v1.9.1)
#  Traceback (most recent call last):
#   File "D:/dev/gitbase/incubation/incubation-python/kv-utf8/utftest.py", line 19, in <module>
#     Builder.load_file("utftestkv.kv")
#   File "C:\Python34\lib\site-packages\kivy\lang.py", line 1831, in load_file
#     data = fd.read()
# UnicodeDecodeError: 'cp932' codec can't decode byte 0x86 in position 141: illegal multibyte sequence
Builder.load_file("utftestkv.kv")

# # This is a temporary workaround that DOES work
# with open("utftestkv.kv", 'r', encoding='utf8') as f:
#     Builder.load_string(f.read())

# # This will load properly
# Builder.load_string('''
# <UtfTestScreen>
#     Label:
#         # font_name: '../SourceHanSerifJP/SourceHanSerifJP-Medium.otf'
#         id: inst_txt
#         text: u'テストテキスト'
#
# ''')


class UtfTestScreen(BoxLayout):
    def __init__(self, **kwargs):
        super(UtfTestScreen, self).__init__(**kwargs)

        # # Test using Japanese text from py file to kv specified parts - works
        # self.ids.inst_txt.text = 'テストテキスト'


class UtfTestApp(App):
    def build(self):
        return UtfTestScreen()


if __name__ == "__main__":
    UtfTestApp().run()
