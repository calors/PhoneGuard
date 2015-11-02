package com.yjb.guard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		TextView _textView = new TextView(this);
		_textView.setText(R.string.text_help);
		_textView.setTextSize(18);
		_textView.setPadding(10, 10, 10, 10);
		setContentView(_textView);
	}
}
