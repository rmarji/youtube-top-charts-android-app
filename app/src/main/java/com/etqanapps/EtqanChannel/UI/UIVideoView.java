package com.etqanapps.EtqanChannel.UI;

import java.text.NumberFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MyChannel.R;
import com.etqanapps.EtqanChannel.Ac_Vv;
import com.etqanapps.EtqanChannel.Controllers.Consts;
import com.etqanapps.EtqanChannel.Controllers.DBAProcressor;
import com.etqanapps.EtqanChannel.Controllers.DataProcessor;
import com.etqanapps.EtqanChannel.DataModel.VideoModel;
import com.etqanapps.EtqanChannel.Listeners.MenuActionsListener;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.yahia.libs.CustomViews.ImageFromUrl.ImageFileModel;
import com.yahia.libs.CustomViews.ImageFromUrl.UIImage;
import com.yahia.libs.InternetConnections.ConnectionController2;
import com.yahia.libs.InternetConnections.onConnectionController;

public class UIVideoView extends RelativeLayout implements OnClickListener,
		onConnectionController {
	VideoModel video;

	TextView tv_dec;
	TextView tv_title;
	TextView tv_title_header;
	TextView tv_viewsCount;
	TextView tv_date;
	TextView tv_rating;
	TextView tv_duration;
	UIImage img;
	Button btnOpenVideo;
	Button btnOpenShare;
	Button btnFavo;
	Button btnDownload;
	Button btnOpenLink;
	Button btnHome;
	
	static Context c;
	MenuActionsListener listener;

	// AdView ads1;
	// AdView ads2;
	DBAProcressor dba;
	Boolean FavoStatus;
	static ProgressDialog dialog;
	Activity ac;
	
	AdView ad1;
	AdView ad2;
	String AD_UNIT_ID;
	
	public UIVideoView(Context c ){
		super(c);
	}
	public UIVideoView(Context context, MenuActionsListener listener,Activity ac) {
		super(context);
		video = new VideoModel();
		this.listener = listener;
		c = context;
		dba = new DBAProcressor();
		this.ac=ac;
		
		dialog = new ProgressDialog(c);
		dialog.setCancelable(false);

		String inflater = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				inflater);
		AD_UNIT_ID=c.getString(R.string.AD_UNIT_ID);
		int view = 0; 
			 
		if(AD_UNIT_ID.length()>1){	
			view = R.layout.ui_video_view_with_ads;
			
		}else{
			view = R.layout.ui_video_view;
			
		}

		li.inflate(view, this, true);

		// init Views
		tv_title = (TextView) findViewById(R.id.vv_tv_title);
		tv_title_header = (TextView) findViewById(R.id.vv_tv_title_header);
		tv_dec = (TextView) findViewById(R.id.vv_tv_desc);
		tv_viewsCount = (TextView) findViewById(R.id.vv_tv_views_count);
		tv_date = (TextView) findViewById(R.id.vv_tv_date);
		tv_rating = (TextView) findViewById(R.id.vv_tv_rating);
		tv_duration = (TextView) findViewById(R.id.vv_tv_duration);

		img = (UIImage) findViewById(R.id.video_img);
		// ads1 = (AdView) findViewById(R.id.adView1);
		// ads2 = (AdView) findViewById(R.id.adView2);
		btnOpenVideo = (Button) findViewById(R.id.btnOpenVideo);
		btnOpenShare = (Button) findViewById(R.id.btnShare);
		btnFavo = (Button) findViewById(R.id.btnFavo);
		btnDownload = (Button) findViewById(R.id.btnDownload);
		btnOpenLink = (Button) findViewById(R.id.btnOpenLink);
		btnHome = (Button) findViewById(R.id.btnHome);
		// btnDownload.setVisibility(Button.VISIBLE);

		btnOpenShare.setOnClickListener(this);
		btnDownload.setOnClickListener(this);
		btnOpenVideo.setOnClickListener(this);
		btnFavo.setOnClickListener(this);
		btnOpenLink.setOnClickListener(this);
		btnHome.setOnClickListener(this);
		
		
		if(AD_UNIT_ID.length()>1){			 
			ad1=(AdView)findViewById(R.id.adView1);
			ad2=(AdView)findViewById(R.id.adView2);
			
			}
	}

	private void reLoadAds() {
		if(ad1!=null){
			ad1.loadAd(new AdRequest());
			ad2.loadAd(new AdRequest());
		}
		
	}

	public void refreshData(VideoModel video) {
		this.video = video;
		reLoadAds();

		String imageUrl = "http://i.ytimg.com/vi/" + video.getID()
				+ "/mqdefault.jpg";
		if (isTablet()) {
			imageUrl = "http://i.ytimg.com/vi/" + video.getID()
					+ "/hqdefault.jpg";
		}

		img.setImage(new ImageFileModel(imageUrl, null, null), 320, 180);

		final NumberFormat formatter = NumberFormat.getNumberInstance(Locale
				.getDefault());

		formatter.setMaximumFractionDigits(2);

		String date = "";
		try {
			date = video.getPublished().substring(0, 10);
		} catch (Exception e) {
			date = c.getResources().getString(R.string.not_set);
		}

		String viewCount = "";
		try {
			viewCount = formatter.format(Double.parseDouble(video
					.getViewCount()));
		} catch (Exception ex) {
			viewCount = c.getResources().getString(R.string.not_set);
		}

		String ratings = null;
		try {
			ratings = formatter.format(Double.parseDouble(video.getRating()));
		} catch (Exception ex) {
			ratings = c.getResources().getString(R.string.not_set);
		}

		String duration = "";
		try { 

			formatter.setMaximumFractionDigits(2);
			int durationInt = Integer.parseInt(video.getDuration());
			Double minutes = Math.floor(durationInt / 60.0);
			Double seconds = Math.floor((durationInt) - (minutes * 60));
			
			String str_min=formatter.format(minutes);
			if(minutes<=9){
				str_min="0"+formatter.format(minutes);
			} 
			
			String str_sec=formatter.format(seconds);
			if(seconds<=9){
				str_sec="0"+formatter.format(seconds);
			} 
			 
			duration = str_min + " : "+ str_sec;
		} catch (Exception e) {
			duration = c.getResources().getString(R.string.not_set);
		}

		

		/*String html = "";
		 * if (c.getResources().getString(R.string.app_lang)
		 * .equals(Consts.LANG_EN)) { html =
		 * "<html  ><head><style>*{	line-height: 25px;text-align: left;	direction: ltr;	color: white;background: #ccc;font-size: 20px;}table{width: 100%25;}text{padding-left: 10px;	font-size: 13px!important;color: white;	text-align: right !important;	display: block;}value{	font-size: 13px!important;}</style><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>	<table  >	<tr>	<td><text>Duration :</text> </td>	<td><value>"
		 * + duration +
		 * "</value>  </td>	<td><text>Date :</text>  </td>	<td><value>" + date +
		 * "</value>  </td>	</tr>	<tr>	<td><text>Views :</text>  </td>	<td><value>"
		 * + viewCount +
		 * "  </value>  </td>	<td><text>  Rating :</text>  </td>	<td><value>" +
		 * ratings + "</value>  </td>	</tr> 	</table></body></html>"; } else {
		 * html =
		 * "<html  ><head><style>*{	line-height: 25px;text-align: right;	direction: rtl;	color: white;background: #ccc;font-size: 20px;}table{width: 100%25;}text{	font-size: 13px!important;color: white;	text-align: left !important;	display: block;}value{	font-size: 13px!important;}</style><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>	<table  >	<tr>	<td><text>مدة الفيديو :</text> </td>	<td><value>"
		 * + duration +
		 * "</value>  </td>	<td><text>رفع بتاريخ :</text>  </td>	<td><value>"
		 * + date +
		 * "</value>  </td>	</tr>	<tr>	<td><text>عدد المشاهدات :</text>  </td>	<td><value>"
		 * + viewCount +
		 * "</value>  </td>	<td><text>التقييم :</text>  </td>	<td><value>"
		 * + ratings + "</value>  </td>	</tr> 	</table></body></html>"; }
		 */
		tv_title.setText(video.getTitle());
		tv_title_header.setText(video.getTitle());
		tv_date.setText(date);
		tv_duration.setText(duration);
		tv_rating.setText(ratings);
		tv_viewsCount.setText(viewCount);
		tv_dec.setText(video.getDescription());

		Linkify.addLinks(tv_dec, Linkify.ALL);

		FavoStatus = dba.checkFavoStatus(video);
		checkFaoStatus();

	}

	private void checkFaoStatus() {
		if (FavoStatus) {
			btnFavo.setBackgroundResource(R.drawable.vv_btn_favhover);
		} else {
			btnFavo.setBackgroundResource(R.drawable.vv_btn_fav);
		}
	}

	private void processFavos() {
		if (FavoStatus) {
			dba.removeFavorites(video);
		} else {
			dba.insertintFavorites(video);
		}
		FavoStatus = !FavoStatus;
		checkFaoStatus();
	}

	public static boolean isTablet() {
		DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
		int width = displayMetrics.widthPixels / displayMetrics.densityDpi;
		int height = displayMetrics.heightPixels / displayMetrics.densityDpi;

		double screenDiagonal = Math.sqrt(width * width + height * height);
		System.out.println("screenDiagonal : " + screenDiagonal);

		return (screenDiagonal >= 6.0);
	}

	public void viewRemoved() {
		img.removeImage();
	}

	@Override
	public void onClick(View v) {
		if(v==btnHome){
			listener.goBack();
		}else 		if (v == btnOpenShare) {
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String videoLink = "http://www.youtube.com/watch?v="
					+ video.getID();
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, c
					.getResources().getString(R.string.share_text_1)
					+ " "
					+ video.getTitle()
					+ " "
					+ videoLink
					+ " "
					+ c.getResources().getString(R.string.share_text_2));
			c.startActivity(Intent.createChooser(sharingIntent, c
					.getResources().getString(R.string.share_text_3)));
		} else if (v == btnOpenVideo) {
			// Open Video Chooser
			 Intent intent =new Intent(ac,Ac_Vv.class);
			 intent.putExtra("video_id", video.getID());
			 c.startActivity(intent);
			/*
			c.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("http://www.youtube.com/watch?v=" + video.getID())));*/
		} else if ( v == btnOpenLink) {
			c.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("http://www.youtube.com/watch?v=" + video.getID())));
		}else if (v == btnFavo) {
			// Favorites
			processFavos();
		} else if (v == btnDownload) {
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
				String link = "http://www.youtube.com/get_video_info?video_id="
						+ video.getID() + "&fmt=18";
				ConnectionController2 connection = new ConnectionController2(c,
						0);
				connection.setOnConnectionDone(this);
				connection.setShowLoadingDialog(true);
				connection.startConnectoin(link);

			} else {
				Toast.makeText(c,
						c.getString(R.string.feature_not_supported_for_2_2),
						Toast.LENGTH_LONG).show();
			}
		}

	}
 

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			switch (message.what) {

			case 2:
				Toast.makeText(c, c.getString(R.string.download_started),
						Toast.LENGTH_SHORT).show();
				break;

			}
		}
	};

	@Override
	public void onConnectionDone(int code, Message message) {
		String downloadLink = DataProcessor.getYoutubeDownloadLink(message.obj
				.toString());
		if (downloadLink == null) {
			Toast.makeText(c, "null", Toast.LENGTH_SHORT).show();
		} else {
			System.out.println("downloadLink : "+Uri.parse(downloadLink));
			/*
			 * 
			Toast.makeText(c, c.getString(R.string.download_started),
					Toast.LENGTH_SHORT).show();
			// Start Download
			DownloadManager dm = (DownloadManager) c
					.getSystemService(Context.DOWNLOAD_SERVICE);
			Request request = new Request(Uri.parse(downloadLink));
			enqueue = dm.enqueue(request);*/
		}
	}

	@Override
	public void onConnectionError(int code, String response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionStarted(int code, String response) {
		// TODO Auto-generated method stub

	}
}
