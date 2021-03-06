package jamesdev.ttpod_task.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import jamesdev.ttpod_task.Task.DownloadTask;
import jamesdev.ttpod_task.util.Constants;
import jamesdev.ttpod_task.util.FileHelper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Benpeng.Jiang
 */
public class SkinViewHolder {
    private String mThumbName;
    private Context mContext;
    private BaseAdapter mBaseAdapter;
    private static final String TAG = "SkinViewHolder";

    public ImageView imageView;
    public ProgressBar progressBar;
    public ImageView deleteImageView;

    public String skinUrl;

    public boolean isEmbeded;
    public boolean isLast;
    public int position;


    /**
     * Constructor
     * @param context Activity whitch creates adapter
     * @param baseAdapter Adapter whitch holds this object
     * @param p position in adapter
     */
    public SkinViewHolder(Context context, BaseAdapter baseAdapter, int p) {
        mContext = context;
        mBaseAdapter =baseAdapter;
        position = p;
        Log.d(TAG, "create new one, position:" + position);
    }

    // called when clicked in view mode
    public void doResponse() {
        if (this.isEmbeded || FileHelper.isSkinExist(this.mThumbName)) {
            setSkin();
        } else if (this.isLast) {
            downloadMore();
        } else {
            startSkinDownload();
        }
    }

    // called when clicked in edit mode
    public void doEdit() {
        Log.d(TAG, "edit skin");
        if (!this.isEmbeded && FileHelper.deleteSkin(this.mThumbName)) {
            Log.d(TAG, "delete file success");
            mBaseAdapter.notifyDataSetChanged();
        } else {
            Log.d(TAG, "cannot delete file");
        }
    }

    private void startSkinDownload() {
        DownloadTask downloadTask = new DownloadTask(mContext, this);
        downloadTask.execute();
    }

    private void setSkin() {
        Log.i(TAG, "setSkin: " + mThumbName);
    }

    private void downloadMore() {
        Log.d(TAG, "download more skins");
    }

    /**
     * load preview image of skin
     * @param thumbName skin name
     * throws IOException
     */
    public void loadThumb(String thumbName) {
        this.mThumbName = thumbName;

        if (this.isEmbeded || this.isLast || FileHelper.isSkinExist(mThumbName)) {
            this.progressBar.setVisibility(View.GONE);
        } else {
            this.progressBar.setProgress(0);
            this.progressBar.setVisibility(View.VISIBLE);
        }

        String imagePath = Constants.SkinJSON.PREVIEW_DIR + mThumbName + Constants.SkinJSON.IMAGE_FORMAT;

        Log.d(TAG, "position: " + position + " new thumbName: " + mThumbName);

        try {
            InputStream ims = mContext.getAssets().open(imagePath);
            Drawable drawable = Drawable.createFromStream(ims, null);
            this.imageView.setImageDrawable(drawable);
        } catch (IOException ex) {
            Log.e(TAG, "getView error:" + position);
            ex.printStackTrace();
        }
    }

    /**
     * show or hide delete icon
     * @param thumbName
     */
    public void setDeleteImageView(String thumbName) {
        mThumbName = thumbName;
        if (FileHelper.isSkinExist(mThumbName)) {
            deleteImageView.setVisibility(View.VISIBLE);
        } else {
            deleteImageView.setVisibility(View.GONE);
        }
    }

    /**
     * return skin name
     * @return skin name
     */
    public String getThumbName() {
        return mThumbName;
    }
}