package nl.timherreijgers.videoplayer;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

public class VideoControlView extends ConstraintLayout implements View.OnClickListener {

    private ImageButton playButton;
    private OnControlInteractedListener listener;

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
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onPauseButtonClicked();
    }

    public void setListener(OnControlInteractedListener listener) {
        this.listener = listener;
    }

    public interface OnControlInteractedListener {
        void onPauseButtonClicked();
    }
}
