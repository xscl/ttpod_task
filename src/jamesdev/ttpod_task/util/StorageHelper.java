package jamesdev.ttpod_task.util;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public class StorageHelper {
	private static String[] downloadedFiles = null;
	private static StorageHelper mInstance = null;
	private static final String TAG = "StorageHelper";
	
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
	
	
	protected StorageHelper() {

	}
	
	public static StorageHelper getInstance() {
		if (mInstance == null) {
			mInstance = new StorageHelper();
		}
		return mInstance;
	}
	
	public void init() {
		getSkinFiles();	
	}
	
	public boolean isSkinExist(String skinName) {
		boolean isExist = false;
		if (downloadedFiles == null) {
			return isExist;
		}
		for (int i = 0; i < downloadedFiles.length; i++) {
			if (downloadedFiles[i].equals(skinName + Constants.SkinJSON.SKIN_SUFFIX)) {
				isExist = true;
				break;
			}
		}
		
		return isExist;
	}
}
