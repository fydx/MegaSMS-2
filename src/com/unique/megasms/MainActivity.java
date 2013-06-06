package com.unique.megasms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	private List<content_sms> contentList = null; //父列表
	private SimpleAdapter mSchedule;
	private Button newsmsButton;

	private ImageView guideImageView;
	ArrayList<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_main);

	
		contentList = new  ArrayList<content_sms>();
		newsmsButton = (Button)findViewById(R.id.button_writesms);
		newsmsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, NewsmsActivity.class);
				startActivity(intent);
			}
		});
		int a= 0;
		int b= 1;
		int c = 2;
		String name1 = "name1";
		String name2 = "name2";
		String name3 = "name3";
		List<Integer> status_test = new ArrayList<Integer>();
		status_test.add(new Integer(a));
		status_test.add(new Integer(b));
		status_test.add(new Integer(c));
		List<String> names_test = new  ArrayList<String>();
		names_test.add(name1);
		names_test.add(name2);
		names_test.add(name3);
		
		
		content_sms content_sms_1 = new content_sms("111","name1#name2#name3#","#5554#5556#5554#","0#1#1");
		content_sms content_sms_2 = new content_sms("222","name4#name5#name6#","#5554#5556#5554#","0#1#1");
		contentList.add(content_sms_1);
		contentList.add(content_sms_2);
		ListView listView = (ListView)findViewById(R.id.listview_main);
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		for (content_sms sms : contentList) {
			// sb.append(food.toString());
			HashMap<String, String> map = new HashMap<String, String>();
			//map.put("date", sms.getDatesString());
			map.put("content", sms.getContentString());
			mylist.add(map);
		}
		
		 mSchedule = new SimpleAdapter(this, 
				mylist,
				R.layout.listitem_main,// ListItem
				//  key
				new String[] { "content" },
				// ListItem 的 TextView ID
				new int[] {  R.id.textView_content });
		         // 设置并且显示
			listView.setDividerHeight(0);
		    listView.setAdapter(mSchedule);
		    listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent2 = new Intent();
					intent2.setClass(MainActivity.this, StatusActivity.class);
					intent2.putExtra("name", contentList.get(position).getNames());
					intent2.putExtra("status", contentList.get(position).getStatuses());
					startActivity(intent2);
				}
		    	
		    	
			});
		
		 
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	 
}
