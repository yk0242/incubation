# -*- coding: utf-8 -*-

# test changing widget texts interactively from .py using msg from fmsg.py
import pygame.mixer
from kivy.app import App
from kivy.lang import Builder
from kivy.uix.boxlayout import BoxLayout

from fmsg import FMsg

Builder.load_file('FMsgChangerKv.kv')


class FMsgChangerScreen(BoxLayout):

    def __init__(self, **kwargs):
        super(FMsgChangerScreen, self).__init__(**kwargs)
        self.init_view(1)
        pygame.mixer.init()

    def set_view(self, screen_id):
        if screen_id == 1:  # FMsgChanger
            for w_id, widget in self.ids.items():
                if hasattr(widget, 'text_id') and FMsg.get_msg(widget.text_id) is not None:
                    print(widget, widget.text_id)  # DEBUG
                    widget.text = FMsg.get_msg(widget.text_id)
            pygame.mixer.music.load(FMsg.get_snd("msg_0000"))  # 言語変更メッセージ
            pygame.mixer.music.play()
        else:
            print("screen id unidentified: " + screen_id)

    def init_view(self, screen_id):
        if screen_id == 1:  # FMsgChanger
            self.ids.btn_jp.text = u"日本語へ"
            # self.set_view(screen_id)
        else:
            print("screen id unidentified: " + screen_id)

    @staticmethod
    def change_lang(lang_id):
        FMsg.set_lang(lang_id)


class FMsgChangerApp(App):
    def build(self):
        return FMsgChangerScreen()


if __name__ == "__main__":
    FMsgChangerApp().run()

