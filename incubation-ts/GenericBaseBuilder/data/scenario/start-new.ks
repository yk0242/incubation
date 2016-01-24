;start-new.ks - starting point for new game
; to be called from title screen on new game start

*start

;call these on new game start
[call storage=jsdefs.ks]
[call storage=macros.ks]

[cm]

starting text...あああ[p]

[iscript]
f.base = new sf.Building();
f.base.rename("Generic Base");
[endscript]

Welcome to your new base.[r]
Your base name is currently [emb exp="f.base.name"].[p]

closing text...[p]

[iscript]
f.x=2;
f.y=2;
f.z=0;
f.dir=0;
[endscript]
[jump target=*show-room storage=room.ks]
