package nl.timherreijgers.videoplayertester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

import nl.timherreijgers.videoplayer.VideoPlayer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    
    private VideoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        player = findViewById(R.id.videoPlayer);
        try {
            player.playVideo("http://vjs.zencdn.net/v/oceans.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //player.stop();
    }
}
