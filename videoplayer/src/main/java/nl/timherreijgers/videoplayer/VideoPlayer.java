package nl.timherreijgers.videoplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import java.io.IOException;

public class VideoPlayer extends RelativeLayout implements MediaPlayer.OnPreparedListener,
        SurfaceHolder.Callback, VideoControlView.OnControlInteractedListener,
        Animation.AnimationListener {

    private static final String TAG = "VIDEO_PLAYER";

    private SurfaceHolder surfaceHolder;
    private VideoControlView videoControlView;
    private MediaPlayer mediaPlayer;

    private boolean sourceHasBeenSet = false;
    private boolean playing = false;
    private boolean restored = false;

    private int videoWidth;
    private int videoHeight;
    private String source;

    private Animation videoViewAnimation;

    public VideoPlayer(Context context) {
        this(context, null);
    }

    public VideoPlayer(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.video_view, this);

        videoControlView = findViewById(R.id.videoControlView);
        videoControlView.setListener(this);

        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);
        surfaceView.setOnClickListener((e)->{
            if(videoViewAnimation != null && !videoViewAnimation.hasEnded()) {
                videoViewAnimation.cancel();
                toggleControlViewVisibility();
            }

            if(videoControlView.getVisibility() == VISIBLE) {
                videoViewAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
                videoViewAnimation.setAnimationListener(this);
                videoControlView.startAnimation(videoViewAnimation);
            }else {
                videoViewAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                videoViewAnimation.setAnimationListener(this);
                videoControlView.startAnimation(videoViewAnimation);
            }
        });

        Thread timeThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    post(() -> {
                        if (mediaPlayer.isPlaying())
                            videoControlView.setCurrentTime(mediaPlayer.getCurrentPosition() / 1000);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();
    }

    public void playVideo(String path) throws IOException {
        source = path;
        initMediaPLayer();
    }

    private void initMediaPLayer() throws IOException{
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setScreenOnWhilePlaying(true);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener((mp, percent) -> Log.d(TAG, "onBufferingUpdate() called with: mp = [" + mp + "], percent = [" + percent + "]"));

        mediaPlayer.setDataSource(source);
        mediaPlayer.setScreenOnWhilePlaying(true);
        mediaPlayer.prepareAsync();

        sourceHasBeenSet = true;
    }

    private void startVideoPlayback() {
        if(!sourceHasBeenSet || surfaceHolder == null || videoHeight == 0 || videoWidth == 0 || mediaPlayer == null)
            return;

        Log.d(TAG, "startVideoPlayback: Video playback started!");
        mediaPlayer.setDisplay(surfaceHolder);
        surfaceHolder.setFixedSize(videoWidth, videoHeight);
        mediaPlayer.start();
        playing = true;
        videoControlView.setPlaying(false);
        int currentPosition = 0;
        if(restored)
            mediaPlayer.seekTo(currentPosition * 1000);
        if (mediaPlayer.getDuration() != -1)
            videoControlView.setTotalTime(mediaPlayer.getDuration() / 1000);
        restored = false;
    }

    public boolean isPlaying() {
        return playing;
    }

    public int getTime(){
        return mediaPlayer.getCurrentPosition() / 1000;
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
    }

    private void toggleControlViewVisibility(){
        if(videoControlView.getVisibility() == VISIBLE)
            videoControlView.setVisibility(INVISIBLE);
        else
            videoControlView.setVisibility(VISIBLE);
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
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

    }

    @Override
    public void onPauseButtonClicked() {
        if(!sourceHasBeenSet || surfaceHolder == null || videoHeight == 0 || videoWidth == 0)
            return;

        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();

        videoControlView.setPlaying(!mediaPlayer.isPlaying());
    }

    @Override
    public void onBackButtonClicked() {

    }

    @Override
    public void onTimeChanged(int time) {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        toggleControlViewVisibility();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
