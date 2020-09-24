import 'dart:async';

import 'package:flutter/services.dart';

class FlutterPluginVnpt {
  static const MethodChannel _channel =
      const MethodChannel('flutterPlugin2');

  static Future<String> getDeviceInfo(Map<String, dynamic> value) async {
    final String info = await _channel.invokeMethod('startActivityNative', value);
    return info;
  }
}
