package com.etqanapps.EtqanChannel.Listeners;

import com.etqanapps.EtqanChannel.DataModel.VideoModel;

public interface MenuActionsListener  {
	public void onSetPlayList(int index);
	public void onOpenVideo(VideoModel video);
	public void reloadData();
	public void openInfo();
	public void showMenu();
	public void goBack();
	public void searchFor(String searchWord);
	
	
}
