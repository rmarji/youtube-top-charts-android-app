package com.etqanapps.EtqanChannel.Controllers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.etqanapps.EtqanChannel.DataModel.PlayListModel;
import com.etqanapps.EtqanChannel.DataModel.VideoModel;
import com.yahia.libs.db.DBAdapter;

public class DBAProcressor {
	static String[]stmts={Consts.CREATE_DOWNLOADS,Consts.CREATE_FAVORITES,Consts.CREATE_PLAY_LISTS,Consts.CREATE_VIDEOS};
	private static DBAdapter dba;
	public static void  initDb(Context c){
		if(dba==null){
			dba=new DBAdapter(c,Consts.DB_NAME,1,stmts);
		    dba.open();
		}
	}
	public static ArrayList<VideoModel> getVideos(DBAdapter dba,String parent_id){
		ArrayList<VideoModel> videos=new ArrayList<VideoModel>();
		Cursor cu=dba.selectAllWhere("videos", "parent_id", parent_id);
		if (cu.getCount() >= 1) {
			cu.moveToNext();
			do {
				VideoModel v = new VideoModel();
				v.setDb_id(cu.getInt(0));
				v.setID(cu.getString(1));
				v.setTitle(cu.getString(2));
				v.setDescription(cu.getString(3));
				v.setPublished(cu.getString(4));
				v.setDuration(cu.getString(5));				
				v.setFavoCount(cu.getString(6));
				v.setViewCount(cu.getString(7));
				v.setDislikesCount(cu.getString(8));
				v.setLikesCount(cu.getString(9));
				v.setRating(cu.getString(10));
				v.setRatersCount(cu.getString(11));
				v.setRtsp1(cu.getString(12));
				v.setRtsp2(cu.getString(13));
				v.setParent_id(cu.getString(14));
				
				videos.add(v);			 
			} while (cu.moveToNext());
		}
		cu.close();
        return videos;
	}
	public static void savePlayListsInDb(ArrayList<PlayListModel> playLists){ 
         
        for(int x=0;x<playLists.size();x++){
        		PlayListModel p=playLists.get(x);
        		
        		ContentValues newTaskValues = new ContentValues();
        		newTaskValues.put("play_id", p.getID());
        		newTaskValues.put("title", p.getTitle());
        		newTaskValues.put("published", p.getPublished());
        		newTaskValues.put("thumbID",p.getThumbID());
        		newTaskValues.put("videosCount",p.getVideosCount());        		
        		dba.insertValuesIntoTable("play_lists", newTaskValues);
        		saveVideoInDb(dba, p.getVideos(), p.getID());
        }  
	}
	
	public static void saveVideoInDb(DBAdapter dba,ArrayList<VideoModel> videos,String parent_id){ 
        
        for(int x=0;x<videos.size();x++){
        		VideoModel v=videos.get(x);
        		
        		ContentValues newTaskValues = new ContentValues();
        		newTaskValues.put("video_id", v.getID());
        		newTaskValues.put("title", v.getTitle());
        		newTaskValues.put("description", v.getDescription());
        		newTaskValues.put("published",v.getPublished());
        		newTaskValues.put("duration",v.getDuration());
        		newTaskValues.put("favoCount",v.getFavoCount());
        		newTaskValues.put("viewCount",v.getViewCount());
        		newTaskValues.put("dislikesCount",v.getDislikesCount()); 
        		newTaskValues.put("likesCount",v.getLikesCount());
        		newTaskValues.put("rating",v.getRating());
        		newTaskValues.put("ratersCount",v.getRatersCount());
        		newTaskValues.put("rtsp1",v.getRtsp1());
        		newTaskValues.put("rtsp2",v.getRtsp2());
        		newTaskValues.put("parent_id",parent_id);
        		newTaskValues.put("local_file","");
        		
        		dba.insertValuesIntoTable("videos", newTaskValues);
        		
        } 
         
	}
	
	
	public static ArrayList<PlayListModel> getPlayLists(){
		ArrayList<PlayListModel> playLists=new ArrayList<PlayListModel>();
      
        Cursor cu=dba.selectAll("play_lists");
        if (cu.getCount() >= 1) {
			cu.moveToNext();
			do {
				PlayListModel p  = new PlayListModel();
				p.setDb_id(cu.getInt(0));
				p.setID(cu.getString(1));
				p.setTitle(cu.getString(2));
				p.setPublished(cu.getString(3));
				p.setThumbID(cu.getString(4));
				p.setVideosCount(cu.getString(5));
				p.setVideos(getVideos(dba, p.getID()));				 
				
				playLists.add(p);			 
			} while (cu.moveToNext());
		}  
        cu.close();
        return playLists;
	}

