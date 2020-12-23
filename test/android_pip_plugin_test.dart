import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:android_pip_plugin/android_pip_plugin.dart';

void main() {
  const MethodChannel channel = MethodChannel('android_pip_plugin');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await AndroidPipPlugin.platformVersion, '42');
  });
}
