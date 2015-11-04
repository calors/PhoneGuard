/**
 * Project Name:PhoneGuard
 * File Name:CheckSimReciver.java
 * Package Name:com.yjb.guard
 * Date:2015-11-3下午9:16:10
 * Copyright (c) 2015, genolance@gmail.com All Rights Reserved.
 *
 */
package com.yjb.guard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/**
 * ClassName:CheckSimReciver <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-11-3 下午9:16:10 <br/>
 * 
 * @author jroz
 * @version
 * @since JDK 1.6
 * @see
 */
public class CheckSimReciver extends BroadcastReceiver
{
	private TelephonyManager mManager;
	private ConfUtil mUtil;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		mManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		mUtil = ConfUtil.getConfUtil(context);
		// 获取保存的sim卡序列号
		String _oldSim = mUtil.getSim();
		// 获取现在sim的序列号
		String _newSim = mManager.getSimSerialNumber();
		if (!_newSim.equals(_oldSim))
		{
			// 获取设置的好号码
			String _telePhone = mUtil.getTelephone();
			// 获取短信管理器直接发送短信，不经过系统短信
			SmsManager _smsManager = SmsManager.getDefault();
			_smsManager.sendTextMessage(_telePhone, null, "新卡的手机号", null, null);
		}
	}
}
