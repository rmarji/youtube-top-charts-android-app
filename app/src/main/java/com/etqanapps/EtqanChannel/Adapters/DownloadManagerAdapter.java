package com.etqanapps.EtqanChannel.Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MyChannel.R;
import com.etqanapps.EtqanChannel.Controllers.DBAProcressor;
import com.etqanapps.EtqanChannel.DataModel.VideoModel;
import com.etqanapps.EtqanChannel.UI.UIPlayList;
import com.yahia.libs.CustomViews.ImageFromUrl.ImageFileModel;
import com.yahia.libs.CustomViews.ImageFromUrl.UIImage;

public class DownloadManagerAdapter extends ArrayAdapter<VideoModel> {
	int resource;
	Context c;
	
	int dimntion;
	
	DBAProcressor dba;

	public DownloadManagerAdapter(Context context, int resource,
			List<VideoModel> videos) {
		super(context, resource, videos);
		this.resource = resource;
		this.c = context;
		
		dba=new DBAProcressor();
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout todoView = null;
		final VideoModel video = getItem(position);
		ViewHolder holder = new ViewHolder();
		
		todoView = new RelativeLayout(getContext());
		String inflater = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				inflater);
		li.inflate(resource, todoView, true);
		

		holder.title = (TextView) todoView.findViewById(R.id.video_item_title);
		 
		
		holder.imgFavo=(ImageView)todoView.findViewById(R.id.imgFavo);
		if(dba.checkFavoStatus(video)){
			holder.imgFavo.setImageResource(R.drawable.vv_btn_favhover);
		}else{
			holder.imgFavo.setImageResource(R.drawable.vv_btn_fav);
		}
		
		holder.img = (UIImage) todoView.findViewById(R.id.video_item_image);
		String imageUrl = "http://i.ytimg.com/vi/" + video.getID()+ "/mqdefault.jpg";
		if(UIPlayList.isTablet(c)){
			imageUrl = "http://i.ytimg.com/vi/" + video.getID()+ "/hqdefault.jpg";
		} 
		
		holder.img.onAttacheActoin = false;
		holder.img.setImage(new ImageFileModel(imageUrl, null, null), 0, 0);

		holder.title.setText(video.getTitle());
 
		return todoView;
	}

	static class ViewHolder {
		UIImage img;
		TextView title;
		ImageView imgFavo;
		TextView	 tv_download_rate;
		TextView	 tv_tv_download_speed;
		RelativeLayout re_container;
	}

 
}
