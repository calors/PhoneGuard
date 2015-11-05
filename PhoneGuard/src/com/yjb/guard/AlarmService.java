/**
 * Project Name:PhoneGuard
 * File Name:AlarmService.java
 * Package Name:com.yjb.guard
 * Date:2015-11-4上午11:24:04
 * Copyright (c) 2015, genolance@gmail.com All Rights Reserved.
 *
 */
package com.yjb.guard;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * ClassName:AlarmService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-11-4 上午11:24:04 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class AlarmService extends Service
{
	private Context mContext;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		mContext = getApplicationContext();
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				alarm();
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}

	void alarm()
	{
		MediaPlayer mPlayer = MediaPlayer.create(mContext, R.raw.alarm);
		// 循环播放
		mPlayer.setLooping(true);
		mPlayer.start();
		long stop = System.currentTimeMillis() + 1000 * 60 * 3;// 播放3分钟
		while (System.currentTimeMillis() >= stop)
		{
			mPlayer.stop();
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
