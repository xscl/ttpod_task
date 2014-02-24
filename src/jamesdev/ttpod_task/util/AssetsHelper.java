package jamesdev.ttpod_task.util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Administrator on 14-2-24.
 */
public class AssetsHelper {
    private Context mContext;
    private static AssetsHelper mInstance = null;
    private static final String TAG = "AssetsHelper";

    protected AssetsHelper(Context context) {
        mContext = context;
    }

    public static AssetsHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AssetsHelper(context);
        }

        return mInstance;
    }


    public String[] getAssetFiles(String path) {
        String[] list;
        try {
            list = mContext.getAssets().list(Constants.SkinJSON.EMBEDED_SKIN_DIR);
        } catch (IOException e) {
            Log.e(TAG, "get assets error");
            return null;
        }

        return list;
    }
}
