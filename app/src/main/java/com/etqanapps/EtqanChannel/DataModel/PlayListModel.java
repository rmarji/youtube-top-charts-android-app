package com.etqanapps.EtqanChannel.DataModel;

import java.util.ArrayList;

public class PlayListModel {
	private int db_id;
	private String title;
	private String ID;
	private String published;
	private String thumbID;
	private String videosCount;
	private ArrayList<VideoModel> videos; 
  
	public static String Y_PLAY_LIST_KEY_TITLE ="title";
	public static String Y_PLAY_LIST_KEY_ID ="playlistId";
	public static String Y_PLAY_LIST_KEY_PUBLISHED ="published";
	public static String Y_PLAY_LIST_KEY_THUMB_ID ="thumbId";
	public static String Y_PLAY_LIST_KEY_VIDEOS_COUNT ="videosCount";
	public static String Y_PLAY_LIST_KEY_VIDEOS ="videos";
	
	public int getDb_id() {
		return db_id;
	}
	public void setDb_id(int db_id) {
		this.db_id = db_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getThumbID() {
		return thumbID;
	}
	public void setThumbID(String thumbID) {
		this.thumbID = thumbID;
	}
	public String getVideosCount() {
		return videosCount;
	}
	public void setVideosCount(String videosCount) {
		this.videosCount = videosCount;
	}
	public ArrayList<VideoModel> getVideos() {
		return videos;
	}
	public void setVideos(ArrayList<VideoModel> videos) {
		this.videos = videos;
	}
}
