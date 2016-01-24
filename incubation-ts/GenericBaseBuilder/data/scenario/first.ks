;first.ks - called by TyranoScript first
; is effectively the title page

[call storage=tyrano.ks]
[title name="GenericBaseBuilder_ver.0.0.1.20151124"]

*title
;TODO make proper title screen -- eventually? 
[cm]
Generic Base Builder[r]
[link target=*start storage=start-new.ks]【新たに開始】[endlink][r]
[link target=*load]【ロード】[endlink][r]
[link target=*quit]【終了】[endlink][r]
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

