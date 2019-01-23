package nl.timherreijgers.videoplayer;

import android.content.Context;
import android.os.Build;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

class VideoControlView extends ConstraintLayout implements View.OnClickListener {

    private ImageButton playButton;
    private ImageButton backButton;
    private AppCompatSeekBar progressBar;
    private TextView currentTimeTextView;
    private TextView totalTimeTextView;

    private OnControlInteractedListener listener;
    private AnimatedVectorDrawableCompat playToPause;
    private AnimatedVectorDrawableCompat pauseToPlay;

    private boolean playing;
    private int totalTime;

    public VideoControlView(Context context) {
        this(context, null);
    }

    public VideoControlView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public VideoControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.video_control_view, this);

        progressBar = findViewById(R.id.seekBar);
        progressBar.setProgress(35);

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(this);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        currentTimeTextView = findViewById(R.id.currentTime);
        totalTimeTextView = findViewById(R.id.totalTime);

        playToPause = AnimatedVectorDrawableCompat.create(context, R.drawable.play_to_pause);
        pauseToPlay = AnimatedVectorDrawableCompat.create(context, R.drawable.pause_to_play);

        playing = true;
        totalTime = 0;

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int time = (int) (totalTime / 100d * seekBar.getProgress());
                if(listener != null)
                    listener.onTimeChanged(time);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == playButton.getId()) {
            playing = !playing;

            updatePlayButton();
            if (listener != null)
                listener.onPauseButtonClicked();
        }
        else if(view.getId() == backButton.getId()){
            if (listener != null)
                listener.onBackButtonClicked();
        }
    }

    public void pause(){
        playing = false;
        updatePlayButton();
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

    private String parseTime(int time){
        int minutes = time / 60;
        int seconds = time % 60;
        String result = "";

        if(minutes < 10)
            result += Integer.toString(minutes);
        result += minutes + ":";

        if(seconds < 10)
            result += 0;
        result += Integer.toString(seconds);

        return result;
    }

    public void setTotalTime(int totalTime){
        this.totalTime = totalTime;
        totalTimeTextView.setText(parseTime(totalTime));
    }

    public void setCurrentTime(int time){
        currentTimeTextView.setText(parseTime(time));
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            progressBar.setProgress((int) (((double) time / (double) totalTime) * 100));
        else
            progressBar.setProgress((int) (((double) time / (double) totalTime) * 100), true);
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
        void onBackButtonClicked();
        void onTimeChanged(int time);
    }
}
