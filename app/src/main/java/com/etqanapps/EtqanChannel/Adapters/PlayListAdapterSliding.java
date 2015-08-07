package com.etqanapps.EtqanChannel.Adapters;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
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

public class PlayListAdapterSliding extends ArrayAdapter<VideoModel> {
	int resource;
	Context c;
	DisplayMetrics metrics;
	int dimntion;
	ArrayList<View> views;
	DBAProcressor dba;
	Activity ac;
	int ads_count=0;
	public PlayListAdapterSliding(Context context, int resource,
			List<VideoModel> videos,Activity ac) {
		super(context, resource, videos);
		this.resource = resource;
		this.c = context;
		this.views = new ArrayList<View>();
		metrics = c.getResources().getDisplayMetrics();
		dba = new DBAProcressor();
		this.ac=ac;
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
			 * Context.LAYOUT_INFLATER_SERVICE; LayoutInflater li =
			 * (LayoutInflater) getContext().getSystemService( inflater);
			 * li.inflate(resource, todoView, true); } else { todoView =
			 * (RelativeLayout) convertView; }
			 */
			todoView = new RelativeLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(
					inflater);
			li.inflate(resource, todoView, true);
			// holder.btn = (Button) todoView.findViewById(R.id.video_item_btn);

			holder.title = (TextView) todoView
					.findViewById(R.id.video_item_title);
			holder.tv_duration = (TextView) todoView
					.findViewById(R.id.tv_duration);

			String duration = "";
			try {
				final NumberFormat formatter = NumberFormat
						.getNumberInstance(Locale.getDefault());

				formatter.setMaximumFractionDigits(2);
				int durationInt = Integer.parseInt(video.getDuration());
				Double minutes = Math.floor(durationInt / 60.0);
				Double seconds = Math.floor((durationInt) - (minutes * 60));

				String str_min = formatter.format(minutes);
				if (minutes <=9) {
					str_min = "0" + formatter.format(minutes);
				}

				String str_sec = formatter.format(seconds);
				if (seconds <=9) {
					str_sec = "0" + formatter.format(seconds);
				}

				duration = str_min + " : " + str_sec;
			} catch (Exception e) {
				duration = c.getResources().getString(R.string.not_set);
			}
			holder.tv_duration.setText(duration);
			/*
			 * holder.btn.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { Intent n = new Intent(c,
			 * Ac_Video.class); n.putExtra("video_id", video.getID());
			 * c.startActivity(n);
			 * 
			 * } }) ;
			 */

			holder.img_favorited = (ImageView) todoView
					.findViewById(R.id.img_favorited);
			if (dba.checkFavoStatus(video)) {
				holder.img_favorited.setImageResource(R.drawable.btn_favhover);
			} else {
				holder.img_favorited.setImageResource(R.drawable.btn_fav);
			}

			holder.img = (UIImage) todoView.findViewById(R.id.video_item_image);
			// default.jpg height:90,width:120
			// mqdefault.jpg,height:180,width:320
			// hqdefault.jpg,height:360,width:480
			String imageUrl = "http://i.ytimg.com/vi/" + video.getID()
					+ "/mqdefault.jpg";
			if (UIPlayList.isTablet(c)) {
				imageUrl = "http://i.ytimg.com/vi/" + video.getID()
						+ "/hqdefault.jpg";
			}

			holder.img.onAttacheActoin = false;
			holder.img.setImage(new ImageFileModel(imageUrl, null, null), 0, 0);

			holder.title.setText(video.getTitle());

			todoView.setTag(tag);
			views.add(todoView);
			return todoView;

	}

	static class ViewHolder {
		UIImage img;
		TextView title;
		ImageView img_favorited;
		ImageView img_downloaded;
		TextView tv_duration;
		// Button btn;
		RelativeLayout re_container;
	}

	public int getDPI(int size) {
		return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
	}
}
