;base.ks - especially made for base room controls
; to be called if room transition moves player to base room

*base
[bg time="100" method="crossfade" storage="base-room.jpg"]
[draw-doors]
[cm]
This is your [emb exp="f.base.getRoom(f.x,f.y,f.z).name"].[p]

*basemenu
[cm]
Base menu:[r]
[link target=*disp-data]【display base data】[endlink][r]
[link target=*rename-room]【rename room】[endlink]　　
[link target=*rename-base]【rename base】[endlink][r]
[link target=*add-floor-above]【add floor above】[endlink][r]
[s]

*disp-data
[cm]
Base name: [emb exp="f.base.name"][r]
Base room name: [emb exp="f.base.getRoom(f.x,f.y,f.z).name"][r]
Base height above ground: [emb exp="f.base.heightAbove"][r]
Base depth below ground: [emb exp="f.base.depthBelow"][r]
Base size (NS x EW): [emb exp="f.base.nsSize"] x [emb exp="f.base.ewSize"][r]
[p]
[jump target=*basemenu]

*rename-room
[cm]
Enter a new name for your base room:
[edit left=100 top=200 width=300 height=42 name=tf.text]
[button target=*sbm-rn-r x=420 y=199 graphic="send.png"]
[s]

*sbm-rn-r
[commit]
[cm]
[eval exp="f.base.getRoom(f.x,f.y,f.z).rename(tf.text)"]
Your base room has been renamed as [emb exp="f.base.getRoom(f.x,f.y,f.z).name"].[p]
[jump target=*basemenu]

*rename-base
[cm]
Enter a new name for your base:
[edit left=100 top=200 width=300 height=42 name=tf.text]
[button target=*sbm-rn-b x=420 y=199 graphic="send.png"]
[s]

*sbm-rn-b
[commit]
[cm]
[eval exp="f.base.rename(tf.text)"]
Your base has been renamed as [emb exp="f.base.name"].[p]
[jump target=*basemenu]

*add-floor-above
[cm]
[eval exp="f.base.extendUp()"]
A new floor at level [emb exp="f.base.heightAbove"] has been added.[p]
[jump target=*basemenu]

