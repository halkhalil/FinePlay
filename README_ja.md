[English](./README.md) | 日本語

<img src="./public/images/ja-JP/logo.png" alt="fine✿play" height="54"/>

Play(Java) 2.7 & Bootstrap 4.1  
多言語、レスポンシブな、プロジェクトのひな形(何かのたたき台に)。

| 携帯 | タブレット |
|-------|-----------|
| <img src="./public/images/iPhone.png" height="300"/>  | <img src="./public/images/iPad.png" height="400"/> |

遊び方
----------

##### 環境 #####

	macOS Mojave バージョン 10.14.1（18B75）
	時間帯 UTC
	openjdk バージョン "11.0.1" 2018-10-16 / OpenJDK Runtime Environment 18.9 (build 11.0.1+13)
	Safari バージョン 12.0.1 (14606.2.104.1.1)

##### OpenJDK #####

インストール

[OpenJDK](http://jdk.java.net/11/)

##### コンソール #####

	MacBook:~ user$ cd github/FinePlay

	MacBook:FinePlay user$ chmod +x ./sbt-dist/bin/sbt
	MacBook:FinePlay user$ chmod +x ./sbt
	MacBook:FinePlay user$ chmod +x ./start.sh

	MacBook:FinePlay user$ ./start.sh

##### Safari #####

URL を開く

[http://localhost:9000](http://localhost:9000)

緑色のユーザーアイコンをクリックすると、開発用のユーザーを選択できます。

書類
-------

### Javadoc ###

##### コンソール #####

	MacBook:~ user$ cd github/FinePlay
	MacBook:FinePlay user$ ./sbt doc
	MacBook:~ user$ open -a Safari ./target/scala-2.12/api/index.html

### 公式書類 ###

[Play Framework](https://www.playframework.com/documentation/2.7.x)

[Bootstrap](http://getbootstrap.com/docs/4.1)

[Font Awesome](https://fontawesome.com/how-to-use)

この **ひな形** は、非公式です。

注意
---------------

+ このプロジェクトは、継続性を考慮していません。（しかし、このひな形は、オープンソースソフトウェアのための非常に薄いラッパーなので、心配しないでください。）
+ このプロジェクトは、汎用的に使用する事は、考えられていません。（このプロジェクトは、ひな形なので、フォークもしくは、修正して使用します。）
+ このプロジェクトで使用している、ライブラリのライセンスは、利用者において改めて、確認してください。

やる事(やるとは、言ってない)
---------------

###### このプロジェクトが、将来更新されるかどうかは、未定です。 ######

### FinePlay 2.7.0 ###
+ Framework 2.7.0 リリース版に更新

#### Play Framework 2.7.0 ####
+ application.conf の更新

### FinePlay 2.7.x ###
+ Bootstrap 4.2 リリース版に更新
+ Shepherd 2.0.0 リリース版に更新

#### Bootstrap 4.2 ####
+ notify 実装の外観を、Toast ベースに書換(マッチングが、悪い場合はやらない)
+ Hammer の include を移動(include しなくても動くっぽい？ので、その場合はやらないかも)
+ Spinner の学習

### FinePlay x.x.x ###
+ IE の削除 / system_menuのリファクタリング
+ Bootbox 5.0 リリース版(作者居ないっぽい？)か、フォーク版に更新

リリース履歴
---------------

+ **2.7.0-RC8-βc9** - 2018-12-01
+ **2.6.13-β7** - 2018-06-18
+ **2.6.3-α1** - 2017-08-14
   + 最初のコミット

###### バージョン番号なんて飾りです。偉い人にはそれがわからんのですよ。

ライセンス
-------
著作権 &copy; 2017-2018 [hiro20v](https://github.com/hiro20v)  
[MIT License][mit] の下で配布されます。

[MIT]: http://opensource.org/licenses/MIT
