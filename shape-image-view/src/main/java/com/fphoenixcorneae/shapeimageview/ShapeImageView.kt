package com.fphoenixcorneae.shapeimageview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.fphoenixcorneae.shapeimageview.progress.OnGlideImageViewListener
import com.fphoenixcorneae.shapeimageview.progress.OnProgressListener
import kotlin.math.*

/**
 * @desc：形状图像视图：圆形、矩形（圆角矩形）、心形
 * @date：2020-08-07 16:02
 */
class ShapeImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    /**
     * 图片的宽、高
     */
    private var mWidth = 0
    private var mHeight = 0

    /**
     * 边框：画笔、颜色、宽度、路径
     */
    private val mBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mBorderColor = 0
    private var mBorderWidth = 0f
    private var mBorderPath = Path()

    /**
     * 圆角、左上角、右上角、左下角、右下角弧度
     */
    private var mRadius = 0f
    private var mRadiusTopLeft = 0f
    private var mRadiusTopRight = 0f
    private var mRadiusBottomLeft = 0f
    private var mRadiusBottomRight = 0f

    /**
     * 圆角弧度数组
     */
    private var mRadii: FloatArray = floatArrayOf()

    /**
     * 图片形状类型,默认为矩形
     */
    private var mShapeType = ShapeType.RECTANGLE

    /**
     * 图片加载器
     */
    private var mImageLoader: GlideImageLoader = GlideImageLoader(this)

    /**
     * 矩形范围
     */
    private var mRectF = RectF()

    /**
     * 图片：画笔、路径
     */
    private val mDrawablePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mDrawablePath = Path()

    /**
     * 保存心形所有点的坐标的数组
     */
    private var mHeartPointList: ArrayList<Pair<Double, Double>>? = null

    /**
     * 形状类型
     */
    @IntDef(
            ShapeType.RECTANGLE,
            ShapeType.CIRCLE,
            ShapeType.HEART
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class ShapeType {
        companion object {
            /* 矩形 */
            const val RECTANGLE = 0

            /* 圆形 */
            const val CIRCLE = 1

            /* 心形 */
            const val HEART = 2
        }
    }

    private fun initAttr(
            context: Context,
            attrs: AttributeSet?
    ) {
        attrs?.let {
            val array = context.obtainStyledAttributes(it, R.styleable.ShapeImageView)
            try {
                mBorderWidth = array.getDimension(
                        R.styleable.ShapeImageView_siv_border_width,
                        0f
                )
                mBorderColor = array.getColor(
                        R.styleable.ShapeImageView_siv_border_color,
                        0
                )
                mRadius = array.getDimension(
                        R.styleable.ShapeImageView_siv_radius,
                        0f
                )
                mRadiusTopLeft = array.getDimension(
                        R.styleable.ShapeImageView_siv_radius_top_left,
                        0f
                )
                mRadiusTopRight = array.getDimension(
                        R.styleable.ShapeImageView_siv_radius_top_right,
                        0f
                )
                mRadiusBottomLeft = array.getDimension(
                        R.styleable.ShapeImageView_siv_radius_bottom_left,
                        0f
                )
                mRadiusBottomRight = array.getDimension(
                        R.styleable.ShapeImageView_siv_radius_bottom_right,
                        0f
                )
                mShapeType = array.getInteger(
                        R.styleable.ShapeImageView_siv_shape_type,
                        ShapeType.RECTANGLE
                )
            } finally {
                array.recycle()
            }
        }
        // 初始化属性
        initProperties()
    }

    /**
     * 初始化属性
     */
    private fun initProperties() {
        when (mShapeType) {
            ShapeType.RECTANGLE -> {
                /* 初始化圆角弧度数组 */
                /* 向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。*/
                /* 圆角的半径，依次为左上角xy半径，右上角，右下角，左下角 */
                mRadii = when {
                    mRadius > 0f -> {
                        floatArrayOf(
                                mRadius, mRadius,
                                mRadius, mRadius,
                                mRadius, mRadius,
                                mRadius, mRadius
                        )
                    }
                    else -> {
                        floatArrayOf(
                                mRadiusTopLeft, mRadiusTopLeft,
                                mRadiusTopRight, mRadiusTopRight,
                                mRadiusBottomRight, mRadiusBottomRight,
                                mRadiusBottomLeft, mRadiusBottomLeft
                        )
                    }
                }
            }
            ShapeType.HEART -> {
                mHeartPointList = arrayListOf()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas) {
        when {
            drawable == null || mWidth == 0 || mHeight == 0 -> {
                return
            }
            else -> {
                // 绘制图片
                drawDrawable(canvas)
                // 绘制边框
                drawBorder(canvas)
            }
        }
    }

    /**
     * 绘制图片
     *
     * @param canvas    画布
     */
    private fun drawDrawable(canvas: Canvas) {
        val saveCount = canvas.save()
        // 重置Path
        mDrawablePath.reset()
        when (mShapeType) {
            ShapeType.RECTANGLE -> {
                // 绘制矩形
                drawRectangle()
            }
            ShapeType.CIRCLE -> {
                // 绘制圆形
                drawCircle()
            }
            ShapeType.HEART -> {
                // 绘制心形
                drawHeart()
            }
        }
        // 闭合图片路径
        mDrawablePath.close()
        // 剪裁图形
        canvas.clipPath(mDrawablePath)
        // 合并图像矩阵
        canvas.concat(imageMatrix)
        // 绘制图片到画布
        drawable.draw(canvas)
        canvas.restoreToCount(saveCount)
    }

    /**
     * 绘制矩形
     */
    private fun drawRectangle() {
        mRectF.left = mBorderWidth
        mRectF.top = mBorderWidth
        mRectF.right = mWidth - mBorderWidth
        mRectF.bottom = mHeight - mBorderWidth
        // 添加圆角矩形路径,Path.Direction.CW-顺时针;Path.Direction.CCW-逆时针
        mDrawablePath.addRoundRect(mRectF, mRadii, Path.Direction.CW)
    }

    /**
     * 绘制圆形
     */
    private fun drawCircle() {
        // 添加圆形路径,Path.Direction.CW-顺时针;Path.Direction.CCW-逆时针
        mDrawablePath.addCircle(
                mWidth.toFloat() / 2,
                mHeight.toFloat() / 2,
                mWidth.toFloat() / 2 - mBorderWidth,
                Path.Direction.CW
        )
    }

    /**
     * 笛卡尔心形线绘制心形
     */
    private fun drawHeart() {
        // t 代表弧度
        var t = 0.0
        // vt 代表 t 的增量
        val vt = 0.01
        // maxT 代表 t 的最大值
        val maxT = 2 * Math.PI
        // 需要循环的次数
        val maxI = ceil(maxT / vt).toInt()
        // 控制心形大小
        val size = min(mWidth / 2 , mHeight / 2 ) / 16
        // 根据方程得到所有点的坐标
        mHeartPointList?.clear()
        for (i in 0..maxI) {
            // x=16 * (sin(t)) ^ 3;
            // x 用来暂时保存每次循环得到的 x 坐标
            val x = 16 * sin(t).pow(3.0)
            // y=13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t)
            // y 用来暂时保存每次循环得到的 y 坐标
            val y = 13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t)
            t += vt
            mHeartPointList?.add((x * size + (mWidth / 2).toFloat()) to (-y * size + (mHeight / 2).toFloat()))
        }
        // 根据点的坐标，画出心形线
        setHeartPath()
    }

    /**
     * 获取心形路径
     */
    private fun setHeartPath(isBorder: Boolean = false) {
        mHeartPointList?.forEachIndexed { index, pair ->
            when {
                isBorder -> {
                    when (index) {
                        0 -> {
                            mBorderPath.moveTo(
                                    pair.first.toFloat(),
                                    pair.second.toFloat()
                            )
                        }
                        else -> {
                            mBorderPath.lineTo(
                                    pair.first.toFloat(),
                                    pair.second.toFloat()
                            )
                        }
                    }
                }
                else -> {
                    when (index) {
                        0 -> {
                            mDrawablePath.moveTo(
                                    pair.first.toFloat(),
                                    pair.second.toFloat()
                            )
                        }
                        else -> {
                            mDrawablePath.lineTo(
                                    pair.first.toFloat(),
                                    pair.second.toFloat()
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * 绘制边框
     *
     * @param canvas 画布
     */
    private fun drawBorder(canvas: Canvas) {
        if (mBorderWidth > 0) {
            val saveCount = canvas.save()
            mBorderPaint.strokeWidth = mBorderWidth
            mBorderPaint.style = Paint.Style.STROKE
            mBorderPaint.color = mBorderColor
            // 重置Path
            mBorderPath.reset()
            when (mShapeType) {
                ShapeType.RECTANGLE -> {
                    // 绘制矩形边框
                    drawRectangleBorder(canvas)
                }
                ShapeType.CIRCLE -> {
                    // 绘制圆形边框
                    drawCircleBorder(canvas)
                }
                ShapeType.HEART -> {
                    // 绘制心形边框
                    drawHeartBorder(canvas)
                }
            }
            // 闭合Path
            mBorderPath.close()
            canvas.restoreToCount(saveCount)
        }
    }

    /**
     * 绘制(圆角)矩形边框
     */
    private fun drawRectangleBorder(canvas: Canvas) {
        mRectF.left = mBorderWidth / 2
        mRectF.top = mBorderWidth / 2
        mRectF.right = mWidth - mBorderWidth / 2
        mRectF.bottom = mHeight - mBorderWidth / 2
        mBorderPath.addRoundRect(mRectF, mRadii, Path.Direction.CW)
        canvas.drawPath(mBorderPath, mBorderPaint)
    }

    /**
     * 绘制圆形边框
     */
    private fun drawCircleBorder(canvas: Canvas) {
        canvas.drawCircle(
                mWidth.toFloat() / 2,
                mHeight.toFloat() / 2,
                (mWidth.toFloat() - mBorderWidth) / 2,
                mBorderPaint
        )
    }

    /**
     * 绘制心形边框
     */
    private fun drawHeartBorder(canvas: Canvas) {
        setHeartPath(true)
        canvas.drawPath(mBorderPath, mBorderPaint)
    }

    /**
     * 设置边框颜色
     *
     * @param id 颜色资源id
     */
    fun setBorderColor(@ColorRes id: Int) {
        mBorderColor = ContextCompat.getColor(context, id)
        postInvalidate()
    }

    /**
     * 设置边框宽度
     *
     * @param dpValue 边框宽度,单位为DP
     */
    fun setBorderWidth(dpValue: Int) {
        mBorderWidth = DisplayUtils.dip2px(context, dpValue.toFloat())
        postInvalidate()
    }

    /**
     * 设置圆角半径
     *
     * @param dpValue 圆角半径
     */
    fun setRadius(dpValue: Int) {
        mRadius = DisplayUtils.dip2px(context, dpValue.toFloat())
        postInvalidate()
    }

    /**
     * 设置圆角半径
     *
     * @param radii 圆角半径
     */
    fun setRadii(radii: FloatArray) {
        mRadii = radii
        postInvalidate()
    }

    /**
     * 设置形状类型
     *
     * @param shapeType 形状类型[ShapeType]
     */
    fun setShapeType(@ShapeType shapeType: Int) {
        mShapeType = shapeType
        postInvalidate()
    }

    /**
     * @param placeholder if placeholder's size is 1,placeholder is placeholder,is error as well.
     *                    if placeholder's size is 2,placeholder[0] is placeholder,placeholder[1] is error.
     */
    fun load(obj: Any?, vararg placeholder: Int): ShapeImageView {
        mImageLoader.load(obj, *placeholder)
        return this
    }

    fun load(obj: Any?, options: RequestOptions): ShapeImageView {
        mImageLoader.load(obj, options)
        return this
    }

    /**
     * @param placeholder if placeholder's size is 1,placeholder is placeholder,is error as well.
     *                    if placeholder's size is 2,placeholder[0] is placeholder,placeholder[1] is error.
     */
    fun load(
            obj: Any?,
            transitionOptions: TransitionOptions<*, in Drawable?>,
            vararg placeholder: Int
    ): ShapeImageView {
        mImageLoader.load(obj, transitionOptions, *placeholder)
        return this
    }

    fun setOnGlideImageViewListener(listener: OnGlideImageViewListener?): ShapeImageView {
        mImageLoader.setOnGlideImageViewListener(listener)
        return this
    }

    fun setOnProgressListener(listener: OnProgressListener?): ShapeImageView {
        mImageLoader.setOnProgressListener(listener)
        return this
    }

    init {
        initAttr(context, attrs)
    }
}