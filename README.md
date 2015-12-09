# Flurry Analytics Sample App - Android

This project showcases how Flurry Analytics can be integrated into your Android App. It also demonstrates how you can use Flurry Analytics to get basic insights into your users and app performance. This is a simple app which uses the user’s location to display a feed of public Flickr photos that have been taken in the same area. On clicking on a photo, it will show the user a detailed view of the photo.

## Features

* Integration -  Demonstrates integration of Flurry Analytics in an app, along with best practices for integrating Flurry Analytics in your Android apps.
* Custom Events - Showcases the logging of events like fetch_photo_feed, photo_selected, fetch_photo_stats, etc. 
  Shows use of event parameters, capture event duration.
* Advanced Features - Tracks geographic location of users, errors, version name and page views.

## Prerequisites

- Android Studio
- Android SDK Platform Tools r21 or later
- Android SDK Build Tools r21 or later
- Runtime of Android 4.0.3 (API 15) or later
- Flickr API key - To get this API key, follow instructions 
[here](https://www.flickr.com/services/apps/create/).
- Flurry API key - To get this API key, follow instructions 
[here](https://developer.yahoo.com/flurry/docs/analytics/gettingstarted/android/#get-your-api-keys).
- This app uses two third party libraries - 
  * [Picasso](http://square.github.io/picasso/) - For remote image loading (Copyright 2013 Square, Inc. Picasso is licensed under the Apache License, Version 2.0 )
  * [Android AsyncHTTPClient](http://loopj.com/android-async-http/) - For asynchronous network requests (Copyright (c) 2011-2015 James Smith james@loopj.com The Android Asynchronous Http Client is released under the Android-friendly Apache License, Version 2.0.)

For more info on getting started with Flurry for Android, see
[here](https://developer.yahoo.com/flurry/docs/analytics/gettingstarted/android/).

## Project Setup

1. Clone your repository and open the project in Android Studio. 
2. Add your Flickr API key in FlickrClient.java
3. Add your Flurry API key in FlurryAnalyticsSampleAndroidApp.java
4. Build project and launch on emulator or device. 

Note - If using an emulator, make sure to enable GPS.

## Copyright

    Copyright 2015 Yahoo Inc.
    Licensed under the terms of the zLib license. Please see LICENSE file for terms.
