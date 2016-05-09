package matrix.www.imooc.com.mymatriximagedemo.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by zzy on 2015/11/13.
 */
public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private Boolean mOnce = false;

    /**
     * 初始化时缩放的值
     */
    private float mInitScale;

    /**
     * 双击放大时到达的值
     */
    private float mMidScale;

    /**
     * 放大的最大值
     */
    private float mMaxScale;

    private Matrix mScaleMatrix;


    //---------自由移动------------
     /**
     * 捕获上一次多点触控的数量
     */
    private int mLastPointerCount;

    private float mLastX;
    private float mLastY;
    private int mTouchSlop;
    private boolean isCanDrag;
    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    //------------双击放大缩小--------

    private GestureDetector mGestureDetector;

    private boolean isAutoScale;

    /**
     * 捕获用户多指触控时缩放比例
     */
    private ScaleGestureDetector mScaleGestureDetector;


    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //init
        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);

        mScaleGestureDetector = new ScaleGestureDetector(context, this);

        //onTouch监听事件
        setOnTouchListener(this);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();


        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                if (isAutoScale) return true;

                float x = e.getX();
                float y = e.getY();

                if (getScale() < mMidScale) {

                    /*mScaleMatrix.postScale(mMidScale / getScale(), mMidScale / getScale(), x, y);
                    setImageMatrix(mScaleMatrix);*/

                    postDelayed(new AutoScaleRunnable(mMidScale, x, y), 16);
                    isAutoScale = true;


                } else {
                   /* mScaleMatrix.postScale(mInitScale / getScale(), mInitScale / getScale(), x, y);
                    setImageMatrix(mScaleMatrix);*/

                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                    isAutoScale = true;
                }
                return true;
            }
        });
    }


    private class AutoScaleRunnable implements Runnable {

        /**
         * 缩放的目标值
         */
        private float mTargetScale;

        /**
         * 缩放的中心点
         */
        private float x;
        private float y;

        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;

        private float tmpScale;


        public AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;

            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            }
            if (getScale() > mTargetScale) {
                tmpScale = SMALL;
            }
        }

        @Override
        public void run() {

            //进行缩放
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            float currentScale = getScale();

            if ((tmpScale > 1.0f && currentScale < mTargetScale) ||
                    (tmpScale < 1.0f && currentScale > mTargetScale)
                    ) {
                postDelayed(this, 16);

            } else {
                //设置为目标值
                float scale = mTargetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);

                isAutoScale = false;
            }

        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * 获取ImageView加载完成的图片
     */
    @Override
    public void onGlobalLayout() {
        if (!mOnce) {

            //得到控件的宽和高
            int width = getWidth();
            int height = getHeight();

            //得到图片以及宽和高
            Drawable drawable = getDrawable();
            if (drawable == null) return;

            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();

            float scale = 1.0f;

            /**
             * 如果图片的宽度大于控件宽度，但是高度小于控件宽度，我们将其缩小
             */
            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }

            /**
             * 如果图片的宽度小于控件宽度，但是高度大于控件宽度，我们将其缩小
             */
            if (dh > height && dw < width) {
                scale = height * 1.0f / dh;
            }
            /**
             * 如果图片的宽度小于控件宽度,高度也小于控件高度，或者高度大于控件宽度，宽度也大于控件宽度
             * 我们将其缩小
             */
            if ((dw > width && dh > height) || (dw < width && dh < height)) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            /**
             * 初始化时缩放的比例
             */
            mInitScale = scale;
            mMaxScale = scale * 4;
            mMidScale = scale * 2;


            //将图片移动至控件的中心
            int dx = getWidth() / 2 - dw / 2;
            int dy = getHeight() / 2 - dh / 2;

            /**
             * 使用Matrix
             *  Matrix数据结构为长度为9的一维数组
             *
             *  xScale xSkew xTrans
             *  yScale yScale yTrans
             *    0      0      0
             *
             */

            //平移
            mScaleMatrix.postTranslate(dx, dy);
            //缩放
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);

            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }
    }

    /**
     * 获取当前图片的缩放值
     *
     * @return
     */
    public float getScale() {

        float[] values = new float[9];
        mScaleMatrix.getValues(values);

        return values[Matrix.MSCALE_X];
    }


    /**
     * 缩放的区间：initScale,maxScale
     *
     * @param detector The detector reporting the event - use this to
     *                 retrieve extended info about event state.
     * @return Whether or not the detector should consider this event
     * as handled. If an event was not handled, the detector
     * will continue to accumulate movement until an event is
     * handled. This can be useful if an application, for example,
     * only wants to update scaling factors if the change is
     * greater than 0.01.
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null) return true;

        //缩放范围的控制
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }

            if (scale * scaleFactor > mMaxScale) {
                scale = mMaxScale / scale;
            }

            //缩放
            //遇到问题，缩小回来时，图片发生的偏移
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            //在设置前调整

            //缩放时边界检测
            checkBorderAndCenterWhenScale();


            setImageMatrix(mScaleMatrix);
        }

        return true;
    }


    /**
     * 获得图片放大缩小以后的宽和高,以及l,r,t,b
     *
     * @return
     */
    private RectF getMatrixRectF() {

        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();

        Drawable drawable = getDrawable();

        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }


    /**
     * 在缩放时候进行边界控制以及位置的控制
     */
    private void checkBorderAndCenterWhenScale() {

        RectF rectF = getMatrixRectF();

        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }

            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }


        //如果宽度或者高度小于控件的宽和高，让其居中
        if (rectF.width() < width) {
            deltaX = width / 2f - rectF.right + rectF.width() / 2f;
        }

        if (rectF.height() < height) {
            deltaY = height / 2f - rectF.bottom + rectF.height() / 2f;
        }


        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * Responds to the beginning of a scaling gesture. Reported by
     * new pointers going down.
     *
     * @param detector
     * @return
     */
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    /**
     * Responds to the end of a scale gesture. Reported by existing
     * pointers going up.
     *
     * @param detector
     */
    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (mGestureDetector.onTouchEvent(event)) return true;
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0;
        float y = 0;

        int pointCount = event.getPointerCount();

        System.out.println("pointCount------------"+pointCount);

        for (int i = 0; i < pointCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }

        x /= pointCount;
        y /= pointCount;

        if (mLastPointerCount != pointCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;

            System.out.println("mLastX----"+mLastX+"===========mLastY"+mLastY);

        }

        mLastPointerCount = pointCount;

        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                if (rectF.width() > getWidth() + 0.01f || rectF.height() > getHeight() + 0.01f) {
                    if (getParent() instanceof ViewPager)
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;

            case MotionEvent.ACTION_MOVE:

                if (rectF.width() > getWidth() + 0.01f || rectF.height() > getHeight() + 0.01f) {
                    if (getParent() instanceof ViewPager)
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);

                    System.out.println("isCanDrag--------------"+isCanDrag);
                }

                if (isCanDrag) {
                    RectF rectf = getMatrixRectF();
                    if (getDrawable() != null) {

                        isCheckLeftAndRight = isCheckTopAndBottom = true;

                        if (rectf.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }

                        if (rectf.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }

                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorderAndCenterWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }
                }

                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;
        }

        return true;
    }

    /**
     * 当移动时进行边界检查
     */
    private void checkBorderAndCenterWhenTranslate() {

        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }

        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }


        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }

        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 判断是否是Move
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }
}
