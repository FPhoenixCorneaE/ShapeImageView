package com.wkz.shapeimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorRes;
import androidx.annotation.IntDef;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.wkz.shapeimageview.progress.OnGlideImageViewListener;
import com.wkz.shapeimageview.progress.OnProgressListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 形状图像视图,圆形、矩形（圆角矩形）
 *
 * @author Administrator
 */
public class ShapeImageView extends ImageView {

    /**
     * 图片的宽高
     */
    private int mWidth, mHeight;
    /**
     * 边框颜色、边框宽度
     */
    private int mBorderColor, mBorderWidth;
    /**
     * 圆角、左上角、右上角、左下角、右下角弧度
     */
    private int mRadius, mRadiusTopLeft, mRadiusTopRight, mRadiusBottomLeft, mRadiusBottomRight;
    /**
     * 圆角弧度数组
     */
    private float[] mRadii;
    /**
     * 图片形状类型
     */
    private int mShapeType = ShapeType.RECTANGLE;
    /**
     * 图片加载器
     */
    private GlideImageLoader mImageLoader;

    @IntDef({
            ShapeType.RECTANGLE,
            ShapeType.CIRCLE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShapeType {
        /**
         * 矩形
         */
        int RECTANGLE = 0;
        /**
         * 圆形
         */
        int CIRCLE = 1;
    }

    public ShapeImageView(Context context) {
        this(context, null);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShapeImageView);
            try {
                mBorderWidth = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_border_width, 0);
                mBorderColor = array.getColor(R.styleable.ShapeImageView_siv_border_color, 0);
                mRadius = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_radius, 0);
                mRadiusTopLeft = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_radius_top_left, 0);
                mRadiusTopRight = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_radius_top_right, 0);
                mRadiusBottomLeft = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_radius_bottom_left, 0);
                mRadiusBottomRight = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_radius_bottom_right, 0);
                mShapeType = array.getInteger(R.styleable.ShapeImageView_siv_shape_type, ShapeType.RECTANGLE);
            } finally {
                array.recycle();
            }
        }

        mImageLoader = new GlideImageLoader(this);
        initRadii();
    }

    /**
     * 初始化圆角弧度数组
     */
    private void initRadii() {
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。*/
        /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
        if (mShapeType == ShapeType.RECTANGLE) {
            if (mRadius > 0) {
                mRadii = new float[]{
                        mRadius, mRadius,
                        mRadius, mRadius,
                        mRadius, mRadius,
                        mRadius, mRadius
                };
            } else {
                mRadii = new float[]{
                        mRadiusTopLeft, mRadiusTopLeft,
                        mRadiusTopRight, mRadiusTopRight,
                        mRadiusBottomRight, mRadiusBottomRight,
                        mRadiusBottomLeft, mRadiusBottomLeft
                };
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null
                || getWidth() == 0
                || getHeight() == 0) {
            return;
        }

        drawDrawable(canvas, getBitmapFromDrawable(drawable));
        drawBorder(canvas);
    }

    /**
     * 绘制图片
     *
     * @param canvas 画布
     * @param bitmap 位图
     */
    private void drawDrawable(Canvas canvas, Bitmap bitmap) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);

        canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);

        if (mShapeType == ShapeType.RECTANGLE) {
            Path path = new Path();
            RectF rectf = new RectF((float) mBorderWidth / 2,
                    (float) mBorderWidth / 2,
                    getWidth() - (float) mBorderWidth / 2,
                    getHeight() - (float) mBorderWidth / 2
            );
            path.addRoundRect(rectf, mRadii, Path.Direction.CW);
            canvas.drawPath(path, paint);
            path.close();
        } else {
            canvas.drawCircle(
                    (float) mWidth / 2,
                    (float) mHeight / 2,
                    (float) mWidth / 2 - mBorderWidth,
                    paint
            );
        }

        // SRC_IN 只显示两层图像交集部分的上层图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // Bitmap缩放
        float scaleWidth = (float) getWidth() / bitmap.getWidth();
        float scaleHeight = (float) getHeight() / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();
    }

    /**
     * 绘制边框
     *
     * @param canvas 画布
     */
    private void drawBorder(Canvas canvas) {
        if (mBorderWidth > 0) {
            Paint paint = new Paint();
            paint.setStrokeWidth(mBorderWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(mBorderColor);
            paint.setAntiAlias(true);
            if (mShapeType == ShapeType.RECTANGLE) {
                Path path = new Path();
                RectF rectf = new RectF(
                        (float) mBorderWidth / 2,
                        (float) mBorderWidth / 2,
                        getWidth() - (float) mBorderWidth / 2,
                        getHeight() - (float) mBorderWidth / 2
                );
                path.addRoundRect(rectf, mRadii, Path.Direction.CW);
                canvas.drawPath(path, paint);
            } else {
                canvas.drawCircle(
                        (float) mWidth / 2,
                        (float) mHeight / 2,
                        (float) (mWidth - mBorderWidth) / 2,
                        paint
                );
            }
        }
    }

    /**
     * 获取Bitmap
     *
     * @param drawable 图片
     * @return Bitmap
     */
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        try {
            Bitmap bitmap;
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置边框颜色
     *
     * @param id 颜色资源id
     */
    public void setBorderColor(@ColorRes int id) {
        this.mBorderColor = ContextCompat.getColor(getContext(), id);
        postInvalidate();
    }

    /**
     * 设置边框宽度
     *
     * @param dpValue 边框宽度,单位为DP
     */
    public void setBorderWidth(int dpValue) {
        this.mBorderWidth = DisplayUtils.dip2px(getContext(), dpValue);
        postInvalidate();
    }

    /**
     * 设置圆角半径
     *
     * @param dpValue 圆角半径
     */
    public void setRadius(int dpValue) {
        this.mRadius = DisplayUtils.dip2px(getContext(), dpValue);
        postInvalidate();
    }

    /**
     * 设置圆角半径
     *
     * @param radii 圆角半径
     */
    public void setRadii(float[] radii) {
        this.mRadii = radii;
        postInvalidate();
    }

    /**
     * 设置形状类型
     *
     * @param shapeType 形状类型{@link ShapeType}
     */
    public void setShapeType(@ShapeType int shapeType) {
        this.mShapeType = shapeType;
        postInvalidate();
    }

    public ShapeImageView load(Object obj, int... placeholder) {
        mImageLoader.load(obj, placeholder);
        return this;
    }

    public ShapeImageView load(Object obj, RequestOptions options) {
        mImageLoader.load(obj, options);
        return this;
    }

    public ShapeImageView load(Object obj, TransitionOptions<?, ? super Drawable> transitionOptions, int... placeholder) {
        mImageLoader.load(obj, transitionOptions, placeholder);
        return this;
    }

    public ShapeImageView listener(OnGlideImageViewListener listener) {
        mImageLoader.setOnGlideImageViewListener(listener);
        return this;
    }

    public ShapeImageView listener(OnProgressListener listener) {
        mImageLoader.setOnProgressListener(listener);
        return this;
    }
}
