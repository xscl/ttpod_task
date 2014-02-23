package jamesdev.ttpod_task.skin;

import jamesdev.ttpod_task.util.Constants;
import jamesdev.ttpod_task.util.DownloadTask;
import jamesdev.ttpod_task.util.SkinViewHolder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    private static final String TAG = "MyActivity";
    GridView gridView;
    String[] imageUrls;
    private List<Map<String, String>> skinData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSkinDataFromJSON();

        gridView = (GridView)findViewById(R.id.gridViewSkin);
        gridView.setAdapter(new ImageAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
            	Log.i(TAG, "startSkinDownload:" +position);
                startSkinDownload(v);
            }
        });
        Log.i(TAG, "onCreate");
        
        String state = Environment.getExternalStorageState();
        
        if (Environment.MEDIA_MOUNTED.equals(state)) {
        	Log.i(TAG, "avaliable");
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
        	Log.i(TAG, "read only");
        } else {
        	Log.i(TAG, "unvisible");
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
    	
        Log.i(TAG, "onStart");
    }

    private void startSkinDownload(View v) {
       SkinViewHolder skinViewHolder = (SkinViewHolder)v.getTag();
       DownloadTask downloadTask = new DownloadTask(this, skinViewHolder);
       downloadTask.execute(); 
    }

    public class ImageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return skinData.size();
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
            final SkinViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
                holder = new SkinViewHolder();
                assert view != null;
                holder.imageView = (ImageView) view.findViewById(R.id.image);
                holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);

                Map<String, String> skinInfo = skinData.get(position);
                holder.skinUrl = skinInfo.get(Constants.SkinJSON.SKIN_URL);

                StringBuilder imagePath = new StringBuilder(Constants.SkinJSON.PREVIEW_DIR);
                String thumbName = skinInfo.get(Constants.SkinJSON.THUMB_NAME);
                holder.thumbName = thumbName;
                Log.d(TAG, "index: " + position);
                Log.d(TAG, "imageName:" + thumbName);
                
                imagePath.append(thumbName).append(Constants.SkinJSON.IMAGE_FORMAT);

                try {
                    InputStream ims = getAssets().open(imagePath.toString());
                    Drawable drawable = Drawable.createFromStream(ims, null);
                    holder.imageView.setImageDrawable(drawable);
                } catch (IOException ex) {
                    Log.e(TAG, "getView" + position);
                    ex.printStackTrace();
                }

                view.setTag(holder);
            } else {
                holder = (SkinViewHolder) view.getTag();
            }
            return view;
        }
    }

}
