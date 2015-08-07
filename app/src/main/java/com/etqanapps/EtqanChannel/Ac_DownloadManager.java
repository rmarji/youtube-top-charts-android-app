package com.etqanapps.EtqanChannel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.MyChannel.R;
public class Ac_DownloadManager extends Activity implements OnItemClickListener{
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_play_list);
		
		lv=(ListView)findViewById(R.id.lv);
		lv.setOnItemClickListener(this);
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		// TODO Auto-generated method stub
		
	}
}
