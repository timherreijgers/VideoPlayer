package nl.timherreijgers.videoplayer;

import android.os.Handler;

public class VideoTime {

    private int time;
    private Handler handler;
    public OnTimeChangedListener listener;

    public void start(){
        time = 0;
        startHandler();
    }

    private void startHandler() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                time++;
                if(hasOnChangedTimeListener())
                    listener.OnTimeChanged(time);
            }
        }, 1000);
    }

    public void stop(){
        handler = null;
        time = 0;
    }

    public void pause(){
        stop();
    }

    public void resume(){
        startHandler();
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
