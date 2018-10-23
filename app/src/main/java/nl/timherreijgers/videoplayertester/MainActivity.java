package nl.timherreijgers.videoplayertester;

import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

import nl.timherreijgers.videoplayer.VideoPlayer;

public class MainActivity extends AppCompatActivity {

    private VideoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        player = findViewById(R.id.videoPlayer);
//        try {
//            player.playVideo("http://vjs.zencdn.net/v/oceans.mp4");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
