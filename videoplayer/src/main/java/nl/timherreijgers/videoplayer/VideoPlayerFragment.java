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
public class VideoPlayerFragment extends Fragment implements VideoPlayer.OnBackButtonPressedListener {

    private static final String TAG = "VIDEO_PLAYER_FRAGMENT";
    private VideoPlayer videoPlayer;

    private OnBackButtonPressedListener onBackButtonPressedListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        videoPlayer = new VideoPlayer(inflater.getContext());
        videoPlayer.setOnBackButtonPressedListener(this);
        return videoPlayer;
    }

    public void playVideo(String path) throws IOException {
        videoPlayer.playVideo(path);
    }

    public boolean isPlaying() {
        return videoPlayer.isPlaying();
    }

    //region Listeners
    public void setOnBackButtonPressedListener(OnBackButtonPressedListener onBackButtonPressedListener) {
        this.onBackButtonPressedListener = onBackButtonPressedListener;
    }

    public boolean hasOnBackButtonPressedListener() {
        return onBackButtonPressedListener != null;
    }

    @Override
    public void onBackButtonPressed() {
        if(onBackButtonPressedListener != null)
            onBackButtonPressedListener.onBackButtonPressed();
    }

    public interface OnBackButtonPressedListener {
        void onBackButtonPressed();
    }
    //endregion
}
