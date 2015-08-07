package com.etqanapps.EtqanChannel.Controllers;


import android.content.Context;

import com.MyChannel.R;


public class Consts {
	public static String CHANEEL_ID;
	public static String SITE_URL;
	public static String APP_LANG;
	

	public static int PLAY_LISTS_CONNECTION=1;
	public static int SEARCH_CONNECTION=133;
	
	public static String CREATE_DOWNLOADS="CREATE TABLE \"downloads\" (\"_id\" INTEGER PRIMARY KEY  NOT NULL ,\"video_id\" VARCHAR NOT NULL ,\"status\" INTEGER NOT NULL  DEFAULT (0) ,\"date\" VARCHAR DEFAULT (0) ,\"file_size\" VARCHAR DEFAULT (0) ,\"link\" TEXT)";
	public static String CREATE_FAVORITES="CREATE TABLE \"favorites\" (\"db_id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , \"video_id\" INTEGER NOT NULL , \"title\" TEXT NOT NULL , \"description\" TEXT NOT NULL , \"published\" TEXT NOT NULL , \"duration\" TEXT NOT NULL , \"favoCount\" TEXT NOT NULL , \"viewCount\" TEXT NOT NULL , \"dislikesCount\" TEXT NOT NULL , \"likesCount\" TEXT NOT NULL , \"rating\" TEXT NOT NULL , \"ratersCount\" TEXT NOT NULL , \"rtsp1\" TEXT NOT NULL , \"rtsp2\" TEXT NOT NULL , \"parent_id\" TEXT)";
	public static String CREATE_PLAY_LISTS="CREATE TABLE \"play_lists\" (\"db_id\" INTEGER PRIMARY KEY  NOT NULL ,\"play_id\" TEXT NOT NULL ,\"title\" TEXT NOT NULL ,\"published\" TEXT NOT NULL ,\"thumbID\" TEXT NOT NULL ,\"videosCount\" TEXT NOT NULL )";
	public static String CREATE_VIDEOS="CREATE TABLE \"videos\" (\"db_id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , \"video_id\" INTEGER NOT NULL , \"title\" TEXT NOT NULL , \"description\" TEXT NOT NULL , \"published\" TEXT NOT NULL , \"duration\" TEXT NOT NULL , \"favoCount\" TEXT NOT NULL , \"viewCount\" TEXT NOT NULL , \"dislikesCount\" TEXT NOT NULL , \"likesCount\" TEXT NOT NULL , \"rating\" TEXT NOT NULL , \"ratersCount\" TEXT NOT NULL , \"rtsp1\" TEXT NOT NULL , \"rtsp2\" TEXT NOT NULL , \"parent_id\" TEXT, \"local_file\" INTEGER NOT NULL  DEFAULT 0)";
	
	public static String  DB_NAME="channel";
	
	 
	public static String  LANG_AR="ar";
	public static String  LANG_EN="en";
	
	public static void initChannel(Context c){
		CHANEEL_ID=c.getString(R.string.CHANEEL_ID);
		SITE_URL=c.getString(R.string.FEED_URL)+"?id="+CHANEEL_ID;
		APP_LANG=c.getString(R.string.app_lang);
	}

	public static boolean isEnglish() {
		if(APP_LANG.equals(LANG_EN)){
			return true;
		}else{
			return false;
		} 
	}
}
