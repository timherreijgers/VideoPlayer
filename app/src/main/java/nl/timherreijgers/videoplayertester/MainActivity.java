package nl.timherreijgers.videoplayertester;

import androidx.appcompat.app.AppCompatActivity;
import nl.timherreijgers.videoplayer.VideoPlayerFragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new VideoPlayerFragment()).commit();
//        VideoPlayerFragment videoPlayer = (VideoPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_video_player);
//
//        if(videoPlayer != null) {
//            try {
//                videoPlayer.setOnBackButtonPressedListener(this);
//                videoPlayer.playVideo("http://vjs.zencdn.net/v/oceans.mp4");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
