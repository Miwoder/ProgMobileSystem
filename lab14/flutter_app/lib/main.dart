import 'package:flutter/material.dart';
import 'home.dart';
import 'widg.dart';

void main() => runApp(MaterialApp(
    theme:
        ThemeData(brightness: Brightness.dark, primaryColor: Colors.blueGrey),
    home: HelloCard()));
