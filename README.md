## 概要
幼児向け算数アプリ「さんすうキッズ」のプロジェクト

## 仕様書
- [spec.md](./spec.md)

## 特徴
- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)を利用

### Android版のビルド方法
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### iOS版のビルド方法
iOSアプリの開発版をビルドして実行するには、IDEのツールバーにある**実行ウィジェット（Run Widget）**から実行構成を選択して使用するか、Xcodeで [/iosApp](./iosApp) ディレクトリを開いてそこから実行してください。
