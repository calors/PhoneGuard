/**
 * Project Name:PhoneGuard
 * File Name:ConfUtil.java
 * Package Name:com.yjb.guard
 * Date:2015-11-2下午2:33:35
 * Copyright (c) 2015, genolance@gmail.com All Rights Reserved.
 *
 */
package com.yjb.guard;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

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
	// 自定义的随机字符串，用于生成密匙
	private static final String KEY_STR = "PhoneGuardKEYkey";
	private static final String ALGORITHM = "AES";

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

	// 加密
	private static String encrypt(String Data)
	{
		byte[] encrypt = null;
		try
		{
			// 生成key
			Key key = generateKey(KEY_STR);
			// 密码编译器，参数（“加密算法/模式/其他”）
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 设置为加密模式，传入key
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 加密完成
			encrypt = cipher.doFinal(Data.getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// 以base64编码返回
		return new String(Base64.encode(encrypt, Base64.DEFAULT));
	}

	// 解密
	public static String decrypt(String encryptData)
	{
		if (encryptData.equals(""))
		{
			return "";
		}
		byte[] decrypt = null;
		try
		{
			// 生成key
			Key key = generateKey(KEY_STR);
			// 密码编译器，参数（“加密算法/模式/填充方式”）
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 设置为解密模式，传入key
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 解密完成
			decrypt = cipher
					.doFinal(Base64.decode(encryptData, Base64.DEFAULT));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new String(decrypt).trim();
	}

	// 根据传入的keyStr，生成key
	private static Key generateKey(String key) throws Exception
	{
		try
		{
			/*
			 * // 以AES加密算法，生成128位的密匙 // key生成器，可以生成对称密匙 KeyGenerator kgen =
			 * KeyGenerator.getInstance("AES"); // 初始化生成器，设置输出密匙为128位
			 * kgen.init(128, new SecureRandom(key.getBytes()));
			 */
			// SecretKeySpec，用于构建秘密密钥规范
			SecretKeySpec keySpec = new SecretKeySpec(KEY_STR.getBytes(),
					ALGORITHM);
			return keySpec;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public String getPwd()
	{
		return decrypt(mPreferences.getString(PWD_KEY, ""));
	}

	public void setPwd(String pwd)
	{
		mPwd = pwd;
		mEditor.putString(PWD_KEY, encrypt(mPwd));
		mEditor.commit();// 提交
	}

	public String getQuestion()
	{
		return decrypt(mPreferences.getString(QUESTION_KEY, ""));
	}

	public void setQuestion(String question)
	{
		mQuestion = question;
		mEditor.putString(QUESTION_KEY, encrypt(mQuestion));
		mEditor.commit();// 提交
	}

	public String getAnswer()
	{
		return decrypt(mPreferences.getString(ANSWER_KEY, ""));
	}

	public void setAnswer(String answer)
	{
		mAnswer = answer;
		mEditor.putString(ANSWER_KEY, encrypt(mAnswer));
		mEditor.commit();// 提交
	}

	public String getTelephone()
	{
		return decrypt(mPreferences.getString(TELEPHONE_KEY, ""));
	}

	public void setTelephone(String telephone)
	{
		mTelephone = telephone;
		mEditor.putString(TELEPHONE_KEY, encrypt(mTelephone));
		mEditor.commit();// 提交
	}

	public String getSim()
	{
		return decrypt(mPreferences.getString(SIMNUMBER, ""));
	}

	public void setSim(String sim)
	{
		mSim = sim;
		mEditor.putString(SIMNUMBER, encrypt(mSim));
		mEditor.commit();// 提交
	}
}
