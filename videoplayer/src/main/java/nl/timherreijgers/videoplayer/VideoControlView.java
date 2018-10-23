package nl.timherreijgers.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class VideoControlView extends RelativeLayout {

    public VideoControlView(Context context) {
        super(context);
    }

    public VideoControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.video_view, this);
    }

}
