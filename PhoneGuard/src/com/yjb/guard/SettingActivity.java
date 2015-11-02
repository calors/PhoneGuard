package com.yjb.guard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingActivity extends Activity
{
	private ConfUtil mUtil;
	private Context mContext;
	private TelephonyManager mManager;
	// 密码框
	private EditText mPwd;
	// 确认密码框
	private EditText mPwdChcek;
	// 问题框
	private Spinner mQuestion;
	// 答案框
	private EditText mAnswer;
	// 好友号码框
	private EditText mTelephone;
	// 问题列表
	private String[] mQuestionList;
	// 选中的问题
	private String mSelectedQuestion;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		mContext = this;
		initView();
	}

	private void initView()
	{
		// 通过系统服务,获取电话管理器
		mManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		mUtil = ConfUtil.getConfUtil(mContext);
		mPwd = (EditText) findViewById(R.id.edPwd);
		mPwdChcek = (EditText) findViewById(R.id.edPwdCheck);
		mQuestion = (Spinner) findViewById(R.id.spQuestion);
		mAnswer = (EditText) findViewById(R.id.edAnswer);
		mTelephone = (EditText) findViewById(R.id.edTelephone);
		// 从资源文件获取问题列表
		mQuestionList = getResources().getStringArray(R.array.questionList);
		// 设置适配器和监听
		ArrayAdapter<CharSequence> _adapter = ArrayAdapter.createFromResource(
				mContext, R.array.questionList,
				android.R.layout.simple_list_item_1);
		mQuestion.setAdapter(_adapter);
		mQuestion.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				mSelectedQuestion = mQuestionList[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * 
	 * btnCommitClick:(设置按钮点击后保存到配置文件). <br/>
	 * 
	 * @author Administrator
	 * @param view
	 * @since JDK 1.6
	 */
	public void btnCommitClick(View view)
	{
		// 密码
		String _pwd = mPwd.getText().toString().trim();
		// 确认密码
		String _pwdCheck = mPwdChcek.getText().toString().trim();
		// 答案
		String _answer = mAnswer.getText().toString().trim();
		// 好友电话
		String _telephone = mTelephone.getText().toString().trim();
		// Sim卡序列号
		String _Sim_numer = mManager.getSimSerialNumber();
		// 判断各项是否为空
		if (TextUtils.isEmpty(_pwd) || TextUtils.isEmpty(_pwdCheck)
				|| TextUtils.isEmpty(_answer) || TextUtils.isEmpty(_telephone))
		{
			Toast.makeText(mContext, "每项均需填写，不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		// 判断两次密码输入是否一致
		if (!_pwd.equals(_pwdCheck))
		{
			Toast.makeText(mContext, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
			return;
		}
		// 如果输入正确，则保存
		mUtil.setAnswer(_answer);
		mUtil.setPwd(_pwd);
		mUtil.setQuestion(mSelectedQuestion);
		mUtil.setSim(_Sim_numer);
		mUtil.setTelephone(_telephone);
		// 设置成功 跳转到登录界面
		Toast.makeText(mContext, "设置成功", Toast.LENGTH_SHORT).show();
		startActivity(new Intent(mContext, LoginActivity.class));
		finish();
	}
}
