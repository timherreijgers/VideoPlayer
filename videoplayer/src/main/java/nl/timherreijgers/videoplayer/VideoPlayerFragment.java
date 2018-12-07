package nl.timherreijgers.videoplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

//TODO: Add things like back button enabled, subtitles enabled, automated repeat, etc.
public class VideoPlayerFragment extends Fragment {

    private static final String TAG = "VIDEO_PLAYER_FRAGMENT";
    private VideoPlayer videoView;
    
    private boolean autoStartPlayWhenBackInFocus = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        videoView = new VideoPlayer(inflater.getContext());
        return videoView;
    }

    @Override
    public void onPause() {
        videoView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(autoStartPlayWhenBackInFocus)
            videoView.pause();
    }

    public void playVideo(String path) throws IOException {
        videoView.playVideo(path);
    }

    public boolean isPlaying() {
        return videoView.isPlaying();
    }

    public boolean getAutoStartPlayWhenBackInFocus() {
        return autoStartPlayWhenBackInFocus;
    }

    public void setAutoStartPlayWhenBackInFocus(boolean autoStartPlayWhenBackInFocus) {
        this.autoStartPlayWhenBackInFocus = autoStartPlayWhenBackInFocus;
    }
}
