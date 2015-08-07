package com.yahia.libs.CustomViews.ImageFromUrl;

public class ImageFileModel {
	private String url;
	private String pathOnSD;
	private Boolean bitmap;
	public ImageFileModel(String url,Boolean image,String pathOnSD){
		//Log.d("ImageFileModel","( "+url+","+image+","+pathOnSD+" )");
		this.setBitmap(image);
		this.setUrl(url);
		this.setPathOnSD(pathOnSD);
		/*if(url !=null && pathOnSD !=null){
			//Log.d("ImageFileModel", "url : "+url);
			//Log.d("ImageFileModel", "pathOnSD : "+pathOnSD);

			this.setBitmap(BitmapFactory.decodeFile(pathOnSD));	
		}*/
	}
	 
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	 
	public String getPathOnSD() {
		return pathOnSD;
	}

	public void setPathOnSD(String pathOnSD) {
		this.pathOnSD = pathOnSD;
	}

	public Boolean getBitmap() {
		return bitmap;
	}

	public void setBitmap(Boolean bitmap) {
		this.bitmap = bitmap;
	}

}
