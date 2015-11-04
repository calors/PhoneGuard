package com.yjb.guard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupWindow;

public class HomeActivity extends Activity
{
	private Context mContext;
	private GridView mGridView;
	private PopupWindow pop;
	// 图片资源ID
	private int[] mImages = { R.drawable.back, R.drawable.del,
			R.drawable.destory, R.drawable.locate, R.drawable.lock,
			R.drawable.setting, R.drawable.warn };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mContext = this;
		initView();
		initPopupWindow();
	}

	// 初始化popupwindow
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	private void initPopupWindow()
	{
		// popupwindw的布局
		View view = getLayoutInflater().inflate(R.layout.popup_window, null);
		// new一个popupwindow
		pop = new PopupWindow(view, px2dp(240),
				ViewGroup.LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(true);
		view.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				pop.dismiss();
			}
		});
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
				pop.showAtLocation(parent, Gravity.CENTER, 0, 0);
			}
		});
	}

	// px转成对应的dp值
	private int px2dp(int px)
	{
		return (int) (px * (getResources().getDisplayMetrics().density) + 0.5f);
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
			return mImages[position];
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView _imageView = null;
			if (convertView == null)
			{
				_imageView = new ImageView(mContext);
				// LayoutParams 值是以像素为单位，因此要做转换
				LayoutParams params = new GridView.LayoutParams(px2dp(120),
						px2dp(120));
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
