package com.etqanapps.EtqanChannel;
 
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.MyChannel.R;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
public class AboutApp extends Activity{
	AdView ad1; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub 
		super.onCreate(savedInstanceState);
		
		initContentView();
		
		if(getString(R.string.AD_UNIT_ID).length()>1){
			ad1=(AdView)findViewById(R.id.adView1);
			ad1.setVisibility(View.VISIBLE);
			ad1.loadAd(new AdRequest() );
			
		}
		 
	}
	private void initContentView() {
		setContentView(R.layout.ac_about_app);
		
	}
	public void btnAction(View v){ 
		switch (Integer.parseInt(v.getTag().toString())) {
		case 1:
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_app_site))));
			break;
		case 2:
			Intent i = new Intent(Intent.ACTION_SEND); 
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_EMAIL  , new String[]{getString(R.string.about_app_mail)});
			i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.about_app_mail_subject));
			i.putExtra(Intent.EXTRA_TEXT   ,"");
			try {
			    startActivity(Intent.createChooser(i,getString(R.string.about_app_mail_chooser_title)));
			} catch (android.content.ActivityNotFoundException ex) {
			    Toast.makeText(this, getString(R.string.about_app_mail_client_notFound), Toast.LENGTH_SHORT).show();
			}
			break;
		case 3:
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_app_twitter))));
			break;
		case 4:
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_app_facebook))));
			break;
		case 5:
			finish();
			break;

		 
		}
	}
	
	public   boolean isTablet() {
		DisplayMetrics displayMetrics =  getResources().getDisplayMetrics();
		int width = displayMetrics.widthPixels / displayMetrics.densityDpi;
		int height = displayMetrics.heightPixels / displayMetrics.densityDpi;

		double screenDiagonal = Math.sqrt(width * width + height * height);
		System.out.println("screenDiagonal : " + screenDiagonal);

		return (screenDiagonal >= 6.0);
	}

}
