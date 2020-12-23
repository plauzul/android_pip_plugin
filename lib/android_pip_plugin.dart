import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class AndroidPipPlugin {
  static const MethodChannel _channel = const MethodChannel('android_pip_plugin');
  static StreamController onListen = StreamController<bool>.broadcast();
  static Timer count;

  static Future<bool> enterPictureInPictureMode({@required double width, @required double height}) async {
    return await _channel.invokeMethod('enterPictureInPictureMode', {"width": width, "height": height});
  }

  static Future<bool> isPipModeSupported() async {
    return await _channel.invokeMethod('isPipModeSupported');
  }

  static Future<bool> isPip() async {
    return await _channel.invokeMethod('isPip');
  }

  static Stream listenPip() {
    _checkPip();
    return onListen.stream;
  }

  static void closeListen() {
    onListen.close();
    if (count != null)
      count.cancel();
  }

  static void _checkPip() {
    count = Timer.periodic(Duration(seconds: 1), (timer) async {
      bool isPip = await _channel.invokeMethod('isPip');
      if (!onListen.isClosed)
        onListen.sink.add(isPip);
    });
  }
}
