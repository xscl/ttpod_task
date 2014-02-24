package jamesdev.ttpod_task.activity;

import android.content.Context;
import android.media.Image;
import jamesdev.ttpod_task.adapter.SkinItemAdapter;
import jamesdev.ttpod_task.util.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	  /**
     * Called when the activity is first created.
     */
    private static final String TAG = "MainActivity";
    GridView gridView;
    String[] imageUrls;
    private List<Map<String, String>> skinData;
    private ArrayList<SkinViewHolder> skinViewHolders;
    private ArrayList<View> itemViews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        skinViewHolders = new ArrayList<SkinViewHolder>();
        itemViews = new ArrayList<View>();

        StorageHelper.getInstance().init();
        getSkinDataFromJSON();

        gridView = (GridView)findViewById(R.id.gridViewSkin);
        gridView.setAdapter(new SkinItemAdapter(this, skinData));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
            	Log.d(TAG, "startSkinDownload:" +position);
                startSkinDownload(v);
            }
        });
        Log.i(TAG, "onCreate");
        
        String state = Environment.getExternalStorageState();
        
        if (Environment.MEDIA_MOUNTED.equals(state)) {
        	Log.d(TAG, "visible");
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
        	Log.d(TAG, "read only");
        } else {
        	Log.d(TAG, "invisible");
        }
        
        File skinDir = new File(Constants.SkinJSON.SKIN_DIR);
        if (!skinDir.exists()) {
        	if (!skinDir.mkdirs()) {
        		Log.e(TAG, "make skin dir error: " + skinDir.getPath());
        	}
        	Log.d(TAG, "dir create success: " + skinDir.getPath());
        }
    }

    private void getSkinDataFromJSON() {
        JsonParser jsonParser = new JsonParser(this);
        skinData = jsonParser.getDataFromJSON();
    }

    @Override
    public void onStart() {
    	super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }


    private void startSkinDownload(View v) {
       SkinViewHolder skinViewHolder = (SkinViewHolder)v.getTag();
       DownloadTask downloadTask = new DownloadTask(this, skinViewHolder);
       downloadTask.execute(); 
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
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
            View  view = getLayoutInflater().inflate(resource, parent, false);
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
                InputStream ims = getAssets().open(imagePath);
                Drawable drawable = Drawable.createFromStream(ims, null);
                holder.imageView.setImageDrawable(drawable);
            } catch (IOException ex) {
                Log.e(TAG, "getView error:" + position);
                ex.printStackTrace();
            }
        }
    }
}
