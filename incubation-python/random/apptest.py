# -*- coding: utf-8 -*-

# based on https://kivy.org/docs/guide/basic.html#quickstart
# Japanese font texting based on http://supportdoc.net/support-kivy/03jp.html
# test font "Source Hans Serif" https://typekit.com/fonts/source-han-serif-japanese

# import kivy
from kivy.app import App
from kivy.uix.label import Label
from kivy.uix.button import Button
from kivy.uix.gridlayout import GridLayout
from kivy.uix.textinput import TextInput


class LoginScreen(GridLayout):
    def __init__(self, **kwargs):
        super(LoginScreen, self).__init__(**kwargs)
        self.cols = 2
        
        self.add_widget(Label(text=u'ユーザー名：', font_name='../IPAexfont00301/ipaexg.ttf'))
        self.username = (TextInput(multiline=False))
        self.add_widget(self.username)
        
        # self.add_widget(Label(text=u"パスワード："))
        self.add_widget(Label(text=u'パスワード：', font_name='../IPAexfont00301/ipaexm.ttf'))
        self.password = TextInput(password=True, multiline=False)
        self.add_widget(self.password)
        
        self.add_widget(Button(text=u"""＿人人人人人人＿　（マルチ
＞　ログイン　＜　　ライン
￣Y^Y^Y^Y^Y^Y￣　　テスト）""", font_name='../IPAexfont00301/ipaexg.ttf'))


class MyTestAppApp(App):
    
    def build(self):
        return LoginScreen()

if __name__ == "__main__":
    MyTestAppApp().run()
