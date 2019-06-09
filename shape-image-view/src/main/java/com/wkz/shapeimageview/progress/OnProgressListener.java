package com.wkz.shapeimageview.progress;

import com.bumptech.glide.load.engine.GlideException;

/**
 * @author Administrator
 */
public interface OnProgressListener {

    void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception);
}
