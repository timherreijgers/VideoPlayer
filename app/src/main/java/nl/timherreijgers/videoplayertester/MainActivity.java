package nl.timherreijgers.videoplayertester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import nl.timherreijgers.videoplayer.VideoPlayer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoPlayer videoPlayer = findViewById(R.id.videoPlayer);
        try {
            videoPlayer.playVideo("http://vjs.zencdn.net/v/oceans.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
