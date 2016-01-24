;basemenu.ks - especially made for base room controls
; to be called if room transition moves player to base room and user selects "show base menu"

;JIC someone calls this file without a target
*start
[jump target=*basemenu]

;place here so as to minimize possibility of changes affecting this position
*save
[cm]
セーブ画面を開きます…[r]
[showsave]
≪セーブ≫[p]
[jump target=*show-basemenu]

; ======= DO NOT CHANGE ABOVE THIS LINE IF AT ALL POSSIBLE! =======

*show-basemenu
[bg time="100" method="crossfade" storage="basemenu-bg.jpg"]

*basemenu
[cm]
[nowait]
司令部メニュー：[r]
 　[link target=*disp-data]【基地情報表示】[endlink][r]
 　[link target=*rename-base]【基地名変更】[endlink][r]
 　[link target=*add-floor-above]【地上階を追加する】[endlink]
_　[link target=*add-floor-below]【地下階を追加する】[endlink][r]
 　[link target=*save]【セーブ】[endlink][r]
 　[r]
 　[r]
 　[link target=*show-room storage=room.ks]【部屋に戻る】[endlink][r]
[endnowait]
[s]


;------------------------------------------------------------------------
*disp-data
[cm]
基地名称： [emb exp="f.base.name"][r]
司令室名称： [emb exp="f.base.getRoom(f.x,f.y,f.z).name"][r]
地上階数： [emb exp="f.base.heightAbove"]
_　地下階数： [emb exp="f.base.depthBelow"][r]
基地面積（南北 x 東西）： [emb exp="f.base.nsSize"] x [emb exp="f.base.ewSize"][r]
現在地座標 (x,y,z)： ([emb exp="f.x"], [emb exp="f.y"], [emb exp="f.z"])
[p]
[jump target=*basemenu]

;------------------------------------------------------------------------
*rename-base
[cm]
現在の基地名は「[emb exp="f.base.name"]」です。[r]
新しい基地名を入力してください。[r]
[edit left=100 top=200 width=300 height=42 name=tf.text]
[button target=*sbm-rn-b x=420 y=199 graphic="send.png"]
[s]

*sbm-rn-b
[commit]
[cm]
[eval exp="f.base.rename(tf.text)"]
この基地は「[emb exp="f.base.name"]」と命名されました。[p]
[jump target=*basemenu]

;------------------------------------------------------------------------
*add-floor-above
[cm]
[eval exp="f.base.extendUp()"]
A new floor at level [emb exp="f.base.heightAbove"] has been added.[p]
[jump target=*basemenu]

;------------------------------------------------------------------------
*add-floor-below
[cm]
[eval exp="f.base.extendDown()"]
A new floor at level -[emb exp="f.base.depthBelow"] has been added.[p]
[jump target=*basemenu]

;------------------------------------------------------------------------
