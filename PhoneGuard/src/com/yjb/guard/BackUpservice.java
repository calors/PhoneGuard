/**
 * Project Name:PhoneGuard
 * File Name:BackUpservice.java
 * Package Name:com.yjb.guard
 * Date:2015-11-3下午11:39:36
 * Copyright (c) 2015, genolance@gmail.com All Rights Reserved.
 *
 */
package com.yjb.guard;

import java.util.ArrayList;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

/**
 * ClassName:BackUpservice <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-11-3 下午11:39:36 <br/>
 * 
 * @author jroz
 * @version
 * @since JDK 1.6
 * @see
 */
public class BackUpservice extends Service
{
	private ConfUtil mUtil;
	private ContentResolver mResolver;
	private Context mContext;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		mContext = getApplicationContext();
		mResolver = getContentResolver();
		mUtil = ConfUtil.getConfUtil(mContext);
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] columns1 = { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME };
		Cursor cursor = mResolver.query(uri, columns1, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while (cursor.moveToNext())
		{
			int id = cursor.getInt(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			buffer.append(name + ":");
			// 查出这个人有多少个电话: 由id获取对应这个id的电话
			Uri uri2 = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			String comlums2[] = { ContactsContract.CommonDataKinds.Phone.NUMBER };
			Cursor cursor2 = mResolver.query(uri2, comlums2,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
					new String[] { String.valueOf(id) }, null);
			while (cursor2.moveToNext())
			{
				String phone = cursor2
						.getString(cursor2
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				buffer.append(phone + "/");
			}
		}
		// 查找完成，发短信
		String telephone = mUtil.getTelephone();
		SmsManager manager = SmsManager.getDefault();
		ArrayList<String> parts = manager.divideMessage(buffer.toString());
		manager.sendMultipartTextMessage(telephone, null, parts, null, null);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
