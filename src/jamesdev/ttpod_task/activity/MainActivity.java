package jamesdev.ttpod_task.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import jamesdev.ttpod_task.adapter.SkinItemAdapter;
import jamesdev.ttpod_task.util.Constants;
import jamesdev.ttpod_task.util.GlobalMode;
import jamesdev.ttpod_task.util.JsonParser;
import jamesdev.ttpod_task.view.SkinViewHolder;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Benpeng.Jiang
 */
public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private GridView mGridView;
    private Button mSkinEditButton;
    private List<Map<String, String>> mSkinData;
    private SkinItemAdapter mSkinItemAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSkinData = JsonParser.getInstance().init(this).getDataFromJSON();
        mSkinItemAdapter = new SkinItemAdapter(this, mSkinData);

        mSkinEditButton = (Button)findViewById(R.id.button_skin_edit);
        mSkinEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button self = (Button)view;
                if (GlobalMode.SkinMode == Constants.GlobalState.SKIN_VIEW) {
                    GlobalMode.SkinMode = Constants.GlobalState.SKIN_EDIT;
                    self.setText(getString(R.string.button_save_skin));
                } else if (GlobalMode.SkinMode == Constants.GlobalState.SKIN_EDIT) {
                    GlobalMode.SkinMode = Constants.GlobalState.SKIN_VIEW;
                    self.setText(getString(R.string.button_edit_skin));
                }
                mSkinItemAdapter.refresh();
            }
        });

        mGridView = (GridView)findViewById(R.id.gridViewSkin);
        mGridView.setAdapter(mSkinItemAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
