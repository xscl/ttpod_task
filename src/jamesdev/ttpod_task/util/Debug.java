package jamesdev.ttpod_task.util;

import android.util.Log;

import java.util.List;

/**
 * Created by Benpeng.jiang on 14-2-24.
 * Just for debug in developing, remove relative codes before release
 */
public final class  Debug {
    private static final String TAG = "Debug";

    public static void showList(List list) {
            for (int i = 0; i < list.size(); i++) {
                Log.i(TAG, "item:" + list.get(i));
            }
     }

    public static void showStringArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            Log.i(TAG, "item:" + array[i]);
        }
    }

}
