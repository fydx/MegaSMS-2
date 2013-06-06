package com.unique.megasms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.bool;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class myAdapter extends SimpleAdapter {

	LayoutInflater mInflater;
	private List<? extends Map<String, ?>> mList;
	private TextView name;
	HashMap<Integer, Boolean> map;

	public myAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		map = new HashMap<Integer, Boolean>();
		mInflater = LayoutInflater.from(context);
		mList = data;
		for (int i = 0; i < data.size(); i++) {
			map.put(i, false);
		}
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_contact, null);
		}

		name = (TextView) convertView.findViewById(R.id.name);
		name.setText(mList.get(position).get("name").toString());

		CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.check);
		checkBox.setChecked(map.get(position));
		if (map.get(position)) {
			convertView.setBackgroundColor(Color.GRAY);
		} else {
			convertView.setBackgroundColor(Color.WHITE);
		}
		System.out.println(map.get(position));

		return convertView;
	}

}
