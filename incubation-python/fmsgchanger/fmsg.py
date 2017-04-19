# test message processor


class FMsg:
    s_langid = 0;  # pretend this is static
    # 0: 日本語
    # 1: 英語 etc.

    # s_diclist = []
    # # could use dict(zip(keys, msglist0)), but below is clearer
    # s_diclist.append({'langname': '日本語',
    #        'msg_0001': 'メッセージその一',
    #        'msg_0002': 'メッセージそのに',
    #        'msg_0003': 'メッセージその３'
    #                   })
    # s_diclist.append({'langname': 'English',
    #        'msg_0001': 'Message One',
    #        'msg_0002': 'Message two',
    #        'msg_0003': 'Message 3'
    #                   })

    # using dict directly may be clearer?
    s_diclist = [dict(langname='日本語',
                      msg_0001='メッセージその一',
                      msg_0002='メッセージそのに',
                      msg_0003='メッセージその３',
                      dummy_line='-'),  # dummy line to ease input to lines above

                 dict(langname='English',
                      msg_0001='Message One',
                      msg_0002='Message two',
                      msg_0003='Message 3',
                      dummy_line='-')]  # dummy line to ease input to lines above

    @staticmethod
    def set_lang(alangid):
        FMsg.s_langid = alangid

    @staticmethod
    def get_msg(msgid):
        return FMsg.s_diclist[FMsg.s_langid].get(msgid)
