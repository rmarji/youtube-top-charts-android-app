package com.etqanapps.EtqanChannel.Adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MyChannel.R;
import com.etqanapps.EtqanChannel.Controllers.DBAProcressor;
import com.etqanapps.EtqanChannel.DataModel.PlayListModel;
import com.etqanapps.EtqanChannel.UI.UIPlayList;
import com.yahia.libs.CustomViews.ImageFromUrl.ImageFileModel;
import com.yahia.libs.CustomViews.ImageFromUrl.UIImage;
public class UIMenuAdapter extends ArrayAdapter<PlayListModel> {
	int resource;
	Context c;
	DisplayMetrics metrics;
	int dimntion;
	ArrayList<View> views;
	DBAProcressor dba;
	Activity ac;
	int ads_count;
	public UIMenuAdapter(Context context, int resource,List<PlayListModel> playLists,Activity ac) {
		super(context, resource, playLists);
		this.resource = resource;
		this.c = context;
		this.views = new ArrayList<View>();
		metrics = c.getResources().getDisplayMetrics();
		dba=new DBAProcressor();
		this.ac=ac;
		ads_count=0;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		    	RelativeLayout todoView = null;
				final PlayListModel playList = getItem(position);
				ViewHolder holder = new ViewHolder();
				String tag = "" + position + "";

				for (int x = 0; x < views.size(); x++) {
					todoView = (RelativeLayout) views.get(x);
					if (todoView.getTag().equals(tag)) {
						return todoView;
					}
				}
				/*
				 * if (convertView == null) { todoView = new
				 * RelativeLayout(getContext()); String inflater =
				 * Context.LAYOUT_INFLATER_SERVICE; LayoutInflater li = (LayoutInflater)
				 * getContext().getSystemService( inflater); li.inflate(resource,
				 * todoView, true); } else { todoView = (RelativeLayout) convertView; }
				 */
				todoView = new RelativeLayout(getContext());
				String inflater = Context.LAYOUT_INFLATER_SERVICE;
				LayoutInflater li = (LayoutInflater) getContext().getSystemService(
						inflater);
				li.inflate(resource, todoView, true);
				//holder.btn = (Button) todoView.findViewById(R.id.video_item_btn);

				holder.title = (TextView) todoView.findViewById(R.id.textView1);
			 
		 
				holder.title.setText(playList.getTitle());
				holder.img = (UIImage) todoView.findViewById(R.id.imageView1);
				// default.jpg height:90,width:120
				// mqdefault.jpg,height:180,width:320
				// hqdefault.jpg,height:360,width:480
				if(position==0){
					holder.img.pb.setVisibility(View.INVISIBLE);
					holder.img.im.setImageResource(R.drawable.ls_btn_favmenu);
				}else{
					String imageUrl = "http://i.ytimg.com/vi/" + playList.getThumbID()+ "/mqdefault.jpg";
					if(UIPlayList.isTablet(c)){
						imageUrl = "http://i.ytimg.com/vi/" + playList.getThumbID()+ "/hqdefault.jpg";
					} 
					
					holder.img.onAttacheActoin = false;
					holder.img.setImage(new ImageFileModel(imageUrl, null, null), 0, 0);
				}
				

				todoView.setTag(tag);
				views.add(todoView);
				return todoView;
		     
		
		
	}

	static class ViewHolder {
		UIImage img;
		TextView title;
	 
	}

	public int getDPI(int size) {
		return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
	}
}
