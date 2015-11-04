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
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.telephony.SmsManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

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
	private static final int UPDATE_TIME = 5000;
	private ConfUtil mUtil;
	private Context mContext;
	private String address = null;
	private LocationClient mLocationClient = null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		mContext = getApplicationContext();
		mUtil = ConfUtil.getConfUtil(mContext);
		mLocationClient = new LocationClient(mContext);
		// 新开线程，防止阻塞
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
		// 设置定位条件
		LocationClientOption _option = new LocationClientOption();
		_option.setOpenGps(true);// 是否打开GPS
		_option.setLocationMode(LocationMode.Hight_Accuracy);// 高精度定位,GPS，网络同时使用
		_option.setCoorType("bd09ll");// 设置返回值的坐标类型。
		_option.setIsNeedLocationDescribe(true);// 设置是否需要位置语义化结果，
		_option.setIsNeedAddress(true);// 设置是否需要地址信息
		_option.setPriority(LocationClientOption.NetWorkFirst);// 设置定位优先级
		_option.setProdName("LocationDemo"); // 设置产品线名称。
		_option.setScanSpan(UPDATE_TIME);// 设置定时定位的时间间隔。单位毫秒
		mLocationClient.setLocOption(_option);
		mLocationClient.registerLocationListener(new BDLocationListener()
		{
			@Override
			public void onReceiveLocation(BDLocation location)
			{
				if (location.getAddrStr() == null)
				{
					return;
				}
				address = location.getAddrStr();// 获取地址
				sendAddress();// 发送消息给好友
				mLocationClient.stop();// 停止定位
			}
		});
		mLocationClient.start();// 开始定位
		// 开启定时器，以便决定什么时候调用google的API进行查询
		final Timer _timer = new Timer();
		TimerTask _task = new TimerTask()
		{
			@Override
			public void run()
			{
				if (address == null)
				{
					// 百度定位不到，则调用google的api
					mLocationClient.stop();// 停止定位
					locateWithGoogleMap();
					_timer.purge();// 移除任务
				}
			}
		};
		_timer.schedule(_task, 1000 * 60);// 60秒后，判断百度sdk是否获取到地址
	}

	private void sendAddress()
	{
		// 把位置信息发给好友
		SmsManager manager = SmsManager.getDefault();
		String telephone = mUtil.getTelephone();
		if (address.length() > 70)
		{
			ArrayList<String> parts = manager.divideMessage(address);
			manager.sendMultipartTextMessage(telephone, null, parts, null, null);
		}
		else
		{
			manager.sendTextMessage(telephone, null, address, null, null);
		}
	}

	private void locateWithGoogleMap()
	{
		// 获得定位服务
		LocationManager _manager = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);
		// 获得最后的位置
		Location location = _manager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		// GPS获取不到，则用网络获取
		if (location == null)
		{
			location = _manager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		double lat = location.getLatitude();// 得到纬度
		double lon = location.getLongitude();// 得到经度
		// 把经纬度进行转码得位置描述 后给好友通过短信把位置信息发过去
		Geocoder geocoder = new Geocoder(mContext);
		try
		{
			List<Address> list = geocoder.getFromLocation(lat, lon, 1);
			Address a = list.get(0);// 同一个位置可能有多个描述，拿一个即可
			address = a.getLocality();// 得到位置信息
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		sendAddress();
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
}
