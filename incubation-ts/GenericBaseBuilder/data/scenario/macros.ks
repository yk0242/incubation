;macros.ks - defines all common macros
; to be called from make.ks and game start
;　

;draws doors of the room to match current user orientation
[macro name=draw-doors]
[cm][nowait]TODO draw doors here...[r][r]
（仮）ドアのある方向：
[if exp="tf.res=f.base.getRoom(f.x,f.y,f.z).doors[0]==1"]
北
[endif]
[if exp="tf.res=f.base.getRoom(f.x,f.y,f.z).doors[1]==1"]
東
[endif]
[if exp="tf.res=f.base.getRoom(f.x,f.y,f.z).doors[2]==1"]
南
[endif]
[if exp="tf.res=f.base.getRoom(f.x,f.y,f.z).doors[3]==1"]
西
[endif]
[endnowait]
[p]



[endmacro]


;writes the direction the user is currently facing
[macro name=write-dir]
[if exp="f.dir==0"]
北
[elsif exp="f.dir==1"]
東
[elsif exp="f.dir==2"]
南
[elsif exp="f.dir==3"]
西
[else]
《内部エラー》
[endif]
[endmacro]


;writes the Japanese legible version of the room type, if it is specified here
[macro name=write-room-type]
[eval exp="tf.type=f.base.getRoom(f.x,f.y,f.z).type"]
[if exp="tf.type=='empty'||tf.type==''"]
空き部屋
[elsif exp="tf.type=='base-room'"]
司令室
;[elsif exp="tf.type=='foobarbaz'"]
;ほにゃらら
[else]
[emb exp="f.base.getRoom(f.x,f.y,f.z).type"]
[endif]
[endmacro]


;display and process transition to next room
[macro name=show-move-next-room]
TODO show door opening...
[eval exp="sf.move(f)"]
[bg time="500" method="crossfade" storage="black.jpg"]
[endmacro]


;return for when macro.ks was called
[return]
