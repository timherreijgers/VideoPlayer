package nl.timherreijgers.videoplayer;

import android.util.Log;

public class TimerTask extends Thread {

    private static final String TAG = VideoPlayer.class.getSimpleName();

    private int time;
    private boolean running;

    public TimerTask(){
        running = true;
    }

    @Override
    public void run() {
        while(running) {
            try {
                Thread.sleep(1000);
                time+=1000;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        Log.d(TAG, "start: TimerTask state: " + getState());
    }

    public void stopTimer(){
        running = false;
    }

    public void reset() {
        time = 0;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }
}
