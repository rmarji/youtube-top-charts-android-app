package com.yahia.libs.InternetConnections;

public class DownloadImageModel {
	private onImageLoaded oil;
	private String url;
	
	public onImageLoaded getOil() {
		return oil;
	}

	public void setOil(onImageLoaded oil) {
		this.oil = oil;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DownloadImageModel(onImageLoaded oil, String url) { 
		this.oil = oil;
		this.url = url;
	}
	
	
}
