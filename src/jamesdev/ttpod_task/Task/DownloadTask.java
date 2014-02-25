package jamesdev.ttpod_task.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import jamesdev.ttpod_task.util.Constants;
import jamesdev.ttpod_task.util.SkinViewHolder;

public class DownloadTask extends AsyncTask<Void, Integer, Integer> {
	private static final String TAG = "DownLoadTask";
	private SkinViewHolder mSkinViewHolder;
	private String fileName;
	private Integer fileSize;
	private Context mContext;
	
	public DownloadTask(Context context, SkinViewHolder skinViewHolder) {
		super();
		this.mContext = context;
		this.mSkinViewHolder = skinViewHolder;
		this.fileName = skinViewHolder.getThumbName();
	}

	@Override
	protected Integer doInBackground(Void... params) {
		try {
			URL url = new URL(mSkinViewHolder.skinUrl);
			URLConnection urlConn = url.openConnection();
			
			InputStream is = urlConn.getInputStream();
			fileSize = urlConn.getContentLength();
			
			if (fileSize <= 0) {
				throw new RuntimeException("cannot get file size");
			}
			
			if (is == null) {
				throw new RuntimeException("cannot get file");
			}
			
			StringBuilder skinName = new StringBuilder(fileName);
			skinName.append(Constants.SkinJSON.SKIN_SUFFIX);
			String absoluteFilename = Constants.SkinJSON.SKIN_DIR + skinName.toString();
			
			File targetFile = new File(absoluteFilename);
			Log.d(TAG, "path:" + targetFile.getPath());
			if (targetFile.exists()) {
				targetFile.delete();
			}
			
			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				Log.e(TAG, "create file error: " + targetFile.getPath());
				e.printStackTrace();
			}
			
			FileOutputStream fOS = new FileOutputStream(absoluteFilename);
			
			byte buf[] = new byte[1024];
			int downLoadFilePosition = 0;
			int numRead;
			
			while ((numRead = is.read(buf)) != -1) {
				fOS.write(buf, 0, numRead);
				downLoadFilePosition += numRead;
				publishProgress((int)((downLoadFilePosition/(float)fileSize)*100));
			}
			
			try {
				is.close();
				fOS.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileSize;
	}
	
	@Override
	protected void onPreExecute() {
		Toast.makeText(mContext, "start download", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		mSkinViewHolder.progressBar.setVisibility(View.GONE);
		Toast.makeText(mContext, "download compeleted", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
		mSkinViewHolder.progressBar.setProgress(progress[0]);
	}
	
}
