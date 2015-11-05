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
import android.provider.BaseColumns;
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
public class BackUpService extends Service
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
		// 启用新线程 避免阻塞
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				backUp();
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}

	private void backUp()
	{
		// 联系人的URI
		Uri _uri = ContactsContract.Contacts.CONTENT_URI;
		// 设置查询的列：ID和名字
		String[] selection = { BaseColumns._ID,
				ContactsContract.Contacts.DISPLAY_NAME };
		// 查询
		Cursor _cursor = mResolver.query(_uri, selection, null, null, null);
		// buffer用来缓存所有的联系人的信息
		StringBuffer _buffer = new StringBuffer();
		while (_cursor.moveToNext())
		{
			// 获得ID
			int _ID = _cursor.getInt(_cursor.getColumnIndex(BaseColumns._ID));
			// 获得名字
			String _name = _cursor.getString(_cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			_buffer.append(_name + ":");
			// 查出这个人有多少个电话: 由id获取对应这个id的电话
			Uri _uri2 = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			// 设置查询的列：number
			String selection2[] = { ContactsContract.CommonDataKinds.Phone.NUMBER };
			// 通过id查询
			Cursor cursor2 = mResolver.query(_uri2, selection2,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
					new String[] { String.valueOf(_ID) }, null);
			while (cursor2.moveToNext())
			{
				// 获得电话号码
				String phone = cursor2
						.getString(cursor2
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				_buffer.append(phone + "/");
			}
		}
		// 查找完成，发短信
		String _telephone = mUtil.getTelephone();// 获得好友号码
		SmsManager _manager = SmsManager.getDefault();// 获得消息管理器
		String msg = _buffer.toString();
		if (msg.length() == 0)
		{
			return;
		}
		if (msg.length() > 70)
		{
			// 把短信拆分
			ArrayList<String> parts = _manager.divideMessage(msg);
			_manager.sendMultipartTextMessage(_telephone, null, parts, null,
					null);
		}
		else
		{
			_manager.sendTextMessage(_telephone, null, msg, null, null);
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
}
