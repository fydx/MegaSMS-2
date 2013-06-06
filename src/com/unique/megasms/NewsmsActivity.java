package com.unique.megasms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.R.integer;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NewsmsActivity extends Activity {
	private ImageView addContact;
	private Button sendsmsButton, addnameButton;
	private EditText inputEditText, numberEditText;
	public static ArrayList<HashMap<String, String>> backArrayList;

	private SQLiteDatabase db;
	private MyOpenHelper helper;
	int i = 1;

	/* 自定义ACTION常数，作为广播的Intent Filter识别常数 */
	private String SMS_SEND_ACTIOIN = "SMS_SEND_ACTIOIN";
	private String SMS_DELIVERED_ACTION = "SMS_DELIVERED_ACTION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newsms);
		inputEditText = (EditText) findViewById(R.id.smsinput);
		numberEditText = (EditText) findViewById(R.id.text_number);
		backArrayList = new ArrayList<HashMap<String, String>>();
		helper = new MyOpenHelper(this, null, null, 0);
		db = helper.getWritableDatabase();
		helper.onUpgrade(db, 0, 1);
		addnameButton = (Button) findViewById(R.id.button_addname);
		addnameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageSpan imageSpan = new ImageSpan(NewsmsActivity.this,
						BitmapFactory.decodeResource(getResources(),
								R.drawable.contact));
				SpannableString spannableString = new SpannableString("#name#");
				spannableString.setSpan(imageSpan, 0, spannableString.length(),
						SpannableString.SPAN_MARK_MARK);
				inputEditText.append(spannableString);

			}
		});
		addContact = (ImageView) findViewById(R.id.addcontact);
		addContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(NewsmsActivity.this, ContactActivity.class);
				startActivity(intent);
			}
		});
		sendsmsButton = (Button) findViewById(R.id.sendsms);
		sendsmsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("smscontent", inputEditText.getText().toString());
				Log.e("empty", String.valueOf(backArrayList.isEmpty()));
				Log.e("size", String.valueOf(backArrayList.size()));
				sendMessage(inputEditText.getText().toString(), backArrayList);
				Intent intent = new Intent(NewsmsActivity.this, MyService.class);
				startService(intent);
				intent =  new Intent();
				intent.setClass(NewsmsActivity.this, StatusActivity.class);
				startActivity(intent);
				finish();

			}
		});
	}

	public void sendMessage(String input,
			ArrayList<HashMap<String, String>> maps) {
		Intent itSend = new Intent(SMS_SEND_ACTIOIN);
		Intent itDeliver = new Intent(SMS_DELIVERED_ACTION);

		/* sentIntent参数为传送后接受的广播信息PendingIntent */
		PendingIntent mSendPI = PendingIntent.getBroadcast(
				getApplicationContext(), 0, itSend, 0);

		/* deliveryIntent参数为送达后接受的广播信息PendingIntent */
		PendingIntent mDeliverPI = PendingIntent.getBroadcast(
				getApplicationContext(), 0, itDeliver, 0);

		for (HashMap<String, String> contact : maps) {
			String sendString = input.replaceAll("#name#", contact.get("name"));
			// 数据库数据
			Log.e("smscontent", sendString);
			if (sendString != null) {
				SmsManager sms = SmsManager.getDefault();
				// 如果短信没有超过限制长度，则返回一个长度的List。
				List<String> texts = sms.divideMessage(sendString);
				for (String text : texts) {
					// 发送短信
					sms.sendTextMessage(contact.get("number"), null, text,
							mSendPI, mDeliverPI);
				}
				ContentValues cv = new ContentValues();
				cv.put("name", contact.get("name").toString());
				cv.put("number", contact.get("number").toString());
				cv.put("status", 0);
				cv.put("text", sendString);
				db.insert("list_table", null, cv);
				Intent intent = new Intent(NewsmsActivity.this, MyService.class);
				startService(intent);
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		String textString = new String();
		if (backArrayList.size() != 0) {
			textString += backArrayList.get(0).get("number");
			for (int i = 1; i < backArrayList.size(); i++) {
				textString += ", " + backArrayList.get(i).get("number");
			}
		}
		System.out.println(textString);
		numberEditText.setText(textString);

	}

}