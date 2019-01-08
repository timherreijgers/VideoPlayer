package nl.timherreijgers.videoplayertester;

import androidx.appcompat.app.AppCompatActivity;
import nl.timherreijgers.videoplayer.VideoPlayerFragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, videoPlayerFragment).commit();
        videoPlayerFragment.setVideoPath("https://vjs.zencdn.net/v/oceans.mp4");
    }
}
