*Read this in other languages: [English](README.md), [中文](README.zh-tw.md).*

# WeekCalendar
A custom calendar for the week.

<div style="dispaly:flex">
    <img src="https://user-images.githubusercontent.com/25738593/139564182-d602a009-33e3-4d27-b4a6-4269b1ed6baf.gif" width="32%">
</div>

## Supported Android Versions
- Android 4.0 Jelly Bean(API level 16) or higher.

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

## Usage
Define ClockCounter on your xml.
```xml
<com.a1573595.weekcalendar.WeekCalendar
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="32dp"
    app:wc_borderRes="@drawable/star"
    app:wc_focusedTextColor="@android:color/holo_red_light"
    app:wc_textColor="@android:color/holo_red_light" />
```

Set listener.
```kotlin
binding.weekCalendar.setOnSelectedListener {
	...
}
```

## Attribute
| Attribute | Type | Default | Description |
| :------| :------ | :------ | :------ |
| wc_startTimeSeconds | integer | System.currentTimeMillis() / 1000 | start time |
| wc_borderRes | reference | R.drawable.oval_gray | border icon resource |
| wc_textColor | color | #000000 | text color |
| wc_focusedTextColor | color | #ffffff | focused text color |
| wc_isScrollable | boolean | true | scrollable |
| wc_isItemTouchable | boolean | true | item touchable |