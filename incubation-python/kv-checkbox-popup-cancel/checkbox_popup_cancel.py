# -*- coding: utf-8 -*-

""" Test code to check checkbox cancel from popup 
    1. Click on the checkbox, and see a popup dialog come up
    2. Press cancel in the popup (which sets checkbox active=False)
    3. Try re-checking the checkbox
        1. First click does nothing
        2. Second click re-activates checkbox
"""
from kivy.app import App
from kivy.lang import Builder
from kivy.uix.checkbox import CheckBox
from kivy.uix.popup import Popup
from kivy.uix.screenmanager import Screen

Builder.load_string("""
<CheckBoxPopupCancelScreen>
    CheckBoxWithConfirm:
        id: check_1

<CheckBoxPopup>
    BoxLayout:
        orientation: "horizontal"
        Button:
            text: "OK"
            on_press: root.confirm()
        Button:
            text: "Cancel"
            on_press: root.cancel()
""")


class CheckBoxPopupCancelScreen(Screen):
    pass


class CheckBoxPopup(Popup):
    def __init__(self, **kwargs):
        self.caller = kwargs.pop('caller')
        super(CheckBoxPopup, self).__init__(**kwargs)

    def confirm(self):
        # would do something here
        self.dismiss()

    def cancel(self):
        self.caller.active = False
        self.dismiss()


class CheckBoxWithConfirm(CheckBox):
    def on_active(self, checkbox, value):
        super(CheckBoxWithConfirm, self).on_active(checkbox, value)
        if value:  # when checked
            pop = CheckBoxPopup(caller=self)
            pop.open()


class TestApp(App):
    def build(self):
        return CheckBoxPopupCancelScreen()

if __name__ == "__main__":
    TestApp().run()
