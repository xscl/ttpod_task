package jamesdev.ttpod_task.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import jamesdev.ttpod_task.activity.R;
import jamesdev.ttpod_task.util.Constants;
import jamesdev.ttpod_task.util.SkinViewHolder;
import jamesdev.ttpod_task.util.StorageHelper;

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

    public SkinItemAdapter(Context c,  List<Map<String, String>> pSkinData) {
        mContext = c;
        skinData = pSkinData;
    }
    @Override
    public int getCount() {
        return skinData.size();
    }

    @Override
    public Object getItem(int position) {
        return skinData.get(position);
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
            view = newView(R.layout.item_grid_image, parent, false, position);
            holder = new SkinViewHolder();
            view.setTag(holder);
        } else {
            holder = (SkinViewHolder)view.getTag();
        }

        bindView(position, view, holder);
        return view;
    }

    private View newView(int resource, ViewGroup parent, boolean attachToRoot, int position) {
        View  view = ((Activity) mContext).getLayoutInflater().inflate(resource, parent, false);
        return view;
    }

    private void bindView(int position, View view, SkinViewHolder holder) {
        assert view != null;

        holder.imageView = (ImageView) view.findViewById(R.id.image);
        holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);

        Map<String, String> skinInfo = skinData.get(position);
        holder.skinUrl = skinInfo.get(Constants.SkinJSON.SKIN_URL);

        String thumbName = skinInfo.get(Constants.SkinJSON.THUMB_NAME);
        holder.thumbName = thumbName;

        if (StorageHelper.getInstance().isSkinExist(thumbName)) {
            holder.progressBar.setVisibility(View.GONE);
        } else {
            holder.progressBar.setVisibility(View.VISIBLE);
        }

        holder.thumbFileName = thumbName + Constants.SkinJSON.IMAGE_FORMAT;
        String imagePath = Constants.SkinJSON.PREVIEW_DIR + holder.thumbFileName;

        Log.d(TAG, "position: " + position + " path: " + imagePath);

        try {
            InputStream ims = mContext.getAssets().open(imagePath);
            Drawable drawable = Drawable.createFromStream(ims, null);
            holder.imageView.setImageDrawable(drawable);
        } catch (IOException ex) {
            Log.e(TAG, "getView error:" + position);
            ex.printStackTrace();
        }
    }
}
