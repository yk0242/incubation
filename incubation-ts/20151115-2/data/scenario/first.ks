[call storage=tyrano.ks]
[title name=yk242-test-app-20151115-2-if-test]


[eval exp="f.val=0;"]
[eval exp="f.act=0;"]

[call target=*macro]

*title
[cm]
Check savedata upon improper if structures[r]
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
[link target=*ifloop1]【Check if if if】[endlink][r]
[link target=*ifloop2]【Check if else if else if】[endlink][r]
[link target=*ifloop3]【Check if-elsif-elsif-else】[endlink][r]
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
localStorage.removeItem("yk242-test-app-20151115-2-if-test_tyrano_data");
[endscript]
save data cleared! [p]
[jump target=*loopbase]

*ifloop1
[cm]
[eval exp="f.val=Math.floor(Math.random() *8)"]
[broken_if]
[jump target=*loopbase]

*ifloop2
[cm]
[eval exp="f.act++;"]
[if exp="f.act>6"]
[eval exp="f.act=0;"]
[endif]
[set_time]
[jump target=*loopbase]

*ifloop3
[cm]
[eval exp="f.act++;"]
[if exp="f.act>6"]
[eval exp="f.act=0;"]
[endif]
[proper_if]
[jump target=*loopbase]


(should not arrive here...)[p]
[jump target=*loopbase]


*macro
;load macro first.
[macro name=broken_if]
[if exp="f.val>=7" ]
seven+[p]
[if exp="f.val>=5" ]
five six[p]
[if exp="f.val>=3" ]
three four[p]
[elsif exp="f.val==2" ]
two[p]
[elsif exp="f.val==1" ]
one[p]
[else]
zero[p]
[endif]
[endmacro]

[macro name=set_time]
[if exp="f.act<3" ]
am[p]
[else]
[if exp="f.act==3" ]
noon[p]
[else]
[if exp="f.act>3" ]
pm[p]
[endif]
[endmacro]

[macro name=proper_if]
[if exp="f.act<3" ]
am[p]
[elsif exp="f.act==3" ]
noon[p]
[elsif exp="f.act>3" ]
pm[p]
[endif]
[endmacro]

[return]
