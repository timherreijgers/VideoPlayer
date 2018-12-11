# VideoPlayer [![](https://jitpack.io/v/timtim3001/VideoPlayer.svg)](https://jitpack.io/#timtim3001/VideoPlayer)
VideoPlayer is a videoplayer view for Android. The view has a simular look and feel to the youtube player for Android.

# Installation
First add the jitpack repository to your root build.gradle file. 
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Second add the dependency to your project build.gradle file where ${Version} is the latest version from the releases tab or the version in the jitpack badge at the top of this readme. 
```gradle
dependencies {
    implementation 'com.github.timtim3001:VideoPlayer:${Version}'
}
```

# How to use
It's really easy to use this library. All you have to do is include the VideoPlayerFragment in your xml file like: 
```xml
<fragment
        class="nl.timherreijgers.videoplayer.VideoPlayerFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/fragment_video_player" />
```

Once you have added this to your xml file all that is left is to pass in the video you want to play in your main Activity like this:
```java
VideoPlayerFragment videoPlayer = (VideoPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_video_player);
if(videoPlayer != null) {
    try {
        videoPlayer.playVideo("Your video link here");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

If you want to use the integrated back button you have to add the following line: 
```java
videoPlayer.setBackButtonPressedListener(() -> doSomething());
```

# Features
- Playing video from url
- Integrated pause button
- Integrated back button
- Integrated non-scrollable progress bar

# Future features
- Scroll through video
- customizable colors

# Known bugs
No known bugs yet :D
