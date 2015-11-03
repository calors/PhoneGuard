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
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

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
public class SimReciver extends BroadcastReceiver
{
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	// 协议
	public static final String REMOTE_LOCK = "#lock#";
	public static final String REMOTE_BACK = "#back#";
	public static final String REMOTE_DEL = "#del#";
	public static final String REMOTE_LOCATION = "#locate#";
	public static final String REMOTE_ALARM = "#alarm#";
	private static final String TAG = "genolog";
	private ConfUtil mUtil;
	private ContentResolver mResolver;// 得到内容解析者
	private Context mContext;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		mContext = context;
		mUtil = ConfUtil.getConfUtil(mContext);
		mResolver = context.getContentResolver();
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
				String number = null;
				for (SmsMessage msg : messages)
				{
					// 获取短信内容
					body = msg.getMessageBody();
					number = msg.getOriginatingAddress();
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
						abortBroadcast();// 把广播结束掉，否则小偷也看到这条短信了,就看到密码了,就可以解锁了
						Toast.makeText(mContext, "正在锁屏...", 1).show();
						// 锁屏操作
						mylock();
					}
				}
				// 备份
				if (body != null && body.contains(REMOTE_BACK))
				{
					int len = REMOTE_BACK.length();
					String pwd = body.substring(len);// 截取出短信中的密码
					if (oldPass.equals(pwd))
					{
						Toast.makeText(mContext, "back", 0).show();
					}
				}
				// 删除
				if (body != null && body.contains(REMOTE_DEL))
				{
					int len = REMOTE_DEL.length();
					String pwd = body.substring(len);// 截取出短信中的密码
					if (oldPass.equals(pwd))
					{
						Toast.makeText(mContext, "delete", 0).show();
					}
				}
				// 定位
				if (body != null && body.contains(REMOTE_LOCATION))
				{
					int len = REMOTE_LOCATION.length();
					String pwd = body.substring(len);// 截取出短信中的密码
					if (oldPass.equals(pwd))
					{
						Toast.makeText(mContext, "locate", 0).show();
					}
				}
				// 警报
				if (body != null && body.contains(REMOTE_ALARM))
				{
					int len = REMOTE_ALARM.length();
					String pwd = body.substring(len);// 截取出短信中的密码
					if (oldPass.equals(pwd))
					{
						Toast.makeText(mContext, "alarm", 0).show();
					}
				}
			}// if 判断bundle!=null
		}// if 判断广播消息结束
	}

	private void mylock()
	{
		Intent service = new Intent(mContext, LockScreenService.class);
		mContext.startService(service);
	}
}
