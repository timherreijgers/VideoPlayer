package nl.timherreijgers.videoplayer;

import android.os.AsyncTask;
import android.util.Log;

public class TimerTask extends AsyncTask<Void, Void, Void>{

    private static final String TAG = VideoPlayer.class.getSimpleName();

    private int time;
    private boolean running;

    public TimerTask() {
        running = true;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while(running) {
            try {
                Thread.sleep(1000);
                time+=1000;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void start(){

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
