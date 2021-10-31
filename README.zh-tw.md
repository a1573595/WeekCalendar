*其他語言版本: [English](README.md), [中文](README.zh-tw.md).*

# WeekCalendar
自定義的周曆。

<div style="dispaly:flex">
    <img src="https://user-images.githubusercontent.com/25738593/139564182-d602a009-33e3-4d27-b4a6-4269b1ed6baf.gif" width="32%">
</div>

## 支援Android版本
- Android 4.0 Jelly Bean(API level 16)或更高。

## Gradle
```groovy
allprojects {
    repositories {
    ...
    
    maven { url 'https://jitpack.io' }
    }
}
```

```groovy
dependencies {
    implementation 'com.github.a1573595:WeekCalendar:1.0.1'
}
```

## 用法
Define WeekCalendar on your xml.
```xml
<com.a1573595.weekcalendar.WeekCalendar
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="32dp"
    app:wc_borderRes="@drawable/star"
    app:wc_focusedTextColor="@android:color/holo_red_light"
    app:wc_textColor="@android:color/holo_red_light" />
```

設定監聽器。
```kotlin
binding.weekCalendar.setOnSelectedListener {
	...
}
```