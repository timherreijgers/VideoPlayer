package nl.timherreijgers.videoplayer;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class VideoPlayerFragment extends Fragment implements Animation.AnimationListener {

    private static String TAG = "VIDEO_PLAYER";

    private VideoPlayerFragmentViewModel viewModel;
    private SurfaceView surfaceView;
    private VideoControlView videoControlView;
    private Animation videoViewAnimation;
    private String tempVideoPath;

    public VideoPlayerFragment() {
        Log.d(TAG, "VideoPlayerFragment() called");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        View view = inflater.inflate(R.layout.fragment_video_player, container, false);
        surfaceView = view.findViewById(R.id.surface_view);
        videoControlView = view.findViewById(R.id.video_control_view);
        viewModel = ViewModelProviders.of(this).get(VideoPlayerFragmentViewModel.class);
        videoControlView.setListener(viewModel);
        if(tempVideoPath != null) {
            viewModel.setVideoPath(tempVideoPath);
            tempVideoPath = null;
        }

        viewModel.getPlaying().observe(this, videoControlView::setPlaying);
        viewModel.getTotalDuration().observe(this, videoControlView::setTotalTime);
        viewModel.getCurrentTime().observe(this, videoControlView::setCurrentTime);
        viewModel.getBuffering().observe(this, videoControlView::setBuffering);

        surfaceView.getHolder().addCallback(viewModel);
        surfaceView.setOnClickListener(e -> {
            if(videoViewAnimation == null) {
                videoControlView.setVisibility(View.VISIBLE);
                videoViewAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                videoControlView.startAnimation(videoViewAnimation);
            }

            if(videoViewAnimation != null && !videoViewAnimation.hasEnded()) {
                videoViewAnimation.cancel();
                toggleControlViewVisibility();
            }

            if(videoControlView.getVisibility() == VISIBLE) {
                videoViewAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
                videoViewAnimation.setAnimationListener(this);
                videoControlView.startAnimation(videoViewAnimation);
            }else {
                videoViewAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                videoViewAnimation.setAnimationListener(this);
                videoControlView.startAnimation(videoViewAnimation);
            }
        });
        return view;
    }

    private void toggleControlViewVisibility(){
        if(videoControlView.getVisibility() == VISIBLE)
            videoControlView.setVisibility(INVISIBLE);
        else
            videoControlView.setVisibility(VISIBLE);
    }

    public void setVideoPath(String videoPath) {
        if(viewModel == null){
            tempVideoPath = videoPath;
        } else {
            viewModel.setVideoPath(videoPath);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        toggleControlViewVisibility();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void setOnBackButtonPressedListener(OnBackButtonPressedListener onBackButtonPressedListener) {
        viewModel.setOnBackButtonPressedListener(onBackButtonPressedListener);
    }

    public boolean hasOnBackButtonPressedListener() {
        return  viewModel.hasOnBackButtonPressedListener();
    }

    public interface OnBackButtonPressedListener {
        void onBackButtonPressed();
    }
}
