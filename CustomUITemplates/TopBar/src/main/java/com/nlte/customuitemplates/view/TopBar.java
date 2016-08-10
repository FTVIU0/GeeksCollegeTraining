package com.nlte.customuitemplates.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nlte.customuitemplates.R;

/**TopBar母版
 * @author NLTE
 * @time 2016/4/27 0027 23:43
 */
public class TopBar extends RelativeLayout {

    /* 1. 获取自定义的控件*/
    private Button leftButton;
    private Button rightButton;
    private TextView tvTitle;

    /* 2. 获取自定义控件的属性*/
    private int leftTextColor;
    private Drawable leftBackground;
    private String leftText;

    private int rightTextColor;
    private Drawable rightBackground;
    private String rightText;

    private String tbTitleText;
    private float tbTitleTextSize;
    private int tbTitleTextColor;

    /* 8. 布局属性*/
    private LayoutParams leftParams;
    private LayoutParams rightParams;
    private LayoutParams titleParams;

    private topbarClickListener listener;

    /*将模板与调用者联系在一起*/
    //定义接口
    public interface topbarClickListener{
        public void leftClick();
        public void rightClick();
    }
    //暴露方法给调用者，方便以匿名内部类的方式实现调用
    public void setOnTopbarClickListener(topbarClickListener listener){
        this.listener = listener;
    }

    /**
     * @param context 上下文
     * @param attrs 自定义的属性参数
     */
    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        /* 3. 获取atts自定义属性的值（属性和值的映射）*/
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

        /* 4. 属性赋值*/
        leftTextColor = ta.getColor(R.styleable.TopBar_leftTextColor, 0);
        leftBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);
        leftText = ta.getString(R.styleable.TopBar_leftText);

        rightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor, 0);
        rightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
        rightText = ta.getString(R.styleable.TopBar_rightText);

        tbTitleText = ta.getString(R.styleable.TopBar_tbTitleText);
        tbTitleTextSize = ta.getDimension(R.styleable.TopBar_tbTitleTextSize, 0);
        tbTitleTextColor = ta.getColor(R.styleable.TopBar_tbTitleTextColor, 0);

        /* 5. 回收  1.避免浪费资源 2.避免由于缓存引起的错误*/
        ta.recycle();

        /* 6. 创建所需要的控件*/
        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        /* 7. 将属性赋值给控件*/
        leftButton.setText(leftText);
        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);

        rightButton.setText(rightText);
        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);

        tvTitle.setText(tbTitleText);
        tvTitle.setTextColor(tbTitleTextColor);
        tvTitle.setTextSize(tbTitleTextSize);
        tvTitle.setGravity(Gravity.CENTER);

        //为了美观，给RelativeLayout设置背景
        setBackgroundColor(0xFFF59563);

        /* 9. 设置布局属性*/
        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);//宽高属性
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);//添加规则

        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);//宽高属性
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);//添加规则

        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);//宽高属性
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);//添加规则

        /* 10. 将控件以LayoutParams的形式添加到ViewGroup中*/
        addView(leftButton, leftParams);
        addView(rightButton, rightParams);
        addView(tvTitle, titleParams);

        /*11.动态部分 点击事件：通过接口回调实现脱离*/
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });
    }
}
