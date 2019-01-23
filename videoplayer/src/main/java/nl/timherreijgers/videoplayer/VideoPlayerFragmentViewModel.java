package nl.timherreijgers.videoplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VideoPlayerFragmentViewModel extends ViewModel implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControlView.OnControlInteractedListener {

    private static String TAG = "VIDEO_PLAYER";

    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private int videoWidth;
    private int videoHeight;
    private String videoPath;
    private VideoPlayerFragment.OnBackButtonPressedListener onBackButtonPressedListener;

    private MutableLiveData<Boolean> playing;
    private MutableLiveData<Integer> totalDuration;
    private MutableLiveData<Integer> currentTime;
    private MutableLiveData<Boolean> buffering;

    public VideoPlayerFragmentViewModel() {
        Log.d(TAG, "VideoPlayerFragmentViewModel() called");
        playing = new MutableLiveData<>();
        totalDuration = new MutableLiveData<>();
        currentTime = new MutableLiveData<>();
        buffering = new MutableLiveData<>();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setScreenOnWhilePlaying(true);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Log.d(TAG, "onError() called with: mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]");
            return false;
        });
        mediaPlayer.setOnBufferingUpdateListener((mp, percent) -> Log.d(TAG, "onBufferingUpdate() called with: mp = [" + mp + "], percent = [" + percent + "]"));
        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "onInfo() called with: mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]");
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        buffering.setValue(true);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        buffering.setValue(false);
                        break;
                }
                return false;
            }
        });

        Thread timeThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                        if (mediaPlayer.isPlaying())
                            ((MutableLiveData<Integer>) currentTime).postValue(mediaPlayer.getCurrentPosition() / 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();
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
        playing.postValue(false);
        totalDuration.postValue(mediaPlayer.getDuration() / 1000);
    }

    void setVideoPath(String path) {
        this.videoPath = path;
        initializeMediaPlayer();
    }

    void onPause() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    void setOnBackButtonPressedListener(VideoPlayerFragment.OnBackButtonPressedListener onBackButtonPressedListener) {
        this.onBackButtonPressedListener = onBackButtonPressedListener;
    }

    boolean hasOnBackButtonPressedListener() {
        return onBackButtonPressedListener != null;
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

    @Override
    public void onPauseButtonClicked() {
        if(videoPath == null || surfaceHolder == null || videoHeight == 0 || videoWidth == 0)
            return;

        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();

        ((MutableLiveData<Boolean>) playing).postValue(!mediaPlayer.isPlaying());
    }

    @Override
    public void onBackButtonClicked() {
        if(hasOnBackButtonPressedListener())
            onBackButtonPressedListener.onBackButtonPressed();
    }

    @Override
    public void onTimeChanged(int time) {
        mediaPlayer.seekTo(time * 1000);
    }

    public LiveData<Boolean> getPlaying() {
        return playing;
    }

    public LiveData<Integer> getTotalDuration() {
        return totalDuration;
    }

    public LiveData<Integer> getCurrentTime() {
        return currentTime;
    }

    public LiveData<Boolean> getBuffering() {
        return buffering;
    }
}
