package nl.timherreijgers.videoplayertester;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import nl.timherreijgers.videoplayer.VideoPlayerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoPlayerFragment videoPlayer = (VideoPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_video_player);

        try {
            videoPlayer.playVideo("http://vjs.zencdn.net/v/oceans.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
