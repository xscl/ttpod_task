package jamesdev.ttpod_task.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import jamesdev.ttpod_task.activity.R;
import jamesdev.ttpod_task.util.AssetsHelper;
import jamesdev.ttpod_task.util.Constants;
import jamesdev.ttpod_task.util.GlobalMode;
import jamesdev.ttpod_task.util.SkinViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Bengpeng.Jiang.
 */
public class SkinItemAdapter extends BaseAdapter {
    private Context mContext;
    private  List<Map<String, String>> mSkinData;
    private int mEmbededNo;
    private String[] mEmbededFiles;

    private static final String TAG = "SkinItemAdapter";

    public SkinItemAdapter(Context context,  List<Map<String, String>> pSkinData) {
        mContext = context;
        mSkinData = pSkinData;
        mEmbededFiles = AssetsHelper.getInstance(mContext).getAssetFiles(Constants.SkinJSON.EMBEDED_SKIN_DIR);
        mEmbededNo = mEmbededFiles.length;
    }
    @Override
    public int getCount() {
        return mEmbededNo + mSkinData.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView in position:" + position);
        final SkinViewHolder holder;
        View view = convertView;

        if (convertView == null) {
            view = newView(R.layout.item_grid_image, parent, false);
            holder = new SkinViewHolder(mContext, this, position);
            view.setTag(holder);
        } else {
            holder = (SkinViewHolder)view.getTag();
        }

        bindView(position, view, holder);
        return view;
    }

    private View newView(int resource, ViewGroup parent, boolean attachToRoot) {
        View  view = ((Activity) mContext).getLayoutInflater().inflate(resource, parent, attachToRoot);
        return view;
    }

    private void bindView(int position, View view, SkinViewHolder holder) {
        assert view != null;

        holder.imageView = (ImageView) view.findViewById(R.id.image);
        holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
        holder.deleteImageView = (ImageView)view.findViewById(R.id.deleteImageView);

        String thumbName;

        if (position == this.getCount() - 1) {
            holder.isLast = true;
            thumbName = Constants.SkinJSON.DOWNLOAD_NAME;
            holder.progressBar.setVisibility(View.GONE);
            holder.deleteImageView.setVisibility(View.GONE);
        } else {
            holder.isLast = false;
            if (position < mEmbededNo) {
                holder.isEmbeded = true;
                thumbName = mEmbededFiles[position].split("\\.")[0];
                holder.deleteImageView.setVisibility(View.GONE);
            } else {
                holder.isEmbeded = false;
                Map<String, String> skinInfo = mSkinData.get(position - mEmbededNo);
                holder.skinUrl = skinInfo.get(Constants.SkinJSON.SKIN_URL);
                thumbName = skinInfo.get(Constants.SkinJSON.THUMB_NAME);

                if (GlobalMode.SkinMode == Constants.GlobalState.SKIN_EDIT) {
                    holder.setDeleteImageView(thumbName);
                } else {
                    holder.deleteImageView.setVisibility(View.GONE);
                }
            }
        }

        holder.loadThumb(thumbName);
    }

    public void refresh() {
        this.notifyDataSetChanged();
    }
}
