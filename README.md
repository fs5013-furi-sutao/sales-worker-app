# sales.worker
販売管理システムの試作品です

## Get started

### リポジトリをクローンする
```console
git clone https://github.com/fs5013-furi-sutao/sales.worker.git
```

### ローカルリポジトリ内に移動
```console
cd ./sales.worker
```

### DB コンテナを起動
MySQL コンテナを起動させる。
```console
docker-compose up -d
```

initdb.d にある SQL ファイルが実行され、初期データも登録される。

バックエンドディレクトリに移動
```console
cd ./backend-spring-boot
```

gradle コマンドでバックエンドサーバを起動
```console
gradle bootRun
```

Gradle をインストールしていない場合は、gradlew バッチを実行
```console
./gradlew bootRun
```

別のコンソールでフロントエンドディレクトリに移動
```console
cd ./frontend-vue
```

必要な Node モジュールをインストール
```console
yarn
```

フロントエンドサーバを起動する
```console
yarn serve
```

ブラウザからフロントエンドサーバにアクセスする
```
http://localhost:8081/
```
