package com.yjb.guard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity
{
	private ConfUtil mUtil;
	private Context mContext;
	private Intent mIntent;
	// 获取配置文件中的密码
	private String mPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		mUtil = new ConfUtil(mContext);
		mIntent = new Intent();
		if (isFirstLogin())
		{
			mIntent.setClass(mContext, SettingActivity.class);
			startActivity(mIntent);
		}
	}
	/**
	 * 
	 * isFirstLogin:(判断是否是第一次运行程序). <br/>
	 * TODO(在程序启动的时候，调用该方法，判断用户是否第一次安装启动).<br/>
	 * TODO(通过读取配置文件，判断用户是否设置了密码。如果没有，则返回true，否则返回false).<br/>
	 *
	 * @author Administrator
	 * @return 
	 * @since JDK 1.6
	 */
	private boolean isFirstLogin()
	{
		mPwd = mUtil.getPwd();
		if (mPwd.equals(""))
		{
			return true;
		}
		return false;
	}
}
