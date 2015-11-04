/**
 * Project Name:PhoneGuard
 * File Name:SimReciver.java
 * Package Name:com.yjb.guard
 * Date:2015-11-3下午10:00:47
 * Copyright (c) 2015, genolance@gmail.com All Rights Reserved.
 *
 */
package com.yjb.guard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * ClassName:SimReciver <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-11-3 下午10:00:47 <br/>
 * 
 * @author jroz
 * @version
 * @since JDK 1.6
 * @see
 */
public class SmsReciver extends BroadcastReceiver
{
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	// 协议
	public static final String REMOTE_LOCK = "#lock#";
	public static final String REMOTE_BACK = "#back#";
	public static final String REMOTE_DEL = "#del#";
	public static final String REMOTE_LOCATION = "#locate#";
	public static final String REMOTE_ALARM = "#alarm#";
	private ConfUtil mUtil;
	private Context mContext;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		mContext = context;
		mUtil = ConfUtil.getConfUtil(mContext);
		// 侦听短信的广播后从收到的短信中获取短信的发送方和短信内容,自行处理
		String action = intent.getAction();
		if (SMS_RECEIVED_ACTION.equals(action))
		{
			// 获取intent参数
			Bundle bundle = intent.getExtras();
			// 判断bundle内容
			if (bundle != null)
			{
				// 取pdus内容,转换为Object[]
				Object[] pdus = (Object[]) bundle.get("pdus");
				// 解析短信
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < messages.length; i++)
				{
					byte[] pdu = (byte[]) pdus[i];
					messages[i] = SmsMessage.createFromPdu(pdu);
				}
				// 解析完内容后分析具体参数
				String body = null;
				String phoneNumber = null;
				for (SmsMessage msg : messages)
				{
					// 获取短信内容
					body = msg.getMessageBody();
					phoneNumber = msg.getOriginatingAddress();
				}
				// 不是好友的号码,不执行操作。防止误删
				if (!phoneNumber.endsWith(mUtil.getTelephone()))
				{
					return;
				}
				String oldPass = mUtil.getPwd();// 取出保存的密码 密码一致才执行各种功能
				// 和协议比较
				// 1.远程锁屏发过来的短信格式是 #lock#pass
				if (body != null && body.contains(REMOTE_LOCK))// 判断是锁屏
				{
					int len = REMOTE_LOCK.length();
					String pwd = body.substring(len);// 截取出短信中的密码
					if (oldPass.equals(pwd))
					{
						abortBroadcast();// 把广播结束掉，否则小偷也看到这条短信了
						// 锁屏操作
						lock();
					}
				}
				// 备份
				if (body != null && body.contains(REMOTE_BACK))
				{
					int len = REMOTE_BACK.length();
					String pwd = body.substring(len);// 截取出短信中的密码
					if (oldPass.equals(pwd))
					{
						abortBroadcast();// 把广播结束掉，否则小偷也看到这条短信了
						back();
					}
				}
				// 删除
				if (body != null && body.contains(REMOTE_DEL))
				{
					int len = REMOTE_DEL.length();
					String pwd = body.substring(len);// 截取出短信中的密码
					if (oldPass.equals(pwd))
					{
						abortBroadcast();// 把广播结束掉，否则小偷也看到这条短信了
						delete();
					}
				}
				// 定位
				if (body != null && body.contains(REMOTE_LOCATION))
				{
					int len = REMOTE_LOCATION.length();
					String pwd = body.substring(len);// 截取出短信中的密码
					if (oldPass.equals(pwd))
					{
						abortBroadcast();// 把广播结束掉，否则小偷也看到这条短信了
						locate();
					}
				}
				// 警报
				if (body != null && body.contains(REMOTE_ALARM))
				{
					int len = REMOTE_ALARM.length();
					String pwd = body.substring(len);// 截取出短信中的密码
					if (oldPass.equals(pwd))
					{
						abortBroadcast();// 把广播结束掉，否则小偷也看到这条短信了
						alarm();
					}
				}
			}// if 判断bundle!=null
		}// if 判断广播消息结束
	}

	private void alarm()
	{
		Intent service = new Intent(mContext, AlarmService.class);
		mContext.startService(service);
	}

	private void locate()
	{
		Intent service = new Intent(mContext, LocateService.class);
		mContext.startService(service);
	}

	private void delete()
	{
		Intent service = new Intent(mContext, DelService.class);
		mContext.startService(service);
	}

	private void back()
	{
		Intent service = new Intent(mContext, BackUpService.class);
		mContext.startService(service);
	}

	private void lock()
	{
		Intent service = new Intent(mContext, LockScreenService.class);
		mContext.startService(service);
	}
}
