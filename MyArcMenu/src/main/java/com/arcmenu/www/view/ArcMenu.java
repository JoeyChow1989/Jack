package com.arcmenu.www.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.arcmenu.www.R;

/**
 * Created by 周正尧 on 2015/11/27.
 * 自定义View类
 *
 */
public class ArcMenu extends ViewGroup implements View.OnClickListener {


    private static final int POS_LEFT_TOP = 0;
    private static final int POS_LEFT_BOTTOM = 1;
    private static final int POS_RIGHT_TOP = 2;
    private static final int POS_RIGHT_BOTTOM = 3;

    /**
     * 菜单的位置枚举类
     */
    public enum Position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    private Position mPosition = Position.RIGHT_BOTTOM;
    private int mRadius;


    /**
     * 菜单的状态
     */
    private Status mCurrentStatus = Status.CLOSE;

    public enum Status {
        OPEN, CLOSE
    }


    private View mCButton;

    private OnMenuItemClickListener mMenuItemClickListener;

    /**
     * 点击子菜单项的回调接口
     */
    public interface OnMenuItemClickListener {
        void onclick(View view, int position);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener mMenuItemClickListener) {
        this.mMenuItemClickListener = mMenuItemClickListener;
    }

    /**
     * 构造
     *
     * @param context
     */
    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        //获取自定义属性
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);

        int pos = array.getInt(R.styleable.ArcMenu_position, POS_RIGHT_BOTTOM);

        switch (pos) {
            case POS_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;

        }

        mRadius = (int) array.getDimension(R.styleable.ArcMenu_radius, (int)
                (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                        getResources().getDisplayMetrics())));

        System.out.println("position:" + mPosition + "radius:" + mRadius);
        array.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //测量child
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (changed) {
            layoutCButton();
            int count = getChildCount();

            for (int i = 0; i < count - 1; i++) {

                View child = getChildAt(i + 1);

                child.setVisibility(View.GONE);

                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();

                //如果菜单位置在底部  左下和右下
                if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {

                    ct = getMeasuredHeight() - cHeight - ct;
                }

                //右上 ， 右下
                if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {

                    cl = getMeasuredWidth() - cWidth - cl;
                }

                child.layout(cl, ct, cl + cWidth, ct + cHeight);
            }
        }
    }

    /**
     * 定位主菜单按钮
     */
    private void layoutCButton() {
        mCButton = getChildAt(0);

        mCButton.setOnClickListener(this);

        int l = 0;
        int t = 0;
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();

        switch (mPosition) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }

        mCButton.layout(l, t, l + width, t + width);
    }

    @Override
    public void onClick(View v) {

        rotateCButton(v, 0f, 450f, 300);

        toggleMenu(300);
    }


    /**
     * 切换菜单
     */
    public void toggleMenu(int duration) {

        //为所有的menuItem添加动画和旋转动画
        int count = getChildCount();

        for (int i = 0; i < count - 1; i++) {
            final View childView = getChildAt(i + 1 );
            childView.setVisibility(View.VISIBLE);
            //end 0,0,0,0
            //start
            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            int xflag = 1;
            int yflag = 1;

            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
                xflag = -1;
            }

            if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
                yflag = -1;
            }


            AnimationSet animationSet = new AnimationSet(true);
            Animation transAnim = null;
            //to open
            if (mCurrentStatus == Status.CLOSE) {

                transAnim = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);

                childView.setClickable(true);
                childView.setFocusable(true);

            } else {
                transAnim = new TranslateAnimation(0, xflag * cl, 0, yflag * ct);

                childView.setClickable(false);
                childView.setFocusable(false);
            }
            transAnim.setFillAfter(true);
            transAnim.setDuration(duration);
            transAnim.setStartOffset((i * 100) / count);

            transAnim.setAnimationListener(new Animation.AnimationListener()

            {
                @Override
                public void onAnimationStart(Animation animation) {


                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mCurrentStatus == Status.CLOSE) {
                        childView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            //旋转动画
            RotateAnimation rotateAnim = new RotateAnimation(0f, 720f, Animation.RELATIVE_TO_SELF
                    , 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnim.setFillAfter(true);
            rotateAnim.setDuration(duration);


            animationSet.addAnimation(rotateAnim);
            animationSet.addAnimation(transAnim);

            childView.startAnimation(animationSet);

            final int pos = i + 1;

            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMenuItemClickListener != null) {
                        mMenuItemClickListener.onclick(childView, pos);
                    }

                    menuItemAnim(pos - 1);
                    changeMenuStatus();
                }
            });


        }

        //切换菜单状态
        changeMenuStatus();
    }


    /**
     * item 点击动画
     *
     * @param pos
     */
    private void menuItemAnim(int pos) {

        for (int i = 0; i < getChildCount() - 1; i++) {

            View childView = getChildAt(i + 1);
            if (i == pos) {
                childView.startAnimation(scaleBigAnim(300));
            } else {
                childView.startAnimation(scaleSmallAnim(300));
            }
            childView.setClickable(false);
            childView.setFocusable(false);
        }

    }

    private Animation scaleSmallAnim(int duration) {

        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);

        return animationSet;
    }


    /**
     * 为当前点击的Item设置变大和透明度的降低的动画
     *
     * @param duration
     * @return animationSet
     */
    private Animation scaleBigAnim(int duration) {

        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);

        return animationSet;
    }

    /**
     * 切换菜单状态
     */
    private void changeMenuStatus() {
        mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }


    public boolean isOpen(){
        return mCurrentStatus == Status.OPEN;
    }


    /**
     * 旋转动画
     *
     * @param view
     * @param start
     * @param end
     * @param duration
     *
     */
    private void rotateCButton(View view, float start, float end, int duration) {

        RotateAnimation anim = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF
                , 0.5f, Animation.RELATIVE_TO_SELF
                , 0.5f);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }
}
