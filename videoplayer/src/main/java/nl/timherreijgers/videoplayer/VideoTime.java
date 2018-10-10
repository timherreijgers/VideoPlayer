package nl.timherreijgers.videoplayer;

import android.os.Handler;
import android.widget.VideoView;

public class VideoTime {

    private int time;
    private Handler handler;
    public OnTimeChangedListener listener;

    public VideoTime(){
        time = 0;
        handler = new Handler();
    }

    public void start(){

    }

    public void stop(){

    }

    public void setTime(int seconds){
        time = seconds;
    }

    public int getTime(int time){
        return time;
    }

    public void setListener(OnTimeChangedListener listener) {
        this.listener = listener;
    }

    public boolean hasOnChangedTimeListener(){
        return listener != null;
    }

    public interface OnTimeChangedListener{
        void OnTimeChanged(int seconds);
    }
}
