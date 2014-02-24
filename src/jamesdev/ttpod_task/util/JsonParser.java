package jamesdev.ttpod_task.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 14-2-21.
 */
public class JsonParser {
    private Context mContext = null;
    private List<Map<String, String>> skinData;
    private final static String fileName = Constants.SkinJSON.JSON_FILE;
    private static final String TAG = "JsonParser";

    public JsonParser(Context context) {
        mContext = context;
        skinData = new ArrayList<Map<String, String>>();
    }

    public List<Map<String, String>>  getDataFromJSON() {
        String jsonStr = getJson(fileName);
        setData(jsonStr);
        return skinData;
    }

    private String getJson(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(mContext.getAssets().open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "getJson");
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void setData(String str) {
        try {
            JSONArray array = new JSONArray(str);
            int len = array.length();
            Map<String, String> map;
            for (int i = 0; i < len; i++) {
                JSONObject object = array.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put(Constants.SkinJSON.THUMB_NAME, object.getString(Constants.SkinJSON.THUMB_NAME));
                map.put(Constants.SkinJSON.SKIN_URL, object.getString(Constants.SkinJSON.SKIN_URL));
                skinData.add(map);
            }
        } catch (JSONException e) {
            Log.e(TAG, "setData");
            e.printStackTrace();
        }
    }
}
