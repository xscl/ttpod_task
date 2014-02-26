package jamesdev.ttpod_task.util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Benpeng.Jiang on 14-2-24.
 */
public class AssetsHelper {
    private Context mContext;
    private static AssetsHelper mInstance = null;

    private static final String TAG = "AssetsHelper";

    protected AssetsHelper() {
    }

    /**
     * Get unique object
     * @param context
     * @return
     */
    public static AssetsHelper getInstance() {
        if (mInstance == null) {
            mInstance = new AssetsHelper();
        }
        return mInstance;
    }

    /**
     * Initialize members
     * @param context
     * @return
     */
    public AssetsHelper init(Context context) {
        mContext = context;
        return mInstance;
    }

    /**
     * Get files in assets
     * @param path path to get files
     * @return all the files name in path
     * throws IOException
     */
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
