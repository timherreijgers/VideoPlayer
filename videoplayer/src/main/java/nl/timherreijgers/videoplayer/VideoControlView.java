package nl.timherreijgers.videoplayer;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

class VideoControlView extends ConstraintLayout implements View.OnClickListener {

    private ImageButton playButton;
    private OnControlInteractedListener listener;
    private boolean playing;

    public VideoControlView(Context context) {
        this(context, null);
    }

    public VideoControlView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public VideoControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.video_control_view, this);
        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(this);
        playing = true;
    }

    @Override
    public void onClick(View view) {
        playing = !playing;
        updatePlayButton();
        if(listener != null)
            listener.onPauseButtonClicked();
    }

    private void updatePlayButton() {
        if(playing)
            playButton.setImageResource(R.drawable.play_arrow);
        else
            playButton.setImageResource(R.drawable.pause_buton);
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
