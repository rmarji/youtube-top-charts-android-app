package com.etqanapps.EtqanChannel.DataModel;

public class VideoModel {
	private int db_id;
	private String  title;
	private String  description;
	private String  published;
	private String  duration;
	private String  ID;
	private String  favoCount;
	private String  viewCount;
	private String  dislikesCount;
	private String  likesCount;
	private String  rating;
	private String  ratersCount;
	private String  rtsp1;
	private String  rtsp2;
	private String  parent_id;
 

	 public static String  Y_VIDEOKEY_TITLE ="title";
	 public static String Y_VIDEOKEY_DESCRIPTION ="description";
	 public static  String Y_VIDEOKEY_PUBLISHED ="published";
	 public static String Y_VIDEOKEY_DURATION ="duration";
	 public static String Y_VIDEOKEY_ID ="videoid";
	 public static String Y_VIDEOKEY_FAVORITES_COUNT ="favoriteCount";
	 public static String Y_VIDEOKEY_VIEW_COUNT ="viewCount";
	 public static String Y_VIDEOKEY_DIS_LIKES_COUNT ="numDislikes";
	 public static String Y_VIDEOKEY_LIKES_COUNT ="numLikes";
	 public static String Y_VIDEOKEY_RATING ="rating";
	 public static String Y_VIDEOKEY_RATERS_COUNT ="numRaters";
	 public static String Y_VIDEOKEY_RTSP1 ="rtsp1";
	 public static String Y_VIDEOKEY_RTSP2 ="rtsp2";
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getFavoCount() {
		return favoCount;
	}
	public void setFavoCount(String favoCount) {
		this.favoCount = favoCount;
	}
	public String getViewCount() {
		return viewCount;
	}
	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}
	public String getDislikesCount() {
		return dislikesCount;
	}
	public void setDislikesCount(String dislikesCount) {
		this.dislikesCount = dislikesCount;
	}
	public String getLikesCount() {
		return likesCount;
	}
	public void setLikesCount(String likesCount) {
		this.likesCount = likesCount;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getRatersCount() {
		return ratersCount;
	}
	public void setRatersCount(String ratersCount) {
		this.ratersCount = ratersCount;
	}
	public String getRtsp1() {
		return rtsp1;
	}
	public void setRtsp1(String rtsp1) {
		this.rtsp1 = rtsp1;
	}
	public String getRtsp2() {
		return rtsp2;
	}
	public void setRtsp2(String rtsp2) {
		this.rtsp2 = rtsp2;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
}
