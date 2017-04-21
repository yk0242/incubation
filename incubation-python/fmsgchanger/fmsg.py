# test message processor


class FMsg:
    _s_langid = 0  # pretend this is static and private etc.
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

    # # using dict directly may be clearer?
    # _s_diclist = [dict(langname='日本語',
    #                    msg_0001='メッセージその一',
    #                    msg_0002='メッセージそのに',
    #                    msg_0003='メッセージその３',
    #                    dummy_line='-'),  # dummy line to ease input to lines above
    #
    #              dict(langname='English',
    #                   msg_0001='Message One',
    #                   msg_0002='Message two',
    #                   msg_0003='Message 3',
    #                   dummy_line='-')]  # dummy line to ease input to lines above

    _MSG_FILELIST = [
        'm_japanese.csv',
        'm_english.csv'
    ]
    _S_MAX_LANGID = len(_MSG_FILELIST)-1  # maximum langid allowed

    _s_msg_dict = {}  # dictionary of messages

    def __init__(self, **kwargs):
        super(FMsg, self).__init__(**kwargs)

    @staticmethod
    def _read_mdict_from_file(langid):
        if langid > FMsg._S_MAX_LANGID:
            return False
        FMsg._s_msg_dict.clear()  # clear dict jic
        with open(FMsg._MSG_FILELIST[langid], encoding='utf8') as fin:
            for line in fin:
                if ',' in line:
                    (key, val) = line.split(',')
                    FMsg._s_msg_dict[key.strip()] = val.strip()
                    print(FMsg._s_msg_dict)  # DEBUG
        return True

    @staticmethod
    def set_lang(langid):
        if langid > FMsg._S_MAX_LANGID:
            return False
        FMsg._s_langid = langid
        FMsg._read_mdict_from_file(langid)
        return True

    @staticmethod
    def get_msg(msgid):
        #  returns corresponding message, or None if message not found
        return FMsg._s_msg_dict.get(msgid)
