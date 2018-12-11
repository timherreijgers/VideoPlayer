package nl.timherreijgers.videoplayertester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import nl.timherreijgers.videoplayer.VideoPlayerFragment;

public class MainActivity extends AppCompatActivity implements VideoPlayerFragment.OnBackButtonPressedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoPlayerFragment videoPlayer = (VideoPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_video_player);

        if(videoPlayer != null) {
            try {
                videoPlayer.setOnBackButtonPressedListener(this);
                videoPlayer.playVideo("http://vjs.zencdn.net/v/oceans.mp4");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackButtonPressed() {
        Log.d("MAIN_ACTIVITY", "onBackButtonPressed: The back button has been pressed!");
    }
}
