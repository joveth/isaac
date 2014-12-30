package com.jov.isaac.adapter;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jov.isaac.R;
import com.jov.isaac.adapter.util.StringUtil;
import com.jov.isaac.db.ToolBean;

public class DataAdapter extends BaseAdapter {
	private List<ToolBean> list;
	private Context ctx;

	public DataAdapter(Context ctx, List<ToolBean> list) {
		this.list = list;
		this.ctx = ctx;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		final Holder hold;
		if (arg1 == null) {
			hold = new Holder();
			arg1 = View.inflate(ctx, R.layout.item, null);
			hold.image = (ImageView) arg1.findViewById(R.id.icon_img);
			hold.title = (TextView) arg1.findViewById(R.id.tool_name);
			hold.desc = (TextView) arg1.findViewById(R.id.tool_desc);
			hold.other = (TextView) arg1.findViewById(R.id.tool_other);
			hold.detail = (LinearLayout) arg1.findViewById(R.id.icon_panel);
			arg1.setTag(hold);
		} else {
			hold = (Holder) arg1.getTag();
		}
		try {
			if(!StringUtil.isEmpty(list.get(position).getImg())){
				hold.image.setImageBitmap(BitmapFactory.decodeStream(ctx
						.getAssets().open(list.get(position).getImg())));
			}else{
				hold.image.setImageBitmap(null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		hold.title.setText(list.get(position).getName() + "["
				+ list.get(position).getEnName() + "]");
		hold.desc.setText(list.get(position).getDesc());
		String power = list.get(position).getPower();
		if (!StringUtil.isEmpty(power)) {
			power += "，";
		}
		hold.other.setText(power + list.get(position).getUnlock());
		hold.detail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
			}
		});
		return arg1;
	}

	static class Holder {
		ImageView image;
		TextView title;
		TextView desc;
		TextView other;
		LinearLayout detail;
	}
}