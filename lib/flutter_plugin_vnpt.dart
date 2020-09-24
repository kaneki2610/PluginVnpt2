import 'dart:async';

import 'package:flutter/services.dart';

class FlutterPluginVnpt {
  static const MethodChannel _channel =
      const MethodChannel('flutterPluginVnpt');

  static void gotoFlutterNative1() async {
    await _channel.invokeMethod('startActivityNative1');
  }
}
