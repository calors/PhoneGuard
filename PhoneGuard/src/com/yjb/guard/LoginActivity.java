package com.yjb.guard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{
	private EditText mPwdEditText;
	private ConfUtil mUtil;
	private Context mContext;
	private Intent mIntent;
	public static final int REQUESTCODE = 1;// 请求码
	public static final int RESULTCODE = 2;// 结果码

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;
		initView();
	}

	private void initView()
	{
		mPwdEditText = (EditText) findViewById(R.id.edLoginPwd);
		mUtil = ConfUtil.getConfUtil(mContext);
	}

	// 登录按钮点击
	public void btnLoginClick(View view)
	{
		String _pwd = mPwdEditText.getText().toString().trim();
		mIntent = new Intent();
		if (mUtil.getPwd().equals(_pwd))
		{
			mIntent.setClass(mContext, HomeActivity.class);
			startActivity(mIntent);
			finish();
		}
		else
		{
			Toast.makeText(mContext, "密码错误", Toast.LENGTH_SHORT).show();
			mPwdEditText.setText("");
		}
	}

	// 忘记密码按钮点击
	public void btnLostPwdClick(View view)
	{
		mIntent = new Intent();
		mIntent.setClass(mContext, FindPassActivity.class);
		startActivityForResult(mIntent, REQUESTCODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == REQUESTCODE && resultCode == RESULTCODE)
		{
			String _pwd = null;
			if (data != null)
			{
				_pwd = (String) data.getExtras().get("pwd");
			}
			mPwdEditText.setText(_pwd);
			Toast.makeText(mContext, "密码已找回，可直接登陆", Toast.LENGTH_SHORT).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
