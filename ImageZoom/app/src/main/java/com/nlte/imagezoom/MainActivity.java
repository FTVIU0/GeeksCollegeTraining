package com.nlte.imagezoom;

import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mIv;
    private float mTouchAXBefore;
    private float mTouchAYBefore;
    private float mTouchBXBefore;
    private float mTouchBYBefore;
    private int mTopMarginBefore;
    private int mLeftMarginBefore;
    private int mHeightBefore;
    private int mWidthBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIv = (ImageView) findViewById(R.id.image);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int actionMasked = MotionEventCompat.getActionMasked(event);

        if (MotionEventCompat.getPointerCount(event) == 2) {

            switch (actionMasked) {

                case MotionEvent.ACTION_POINTER_DOWN:

                    //缩放前
                    //获取A点的坐标
                    mTouchAXBefore = getTouchX(event, 0);
                    mTouchAYBefore = getTouchY(event, 0);

                    //获取B点的坐标
                    mTouchBXBefore = getTouchX(event, 1);
                    mTouchBYBefore = getTouchY(event, 1);

                    //获取图片的LayoutPareme
                    FrameLayout.LayoutParams paramsBefore = (FrameLayout.LayoutParams) mIv.getLayoutParams();
                    mTopMarginBefore = paramsBefore.topMargin;
                    mLeftMarginBefore = paramsBefore.leftMargin;

                    //获取图片的宽高
                    mHeightBefore = mIv.getHeight();
                    mWidthBefore = mIv.getWidth();

                    break;

                case MotionEvent.ACTION_MOVE:

                    //缩放后
                    //获取A点的坐标
                    float touchAXAfter = getTouchX(event, 0);
                    float touchAYAfter = getTouchY(event, 0);

                    //获取B点的坐标
                    float touchBXAfter = getTouchX(event, 1);
                    float touchBYAfter = getTouchY(event, 1);

                    //应用勾股定理
                    double squareBefore = Math.pow((mTouchAXBefore - mTouchBXBefore), 2)
                            + Math.pow((mTouchAYBefore - mTouchBYBefore), 2);
                    double squareAfter = Math.pow((touchAXAfter - touchBXAfter), 2)
                            + Math.pow((touchAYAfter - touchBYAfter), 2);

                    //缩放比例
                    double zoomRatio = squareBefore / squareAfter;

                    //设置图片的LayoutPareme
                    FrameLayout.LayoutParams paramsAfter = (FrameLayout.LayoutParams) mIv.getLayoutParams();

                    //设置图片的上边距和作边距
                    paramsAfter.topMargin = (int) (mTopMarginBefore / zoomRatio);
                    paramsAfter.leftMargin = (int) (mLeftMarginBefore / zoomRatio);


                    //设置图片的宽高
                    int heightAfter = (int) (mHeightBefore / zoomRatio);
                    int widthAfter = (int) (mWidthBefore / zoomRatio);
                    if (heightAfter > 0 && widthAfter > 0) {
                        paramsAfter.height = heightAfter;
                        paramsAfter.width = widthAfter;
                        mIv.setLayoutParams(paramsAfter);
                    }

                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private float getTouchX(MotionEvent event, int pointerIndex) {
        return MotionEventCompat.getX(event, pointerIndex);
    }

    private float getTouchY(MotionEvent event, int pointerIndex) {
        return MotionEventCompat.getY(event, pointerIndex);
    }
}
