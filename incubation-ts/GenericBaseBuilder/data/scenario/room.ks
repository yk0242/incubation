;room.ks - general room commands go here. 
; 

*show-room
[bg time="100" method="crossfade" storage="room.jpg"]
[draw-doors]

*menu
[cm]
[nowait]
「[emb exp="f.base.getRoom(f.x,f.y,f.z).name"]」（[write-dir]向き）[r]
 　[link target=*disp-data]【部屋情報表示】[endlink][r]
 　[link target=*rename-room]【部屋名称変更】[endlink]
_　[link target=*change-room-type]【部屋種別変更】[endlink][r]
 　[link target=*place-door]【正面の壁にドアを設置】[endlink][r]
 　[r]
 　[r]
 　[r]
[if exp="f.base.getRoom(f.x,f.y,f.z).type=='base-room'"]
 　[link target=*show-basemenu storage=basemenu.ks]【司令部メニューを開く】[endlink][r]
[else]
 　[r]
[endif]
_　　　　　　　　　　　　　　　　[link target=*go-forwards]【↑】[endlink][r]
 　[link target=*turn-left]【←】[endlink]
_　　　　　　　　　　　　　　　　　　　　　　　　　　　　　[link target=*turn-right]【→】[endlink][r]
 　[r]
;TODO Staircases go on the line above
[endnowait]
[s]


;------------------------------------------------------------------------
*disp-data
[cm]
部屋名称： [emb exp="f.base.getRoom(f.x,f.y,f.z).name"][r]
部屋種別： [write-room-type][r]
現在地座標 (x,y,z)： ([emb exp="f.x"], [emb exp="f.y"], [emb exp="f.z"])
[p]
[jump target=*menu]

;------------------------------------------------------------------------
*rename-room
[cm]
現在の部屋名称は「[emb exp="f.base.getRoom(f.x,f.y,f.z).name"]」です。[r]
新しい部屋名称を入力してください。[r]
[edit left=100 top=200 width=300 height=42 name=tf.text]
[button target=*sbm-rn-r x=420 y=199 graphic="send.png"]
[s]

*sbm-rn-r
[commit]
[cm]
[eval exp="f.base.getRoom(f.x,f.y,f.z).rename(tf.text)"]
この部屋は「[emb exp="f.base.getRoom(f.x,f.y,f.z).name"]」と命名されました。[p]
[jump target=*menu]

;------------------------------------------------------------------------
*change-room-type
[cm]
[if exp="tf.type=f.base.getRoom(f.x,f.y,f.z).type=='base-room'"]
  司令室は直接種別変更できません。[p]
  [jump target=*menu]
[endif]
現在の部屋種別は「[write-room-type]」です。[r]
新しい部屋種別を入力してください。[r]
[edit left=100 top=200 width=300 height=42 name=tf.text]
[button target=*sbm-ch-r-t x=420 y=199 graphic="send.png"]
[s]

*sbm-ch-r-t
[commit]
[cm]
[if exp="tf.text!='base-room'"]
  [eval exp="f.base.getRoom(f.x,f.y,f.z).changeType(tf.text)"]
  この部屋は「[write-room-type]」と分類されました。[p]
[else]
  司令室は複数設置できません。[p]
[endif]
[jump target=*menu]

;------------------------------------------------------------------------
*place-door
[cm]
[eval exp="tf.res=f.base.getFloor(f.z).addDoor(f.x,f.y,f.dir)"]
[if exp="tf.res==0"]
  正面にドアを設置しました。[p]
[elsif exp="tf.res==1"]
  正面に既にドアがあります。[p]
[elsif exp="tf.res==-1"]
  敷地外に出てしまいます。[r]
  先に司令部にて敷地を拡げてください。[p]
[else]
  《内部エラー》ドアの設置に失敗しました。[p]
[endif]
[jump target=*show-room]

;------------------------------------------------------------------------
*turn-left
[eval exp="tf.turn=-1"]
[jump target=*turn]

*turn-right
[eval exp="tf.turn=1"]
[jump target=*turn]

*turn
[iscript]
f.dir+=tf.turn+4;
f.dir%=4;
[endscript]
[jump target=*show-room]

;------------------------------------------------------------------------
*go-forwards
[cm]
[if exp="tf.type=f.base.getRoom(f.x,f.y,f.z).doors[f.dir]==1"]
[show-move-next-room]
[jump target=*show-room]
[else]
ドアが無いと通れません。[p]
[endif]
[jump target=*menu]
;------------------------------------------------------------------------