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

import java.io.FileDescriptor;
import java.io.IOException;

public class VideoPlayer extends RelativeLayout implements MediaPlayer.OnPreparedListener,
        SurfaceHolder.Callback, VideoControlView.OnControlInteractedListener,
        Animation.AnimationListener {

    private static final String TAG = VideoPlayer.class.getSimpleName();

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private VideoControlView videoControlView;
    private MediaPlayer mediaPlayer;

    private boolean sourceHasBeenSet = false;
    private boolean playing = false;

    private int videoWidth;
    private int videoHeight;

    private Thread timeThread;
    private Animation videoViewAnimation;

    public VideoPlayer(Context context) {
        this(context, null);
    }

    public VideoPlayer(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.video_view, this);
        mediaPlayer = new MediaPlayer();

        videoControlView = findViewById(R.id.videoControlView);
        videoControlView.setListener(this);

        surfaceView = findViewById(R.id.surfaceView);
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

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        timeThread = new Thread(() -> {
            while(true){
                try{
                    Thread.sleep(1000);
                    if(mediaPlayer == null)
                        return;

                    post(() -> videoControlView.setCurrentTime(mediaPlayer.getCurrentPosition() / 1000));
                    Log.d(TAG, "VideoPlayer: Thread ticked :D");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();
    }

    public void playVideo(String path) throws IOException{
        mediaPlayer.setOnPreparedListener(this);

        mediaPlayer.setDataSource(path);
        mediaPlayer.setScreenOnWhilePlaying(true);
        mediaPlayer.prepareAsync();

        sourceHasBeenSet = true;
    }

    private void startVideoPlayback() {
        if(!sourceHasBeenSet || surfaceHolder == null || videoHeight == 0 || videoWidth == 0)
            return;

        mediaPlayer.setDisplay(surfaceHolder);
        surfaceHolder.setFixedSize(videoWidth, videoHeight);
        mediaPlayer.start();
        playing = true;
        videoControlView.setPlaying(false);
        if(mediaPlayer.getDuration() != -1)
            videoControlView.setTotalTime(mediaPlayer.getDuration() / 1000);
    }

    public boolean isPlaying() {
        return playing;
    }

    public int getTime(){
        return mediaPlayer.getCurrentPosition() / 1000;                //
    }

    public void stop() {
        mediaPlayer.stop();
    }

    private void toggleControlViewVisibility(){
        if(videoControlView.getVisibility() == VISIBLE)
            videoControlView.setVisibility(INVISIBLE);
        else
            videoControlView.setVisibility(VISIBLE);
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
        mediaPlayer.stop();
        mediaPlayer = null;
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