	public static VideoModel getVideo(String id){
		VideoModel v=new VideoModel();
      
		Cursor cu=dba.selectAllWhere("videos", "video_id", id);
        if (cu.getCount() >= 1) {
			cu.moveToNext();
			do {
				v.setDb_id(cu.getInt(0));
				v.setID(cu.getString(1)); 
				v.setTitle(cu.getString(2));
				v.setDescription(cu.getString(3));
				v.setPublished(cu.getString(4));
				v.setDuration(cu.getString(5));				
				v.setFavoCount(cu.getString(6));
				v.setViewCount(cu.getString(7));
				v.setDislikesCount(cu.getString(8));
				v.setLikesCount(cu.getString(9));
				v.setRating(cu.getString(10));
				v.setRatersCount(cu.getString(11));
				v.setRtsp1(cu.getString(12));
				v.setRtsp2(cu.getString(13));
				v.setParent_id(cu.getString(14));			 
			} while (cu.moveToNext());
		}  
        cu.close();
        return v;
	}
	public void insertintFavorites(VideoModel v) {
		ContentValues newTaskValues = new ContentValues();
		newTaskValues.put("video_id", v.getID());
		newTaskValues.put("title", v.getTitle());
		newTaskValues.put("description", v.getDescription());
		newTaskValues.put("published",v.getPublished());
		newTaskValues.put("duration",v.getDuration());
		newTaskValues.put("favoCount",v.getFavoCount());
		newTaskValues.put("viewCount",v.getViewCount());
		newTaskValues.put("dislikesCount",v.getDislikesCount()); 
		newTaskValues.put("likesCount",v.getLikesCount());
		newTaskValues.put("rating",v.getRating());
		newTaskValues.put("ratersCount",v.getRatersCount());
		newTaskValues.put("rtsp1",v.getRtsp1());
		newTaskValues.put("rtsp2",v.getRtsp2());
		newTaskValues.put("parent_id",v.getParent_id());
		
		dba.insertValuesIntoTable("favorites", newTaskValues);
	}
	public void removeFavorites(VideoModel v) {
		dba.deleteFromTableWhereId("favorites", "video_id", v.getID());
	}

	public Boolean checkFavoStatus(VideoModel v) {
		Boolean status = false;
		Cursor cu = dba.selectOneFiledWhere("*", "favorites", "video_id",v.getID());
		if (cu.getCount() >= 1) {
			status = true;
		}
		cu.close();
		return status;
	}
	public static void clearData(){ 
	        String[] tables={"downloads","favorites","play_lists","videos"};
	        dba.Truncate(tables); 
	        
	}
	public ArrayList<VideoModel> getFavos() {
		ArrayList<VideoModel> videos=new ArrayList<VideoModel>();
		Cursor cu=dba.selectAll("favorites");
		if (cu.getCount() >= 1) {
			cu.moveToNext();
			do {
				VideoModel v = new VideoModel();
				v.setDb_id(cu.getInt(0));
				v.setID(cu.getString(1));
				v.setTitle(cu.getString(2));
				v.setDescription(cu.getString(3));
				v.setPublished(cu.getString(4));
				v.setDuration(cu.getString(5));				
				v.setFavoCount(cu.getString(6));
				v.setViewCount(cu.getString(7));
				v.setDislikesCount(cu.getString(8));
				v.setLikesCount(cu.getString(9));
				v.setRating(cu.getString(10));
				v.setRatersCount(cu.getString(11));
				v.setRtsp1(cu.getString(12));
				v.setRtsp2(cu.getString(13));
				v.setParent_id(cu.getString(14));
				
				videos.add(v);			 
			} while (cu.moveToNext());
		}
		cu.close();
        return videos;
	}
}
