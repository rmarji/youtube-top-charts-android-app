package com.yahia.libs.InternetConnections;

import android.os.Message;

public interface onConnectionController {
	public abstract void onConnectionDone(int code,Message message);
 	public abstract void onConnectionError(int code,String response);
 	public abstract void onConnectionStarted(int code,String response);
 	
}
