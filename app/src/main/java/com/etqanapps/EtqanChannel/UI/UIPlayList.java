package com.etqanapps.EtqanChannel.UI;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MyChannel.R;
import com.etqanapps.EtqanChannel.Adapters.PlayListAdapterSliding;
import com.etqanapps.EtqanChannel.Controllers.Consts;
import com.etqanapps.EtqanChannel.DataModel.PlayListModel;
import com.etqanapps.EtqanChannel.DataModel.VideoModel;
import com.etqanapps.EtqanChannel.Listeners.MenuActionsListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class UIPlayList extends RelativeLayout implements OnItemClickListener,
		OnClickListener {
	ListView lv;
	TextView tv_title;
	Button btn_open_menu;
	Button btn_open_info;

	ArrayList<VideoModel> feed;
	Context c;
	PlayListAdapterSliding ad;
	MenuActionsListener listener;
	Activity ac;

	AdView ad1;
	AdView ad2;
	String AD_UNIT_ID;

	public UIPlayList(Context context, MenuActionsListener listener, Activity ac) {
		super(context);
		feed = new ArrayList<VideoModel>();
		this.listener = listener;
		c = context;
		this.ac = ac;

		String inflater = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				inflater);
		li.inflate(R.layout.ui_play_list, this, true);

		lv = (ListView) findViewById(R.id.lv);
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_open_menu = (Button) findViewById(R.id.btn_open_menu);
		btn_open_info = (Button) findViewById(R.id.btn_open_info);

		btn_open_menu.setOnClickListener(this);
		btn_open_info.setOnClickListener(this);

		AD_UNIT_ID = c.getString(R.string.AD_UNIT_ID);
		if (AD_UNIT_ID.length() > 1) {
			initAds();
		}
		lv.setAdapter(ad);
	}

	private void initAds() {
		ad1 = new AdView(ac, AdSize.SMART_BANNER, AD_UNIT_ID);
		ad2 = new AdView(ac, AdSize.SMART_BANNER, AD_UNIT_ID);

		lv.addHeaderView(ad1);
		lv.addFooterView(ad2);

		reLoadAds();

	}

	public void reLoadAds() {
		ad1.loadAd(new AdRequest());
		ad2.loadAd(new AdRequest());

	}

	public UIPlayList refreshData(PlayListModel pl) {
		feed.clear();
		feed.addAll(0, pl.getVideos());
		ad = new PlayListAdapterSliding(c, R.layout.row_video, feed, ac);

		tv_title.setText(pl.getTitle());

		lv.setAdapter(ad);
		lv.setOnItemClickListener(this);
		ad.notifyDataSetChanged();

		if (ad1 != null) {
			reLoadAds();
		}

		return this;
	}

	public static boolean isTablet(Context c) {
		DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
		int width = displayMetrics.widthPixels / displayMetrics.densityDpi;
		int height = displayMetrics.heightPixels / displayMetrics.densityDpi;

		double screenDiagonal = Math.sqrt(width * width + height * height);
		System.out.println("screenDiagonal : " + screenDiagonal);

		return (screenDiagonal >= 6.0);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		VideoModel video = null;
		String AD_UNIT_ID = c.getString(R.string.AD_UNIT_ID);

		if (AD_UNIT_ID.length() > 1) {
			video = feed.get(index - 1);
		} else {
			video = feed.get(index);
		}

		if (video != null) {
			listener.onOpenVideo(video);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == btn_open_info) {
			listener.openInfo();
		} else if (v == btn_open_menu) {
			listener.showMenu();
		}

	}

}
