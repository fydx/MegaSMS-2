package com.unique.megasms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Welcome extends Activity {
	private Handler handler;
	private boolean first = true;
	public static ArrayList<HashMap<String, Object>> maps;
	private int position = 0;
	private SharedPreferences sp;
	private int count1;
	private Timer timer;
	private TimerTask task;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);
		
		handler = new Handler();
		maps = new ArrayList<HashMap<String, Object>>();
	
		// 判断是否为第一次进入程序
		sp = getSharedPreferences("COUNT", Context.MODE_PRIVATE);
		count1 = sp.getInt("COUNT", 0);
		Editor e = sp.edit();
		e.putInt("COUNT", ++count1);
		Log.i("firstboot", String.valueOf(count1));
		e.commit();
		// 判断结束 count1=1 为第一次进入该程序
		
		
//		{
//		 final Intent intent = new Intent(Welcome.this,
//					MainActivity.class);
//			timer = new Timer();
//			task = new TimerTask() {
//				@Override
//				public void run() {
//					startActivity(intent);
//					finish(); // 执行
//				}
//			};
//			}
//		timer.schedule(task, 1000 * 2);
		handler.post(r);
		
	}
	private Runnable r = new Runnable() {

		@Override
		public void run() {
			if (first) {
				handler.postDelayed(r, 5000);

				Cursor cursor = getContentResolver().query(
						ContactsContract.Contacts.CONTENT_URI, null, null,
						null, null);
				if (cursor != null) {
					cursor.moveToFirst();
					while (!cursor.isAfterLast()) {
						String id = cursor.getString(cursor
								.getColumnIndex(ContactsContract.Contacts._ID));
						String name = cursor
								.getString(cursor
										.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

						int ishas = cursor
								.getInt(cursor
										.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
						if (ishas == 0) {
							cursor.moveToNext();
							continue;
						}
						System.out.println(id + " | " + name + " | " + ishas);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("_id", id);
						map.put("position", position++);
						map.put("name", name);
						maps.add(map);
						cursor.moveToNext();
					}
				}
				cursor.close();
				first = false;
			} else {
				handler.removeCallbacks(r);
				finish();
				if(count1 == 1)
				{
					Intent intent2 = new Intent(Welcome.this,
							FirstBootActivity.class);
					startActivity(intent2);
				}else
				{Intent intent = new Intent(Welcome.this,
						FirstBootActivity.class);
				startActivity(intent);}
			}
		}
	};
	
}
