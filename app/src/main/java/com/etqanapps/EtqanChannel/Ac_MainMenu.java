package com.etqanapps.EtqanChannel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.MyChannel.R;
import com.etqanapps.EtqanChannel.Controllers.Consts;
import com.etqanapps.EtqanChannel.Controllers.DBAProcressor;
import com.etqanapps.EtqanChannel.Controllers.DataProcessor;
import com.etqanapps.EtqanChannel.DataModel.PlayListModel;
import com.etqanapps.EtqanChannel.DataModel.VideoModel;
import com.etqanapps.EtqanChannel.Listeners.MenuActionsListener;
import com.etqanapps.EtqanChannel.UI.UIMenu;
import com.etqanapps.EtqanChannel.UI.UIPlayList;
import com.etqanapps.EtqanChannel.UI.UIVideoView;
import com.slidingmenu.lib.CustomViewAbove;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.yahia.libs.InternetConnections.ConnectionController2;
import com.yahia.libs.InternetConnections.onConnectionController;
 
public class Ac_MainMenu extends Activity implements OnOpenedListener,
		MenuActionsListener, onConnectionController, OnClosedListener,
		OnCloseListener {
	int PLAY_LIST_VIEW = 20000;
	int VIDEO_VIDE = 20001;
	int currentView = 0;
	Boolean isCurrentIsSearchPlayList=false;
	UIMenu uiMenu;
	UIPlayList uiPlayList;
	UIVideoView uiVideo;

	private SlidingMenu mRight;
	int currentIndex;
	int newIndex;

	int backPressed = 0;
	static ProgressDialog dialog;
	static Context c;
	static ArrayList<PlayListModel> playLists;
	String SEARCH_WORD = "";
	PlayListModel searchPlayList=new PlayListModel();
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main_menu);

		c = this;
		
		//init Locaization 
		initLang();
		
		// init DB
		DBAProcressor.initDb(this);
		// init Channel Detalis
		Consts.initChannel(this);

		// init index
		currentIndex = 1;
		newIndex = 1;

		// init Menu
		uiMenu = new UIMenu(this, this,this);
		uiPlayList = new UIPlayList(this, this,this);
		uiVideo = new UIVideoView(this, this,this);

		dialog = new ProgressDialog(this);
		dialog.setCancelable(false);

		mRight = (SlidingMenu) findViewById(R.id.slidingmenulayout);
		currentView = PLAY_LIST_VIEW;
		mRight.setMenu(uiMenu);
		mRight.setContent(uiPlayList);

		if (UIPlayList.isTablet(this)) {
			mRight.setBehindOffset(100);
		} else {
			mRight.setBehindOffset(50);
		}

		if(Consts.isEnglish()){
			mRight.setMode(SlidingMenu.LEFT);
		}else{
			mRight.setMode(SlidingMenu.RIGHT);
		}
		mRight.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mRight.setFadeDegree(0.35f);

		mRight.setOnOpenedListener(this);
		mRight.setOnClosedListener(this);
		mRight.setOnCloseListener(this);

		// laod DB
		playLists = DBAProcressor.getPlayLists();

		// Check loaded Data
		if (playLists.size() < 1) {
			loadDataFromInternet(null);
		} else {
			showData();
		}

	}
	private void initLang() {
		String language = c.getString(R.string.app_lang);
		Locale locale = new Locale(language);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
	}
	private void showData() {
		playLists.add(0, getFavoPlayList());
		uiMenu.refreshData(playLists);
		if(playLists.size()>1){
			uiPlayList.refreshData(playLists.get(currentIndex));
		}else{
			handler.sendEmptyMessage(5);
			uiPlayList.refreshData(playLists.get(0));
		}
		
		
	}

	private PlayListModel getFavoPlayList() {
		PlayListModel favoPlaylist = new PlayListModel();
		favoPlaylist.setTitle("Favorites");
		DBAProcressor dba = new DBAProcressor();
		ArrayList<VideoModel> feed = dba.getFavos();
		favoPlaylist.setVideos(feed);
		return favoPlaylist;
	}

	public void loadDataFromInternet(View v) {
		handler.sendEmptyMessage(0);

		ConnectionController2 connection = new ConnectionController2(this,
				Consts.PLAY_LISTS_CONNECTION);
		connection.setOnConnectionDone(this);
		connection.setShowLoadingDialog(false);
		connection.startConnectoin(Consts.SITE_URL);
	}

	@Override
	public void onConnectionDone(int code, final Message message) {
		
		final String response = (String) message.obj;
		if (code == Consts.PLAY_LISTS_CONNECTION) {
			handler.sendEmptyMessage(1);
			newIndex = 1;
			new Thread(new Runnable() {
				@Override
				public void run() {
					DBAProcressor.clearData();
					playLists = DataProcessor.getPlayLists(response);
					if(playLists.size()>0){
						DBAProcressor.savePlayListsInDb(playLists);
						handler.sendEmptyMessage(2);
					}else{
						handler.sendEmptyMessage(6);
					}
					
				}
			}).start();
		}
		if(code == Consts.SEARCH_CONNECTION){
			handler.sendEmptyMessage(3);
			JSONArray ja = null;
			try {
				ja = new JSONArray(response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<VideoModel> searchVideos = DataProcessor.getVideos(ja,"0");
			if(searchVideos.size() >0){
				isCurrentIsSearchPlayList=true;
				searchPlayList=new PlayListModel();
				searchPlayList.setTitle("Search for : "+SEARCH_WORD);
				searchPlayList.setVideos(searchVideos);
				uiPlayList.refreshData(searchPlayList);
				if(currentView==VIDEO_VIDE){
					changeView(PLAY_LIST_VIEW);
				}
				mRight.showAbove();
			}else{
				Toast.makeText(c, getString(R.string.no_search_result), Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onConnectionError(int code, String response) {
		Toast.makeText(c, c.getString(R.string.connection_error),
				Toast.LENGTH_SHORT).show();
		dialog.dismiss();
		uiMenu.onRefreshComplete();
	}

	@Override
	public void onConnectionStarted(int code, String response) {
		// TODO Auto-generated method stub

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			switch (message.what) {
			case 0:
				dialog.setMessage(c.getString(R.string.loading_play_lists));
				dialog.show();
				break;
			case 1:
				dialog.setMessage(c.getString(R.string.saving_play_lists));
				break;
			case 2:
				dialog.dismiss();
				Toast.makeText(c, c.getString(R.string.done),
						Toast.LENGTH_SHORT).show();
				showData();
				break;
			case 3:
				dialog.dismiss();  
				break; 
			case 4:
				dialog.setMessage(c.getString(R.string.str_searching)
						+ SEARCH_WORD);
				dialog.show();
				break;
			case 5:
				dialog.dismiss();
				Toast.makeText(c, c.getString(R.string.no_play_lists),Toast.LENGTH_SHORT).show(); 
				break;
			case 6:
				dialog.dismiss();
				Toast.makeText(c, c.getString(R.string.no_play_lists),Toast.LENGTH_SHORT).show(); 
				showData();
				break;

			}

			
		}
	};

	public void changeView(int view) {
		if (view == PLAY_LIST_VIEW) {
			CustomViewAbove p = (CustomViewAbove) uiVideo.getParent();
			p.removeView(uiVideo);
			uiVideo.viewRemoved();
			mRight.setContent(uiPlayList);
			playLists.remove(0);
			playLists.add(0, getFavoPlayList());
			currentView = PLAY_LIST_VIEW;
			if(isCurrentIsSearchPlayList){
				uiPlayList.refreshData(searchPlayList);
			}else{  
				PlayListModel pl = playLists.get(newIndex);
				uiPlayList.refreshData(pl);
			}
			

		}
		if (view == VIDEO_VIDE) {
			CustomViewAbove p = (CustomViewAbove) uiPlayList.getParent();
			p.removeView(uiPlayList);
			mRight.setContent(uiVideo);
			currentView = VIDEO_VIDE;
		}
	}

	@Override
	public void onSetPlayList(int index) {
		isCurrentIsSearchPlayList=false;
		newIndex = index;
		mRight.showAbove();
		if (currentView != PLAY_LIST_VIEW) {
			changeView(PLAY_LIST_VIEW);
			currentView = PLAY_LIST_VIEW;
		}
	}

	@Override
	public void onOpenVideo(VideoModel video) {
		uiVideo.refreshData(video);
		currentView = VIDEO_VIDE;
		changeView(VIDEO_VIDE);

	}

	@Override
	public void onOpened() {
		backPressed = 0;
	}

	@Override
	public void onClosed() {
		backPressed = 0;
		if (newIndex != currentIndex) {
			PlayListModel pl = playLists.get(newIndex);
			uiPlayList.refreshData(pl);
			currentIndex = newIndex;
		}

	}

	@Override
	public void onClose() {

	}

	@Override
	public void onBackPressed() {
		if (currentView == VIDEO_VIDE) {
			changeView(PLAY_LIST_VIEW);
		} else {
			backPressed = backPressed + 1;
			if (backPressed == 2) {
				finish();
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.back_button_pressed),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void reloadData() {
		loadDataFromInternet(null);
	}

	@Override
	public void openInfo() {
		startActivity(new Intent(this, AboutApp.class));
	}

	@Override
	public void showMenu() {
		if (mRight.isBehindShowing()) {
			mRight.showAbove();
		} else {
			mRight.showBehind();
		}
	}

	@Override
	public void goBack() {
		changeView(PLAY_LIST_VIEW);
	}

	public void btnAction(View v) {
		switch (Integer.parseInt(v.getTag().toString())) {
		case 1:
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.about_channel_site))));
			break;
		case 3:
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.about_channel_twitter))));
			break;
		case 4:
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.about_channel_facebook))));
			break;

		}
	}

	@Override
	public void searchFor(String searchWord) {
		SEARCH_WORD = searchWord;
		String searchLink = null;
		try {
			searchLink = c.getString(R.string.SEARCH_URL)+"?id="+ getString(R.string.CHANEEL_ID) + "&search_word=" +URLEncoder.encode(searchWord,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.sendEmptyMessage(4);
		
		ConnectionController2 connection = new ConnectionController2(this,
				Consts.SEARCH_CONNECTION);
		connection.setOnConnectionDone(this);
		connection.setShowLoadingDialog(false);
		connection.startConnectoin(Uri.parse(searchLink).toString());
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	
	
	}
}
