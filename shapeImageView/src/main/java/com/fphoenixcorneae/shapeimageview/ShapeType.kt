package com.fphoenixcorneae.shapeimageview

import androidx.annotation.IntDef

/**
 * 形状类型
 */
@IntDef(
        ShapeType.Rectangle,
        ShapeType.Circle,
        ShapeType.Heart,
        ShapeType.Triangle,
        ShapeType.Star
)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class ShapeType {
    companion object {
        /* 矩形 */
        const val Rectangle = 0

        /* 圆形 */
        const val Circle = 1

        /* 心形 */
        const val Heart = 2

        /* 三角形 */
        const val Triangle = 3

        /* 星形 */
        const val Star = 4
    }
}