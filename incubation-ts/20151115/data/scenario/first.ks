[call storage=tyrano.ks]
[title name=yk242-test-app-20151115-macrojump-test]


;load macro first.
[macro name=m_loop]
looping via macro...loop [emb exp="++f.loopnum;"][p]
[jump target=*loopbase]
[endmacro]


*title
[cm]
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
[eval exp="f.loopnum=0;"]
*loopbase
[cm]
[link target=*mloop]【マクロループ】[endlink][r]
[link target=*save]【セーブ】[endlink][r]
[link target=*clearsave]【セーブクリア】[endlink][r]
[link target=*quit]【終了】[endlink][r]
[s]
[jump target=*loopbase]

*save
[cm]
[showsave]
saved![p]
[jump target=*loopbase]

*clearsave
[cm]
[iscript]
localStorage.removeItem("yk242-test-app-20151115-macrojump-test_tyrano_data");
[endscript]
save data cleared! [p]
[jump target=*loopbase]

*mloop
[cm]
[m_loop]

