package com.yahia.libs.CustomViews.ImageFromUrl;

import android.os.Message;

public interface   onImageDownloaded { 
	public abstract void onImageDownloadedDone(int code,Message message);
 	public abstract void onImageDownloadedError(int code,String response);
 	public abstract void onImageDownloadedStarted(int code,String response); 	


}
