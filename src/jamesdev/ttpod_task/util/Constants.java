package jamesdev.ttpod_task.util;

import android.os.Environment;


/**
 * Created by Administrator on 14-2-21.
 */
public final class Constants {

	public static class SkinJSON {
       public static final String THUMB_NAME = "thumb_name";
	   public static final String SKIN_URL = "skin_url";
	   public static final String PREVIEW_DIR = "preview_img/";
	   public static final String IMAGE_FORMAT = ".PNG";
	   public static final String JSON_FILE = "skin_json.txt";
	   public static final String SKIN_DIR = Environment.getExternalStorageDirectory().toString() + "/ttpod_task/skin/";
	   public static final String SKIN_SUFFIX = ".tsk";
       public static final String EMBEDED_SKIN_DIR = "embeded_skin";
   }

}