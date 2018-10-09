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

public class VideoPlayer extends RelativeLayout implements SurfaceHolder.Callback, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener {

    private static final String TAG = VideoPlayer.class.getSimpleName();

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private boolean isVideoSizeKnown = false;
    private boolean isVideoReadyToBePlayed = false;
    private boolean hasActiveHolder;
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

        timerTask = new TimerTask();
        surfaceView = findViewById(R.id.surfaceView);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
        Log.d(TAG, "VideoPlayer: Reloaded the whole view!!!!!!");
    }

    public void playVideo(String path) throws IOException{
        Log.d(TAG, "playVideo() called with: path = [" + path + "]");
        final VideoPlayer player = this;

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(path);
        mediaPlayer.setOnBufferingUpdateListener(player);
        mediaPlayer.setOnCompletionListener(player);
        mediaPlayer.setOnPreparedListener(player);
        mediaPlayer.setOnVideoSizeChangedListener(player);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                while(!hasActiveHolder);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    mediaPlayer.setDisplay(surfaceHolder);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.execute();

    }

    private void startVideoPlayback() {
        Log.d(TAG, "startVideoPlayback() called");
        surfaceHolder.setFixedSize(videoWidth, videoHeight);
        mediaPlayer.start();
        timerTask.start();
        playing = true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated() called with: surfaceHolder = [" + surfaceHolder + "]");
        hasActiveHolder = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d(TAG, "surfaceChanged() called with: surfaceHolder = [" + surfaceHolder + "], i = [" + i + "], i1 = [" + i1 + "], i2 = [" + i2 + "]");
        hasActiveHolder = false;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceDestroyed() called with: surfaceHolder = [" + surfaceHolder + "]");
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        Log.d(TAG, "onBufferingUpdate() called with: mediaPlayer = [" + mediaPlayer + "], percent = [" + percent + "]");
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onCompletion() called with: mediaPlayer = [" + mediaPlayer + "]");
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPrepared() called with: mediaPlayer = [" + mediaPlayer + "]");
        isVideoReadyToBePlayed = true;
        if(isVideoSizeKnown)
            startVideoPlayback();
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
        Log.d(TAG, "onVideoSizeChanged() called with: mediaPlayer = [" + mediaPlayer + "], width = [" + width + "], height = [" + height + "]");
        if (width == 0 || height == 0) {
            Log.e(TAG, "invalid video width(" + width + ") or height(" + height
                    + ")");
            return;
        }
        videoHeight = height;
        videoWidth = width;

        isVideoSizeKnown = true;
        if(isVideoReadyToBePlayed)
            startVideoPlayback();
    }

    public boolean isPlaying() {
        return playing;
    }

    public int getTime(){
        return timerTask.getTime();
    }
}
