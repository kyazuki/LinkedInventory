# LinkedInventory
インベントリ共有

## 対応バージョン
Minecraft: 1.15.2<br>
Spigot: 1.15.2<br>
https://www.spigotmc.org/wiki/buildtools/

## 概説
プレイヤーのインベントリを共有させます。<br>
実装上では、インベントリが変更されるたびに<br>
その人のインベントリで全プレイヤーのインベントリを上書きしています。

## コマンド
"/linkedinventory"は"/li"に短縮できます<br>
- /linkedinventory toggle
  - インベントリ共有のオン/オフの切り替えができます。<br>
    オンになった時、全プレイヤー/同じチームのインベントリを実行者のインベントリで上書きします。<br>
    また、/linkedinventory toggle <プレイヤー名>で指定されたプレイヤーのインベントリで上書きします。<br>
    Permission: linkedinventory.toggle
- /linkedinventory mode-toggle
  - 全員共有かチーム毎共有かを切り替えできます。<br>
    Permission: linkedinventory.mode_toggle
- /linkedinventory reload
  - コンフィグを再読み込みします。<br>
    Permission: linkedinventory.reload

## configファイル
プラグインを導入した状態で一回でも起動すると、pluginsフォルダ内にLinked_Inventoryフォルダが生成されます。<br>
config.ymlがコンフィグファイルで、その他が翻訳用ファイルです。<br>
値の変更は起動/リロード時に適用されます。
- Delay
  - インベントリ共有が行われるタイミングを指定tick遅延できます。<br>
    例えば、誰かがアイテムを拾ったときにインベントリ共有が行われるのですが、<br>
    共有タイミングが早すぎて拾う直前のインベントリが共有されてしまうことがあります。<br>
    その場合にonPickDelayを変更することで共有タイミングを遅らせることができます。<br>
    デフォルトはすべて0であり、正の整数値のみ指定できます。<br>
    サーバーのラグに応じて変更してください。<br>

## 注意点
インベントリ共有のタイミングは以下の通りです。
- アイテムを捨てたとき
- アイテムを拾ったとき
- ブロックを置いたとき
- アイテムを使ったとき
- 装備やツールの耐久値が減ったとき
- 右クリックしたとき
- インベントリでクリックしたとき
- インベントリでドラッグしたとき
- 両手の持ち物を入れ替えたとき
- 死亡したとき

ほぼ同時に共有が呼び出されると、アイテムが消えたり、増えたりすることがあります。<br>
例えば、AさんとBさんが順番に、かつほぼ同時にアイテムを拾った場合、<br>
まずAさんのインベントリが共有されるため、Bさんの拾ったアイテムが消失します。<br>
拾う/捨てる行為でこれが起きることはほとんどないと思われますが、<br>
複数人で同時にインベントリのアイテムを高速でクリックしたり、<br>
同時にインベントリをドラッグしたりすると発生しやすいです。