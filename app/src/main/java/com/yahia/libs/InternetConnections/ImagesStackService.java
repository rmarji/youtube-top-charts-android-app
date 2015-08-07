package com.yahia.libs.InternetConnections;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;

public class ImagesStackService {
	static ArrayList<DownloadImageModel> ImageStack;
	static Boolean inConnection;
	static Boolean StackStarted;
	DownloadImageModel imgModel;
	
	 private static ImagesStackService instance;
	public static ImagesStackService getInstance() {
		if(instance==null){
			inConnection=false;
			ImageStack=new ArrayList<DownloadImageModel>();
			return new ImagesStackService();
		}else{
			return instance;
		}
		
	}
	public void push(DownloadImageModel imgModel){
		ImageStack.add(imgModel);
		if(!inConnection){
			startNext();
		}
	}
	 
	private void startNext() {	
		if(ImageStack.size()!=0){
			inConnection=true;
			DownloadImageModel imageModel=ImageStack.get(0);	
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				new FileDownloadTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, imageModel);				 
			    }
			    else {
			    	new FileDownloadTask().execute(imageModel);
			    }
			
			ImageStack.remove(0);
		}
	}  

	public class FileDownloadTask extends AsyncTask<DownloadImageModel, Integer, Bitmap>{
		DownloadImageModel dim;
		@Override
		protected Bitmap doInBackground(DownloadImageModel... urls) {
			Bitmap bmp=null;
		    HttpURLConnection con;
			try {
				dim = urls[0];
				URL url = new URL(dim.getUrl());
				con = (HttpURLConnection)url.openConnection();
				InputStream is = con.getInputStream();
			    bmp = BitmapFactory.decodeStream(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
	         return bmp;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (null != result)	{	
				dim.getOil().onConnectionDone(0, result) ;
				inConnection=false;
				startNext();
		    }
		}

	}
 
 

}
