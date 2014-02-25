package jamesdev.ttpod_task.activity;

import android.content.Context;
import android.media.Image;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
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

public class MainActivity extends Activity {
	  /**
     * Called when the activity is first created.
     */
    private static final String TAG = "MainActivity";
    private GridView gridView;
    private Button skinEditButton;

    private String[] imageUrls;

    private List<Map<String, String>> skinData;
    private ArrayList<SkinViewHolder> skinViewHolders;
    private ArrayList<View> itemViews;

    private static String[] downloadedFiles = null;
    private static String[] embadedFiles = null;

    private SkinItemAdapter mSkinItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skinViewHolders = new ArrayList<SkinViewHolder>();
        itemViews = new ArrayList<View>();

        getSkinDataFromJSON();
        mSkinItemAdapter = new SkinItemAdapter(this, skinData);

        skinEditButton = (Button)findViewById(R.id.button_skin_edit);
        skinEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button self = (Button)view;
                if (GlobalMode.SkinMode == Constants.GlobalState.SKIN_VIEW) {
                    GlobalMode.SkinMode = Constants.GlobalState.SKIN_EDIT;
                    self.setText(getString(R.string.button_save_skin));
                    mSkinItemAdapter.refresh();
                    Log.d(TAG, "edit skin: update adapter data");
                } else if (GlobalMode.SkinMode == Constants.GlobalState.SKIN_EDIT) {
                    GlobalMode.SkinMode = Constants.GlobalState.SKIN_VIEW;
                    self.setText(getString(R.string.button_edit_skin));
                    mSkinItemAdapter.refresh();
                    Log.d(TAG, "save skin, update apdater data ");
                }
            }
        });

        gridView = (GridView)findViewById(R.id.gridViewSkin);
        gridView.setAdapter(mSkinItemAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                SkinViewHolder skinViewHolder = (SkinViewHolder)view.getTag();
                if (GlobalMode.SkinMode == Constants.GlobalState.SKIN_VIEW) {
                    skinViewHolder.doResponse();
                } else if (GlobalMode.SkinMode == Constants.GlobalState.SKIN_EDIT) {
                    skinViewHolder.doEdit();
                }
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
        GlobalMode.SkinMode = Constants.GlobalState.SKIN_VIEW;
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }
}
