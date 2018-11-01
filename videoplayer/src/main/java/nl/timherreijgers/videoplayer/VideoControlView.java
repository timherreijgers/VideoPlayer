package nl.timherreijgers.videoplayer;

import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.AnimationUtilsCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

class VideoControlView extends ConstraintLayout implements View.OnClickListener {

    private ImageButton playButton;
    private ImageButton backButton;
    private ProgressBar progressBar;
    private OnControlInteractedListener listener;
    private boolean playing;
    private AnimatedVectorDrawableCompat playToPause;
    private AnimatedVectorDrawableCompat pauseToPlay;

    public VideoControlView(Context context) {
        this(context, null);
    }

    public VideoControlView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public VideoControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.video_control_view, this);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(35);

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(this);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        playing = true;
        playToPause = AnimatedVectorDrawableCompat.create(context, R.drawable.play_to_pause);
        pauseToPlay = AnimatedVectorDrawableCompat.create(context, R.drawable.pause_to_play);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == playButton.getId()) {
            playing = !playing;
            //updatePlayButton();
            if (listener != null)
                listener.onPauseButtonClicked();
        }
        else if(view.getId() == backButton.getId()){
            view.animate();
        }
    }

    private void updatePlayButton() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            updatePlayButtonNonAnimated();
        else
            updatePlayButtonAnimated();
    }

    private void updatePlayButtonNonAnimated(){
        if(playing)
            playButton.setImageResource(R.drawable.play_arrow);
        else
            playButton.setImageResource(R.drawable.pause_buton);
    }

    private void updatePlayButtonAnimated(){
        if(playing) {
            playButton.setImageDrawable(pauseToPlay);
            pauseToPlay.start();
        }else {
            playButton.setImageDrawable(playToPause);
            playToPause.start();
        }
    }

    public void setListener(OnControlInteractedListener listener) {
        this.listener = listener;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
        updatePlayButton();
    }

    public interface OnControlInteractedListener {
        void onPauseButtonClicked();
    }
}
