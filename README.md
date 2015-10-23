# SwipableLayout
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.serhatsurguvec.libraries/swipablelayout/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.serhatsurguvec.libraries/swipablelayout)
[![API](https://img.shields.io/badge/API-8%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=8)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SwipableLayout-green.svg?style=flat)](https://android-arsenal.com/details/1/2666)

Swipe up or down to close view or activity or anything. See [example](https://github.com/SerhatSurguvec/SwipableLayout/tree/master/app/src/main).

![](https://github.com/SerhatSurguvec/SwipableLayout/blob/master/screenshot.gif)


Download
-------
You can also depend on the library through Maven:

```xml
<dependency>
    <groupId>com.serhatsurguvec.libraries</groupId>
    <artifactId>swipablelayout</artifactId>
    <version>0.0.1</version>
</dependency>
```
or Gradle:

```groovy
dependencies {
    compile 'com.serhatsurguvec.libraries:swipablelayout:0.0.1'
}
```
How to use
-------

```xml
  <?xml version="1.0" encoding="utf-8"?>
  <com.serhatsurguvec.swipablelayout.SwipeableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/swipableLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/darker_gray"
    android:orientation="vertical">

    <!--Add your child views here-->

  </com.serhatsurguvec.swipablelayout.SwipeableLayout>
```

After add these lines to your swiping activity theme , then enjoy!!

```xml
      <item name="windowNoTitle">true</item>
      <item name="android:windowIsTranslucent">true</item>
      <item name="android:windowBackground">@android:color/transparent</item>
      <item name="android:windowNoTitle">true</item>
      <item name="windowActionBar">false</item>
      <item name="android:windowAnimationStyle">@null</item>
```

Add layout close listener to do an action/actions
```java
        SwipeableLayout swipeableLayout = (SwipeableLayout) findViewById(R.id.swipableLayout);
        swipeableLayout.setOnLayoutCloseListener(new SwipeableLayout.OnLayoutCloseListener() {
            @Override
            public void OnLayoutClosed() {
                //TODO finish or do smth
            }
        });
```

License
-------

    Copyright 2015 Serhat Sürgüveç.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
