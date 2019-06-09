package com.wkz.shapeimageview.progress;

import com.bumptech.glide.load.engine.GlideException;

/**
 * @author Administrator
 */
public interface OnGlideImageViewListener {

    /**
     * 加载进度
     *
     * @param percent   百分比
     * @param isDone    加载失败或者加载完毕
     * @param exception 异常
     */
    void onProgress(int percent, boolean isDone, GlideException exception);
}
