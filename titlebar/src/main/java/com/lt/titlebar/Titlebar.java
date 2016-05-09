package com.lt.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lt on 2016/3/4.
 */
public class Titlebar extends RelativeLayout{

    private String mLeftButtonText;
    private int mLeftButtonTextColor;
    private float mLeftButtonSize;
    private Drawable mLeftButtonImage;
    private String mTitleButtonText;
    private int mTitleButtonTextColor;
    private float mTitleButtonSize;
    private String mRightButtonText;
    private int mRightButtonTextColor;
    private float mRightButtonSize;
    private Drawable mRightButtonImage;
    private TextView mLeftTextView;
    private ImageView mLeftButton;
    private TextView mRightTextView;
    private ImageView mRightButton;

    public Titlebar(Context context) {
        this(context, null);
    }

    public Titlebar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Titlebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
        initView(context);
    }


    private void init(Context context,AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Titlebar);
        mLeftButtonText = typedArray.getString(R.styleable.Titlebar_leftButtonText);
        mLeftButtonTextColor = typedArray.getColor(R.styleable.Titlebar_leftButtonTextColor, Color.GRAY);
        mLeftButtonSize = typedArray.getDimension(R.styleable.Titlebar_leftButtonTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mLeftButtonImage = typedArray.getDrawable(R.styleable.Titlebar_leftButtonImage);

        mTitleButtonText = typedArray.getString(R.styleable.Titlebar_titleText);
        mTitleButtonTextColor = typedArray.getColor(R.styleable.Titlebar_titleColor, Color.GRAY);
        mTitleButtonSize = typedArray.getDimension(R.styleable.Titlebar_titleSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

        mRightButtonText = typedArray.getString(R.styleable.Titlebar_rightButtonText);
        mRightButtonTextColor = typedArray.getColor(R.styleable.Titlebar_rightButtonTextColor, Color.GRAY);
        mRightButtonSize = typedArray.getDimension(R.styleable.Titlebar_rightButtonTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mRightButtonImage = typedArray.getDrawable(R.styleable.Titlebar_rightButtonImage);
        
        typedArray.recycle();
    }


    private void initView(Context context) {
        if(mLeftButtonImage == null & mLeftButtonText != null){
            // 当用户没有设置左侧按钮图片并设置了左侧的按钮文本属性时--添加左侧文本按钮
            mLeftTextView = new TextView(context);
            mLeftTextView.setText(mLeftButtonText);
            mLeftTextView.setTextColor(mLeftButtonTextColor);
            mLeftTextView.setTextSize(mLeftButtonSize);
            RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            leftParams.addRule(RelativeLayout.CENTER_VERTICAL);
            addView(mLeftTextView, leftParams);
        }else if(mLeftButtonImage != null){
            // 添加左侧图片按钮
            RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            leftParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mLeftButton = new ImageView(context);
            mLeftButton.setImageDrawable(mLeftButtonImage);
            addView(mLeftButton, leftParams);
        }

        if(mTitleButtonText!=null){
            // 添加中间标题
            TextView titleTextView = new TextView(context);
            titleTextView.setText(mTitleButtonText);
            titleTextView.setTextColor(mTitleButtonTextColor);
            titleTextView.setTextSize(mTitleButtonSize);
            RelativeLayout.LayoutParams titleTextViewParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            titleTextViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            addView(titleTextView,titleTextViewParams);
        }

        if(mRightButtonImage == null & mRightButtonText != null){
            // 当用户没有设置右侧按钮图片并设置了左侧的按钮文本属性时--添加右侧文本按钮
            mRightTextView = new TextView(context);
            mRightTextView.setText(mRightButtonText);
            mRightTextView.setTextColor(mRightButtonTextColor);
            mRightTextView.setTextSize(mRightButtonSize);
            RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
            addView(mRightTextView,rightParams);
        }else if(mRightButtonImage != null){
            // 添加右侧图片按钮
            RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mRightButton = new ImageView(context);
            mRightButton.setImageDrawable(mRightButtonImage);
            addView(mRightButton, rightParams);
        }
    }

    /**
     * 在button点击事件接口
     */
    public interface OnButtonClickListener{
        void onLeftClick();
        void onRightClick();
    }

    /**
     * 设置点击事件
     * @param onButtonClickListener
     */
    public void setOnButtonClickListener(final OnButtonClickListener onButtonClickListener) {
        if(onButtonClickListener !=null){
            if(mLeftTextView != null){
                mLeftTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClickListener.onLeftClick();
                    }
                });
            }
            if(mLeftButton != null){
                mLeftButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClickListener.onLeftClick();
                    }
                });
            }
            if(mRightTextView != null){
                mRightTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClickListener.onRightClick();
                    }
                });
            }
            if(mRightButton != null){
                mRightButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClickListener.onRightClick();
                    }
                });
            }
        }
    }
}
