package nl.timherreijgers.videoplayertester;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        player = findViewById(R.id.videoPlayer);
        try {
            player.playVideo("http://vjs.zencdn.net/v/oceans.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        new AsyncTask<Void, Void, Void>(){
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                while(!player.isPlaying());
//                while(true){
//                    try{
//                        Thread.sleep(1000);
//                        Log.d(VideoPlayer.class.getSimpleName(), "run: time:" + player.getTime());
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.execute();
    }
}
