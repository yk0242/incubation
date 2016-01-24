;start-new.ks - starting point for new game
; to be called from title screen on new game start

*start

;call these on new game start
[call storage=jsdefs.ks]
[call storage=macros.ks]

[cm]

開始部分の説明[r]
TODO 脚色のためにも何か書く[p]

[iscript]
f.base = new sf.Building();
f.base.rename("Generic Base");
[endscript]

あなたの新しい基地へようこそ。[r]
現在は [emb exp="f.base.name"] ですが、司令部から適宜調整してください。[p]

それでは、司令室へとお連れします…[p]

……[p]
………[p]

[iscript]
f.x=2;
f.y=2;
f.z=0;
f.dir=0;
[endscript]
[jump target=*show-room storage=room.ks]
