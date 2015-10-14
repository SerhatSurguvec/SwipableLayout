# SwipableLayout
Swipe up or down to close view or activity or anything.


Download

```groovy
repositories {
    maven {
        url 'https://dl.bintray.com/serhatsurguvec/maven/'
    }
}

dependencies {
    compile 'com.serhatsurguvec.libraries:swipablelayout:0.0.1'
}
```
How To Use </br>

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



Problems </br>

Please use lastest support sdk

License</br>

This project under Apache 2.0 License. Please see license.
