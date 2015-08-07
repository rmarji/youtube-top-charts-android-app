package com.yahia.libs.InternetConnections;

import android.graphics.Bitmap; 

public interface onImageLoaded { 
		public abstract void onConnectionDone(int code,Bitmap message);
	 	public abstract void onConnectionError(int code,String response);
	 	public abstract void onConnectionStarted(int code,String response);	 	
	 

}
