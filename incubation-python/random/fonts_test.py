# -*- coding: utf-8 -*-

# based on https://kivy.org/docs/guide/basic.html#quickstart
# Japanese font texting based on http://supportdoc.net/support-kivy/03jp.html
# 源ノ明朝　　　 https://github.com/adobe-fonts/source-han-serif/tree/release/
# 源ノ角ゴシック https://github.com/adobe-fonts/source-han-sans/tree/release/

# import kivy
from kivy.app import App
from kivy.uix.label import Label
from kivy.uix.gridlayout import GridLayout


class FontsShowcase(GridLayout):
    def __init__(self, **kwargs):
        super(FontsShowcase, self).__init__(**kwargs)
        self.cols = 2

        my_ex_text = u"テスト文章"

        self.add_widget(Label(text=my_ex_text+" (no font specified)"))
        self.add_widget(Label(text="-"))

        self.add_widget(Label(text=my_ex_text+u'（ipaexm.ttf）', font_name='../IPAexfont00301/ipaexm.ttf'))
        self.add_widget(Label(text=my_ex_text+u'（ipaexg.ttf）', font_name='../IPAexfont00301/ipaexg.ttf'))

        self.add_widget(Label(text=my_ex_text+u'（SourceHanSerifJP-ExtraLight.otf）', font_name='../SourceHanSerifJP/SourceHanSerifJP-ExtraLight.otf'))
        self.add_widget(Label(text=my_ex_text+u'（SourceHanSansJP-ExtraLight.otf）', font_name='../SourceHanSansJP/SourceHanSansJP-ExtraLight.otf'))

        self.add_widget(Label(text=my_ex_text+u'（SourceHanSerifJP-Light.otf）', font_name='../SourceHanSerifJP/SourceHanSerifJP-Light.otf'))
        self.add_widget(Label(text=my_ex_text+u'（SourceHanSansJP-Light.otf）', font_name='../SourceHanSansJP/SourceHanSansJP-Light.otf'))

        self.add_widget(Label(text=my_ex_text+u'（SourceHanSerifJP-Regular.otf）', font_name='../SourceHanSerifJP/SourceHanSerifJP-Regular.otf'))
        self.add_widget(Label(text=my_ex_text+u'（SourceHanSansJP-Normal.otf）', font_name='../SourceHanSansJP/SourceHanSansJP-Normal.otf'))

        self.add_widget(Label(text=my_ex_text+u'（SourceHanSerifJP-Medium.otf）', font_name='../SourceHanSerifJP/SourceHanSerifJP-Medium.otf'))
        self.add_widget(Label(text=my_ex_text+u'（SourceHanSansJP-Regular.otf）', font_name='../SourceHanSansJP/SourceHanSansJP-Regular.otf'))

        self.add_widget(Label(text=my_ex_text+u'（SourceHanSerifJP-SemiBold.otf）', font_name='../SourceHanSerifJP/SourceHanSerifJP-SemiBold.otf'))
        self.add_widget(Label(text=my_ex_text+u'（SourceHanSansJP-Medium.otf）', font_name='../SourceHanSansJP/SourceHanSansJP-Medium.otf'))

        self.add_widget(Label(text=my_ex_text+u'（SourceHanSerifJP-Bold.otf）', font_name='../SourceHanSerifJP/SourceHanSerifJP-Bold.otf'))
        self.add_widget(Label(text=my_ex_text+u'（SourceHanSansJP-Bold.otf）', font_name='../SourceHanSansJP/SourceHanSansJP-Bold.otf'))

        self.add_widget(Label(text=my_ex_text+u'（SourceHanSerifJP-Heavy.otf）', font_name='../SourceHanSerifJP/SourceHanSerifJP-Heavy.otf'))
        self.add_widget(Label(text=my_ex_text+u'（SourceHanSansJP-Heavy.otf）', font_name='../SourceHanSansJP/SourceHanSansJP-Heavy.otf'))


class FontsTestApp(App):
    def build(self):
        return FontsShowcase()


if __name__ == "__main__":
    FontsTestApp().run()
