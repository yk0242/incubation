;first.ks - called by TyranoScript first
; is effectively the title page

[call storage=tyrano.ks]
[title name="GenericBaseBuilder_ver.0.0.2.20160124"]

*title
;TODO make proper title screen -- eventually? 
[nowait]
[cm]
[r]
_　　　　　　　Generic Base Builder[r]
[r]
[r]
[r]
_　　　　　　[link target=*start storage=start-new.ks]【新たな基地を建設する】[endlink][r]
_　　　　　[link target=*load]【保存した基地をロードする】[endlink][r]
_　　　　　　　　　　　[link target=*quit]【終了】[endlink][r]
[endnowait]
[s]
[jump target=*title]

*load
[cm]
[showload]
[jump target=*title]

*quit
[cm]
[close ask=false]
[jump target=*title]

