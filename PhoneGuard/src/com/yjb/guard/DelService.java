/**
 * Project Name:PhoneGuard
 * File Name:DelService.java
 * Package Name:com.yjb.guard
 * Date:2015-11-4上午10:09:17
 * Copyright (c) 2015, genolance@gmail.com All Rights Reserved.
 *
 */
package com.yjb.guard;

import java.io.File;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.MediaStore.MediaColumns;

/**
 * ClassName:DelService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-11-4 上午10:09:17 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class DelService extends Service
{
	private ContentResolver mResolver;
	private Context mContext;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO Auto-generated method stub
		mContext = getApplicationContext();
		mResolver = mContext.getContentResolver();
		deleteContacts();
		deleteAudio();
		deleteImage();
		deleteVideo();
		return super.onStartCommand(intent, flags, startId);
	}

	// 删除电话本
	void deleteContacts()
	{
		Uri uri = ContactsContract.RawContacts.CONTENT_URI
				.buildUpon()
				.appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER,
						"true").build();
		mResolver.delete(uri, null, null);
	}

	// 删除音频
	void deleteAudio()
	{
		Cursor cs = mResolver.query(
				android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaColumns.DATA }, null, null, null);
		while (cs.moveToNext())
		{
			String path = cs.getString(0);
			File file = new File(path);
			file.delete();
		}
	}

	// 删除图像
	void deleteImage()
	{
		Cursor cs = mResolver.query(
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaColumns.DATA }, null, null, null);
		while (cs.moveToNext())
		{
			String path = cs.getString(0);
			File file = new File(path);
			file.delete();
		}
	}

	// 删除视频
	void deleteVideo()
	{
		Cursor cs = mResolver.query(
				android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaColumns.DATA }, null, null, null);
		while (cs.moveToNext())
		{
			String path = cs.getString(0);
			File file = new File(path);
			file.delete();
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
