## 概要
幼児向け算数アプリ「さんすうキッズ」のプロジェクト

## 仕様書
- [spec.md](./spec.md)

## 特徴
- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)を利用

## Android版のビルド方法
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

## iOS版のビルド方法
iOSアプリの開発版をビルドして実行するには、次のいずれかの方法を使用します。
- IDEのツールバーにある**実行ウィジェット（Run Widget）**から、対象の実行構成を選択して実行する
- Xcodeで [/iosApp](./iosApp) ディレクトリを開き、Xcode上から実行する
