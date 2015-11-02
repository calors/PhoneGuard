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
	private static ConfUtil instance = null;
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
	 * 二进制转十六进制
	 * 
	 * @param bytes
	 * @return
	 */
	/*
	 * private static String bytesToHex(byte[] bytes) { StringBuffer md5str =
	 * new StringBuffer(); // 把数组每一字节换成16进制连成md5字符串 for (int digital : bytes) {
	 * if (digital < 0) { digital += 256;// 此处注意，计算机是按补码存储，所以加256 //
	 * 例如果是负数如-1，则其补码为11111111，对应为255 } if (digital < 16) {
	 * md5str.append("0");// 两位16进制数表示，00~FF，小于十六则第一位为0 }
	 * md5str.append(Integer.toHexString(digital)); } return
	 * md5str.toString().toUpperCase(); }
	 */
	/**
	 * 
	 * md5Encrytor:MD5加密. <br/>
	 * 
	 * @author jroz
	 * @param value
	 * @return MD5值的十六进制字符串
	 * @since JDK 1.6
	 */
	/*
	 * public String md5Encrytor(String value) { MessageDigest md5 = null;
	 * byte[] result = null; try { // 1.创建一个提供信息摘要算法的对象，初始化为md5算法对象,可以填写AES，RSA等
	 * md5 = MessageDigest.getInstance("MD5"); // 2.value.getBytes() 转换成字节数组 //
	 * 3.计算后获得字节数组（MD5加密，128位） result = md5.digest(value.getBytes()); } catch
	 * (NoSuchAlgorithmException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } // 将计算得到的md5值转换成16进制，节省空间 return
	 * bytesToHex(result); }
	 */
	public static ConfUtil getConfUtil(Context context)
	{
		if (instance == null)
		{
			synchronized (ConfUtil.class)
			{
				if (instance == null)
				{
					instance = new ConfUtil(context);
				}
			}
		}
		return instance;
	}

	private ConfUtil(Context context)
	{
		mPreferences = context
				.getSharedPreferences("yjb", Context.MODE_PRIVATE);
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
