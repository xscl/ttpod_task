package jamesdev.ttpod_task.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import jamesdev.ttpod_task.activity.R;
import jamesdev.ttpod_task.util.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-2-24.
 */
public class SkinItemAdapter extends BaseAdapter {
    private Context mContext;
    private  List<Map<String, String>> skinData;
    private static final String TAG = "SkinItemAdapter";
    private int embededNo;
    private String[] embededFiles;

    public SkinItemAdapter(Context context,  List<Map<String, String>> pSkinData) {
        mContext = context;
        skinData = pSkinData;
        embededFiles = AssetsHelper.getInstance(mContext).getAssetFiles(Constants.SkinJSON.EMBEDED_SKIN_DIR);
        embededNo = embededFiles.length;
    }
    @Override
    public int getCount() {
        return embededNo + skinData.size() + 1;
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
            if (position < embededNo) {
                holder.isEmbeded = true;
                thumbName = embededFiles[position].split("\\.")[0];
                holder.deleteImageView.setVisibility(View.GONE);
            } else {
                holder.isEmbeded = false;
                Map<String, String> skinInfo = skinData.get(position - embededNo);
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
