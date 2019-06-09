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
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.wkz.shapeimageview.util.DisplayUtil;

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
    private int width, height;
    /**
     * 边框颜色、边框宽度
     */
    private int borderColor = 0x1A000000, borderWidth;
    /**
     * 圆角弧度
     */
    private int radius, radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight;
    /**
     * 圆角弧度数组
     */
    private float[] mRadii;
    /**
     * 图片类型（圆形, 矩形）
     */
    private int shapeType = ShapeType.RECTANGLE;
    /**
     * 按下的画笔
     */
    private Paint pressedPaint;
    /**
     * 按下的透明度
     */
    private float pressedAlpha = 0.0F;
    /**
     * 按下的颜色
     */
    private int pressedColor = 0x1A000000;

    @IntDef({
            ShapeType.RECTANGLE,
            ShapeType.CIRCLE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShapeType {
        int RECTANGLE = 0;
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
                borderWidth = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_border_width, borderWidth);
                borderColor = array.getColor(R.styleable.ShapeImageView_siv_border_color, borderColor);
                radius = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_radius, 0);
                radiusTopLeft = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_radius_top_left, 0);
                radiusTopRight = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_radius_top_right, 0);
                radiusBottomLeft = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_radius_bottom_left, 0);
                radiusBottomRight = array.getDimensionPixelOffset(R.styleable.ShapeImageView_siv_radius_bottom_right, 0);
                pressedAlpha = array.getFloat(R.styleable.ShapeImageView_siv_pressed_alpha, pressedAlpha);
                if (pressedAlpha > 1) {
                    pressedAlpha = 1;
                } else if (pressedAlpha < 0) {
                    pressedAlpha = 0;
                }
                pressedColor = array.getColor(R.styleable.ShapeImageView_siv_pressed_color, pressedColor);
                shapeType = array.getInteger(R.styleable.ShapeImageView_siv_shape_type, shapeType);
            } finally {
                array.recycle();
            }
        }

        initPressedPaint();
        initRadii();
        setClickable(true);
        setDrawingCacheEnabled(true);
        setWillNotDraw(false);
    }

    /**
     * 初始化按下的画笔
     */
    private void initPressedPaint() {
        pressedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pressedPaint.setStyle(Paint.Style.FILL);
        pressedPaint.setColor(pressedColor);
        pressedPaint.setAlpha(0);
    }

    /**
     * 初始化圆角弧度数组
     */
    private void initRadii() {
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。*/
        /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
        if (shapeType == ShapeType.RECTANGLE) {
            if (radius > 0) {
                mRadii = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
            } else {
                mRadii = new float[]{radiusTopLeft, radiusTopLeft, radiusTopRight, radiusTopRight,
                        radiusBottomRight, radiusBottomRight, radiusBottomLeft, radiusBottomLeft};
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
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
        drawPressed(canvas);
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

        canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);

        if (shapeType == ShapeType.RECTANGLE) {
            Path path = new Path();
            RectF rectf = new RectF((float) borderWidth / 2,
                    (float) borderWidth / 2,
                    getWidth() - (float) borderWidth / 2,
                    getHeight() - (float) borderWidth / 2
            );
            path.addRoundRect(rectf, mRadii, Path.Direction.CW);
            canvas.drawPath(path, paint);
            path.close();
        } else {
            canvas.drawCircle((float) width / 2, (float) height / 2, (float) width / 2 - borderWidth, paint);
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
        if (borderWidth > 0) {
            Paint paint = new Paint();
            paint.setStrokeWidth(borderWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setAntiAlias(true);
            if (shapeType == ShapeType.RECTANGLE) {
                Path path = new Path();
                RectF rectf = new RectF(
                        (float) borderWidth / 2,
                        (float) borderWidth / 2,
                        getWidth() - (float) borderWidth / 2,
                        getHeight() - (float) borderWidth / 2
                );
                path.addRoundRect(rectf, mRadii, Path.Direction.CW);
                canvas.drawPath(path, paint);
            } else {
                canvas.drawCircle(
                        (float) width / 2,
                        (float) height / 2,
                        (float) (width - borderWidth) / 2,
                        paint
                );
            }
        }
    }

    /**
     * 绘制按下效果
     *
     * @param canvas 画布
     */
    private void drawPressed(Canvas canvas) {
        if (shapeType == ShapeType.RECTANGLE) {
            Path path = new Path();
            RectF rectf = new RectF(
                    1,
                    1,
                    width - 1,
                    height - 1
            );
            path.addRoundRect(rectf, mRadii, Path.Direction.CW);
            canvas.drawPath(path, pressedPaint);
        } else {
            canvas.drawCircle(
                    (float) width / 2,
                    (float) height / 2,
                    (float) width / 2,
                    pressedPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressedPaint.setAlpha((int) (pressedAlpha * 255));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                pressedPaint.setAlpha(0);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                pressedPaint.setAlpha(0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取Bitmap内容
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
        this.borderColor = ContextCompat.getColor(getContext(), id);
        invalidate();
    }

    /**
     * 设置边框宽度
     *
     * @param dpValue 边框宽度,单位为DP
     */
    public void setBorderWidth(int dpValue) {
        this.borderWidth = DisplayUtil.dip2px(getContext(), dpValue);
        invalidate();
    }

    /**
     * 设置图片按下颜色透明度
     *
     * @param pressAlpha 按下颜色透明度
     */
    public void setPressedAlpha(@FloatRange(from = 0.0, to = 1.0) float pressAlpha) {
        this.pressedAlpha = pressAlpha;
        invalidate();
    }

    /**
     * 设置图片按下的颜色
     *
     * @param id 颜色资源id
     */
    public void setPressedColor(@ColorRes int id) {
        this.pressedColor = ContextCompat.getColor(getContext(), id);
        pressedPaint.setColor(pressedColor);
        pressedPaint.setAlpha(0);
        invalidate();
    }

    /**
     * 设置圆角半径
     *
     * @param dpValue 圆角半径
     */
    public void setRadius(int dpValue) {
        this.radius = DisplayUtil.dip2px(getContext(), dpValue);
        invalidate();
    }

    /**
     * 设置形状类型
     *
     * @param shapeType 形状类型{@link ShapeType}
     */
    public void setShapeType(@ShapeType int shapeType) {
        this.shapeType = shapeType;
        invalidate();
    }
}
