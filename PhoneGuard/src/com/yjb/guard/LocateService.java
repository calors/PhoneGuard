/**
 * Project Name:PhoneGuard
 * File Name:LocateService.java
 * Package Name:com.yjb.guard
 * Date:2015-11-4上午10:32:13
 * Copyright (c) 2015, genolance@gmail.com All Rights Reserved.
 *
 */
package com.yjb.guard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * ClassName:LocateService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-11-4 上午10:32:13 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class LocateService extends Service
{
	private static final String TAG = "genolog";
	private ConfUtil mUtil;
	private Context mContext;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		//Log.i(TAG, "locateStart");
		mContext = getApplicationContext();
		mUtil = ConfUtil.getConfUtil(mContext);
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				locate();
			}

		}).start();
		return super.onStartCommand(intent, flags, startId);
	}

	private void locate()
	{
		// 获得定位服务
		LocationManager _manager = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);
		// 获得最后的位置
		Location location = _manager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null)
		{
			location = _manager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		Log.i(TAG, location.toString());
		double lat = location.getLatitude();// 得到纬度
		double lon = location.getLongitude();// 得到经度
		// 把经纬度进行转码得位置描述 后给好友通过短信把位置信息发过去
		Geocoder geocoder = new Geocoder(mContext);
		try
		{
			List<Address> list = geocoder.getFromLocation(lat, lon, 1);
			Address a = list.get(0);// 同一个位置可能有多个描述，拿一个即可
			String msg = a.getLocality();// 得到位置信息
			Log.i(TAG, msg);
			// 把位置信息发给好友
			SmsManager manager = SmsManager.getDefault();
			String telephone = mUtil.getTelephone();
			if (msg.length() > 70)
			{
				ArrayList<String> parts = manager.divideMessage(msg);
				manager.sendMultipartTextMessage(telephone, null, parts, null,
						null);
			}
			else
			{
				manager.sendTextMessage(telephone, null, msg, null, null);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
