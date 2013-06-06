package com.unique.megasms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class StatusActivity extends Activity {
	private SimpleAdapter mSchedule;
	private String statusString;
	private String namesString;
	private List<String> names;
	private List<Integer> status;
	// private ItemAdapter itemAdapter;
	private List<HashMap<String, Object>> mylist;
	private MyOpenHelper helper;
	private SQLiteDatabase db;
	private int[] images;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_status);
		helper = new MyOpenHelper(this, null, null, 0);
		db = helper.getWritableDatabase();
		
		images = new int[] { R.drawable.status_red, R.drawable.status_yellow,
				R.drawable.status_green };
		statusString = getIntent().getStringExtra("status");
		namesString = getIntent().getStringExtra("name");
		mylist = new ArrayList<HashMap<String, Object>>();

		Cursor cursor = db.query("list_table", null, null, null, null, null,
				null);
		System.out.println(cursor.getCount());
		if (cursor != null) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", cursor.getString(cursor.getColumnIndex("name")));
				map.put("number",
						cursor.getString(cursor.getColumnIndex("number")));
				map.put("text", cursor.getString(cursor.getColumnIndex("text")));
				map.put("status",
						images[cursor.getInt(cursor.getColumnIndex("status"))]);
				Log.e("map", map.toString());
				mylist.add(map);
				cursor.moveToNext();
			}
		}
		ListView listView = (ListView) findViewById(R.id.listView_status);
		SimpleAdapter adapter = new SimpleAdapter(this, mylist,
				R.layout.listitem_status, new String[] { "name", "status" },
				new int[] { R.id.textView_status, R.id.imageView_bar });
		listView.setAdapter(adapter);
	}

	// class ItemAdapter extends BaseAdapter {
	//
	// private class ViewHolder {
	// public TextView name;
	// public ImageView image;
	// }
	//
	// @Override
	// public int getCount() {
	// return mylist.size();
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// return position;
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// return position;
	// }
	//
	// @Override
	// public View getView(final int position, View convertView,
	// ViewGroup parent) {
	// View view = convertView;
	// final ViewHolder holder;
	// if (convertView == null) {
	// view = getLayoutInflater().inflate(R.layout.listitem_status,
	// parent, false);
	// holder = new ViewHolder();
	// holder.name = (TextView) view
	// .findViewById(R.id.textView_status);
	// holder.image = (ImageView) view
	// .findViewById(R.id.imageView_bar);
	// view.setTag(holder);
	// } else {
	// holder = (ViewHolder) view.getTag();
	// }
	// holder.name.setText((String) mylist.get(position).get("name"));
	// if ((Integer) mylist.get(position).get("status") == 0) {
	// holder.image.setImageDrawable(getResources().getDrawable(
	// R.drawable.status_red));
	//
	// }
	// if ((Integer) mylist.get(position).get("status") == 1) {
	// holder.image.setImageDrawable(getResources().getDrawable(
	// R.drawable.status_yellow));
	//
	// }
	// if ((Integer) mylist.get(position).get("status") == 2) {
	// holder.image.setImageDrawable(getResources().getDrawable(
	// R.drawable.status_green));
	//
	// }
	// return view;
	//
	// }
	//
	// }
}