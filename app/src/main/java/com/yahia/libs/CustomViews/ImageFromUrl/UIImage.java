package com.yahia.libs.CustomViews.ImageFromUrl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.MyChannel.R;
import com.yahia.libs.SdCard;
import com.yahia.libs.InternetConnections.DownloadImageModel;
import com.yahia.libs.InternetConnections.ImagesStackService;
import com.yahia.libs.InternetConnections.onImageLoaded;
public class UIImage extends RelativeLayout implements onImageLoaded{

	private ImageFileModel image;
	
	public ProgressBar pb;
	public ImageView im; 
	Context c;
	String SD_PATH;
	ImDBHandler dbHandler;
    Bitmap currentImage;
    int height;
    int width;

	public boolean onAttacheActoin;
	public UIImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		c=context; 
		dbHandler=new ImDBHandler(c);
		
		LayoutInflater.from(context).inflate(R.layout.image_from_url, this, true);
		
		 pb=(ProgressBar)findViewById(R.id.image_from_url_progressBar);
		 im=(ImageView)findViewById(R.id.image_from_url_imageView);
		 SD_PATH=Environment.getExternalStorageDirectory().toString()+"/."+c.getApplicationInfo().packageName+"/"; 
		  
		 onAttacheActoin=true;
 
	}
	
	 @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void loadImage() {
		 if(!checkImage()){
			 ImagesStackService img=ImagesStackService.getInstance();
			 DownloadImageModel model=new DownloadImageModel(this, image.getUrl());
			 img.push(model);
		 }else {
			 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				 new LoadImageFromSd().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, image.getPathOnSD());
			    }
			    else {
			    	 new LoadImageFromSd().execute(image.getPathOnSD());
			    }
			
			  
		 }
		 
	}
 
	
	 private boolean checkImage() {
		 //Log.d("UIImage", "checkImage() ------- Start");
		 if(SdCard.checkExternalStorageState()){
			 ImageFileModel imag=dbHandler.GetImageFileFromDbWithUrl(image.getUrl());
			 if(imag !=null){ 
				 //Log.d("UIImage", "imag !=null : true");
				 if(imag.getBitmap()!=null){
					 //Log.d("UIImage", "imag.getBitmap()!=null : true");
					 image=imag;
					 //Log.d("UIImage", "checkImage() ------- finish : true");
					 return true;
				 }
			 }
		 }
		 //Log.d("UIImage", "checkImage() ------- finish : false");
		return false;
	}

	
	 
	 private void saveTosd(Bitmap image,String dir,String fileName){
		 	dir=SD_PATH+dir+"/";
		 	SdCard.saveToSd(image, dir, fileName);
	 }
	 
	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	 	if(onAttacheActoin){ 
	 		if(image !=null){
				if(image.getUrl()!=null){
					 loadImage();
				 }
			}
	 	}
	}
	
	public void refreshImage(){
		if(image !=null){
			if(image.getUrl()!=null){
				 loadImage();
			 }
		}
	}
	 
	 Handler handler = new Handler() {
	  	  @Override
		public void handleMessage(Message message ) {
	  		//Log.d("UIImage","message.what : "+message.what);
	  	    switch (message.what) {
	  	    case 0:  	
	  	    	
	  	    	
	  	    	String [] urls=image.getUrl().split("/");
	  	    	String fileName=urls[urls.length-1];
	  	    	String dir="."+urls[urls.length-2];
	  	    	
	  	    	saveTosd(currentImage,dir, fileName);
	  	    	
	  	    	image.setPathOnSD(SD_PATH+dir+"/"+fileName);
	  	    	
	  	    	dbHandler.insertImages(getContext(), image);
	  	    	  
	  	    	im.setImageBitmap(currentImage);
	  	    	pb.setVisibility(View.GONE);
	  	    	im.invalidate();
	  	    	break;
	  	    case 1:
	  	    	im.setImageBitmap(currentImage);
	  	    	pb.setVisibility(View.GONE);
	  	    	break;

	  	    }
	  	  }
	 };
	 
	 
	public ImageFileModel getImage() {
		return image;
	}
  
	public void setImage(ImageFileModel image,int width,int height) {
		this.image = image;
		this.width=width;
		this.height=height;
		
		if(!onAttacheActoin){
			if(this.image !=null){
				if(this.image.getUrl()!=null){
					pb.setVisibility(View.VISIBLE);
					 loadImage();
				 }
			}
		}
	}
	
	public void removeImage() {
		im.setImageResource(R.color.ccc);
	}
	
	public void setImageBitmap(Bitmap b){
		im.setImageBitmap(b);
	}
	
	public class FileDownloadTask extends AsyncTask<String, Integer, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... urls) {
			Bitmap bmp=null;
		    HttpURLConnection con;
			try {
				URL ulrn = new URL(urls[0]);
				con = (HttpURLConnection)ulrn.openConnection();
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
				currentImage=result;
		    	handler.sendEmptyMessage(0);
		    }
		}

	}

	 
	@Override
	public void onConnectionError(int code, String response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionStarted(int code, String response) {
		// TODO Auto-generated method stub
		
	}

 

	@Override
	public void onConnectionDone(int code, Bitmap message) {
		currentImage=message;
		handler.sendEmptyMessage(0);
		
	}

	public class LoadImageFromSd extends AsyncTask<String, Integer, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... urls) {
			BitmapFactory.Options options = new BitmapFactory.Options();
		   // options.inSampleSize = 3; // If number equals 3, it is only showing one third of all pixels. May crash if you are using 2, but 2 works in most cases.
		     
		    //options.outWidth = width;
		    //options.outHeight = height;
		    
		    Bitmap bMap = BitmapFactory.decodeFile(urls[0], options);
	         return bMap;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (null != result)	{	
				currentImage=result;
				handler.sendEmptyMessage(1);
		    }
		}

	}
	
}
