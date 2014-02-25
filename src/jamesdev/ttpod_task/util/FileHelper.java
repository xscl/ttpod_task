package jamesdev.ttpod_task.util;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public class FileHelper {
	private static String[] downloadedFiles = null;
	private static FileHelper mInstance = null;
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
	
	private void getSkinFiles() {
		File skinDir = new File(Constants.SkinJSON.SKIN_DIR);
		File skinFiles[] = skinDir.listFiles();
		if (skinFiles == null) {
			return;
		}

		int fileSize = skinFiles.length;
		downloadedFiles = new String[fileSize];

		for (int i = 0; i < fileSize; i++) {
			Log.d(TAG, "fileName:" + skinFiles[i].getName());
			downloadedFiles[i] = skinFiles[i].getName();
		}
	}

	public static boolean isSkinExist(String skinName) {
		return isFileExisted(skinName + Constants.SkinJSON.SKIN_SUFFIX, Constants.SkinJSON.SKIN_DIR);
	}

    public static boolean isFileExisted(String fileName, String dir) {
        File file = new File(dir + fileName);
        return file.exists();
    }

    private static String getSkinPath(String skinName) {
        return Constants.SkinJSON.SKIN_DIR + skinName + Constants.SkinJSON.SKIN_SUFFIX;
    }

    public static boolean delleteSkin(String skinName) {
        String filePath = getSkinPath(skinName);
        File skin = new File(filePath);
        boolean isDeleted = false;
        if (skin.exists()) {
            isDeleted = skin.delete();
        }
        return isDeleted;
    }
}
