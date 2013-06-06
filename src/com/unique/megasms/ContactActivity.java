package com.unique.megasms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

public class ContactActivity extends Activity implements OnItemClickListener {

	private ListView listView;
	private ArrayList<HashMap<String, Object>> maps;
	private ArrayList<HashMap<String, String>> backArrayList;
	private myAdapter adapter;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contact);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, // 设置此样式为自定义样式
		// R.layout.title // 设置对应的布局
		// );// 自定义布局赋值
		maps = Welcome.maps;
		button = (Button) findViewById(R.id.commit);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NewsmsActivity.backArrayList = backArrayList;
				ContactActivity.this.finish();
				finish();
			}
		});
		backArrayList = new ArrayList<HashMap<String, String>>();
		listView = (ListView) findViewById(R.id.listView_contact);
		adapter = new myAdapter(this, maps, R.layout.listitem_contact,
				new String[] { "name" }, new int[] { R.id.name });
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		CheckBox checkBox = (CheckBox) view.findViewById(R.id.check);
		checkBox.toggle();
		adapter.map.put(position, checkBox.isChecked());
		HashMap<String, String> item = new HashMap<String, String>();
		item.put("name", maps.get(position).get("name").toString());
		String _id = maps.get(position).get("_id").toString();
		Cursor c = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + _id,
				null, null);
		c.moveToFirst();
		String number = c.getString(c
				.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		c.close();
		item.put("number", number);
		if (checkBox.isChecked()) {
			backArrayList.add(item);
		} else {
			backArrayList.remove(item);
		}

		System.out.println(item);
		if (adapter.map.get(position)) {
			view.setBackgroundColor(Color.BLUE);
		} else {
			view.setBackgroundColor(Color.WHITE);
		}
	}
}