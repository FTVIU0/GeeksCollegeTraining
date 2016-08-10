
package com.nlte.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 自定义一个View继承ImageView，增加一个通用的旋转图片资源的方法
 * 
 * @author way
 * 
 */
public class CompassView extends ImageView {
	private float mDirection;// 方向旋转浮点数
	private Drawable mCompass;// 图片资源
	
	//三个构造器
	public CompassView(Context context) {
		super(context);
		mDirection = 0.0f;// 默认不旋转
		mCompass = null;
	}

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDirection = 0.0f;
		mCompass = null;
	}

	public CompassView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mDirection = 0.0f;
		mCompass = null;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mCompass == null) {
			mCompass = getDrawable();// 获取当前view的图片资源
			mCompass.setBounds(0, 0, getWidth(), getHeight());// 图片资源在view的位置，此处相当于充满view
		}

		canvas.save();
		canvas.rotate(mDirection, getWidth() / 2, getHeight() / 2);// 绕图片中心点旋转，
		mCompass.draw(canvas);// 把旋转后的图片画在view上，即保持旋转后的样子
		canvas.restore();// 保存一下
	}

	/**
	 * 自定义更新方向的方法
	 * 
	 * @param direction
	 *            传入的方向
	 */
	public void updateDirection(float direction) {
		mDirection = direction;
		invalidate();// 重新刷新一下，更新方向
	}

}
