[call storage=tyrano.ks]
[title name=yk242-test-app-20151117-ifjump-test]

*title
[cm]
Test if stack with jumps within if[r]
[link target=*start]【開始】[endlink][r]
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


*start
[eval exp="f.loopnum=0"]
*loopbase
[cm]
[link target=*ifloop]【If jump】[endlink][r]
[link target=*endif256]【go thru 256 endifs】[endlink][r]
[link target=*save]【セーブ】[endlink][r]
[link target=*clearsave]【セーブクリア】[endlink][r]
[link target=*quit]【終了】[endlink][r]
[s]
[jump target=*loopbase]

*save
[cm]
[showsave]
save...[p]
[jump target=*loopbase]

*clearsave
[cm]
[iscript]
localStorage.removeItem("yk242-test-app-20151117-ifjump-test_tyrano_data");
[endscript]
save data cleared! [p]
[jump target=*loopbase]

*ifloop
[cm]
loop [emb exp="++f.loopnum"]...[p]
[if exp="true"]
[jump target=*loopbase]
[endif]


shouldn't arrive here...[p]
[jump target=*loopbase]


*endif256
[cm]
processing thru endifs...[r]
[eval exp="tf.looprem=256"]
*eiloop
[if exp="tf.looprem--<0"]
finished processing.[p]
[jump target=*loopbase]
[endif]
;extra endif
[endif]
[jump target=*eiloop]


shouldn't arrive here2...[p]
[jump target=*loopbase]
