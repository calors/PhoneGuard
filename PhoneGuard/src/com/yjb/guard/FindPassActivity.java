package com.yjb.guard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FindPassActivity extends Activity
{
	private ConfUtil mUtil;
	private Context mContext;
	private Spinner mSpinner;
	private EditText mEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_pass);
		mContext = this;
		initView();
	}

	private void initView()
	{
		mUtil = ConfUtil.getConfUtil(mContext);
		mSpinner = (Spinner) findViewById(R.id.spQuestionFindPwd);
		mEditText = (EditText) findViewById(R.id.edAnswerFindPwd);
		ArrayAdapter<CharSequence> _adapter = ArrayAdapter.createFromResource(
				mContext, R.array.questionList,
				android.R.layout.simple_list_item_1);
		mSpinner.setAdapter(_adapter);
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub
			}
		});
	}

	public void btnFindPwdClick(View view)
	{
		String _question = (String) mSpinner.getSelectedItem();
		String _answer = mEditText.getText().toString().trim();
		if (_question.equals(mUtil.getQuestion())
				&& _answer.equals(mUtil.getAnswer()))
		{
			Intent data = new Intent();
			data.putExtra("pwd", mUtil.getPwd());
			setResult(LoginActivity.RESULTCODE, data);
			finish();
		}
		else
		{
			Toast.makeText(mContext, "问题或者答案错误", Toast.LENGTH_SHORT).show();
		}
	}
}
