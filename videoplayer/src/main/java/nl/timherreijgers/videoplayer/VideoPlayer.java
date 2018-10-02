package nl.timherreijgers.videoplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import java.io.IOException;

public class VideoPlayer extends RelativeLayout {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;

    public VideoPlayer(Context context) {
        this(context, null);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.video_view, this);
        surfaceView = findViewById(R.id.surfaceView);

        mediaPlayer = new MediaPlayer();
    }

    public void playVideo(String path) throws IOException {
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mediaPlayer.setDataSource(getContext(), Uri.parse(path));
        mediaPlayer.setDisplay(surfaceHolder);
        mediaPlayer.start();
    }
}
