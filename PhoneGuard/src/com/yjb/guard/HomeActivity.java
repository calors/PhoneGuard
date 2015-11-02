package com.yjb.guard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class HomeActivity extends Activity
{
	private Context mContext;
	private GridView mGridView;
	// 图片资源ID
	private int[] mImages = { R.drawable.back, R.drawable.del,
			R.drawable.destory, R.drawable.locate, R.drawable.lock,
			R.drawable.setting, R.drawable.warn };
	private Class<?>[] mClasses = { ShowActivity.class, ShowActivity.class,
			ShowActivity.class, ShowActivity.class, ShowActivity.class,
			ShowActivity.class, ShowActivity.class };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mContext = this;
		initView();
	}

	private void initView()
	{
		mGridView = (GridView) findViewById(R.id.gvHome);
		// 设置适配器和监听
		GVAdapter _adapter = new GVAdapter();
		mGridView.setAdapter(_adapter);
		mGridView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Intent _intent = new Intent();
				_intent.setClass(mContext, mClasses[position]);
				startActivity(_intent);
			}
		});
	}

	private class GVAdapter extends BaseAdapter
	{
		@Override
		public int getCount()
		{
			return mImages.length;
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView _imageView = null;
			if (convertView == null)
			{
				_imageView = new ImageView(mContext);
				LayoutParams params = new GridView.LayoutParams(120, 120);
				_imageView.setLayoutParams(params);
				_imageView.setPadding(8, 8, 8, 8);
				_imageView.setScaleType(ScaleType.CENTER);
			}
			else
			{
				_imageView = (ImageView) convertView;
			}
			_imageView.setImageResource(mImages[position]);
			return _imageView;
		}
	}
}
