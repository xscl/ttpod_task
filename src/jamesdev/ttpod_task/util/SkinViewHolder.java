package jamesdev.ttpod_task.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SkinViewHolder {
    public ImageView imageView;
    public ProgressBar progressBar;
    public String skinUrl;
    public String thumbName;
    public String thumbFileName;
    public boolean isEmbeded;
    public boolean isLast;

    private static final String TAG = "SkinViewHolder";

    public void doResponse(Context context) {
        if (this.isEmbeded) {
            setSkin();
        } else if (this.isLast ) {
            downloadMore();;
        } else {
            startSkinDownload(context);
        }
    }

    private void startSkinDownload(Context context) {
        DownloadTask downloadTask = new DownloadTask(context, this);
        downloadTask.execute();
    }

    private void setSkin() {
        Log.i(TAG, "setSkin: " + thumbName);
    }

    private void downloadMore() {
        Log.d(TAG, "download more skins");
    }
}