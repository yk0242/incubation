from kivy.event import EventDispatcher
from kivy.properties import StringProperty, NumericProperty, ReferenceListProperty


class Store:
    class __StoreInner(EventDispatcher):
        bg_color_txt = StringProperty('white')
        r = NumericProperty(1)
        g = NumericProperty(1)
        b = NumericProperty(1)
        bg_color = ReferenceListProperty(r, g, b)
        txt_r = NumericProperty(0)
        txt_g = NumericProperty(0)
        txt_b = NumericProperty(0)
        txt_a = NumericProperty(1)
        txt_color = ReferenceListProperty(txt_r, txt_g, txt_b, txt_a)

    # ! only access for changing values directly to trigger events
    instance = None

    def __init__(self):
        if not Store.instance:
            Store.instance = Store.__StoreInner()

    def __getattr__(self, name):
        return getattr(self.instance, name)
