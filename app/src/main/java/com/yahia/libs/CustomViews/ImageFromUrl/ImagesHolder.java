package com.yahia.libs.CustomViews.ImageFromUrl;

import java.util.ArrayList;

public class ImagesHolder {
	private static ArrayList<ImageFileModel> images;
	public static void init(){
		images=new ArrayList<ImageFileModel>();
	}
	
	public static void addImage(ImageFileModel img){
		images.add(img);
	}
	
	public static ImageFileModel getImg(String url){
		ImageFileModel img=null;
		for(int x=0;x<images.size();x++){
			ImageFileModel im=images.get(x);
			if(im.getUrl().equals(url)){
				img=im;
				break;
			}
		}
		return img;
	}

}
