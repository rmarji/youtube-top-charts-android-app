package com.etqanapps.EtqanChannel.DataModel;

public class DownloadStatus {
	private String link;
	private String status;
	public DownloadStatus(String link, String status) {
		this.setLink(link);
		this.setStatus(status);
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
