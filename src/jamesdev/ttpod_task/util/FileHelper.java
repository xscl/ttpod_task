package jamesdev.ttpod_task.util;

import java.io.File;

import android.os.Environment;
import android.util.Log;

/**
 * Crated by Benpeng.Jiang
 */
public class FileHelper {
	private static String[] mDownloadedFiles = null;
	private static final String TAG = "FileHelper";
	
	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public static boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}

    /**
     * check if skin exist
     * @param skinName skin name
     * @return true is exist, false if not
     */
	public static boolean isSkinExist(String skinName) {
		return isFileExisted(skinName + Constants.SkinJSON.SKIN_SUFFIX, Constants.SkinJSON.SKIN_DIR);
	}

    /**
     * check whether file exist
     * @param fileName file name
     * @param dir directory of the file name
     * @return true if exist, false if not
     */
    public static boolean isFileExisted(String fileName, String dir) {
        File file = new File(dir + fileName);
        return file.exists();
    }

    /**
     * get the path of skin
     * @param skinName skin name
     * @return path of skin
     */
    private static String getSkinPath(String skinName) {
        return Constants.SkinJSON.SKIN_DIR + skinName + Constants.SkinJSON.SKIN_SUFFIX;
    }

    /**
     * delete skin
     * @param skinName skin name
     * @return ture if delete success, false if not
     */
    public static boolean deleteSkin(String skinName) {
        String filePath = getSkinPath(skinName);
        File skin = new File(filePath);
        boolean isDeleted = false;
        if (skin.exists()) {
            isDeleted = skin.delete();
        }
        return isDeleted;
    }
}
