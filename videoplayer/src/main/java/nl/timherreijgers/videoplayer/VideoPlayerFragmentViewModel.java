package nl.timherreijgers.videoplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

import androidx.lifecycle.ViewModel;

public class VideoPlayerFragmentViewModel extends ViewModel implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    private static String TAG = "VIDEO_PLAYER";

    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private int videoWidth;
    private int videoHeight;
    private boolean surfaceHolderPrepared = false;
    private String videoPath;

    public VideoPlayerFragmentViewModel() {
        Log.d(TAG, "VideoPlayerFragmentViewModel() called");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setScreenOnWhilePlaying(true);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Log.d(TAG, "onError() called with: mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]");
            return false;
        });
        mediaPlayer.setOnBufferingUpdateListener((mp, percent) -> Log.d(TAG, "onBufferingUpdate() called with: mp = [" + mp + "], percent = [" + percent + "]"));
    }

    private void initializeMediaPlayer() {
        Log.d(TAG, "initializeMediaPlayer() called");
        try {
            mediaPlayer.setDataSource(videoPath);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startVideoPlayback();
    }

    private void startVideoPlayback() {
        Log.d(TAG, "startVideoPlayback() called");
        if(videoPath == null || surfaceHolder == null || videoHeight == 0 || videoWidth == 0 || mediaPlayer == null)
            return;

        Log.d(TAG, "startVideoPlayback: Video playback started!");
        mediaPlayer.setDisplay(surfaceHolder);
        surfaceHolder.setFixedSize(videoWidth, videoHeight);
        mediaPlayer.start();
    }

    public void setVideoPath(String path) {
        this.videoPath = path;
        initializeMediaPlayer();
    }

    public void onPause() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated() called with: holder = [" + holder + "]");
        surfaceHolder = holder;
        startVideoPlayback();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed() called with: holder = [" + holder + "]");
        //TODO: dispose of mediaplayer
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        videoWidth = mp.getVideoWidth();
        videoHeight = mp.getVideoHeight();
        startVideoPlayback();
    }
}
