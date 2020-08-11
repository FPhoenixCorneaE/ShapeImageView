package com.fphoenixcorneae.shapeimageview.progress

import com.bumptech.glide.load.engine.GlideException

interface OnProgressListener {
    fun onProgress(
        imageUrl: String?,
        bytesRead: Long,
        totalBytes: Long,
        isDone: Boolean,
        exception: GlideException?
    )
}