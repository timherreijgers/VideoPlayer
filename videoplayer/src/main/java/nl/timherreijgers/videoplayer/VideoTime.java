package nl.timherreijgers.videoplayer;

import android.os.Handler;

public class VideoTime {

    private int time;
    private Handler handler;
    private OnTimeChangedListener listener;
    private boolean paused;
    private long lastMillis = -1;
    private long millisSinceLastTick = -1;

    public VideoTime(){
        handler = new Handler();
        paused = false;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(paused) return;
            lastMillis = System.currentTimeMillis();
            time++;
            if(hasOnChangedTimeListener())
                listener.OnTimeChanged(time);
            handler.postDelayed(runnable, 1000);
        }
    };

    public void start(){
        time = 0;
        runnable.run();
    }

    public void stop(){
        handler.removeCallbacks(runnable);
        handler = null;
        time = 0;
    }

    public void pause(){
        if(lastMillis != -1)
            millisSinceLastTick = System.currentTimeMillis() - lastMillis;
        handler.removeCallbacks(runnable);
    }

    public void resume(){
        if(millisSinceLastTick != -1 && millisSinceLastTick < 1000) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }, 1000 - millisSinceLastTick);
            lastMillis = -1;
            millisSinceLastTick = -1;
        }else
            runnable.run();
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
