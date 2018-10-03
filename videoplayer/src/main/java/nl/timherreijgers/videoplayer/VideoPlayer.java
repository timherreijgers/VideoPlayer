package nl.timherreijgers.videoplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaTimestamp;
import android.net.Uri;
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
    private int videoWidth;
    private int videoHeight;

    public VideoPlayer(Context context) {
        this(context, null);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.video_view, this);
        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void playVideo(){
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource("http://vjs.zencdn.net/v/oceans.mp4");
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.prepare();
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnVideoSizeChangedListener(this);
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void playVideo(String path) throws IOException {

    }

    private void startVideoPlayback() {
        Log.d(TAG, "startVideoPlayback() called");
        surfaceHolder.setFixedSize(videoWidth, videoHeight);
        mediaPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated() called with: surfaceHolder = [" + surfaceHolder + "]");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d(TAG, "surfaceChanged() called with: surfaceHolder = [" + surfaceHolder + "], i = [" + i + "], i1 = [" + i1 + "], i2 = [" + i2 + "]");
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
//            playVideo();
        }else {
            playVideo();
        }
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
        if (isVideoSizeKnown) {
            startVideoPlayback();
        }
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
        if (isVideoReadyToBePlayed) {
            startVideoPlayback();
        }
    }
}
