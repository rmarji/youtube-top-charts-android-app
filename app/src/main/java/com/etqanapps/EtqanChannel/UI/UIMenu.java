package com.etqanapps.EtqanChannel.UI;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.MyChannel.R;
import com.etqanapps.EtqanChannel.Adapters.UIMenuAdapter;
import com.etqanapps.EtqanChannel.Controllers.Consts;
import com.etqanapps.EtqanChannel.DataModel.PlayListModel;
import com.etqanapps.EtqanChannel.Listeners.MenuActionsListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class UIMenu extends RelativeLayout implements OnItemClickListener {
	PullToRefreshListView lv;
	Context c;
	UIMenuAdapter ad;
	ArrayList<PlayListModel> playLists;
	MenuActionsListener listener;
	AdView ads;
	RelativeLayout footer;
	EditText search;
	Activity ac;

	AdView ad1;
	AdView ad2;
	String AD_UNIT_ID;

	public UIMenu(Context c) {
		super(c);
	}

	public UIMenu(Context context, final MenuActionsListener listener,
			Activity ac) {
		super(context);
		playLists = new ArrayList<PlayListModel>();
		this.listener = listener;
		c = context;
		this.ac = ac;

		String inflater = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				inflater);
		li.inflate(R.layout.ui_menu, this, true);

		// ads = (AdView) findViewById(R.id.adView1);
		ad = new UIMenuAdapter(context, R.layout.row_uimenu_adapter, playLists,
				this.ac);

		/*
		 * LinearLayout header=new LinearLayout(c);
		 * li.inflate(R.layout.header_uimenu, header, true);
		 * lv.addHeaderView(header);
		 */

		RelativeLayout li_header = (RelativeLayout) findViewById(R.id.li_header);
		RelativeLayout li_footer = (RelativeLayout) findViewById(R.id.li_footer);
		footer = new RelativeLayout(c);
		li.inflate(R.layout.footer_uimenu, footer, true);

		AD_UNIT_ID = c.getString(R.string.AD_UNIT_ID);
		if (AD_UNIT_ID.length() > 1) {
			ad1 = new AdView(ac, AdSize.BANNER, AD_UNIT_ID);
			ad2 = (AdView) footer.findViewById(R.id.adView1);

			ad2.setVisibility(View.VISIBLE);

			ad1.loadAd(new AdRequest());
			ad2.loadAd(new AdRequest());

			RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			li_header.addView(ad1,params);

			reLoadAds();
		}

		li_footer.addView(footer);

		lv = (PullToRefreshListView) findViewById(R.id.lv);

		lv.setAdapter(ad);
		lv.setOnItemClickListener(this);

		lv.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(PullToRefreshBase refreshView) {
				listener.reloadData();

			}

		});

		 
		  search = (EditText) footer.findViewById(R.id.searchBox);
		 search.setOnEditorActionListener(new OnEditorActionListener() {
		 
		  @Override public boolean onEditorAction(TextView v, int actionId,
		  KeyEvent event) { if (actionId == EditorInfo.IME_NULL &&
		  event.getAction() == KeyEvent.ACTION_DOWN) { startWordSearch(); }
		  return true; }
		  
		  });
		  
		  search.addTextChangedListener(inputTextWatcher);
		 
	}

	public void reLoadAds() {
		if (ad1 != null) {
			ad1.loadAd(new AdRequest());
			ad2.loadAd(new AdRequest());

			ad1.refreshDrawableState();
			ad2.refreshDrawableState();
		}

	}

	public void refreshData(ArrayList<PlayListModel> playLists) {

		this.playLists.clear();
		this.playLists.addAll(0, playLists);

		ad.notifyDataSetChanged();
		lv.onRefreshComplete();
		if (ad1 != null) {
			reLoadAds();
		}
	}
	public void onRefreshComplete(){
		lv.onRefreshComplete();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int x, long arg3) {
		
		listener.onSetPlayList(x-1);
	}

	public static boolean isTablet(Context c) {
		DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
		int width = displayMetrics.widthPixels / displayMetrics.densityDpi;
		int height = displayMetrics.heightPixels / displayMetrics.densityDpi;

		double screenDiagonal = Math.sqrt(width * width + height * height);
		System.out.println("screenDiagonal : " + screenDiagonal);

		return (screenDiagonal >= 6.0);
	}

	private TextWatcher inputTextWatcher = new TextWatcher() {
		public void afterTextChanged(Editable s) {
			String text = s.toString();
			char[] tex = text.toCharArray();
			if (tex.length > 0) {
				for (int x = 0; x < tex.length; x++) {
					if (tex[x] == '\n') {
						System.out.println("Search text : " + text);
						startWordSearch();
					}
				}

			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

	};

	private void startWordSearch() {
		/*
		 * String
		 * searchLink="http://www.youtube.com/user/"+c.getString(R.string.
		 * CHANEEL_ID)+"/videos?query="+search.getText().toString();
		 * c.startActivity(new Intent(Intent.ACTION_VIEW,
		 * Uri.parse(searchLink)));
		 */
		listener.searchFor(search.getText().toString());
		search.setText("");
		InputMethodManager imm = (InputMethodManager) c
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
	}
}
