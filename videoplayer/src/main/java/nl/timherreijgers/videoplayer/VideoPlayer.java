package nl.timherreijgers.videoplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaTimestamp;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;

public class VideoPlayer extends RelativeLayout implements MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {

    private static final String TAG = VideoPlayer.class.getSimpleName();

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;

    private boolean sourceHasBeenSet = false;

    private int videoWidth;
    private int videoHeight;
    private TimerTask timerTask;

    private boolean playing = false;

    public VideoPlayer(Context context) {
        this(context, null);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.video_view, this);
        mediaPlayer = new MediaPlayer();
        timerTask = new TimerTask();

        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void playVideo(String path) throws IOException{
        Log.d(TAG, "playVideo() called with: path = [" + path + "]");
        mediaPlayer.setOnPreparedListener(this);

        mediaPlayer.setDataSource(path);
        mediaPlayer.setScreenOnWhilePlaying(true);
        mediaPlayer.prepareAsync();

        sourceHasBeenSet = true;
    }

    private void startVideoPlayback() {
        Log.d(TAG, "startVideoPlayback() called with: sourceHasBeenSet = [" + sourceHasBeenSet + "], surfaceHolder = [" + surfaceHolder + "]");
        if(!sourceHasBeenSet || surfaceHolder == null || videoHeight == 0 || videoWidth == 0)
            return;

        mediaPlayer.setDisplay(surfaceHolder);
        surfaceHolder.setFixedSize(videoWidth, videoHeight);
        mediaPlayer.start();
        timerTask.execute();
        playing = true;
    }

    public boolean isPlaying() {
        return playing;
    }

    public int getTime(){
        return timerTask.getTime();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "onPrepared() called with: mp = [" + mp + "]");
        videoWidth = mp.getVideoWidth();
        videoHeight = mp.getVideoHeight();
        startVideoPlayback();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceHolder = holder;
        startVideoPlayback();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mediaPlayer.stop();
        mediaPlayer = null;
    }
}
