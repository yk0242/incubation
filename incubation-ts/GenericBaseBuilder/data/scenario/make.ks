;このファイルは削除しないでください！
;
;make.ks はデータをロードした時に呼ばれる特別なKSファイルです。
;Fixレイヤーの初期化など、ロード時点で再構築したい処理をこちらに記述してください。
;
;

[call storage=jsdefs.ks]
[call storage=macros.ks]

;re-copy Building to ensure prototype functions work
[iscript]
tf.base = f.base;
f.base = new sf.Building();
f.base.copy(tf.base);
[endscript]

;make.ks はロード時にcallとして呼ばれるため、return必須です。
[return]

