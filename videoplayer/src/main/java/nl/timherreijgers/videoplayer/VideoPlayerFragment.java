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

public class VideoPlayerFragment extends Fragment {

    private static String TAG = "VIDEO_PLAYER";

    private VideoPlayerFragmentViewModel viewModel;
    private SurfaceView surfaceView;

    public VideoPlayerFragment() {
        Log.d(TAG, "VideoPlayerFragment() called");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        View view = inflater.inflate(R.layout.fragment_video_player, container, false);
        surfaceView = view.findViewById(R.id.surface_view);
        viewModel = ViewModelProviders.of(this).get(VideoPlayerFragmentViewModel.class);
        viewModel.setVideoPath("http://vjs.zencdn.net/v/oceans.mp4");

        surfaceView.getHolder().addCallback(viewModel);
        surfaceView.setOnClickListener(e -> {
//            TODO: Toggle the visibility for the videoControlView here
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.onPause();
    }
}
