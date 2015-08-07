package com.etqanapps.EtqanChannel.Controllers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etqanapps.EtqanChannel.DataModel.PlayListModel;
import com.etqanapps.EtqanChannel.DataModel.VideoModel;

public class DataProcessor {

	public static ArrayList<PlayListModel> getPlayLists(String respose) {
		ArrayList<PlayListModel> playLists = new ArrayList<PlayListModel>();
		JSONObject json;
		try {

			json = new JSONObject(respose);
			JSONArray array = json.getJSONArray("feed");
			for (int x = 0; x < array.length(); x++) {
				JSONObject obj = array.getJSONObject(x);
				PlayListModel p = new PlayListModel();
				p.setID(obj.get(PlayListModel.Y_PLAY_LIST_KEY_ID).toString());
				p.setTitle(obj.get(PlayListModel.Y_PLAY_LIST_KEY_TITLE)
						.toString());
				p.setPublished(obj.get(PlayListModel.Y_PLAY_LIST_KEY_PUBLISHED)
						.toString());
				p.setThumbID(obj.get(PlayListModel.Y_PLAY_LIST_KEY_THUMB_ID)
						.toString());
				p.setVideosCount(obj.get(
						PlayListModel.Y_PLAY_LIST_KEY_VIDEOS_COUNT).toString());
				p.setVideos(getVideos(
						obj.getJSONArray(PlayListModel.Y_PLAY_LIST_KEY_VIDEOS),
						p.getID()));
				playLists.add(p);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return playLists;

	}

	public static ArrayList<VideoModel> getVideos(JSONArray vids,
			String parentId) {
		ArrayList<VideoModel> videos = new ArrayList<VideoModel>();
		 
		try {
			for (int x = 0; x < vids.length(); x++) { 
				JSONObject obj = vids.getJSONObject(x);
				VideoModel v = new VideoModel();
				v.setParent_id(parentId);
				v.setTitle(obj.get(VideoModel.Y_VIDEOKEY_TITLE).toString());
				v.setDescription(obj.get(VideoModel.Y_VIDEOKEY_DESCRIPTION)
						.toString());
				v.setPublished(obj.get(VideoModel.Y_VIDEOKEY_PUBLISHED)
						.toString());
				v.setDuration(obj.get(VideoModel.Y_VIDEOKEY_DURATION)
						.toString());
				v.setID(obj.get(VideoModel.Y_VIDEOKEY_ID).toString());
				v.setFavoCount(obj.get(VideoModel.Y_VIDEOKEY_FAVORITES_COUNT)
						.toString());
				v.setViewCount(obj.get(VideoModel.Y_VIDEOKEY_VIEW_COUNT)
						.toString());
				v.setDislikesCount(obj.get(
						VideoModel.Y_VIDEOKEY_DIS_LIKES_COUNT).toString());
				v.setLikesCount(obj.get(VideoModel.Y_VIDEOKEY_LIKES_COUNT)
						.toString());
				v.setRating(obj.get(VideoModel.Y_VIDEOKEY_RATING).toString());
				v.setRatersCount(obj.get(VideoModel.Y_VIDEOKEY_RATERS_COUNT)
						.toString());
				v.setRtsp1(obj.get(VideoModel.Y_VIDEOKEY_RTSP1).toString());
				v.setRtsp2(obj.get(VideoModel.Y_VIDEOKEY_RTSP2).toString());
				videos.add(v);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return videos;
	}

	public static String getYoutubeDownloadLink(String string) {
		if(string.contains("status=fail")){
			return null;
		}else{
			 
		    string=string.replace("%25", "%");
		    string=string.replace("%25", "%");
		    string=string.replace("%2C", ",");
		    string=string.replace("%3F", "?");
		    string=string.replace("%2F", "/");
		    string=string.replace("%3A", ":");
		    string=string.replace("%3D", "=");
		    string=string.replace("%3F", "?");
		    string=string.replace("%3B", ";");
		    string=string.replace("%2B", "+");
		    string=string.replace("%22", "\"");
		    string=string.replace("%26", "&");
		    string=string.replace("sig", "signature");
		    String [] a=string.split("url=");
		    for(int x=0;x<a.length;x++){
		    		String link=a[x];
		    		if(link.contains("video/mp4")){
		    			string=link;
		    			
		    			System.out.println("link : "+string);
		    			break;
		    		}
		    }
			return string;
		}
		
	}
}
