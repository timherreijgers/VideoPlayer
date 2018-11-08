package nl.timherreijgers.videoplayer;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class VideoViewParcelable extends View.BaseSavedState {

    private int progress;
    private int totalTime;
    private String source;

    public VideoViewParcelable(Parcelable superState) {
        super(superState);
    }

    public VideoViewParcelable(Parcel in) {
        super(in);
        progress = in.readInt();
        totalTime = in.readInt();
        source = in.readString();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(progress);
        out.writeLong(totalTime);
        out.writeString(source);
    }

    public static final Parcelable.Creator<VideoViewParcelable> CREATOR = new Parcelable.Creator<VideoViewParcelable>() {

        public VideoViewParcelable createFromParcel(Parcel in) {
            return new VideoViewParcelable(in);
        }

        public VideoViewParcelable[] newArray(int size) {
            return new VideoViewParcelable[size];
        }
    };
}
