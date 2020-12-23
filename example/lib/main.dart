import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:android_pip_plugin/android_pip_plugin.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Picture in Picture'),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            AndroidPipPlugin.enterPictureInPictureMode(width: 150, height: 150);
            //AndroidPipPlugin.isPip();
            /*AndroidPipPlugin.listenPip().listen((isPip) {
              Timer(Duration(seconds: 10), () => AndroidPipPlugin.closeListen());
            });*/
          },
          child: Icon(Icons.cast),
        ),
      ),
    );
  }
}
