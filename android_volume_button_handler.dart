import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class AndroidVolumeButtonHandler {
  static AndroidVolumeButtonHandler? _instance;
  final _platform = const MethodChannel("com.codeSomethingDaily/method");
  final _event = const EventChannel("com.codeSomethingDaily/event");

  Stream<String>? stream;

  AndroidVolumeButtonHandler._();

  /// return a singletion instance for invoking methods
  factory AndroidVolumeButtonHandler() {
    _instance ??= AndroidVolumeButtonHandler._();
    return _instance!;
  }

  /// Blocks System change to volume and the volume UI
  void startBlocking() {
    _platform.invokeMethod('startBlockingVolumeKey');
  }

  /// Unblocks System change to volume and the volume UI
  void stopBlocking() {
    _platform.invokeMethod('stopBlockingVolumeKey');
  }

  /// Add a listener to volume up and down
  /// return either "volumeUp" or "volumeDown" as String
  StreamSubscription<String> listen(void Function(String) onData) {
    stream ??= _event.receiveBroadcastStream().map((event) => event as String);
    return stream!.listen(onData);
  }

  /// removes the listener
  void cancel(StreamSubscription<String> subscription) {
    subscription.cancel();
  }

  /// For testing only
  @visibleForTesting
  MethodChannel getMethodChannel() {
    return _platform;
  }

  /// For testing only
  @visibleForTesting
  Future<bool> testMethod() async {
    return await _platform.invokeMethod('stopBlockingVolumeKey');
  }
}
