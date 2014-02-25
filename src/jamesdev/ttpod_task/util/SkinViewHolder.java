package jamesdev.ttpod_task.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;

public class SkinViewHolder {
    public ImageView imageView;
    public ProgressBar progressBar;
    public String skinUrl;
    public String thumbName;
    public String thumbFileName;
    public boolean isEmbeded;
    public boolean isLast;

    public int position;

    private Context mContext;

    private static final String TAG = "SkinViewHolder";

    public SkinViewHolder(Context context, int p) {
        mContext = context;
        position = p;
        Log.d(TAG, "create new one, position:" + position);
    }

    public void doResponse() {
        if (this.isEmbeded) {
            setSkin();
        } else if (this.isLast ) {
            downloadMore();;
        } else {
            startSkinDownload(mContext);
        }
    }

    private void startSkinDownload(Context context) {
        DownloadTask downloadTask = new DownloadTask(mContext, this);
        downloadTask.execute();
    }

    private void setSkin() {
        Log.i(TAG, "setSkin: " + thumbName);
    }

    private void downloadMore() {
        Log.d(TAG, "download more skins");
    }

    public void loadThumb(String thumbName) {
        this.thumbName = thumbName;

        if (this.isEmbeded || this.isLast || StorageHelper.getInstance().isSkinExist(thumbName)) {
            this.progressBar.setVisibility(View.GONE);
        } else {
            this.progressBar.setVisibility(View.VISIBLE);
        }

        this.thumbFileName = thumbName + Constants.SkinJSON.IMAGE_FORMAT;
        String imagePath = Constants.SkinJSON.PREVIEW_DIR + this.thumbFileName;

        Log.d(TAG, "position: " + position + " new thumbName: " + thumbName);

        try {
            InputStream ims = mContext.getAssets().open(imagePath);
            Drawable drawable = Drawable.createFromStream(ims, null);
            this.imageView.setImageDrawable(drawable);
        } catch (IOException ex) {
            Log.e(TAG, "getView error:" + position);
            ex.printStackTrace();
        }
    }
}