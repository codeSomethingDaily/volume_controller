# Flutter Android voulme button handler

--A Flutter plugin for iOS and Android control system volume.--

As far as I can tell it is hard for a plugin to override android default behavior, doing it directly in your app is easier.

follow https://docs.flutter.dev/development/platform-integration/platform-channels and put MainActivity.kt as ${project_name}/android/app/src/kotlin/com/${package_name}/MainActivity.kt, and android_volume_button_handler.dart in your lib to use volume button as a button.
