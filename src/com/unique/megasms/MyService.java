package com.unique.megasms;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

public class MyService extends Service {

	private String SMS_SEND_ACTIOIN = "SMS_SEND_ACTIOIN";
	private String SMS_DELIVERED_ACTION = "SMS_DELIVERED_ACTION";
	private receive mReceiver01, mReceiver02;
	private MyOpenHelper helper;
	private SQLiteDatabase db;
	private int num = 0;

	@Override
	public void onCreate() {
		super.onCreate();
		helper = new MyOpenHelper(this, null, null, 0);
		db = helper.getWritableDatabase();
		Log.e("service","start");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		IntentFilter mFilter01;
		mFilter01 = new IntentFilter(SMS_SEND_ACTIOIN);
		mReceiver01 = new receive();
		registerReceiver(mReceiver01, mFilter01);

		/* 自定义IntentFilter为DELIVERED_SMS_ACTION Receiver */
		mFilter01 = new IntentFilter(SMS_DELIVERED_ACTION);
		mReceiver02 = new receive();
		registerReceiver(mReceiver02, mFilter01);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/* 自定义mServiceReceiver覆盖BroadcastReceiver聆听短信状态信息 */
	public class receive extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String actionString = intent.getAction();

			try {
				/* android.content.BroadcastReceiver.getResultCode()方法 */
				if (actionString.equals(SMS_SEND_ACTIOIN)) {
					switch (getResultCode()) {
					case Activity.RESULT_OK:
						/* 发送短信成功 */
						System.out.println("OK");
						Cursor cursor = db.query("list_table", null, null,
								null, null, null, null);
						String name = cursor.getString(cursor
								.getColumnIndex("name"));
						String number = cursor.getString(cursor
								.getColumnIndex("number"));
						String text = cursor.getString(cursor
								.getColumnIndex("text"));
						ContentValues cv = new ContentValues();
						cv.put("name", name);
						cv.put("number", number);
						cv.put("status", 1);
						cv.put("text", text);
						db.replace("list_table", "id=" + num, cv);
						break;
					default:
						System.out.println("ERROR");
						break;
					}
				} else if (actionString.equals(SMS_DELIVERED_ACTION)) {
					switch (getResultCode()) {
					case Activity.RESULT_OK:
						/* 接收短信成功 */
						break;
					default:
						break;
					}
				} else if (actionString
						.equals("android.provider.Telephony.SMS_RECEIVED")) {
					System.out.println("sdfsldjfjsdjlfjlsdjfl");
					Bundle bundle = intent.getExtras();
					if (null != bundle) {
						Object[] pdus = (Object[]) bundle.get("pdus");
						SmsMessage[] smg = new SmsMessage[pdus.length];
						for (int i = 0; i < pdus.length; i++) {
							smg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
							Log.i(1 + "smg" + i, smg[i].toString());
						}
						for (SmsMessage cursmg : smg) {
							String codeStr = cursmg.getDisplayMessageBody();
							String codeStr2 = cursmg
									.getDisplayOriginatingAddress();
							String codeStr3 = cursmg.getMessageBody();
							String codeStr6 = cursmg.getOriginatingAddress();
							Log.i(1 + "codeStr", codeStr);
							Log.i(1 + "codeStr2", codeStr2);
							Log.i(1 + "codeStr3", codeStr3);
							// Log.i(1 + "codeStr5", codeStr5);
							Log.i(1 + "codeStr6", codeStr6);
						}
					}

				}
			} catch (Exception e) {
				e.getStackTrace();
			}

		}
	}

}
