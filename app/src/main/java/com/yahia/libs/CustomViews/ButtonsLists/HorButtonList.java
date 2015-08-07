package com.yahia.libs.CustomViews.ButtonsLists;
 
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class HorButtonList extends ScrollView{
	 int count;
	 DisplayMetrics metrics;
	 ArrayList<Integer> headerId;
	public HorButtonList(Context context) {
		super(context);
		
		
		 
	}
	public void buildUI(){
		metrics= getResources().getDisplayMetrics();		 
		//this.setBackgroundResource(R.drawable.scrollview_extured_background);
		 
		RelativeLayout play_list_view =getPlayListSkeleton(getContext());
		this.addView(play_list_view,new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		 
		fillPlayList(play_list_view); 
		   
	}
	public RelativeLayout getPlayListSkeleton(Context c){
		 RelativeLayout play_list_view =new RelativeLayout(c);
		 
		 
		 ImageView img=new ImageView(c);
		 //img.setImageResource(R.drawable.tool_bar_bg);
		 img.setLayoutParams(new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,getDPI(22)));
		 img.setScaleType(ScaleType.FIT_XY);
		 img.setId(1234);
		 
		 TextView tv=new TextView(c);
		 RelativeLayout.LayoutParams tv_Layout_params=new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,getDPI(22));
		 tv_Layout_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		 
		 tv.setLayoutParams(tv_Layout_params);
		 tv.setPadding(getDPI(5), getDPI(1), getDPI(20), 0);
		 tv.setTextColor(Color.BLACK);
		 tv.setTextSize(15);
		 tv.setText("Play List");
		    
		 play_list_view.addView(img);
		 play_list_view.addView(tv);
		 return play_list_view;
	}
	
	public void fillPlayList(RelativeLayout play_list){
	 
		
		count =10;
		HorizontalScrollView sc=new HorizontalScrollView(play_list.getContext());
		
		LinearLayout li=new LinearLayout(play_list.getContext()); 
		li.setOrientation(LinearLayout.HORIZONTAL);
		 
		sc.addView(li,new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		
		
		RelativeLayout.LayoutParams sc_params = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		sc_params.addRule(RelativeLayout.BELOW,1234);
		play_list.addView(sc,sc_params);
		
		for(int x=0;x<10;x++){
			LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(getDPI(110),getDPI(125));
			buttonParams.setMargins(getDPI(10), getDPI(5), getDPI(10), getDPI(5));
			LinearLayout playList_view=new LinearLayout(play_list.getContext());
			//playList_view.setBackgroundResource(R.drawable.facebook);
			li.addView(playList_view,buttonParams);			  
		}
	}
	
	public   int getDPI(int size){
	     return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;        
	 }

}
