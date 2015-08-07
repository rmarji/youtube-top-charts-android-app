package com.etqanapps.EtqanChannel;

import android.os.Bundle;

import com.MyChannel.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
public class Ac_Vv extends YouTubeBaseActivity implements OnInitializedListener {
	String video_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vv);
		video_id=getIntent().getExtras().getString("video_id");
		System.out.println("video_id : "+video_id);
		
		YouTubePlayerView player=(YouTubePlayerView)findViewById(R.id.youtube_view);
		
		player.initialize("AIzaSyD2srti8B5DPHCXnIokNeJo4I5sjJwjWHs", this);
	}

	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		arg1.getErrorDialog(this,0).show();
		
		System.out.println("arg0 : "+arg0);
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer player,
			boolean wasRestored) {
		if (!wasRestored) {
		      player.loadVideo(video_id);
		    }
		
	}
}
