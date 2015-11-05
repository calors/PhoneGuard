/**
 * Project Name:PhoneGuard
 * File Name:LockScreenService.java
 * Package Name:com.yjb.guard
 * Date:2015-11-3下午10:56:03
 * Copyright (c) 2015, genolance@gmail.com All Rights Reserved.
 *
 */
package com.yjb.guard;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ClassName:LockScreenService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-11-3 下午10:56:03 <br/>
 * 
 * @author jroz
 * @version
 * @since JDK 1.6
 * @see
 */
public class LockScreenService extends Service
{
	private WindowManager mManager;// 窗口管理器
	private ConfUtil mUtil;
	private View view;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		mManager = (WindowManager) getApplicationContext().getSystemService(
				Context.WINDOW_SERVICE);
		mUtil = ConfUtil.getConfUtil(getApplicationContext());
		// 锁屏的界面对应的view布局为lock.xml,lock.xml中有密码输入框和解锁按钮.把view加入wm中，service启动时实现这个view
		view = View.inflate(getApplicationContext(), R.layout.lock, null);// 加载view
		final EditText pass = (EditText) view.findViewById(R.id.etPass);
		Button button = (Button) view.findViewById(R.id.btnUnlock);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String pwd = pass.getText().toString().trim();
				String oldpwd = mUtil.getPwd();
				if (pwd.equals(oldpwd))// 密码相同则解锁
				{
					stopSelf();// 服务停止，屏幕解锁了
				}
				else
				{
					Toast.makeText(getApplicationContext(), "密码不正确",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		// 布局管理器
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.MATCH_PARENT;
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		mManager.addView(view, params);// 把自定义的view加入窗口中
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (mManager != null && view != null)
		{
			mManager.removeView(view);
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
}
