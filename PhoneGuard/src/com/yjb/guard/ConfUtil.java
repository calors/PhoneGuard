/**
 * Project Name:PhoneGuard
 * File Name:ConfUtil.java
 * Package Name:com.yjb.guard
 * Date:2015-11-2下午2:33:35
 * Copyright (c) 2015, genolance@gmail.com All Rights Reserved.
 *
 */
package com.yjb.guard;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * ClassName:ConfUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-11-2 下午2:33:35 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class ConfUtil
{
	public static final String PWD_KEY = "pwd";// 密码
	public static final String ANSWER_KEY = "answer";// 答案
	public static final String QUESTION_KEY = "question";// 问题
	public static final String TELEPHONE_KEY = "telephone";// 好友电话号码
	public static final String SIMNUMBER = "sim";// 保存sim卡的信息
	
	private SharedPreferences mPreferences;
	private Editor mEditor;
	// 密码
	private String mPwd;
	// 问题
	private String mQuestion;
	// 答案
	private String mAnswer;
	// 好友电话
	private String mTelephone;
	// sim卡信息
	private String mSim;

	/**
	 * 
	 */
	public ConfUtil(Context context)
	{
		mPreferences = context.getSharedPreferences("yjb",
				Context.MODE_PRIVATE);
		mEditor = mPreferences.edit();
	}

	public String getPwd()
	{
		return mPreferences.getString(PWD_KEY, "");
	}

	public void setPwd(String pwd)
	{
		mPwd = pwd;
		mEditor.putString(PWD_KEY, mPwd);
		mEditor.commit();// 提交
	}

	public String getQuestion()
	{
		return mPreferences.getString(QUESTION_KEY, "");
	}

	public void setQuestion(String question)
	{
		mQuestion = question;
		mEditor.putString(QUESTION_KEY, mQuestion);
		mEditor.commit();// 提交
	}

	public String getAnswer()
	{
		return mPreferences.getString(ANSWER_KEY, "");
	}

	public void setAnswer(String answer)
	{
		mAnswer = answer;
		mEditor.putString(ANSWER_KEY, mAnswer);
		mEditor.commit();// 提交
	}

	public String getTelephone()
	{
		return mPreferences.getString(TELEPHONE_KEY, "");
	}

	public void setTelephone(String telephone)
	{
		mTelephone = telephone;
		mEditor.putString(TELEPHONE_KEY, mTelephone);
		mEditor.commit();// 提交
	}

	public String getSim()
	{
		return mPreferences.getString(SIMNUMBER, "");
	}

	public void setSim(String sim)
	{
		mSim = sim;
		mEditor.putString(SIMNUMBER, mSim);
		mEditor.commit();// 提交
	}
}
