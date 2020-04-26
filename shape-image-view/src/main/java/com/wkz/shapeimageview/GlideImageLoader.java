package com.wkz.shapeimageview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.wkz.shapeimageview.progress.OnGlideImageViewListener;
import com.wkz.shapeimageview.progress.OnProgressListener;
import com.wkz.shapeimageview.progress.ProgressManager;

import java.lang.ref.WeakReference;

/**
 * 图片加载器
 *
 * @author Administrator
 */
public class GlideImageLoader {

    private static final String HTTP = "http";

    private WeakReference<ImageView> mImageView;
    private Object mImageUrlObj;
    private long mTotalBytes = 0;
    private long mLastBytesRead = 0;
    private boolean mLastStatus = false;
    private Handler mMainThreadHandler;

    private OnProgressListener internalProgressListener;
    private OnGlideImageViewListener onGlideImageViewListener;
    private OnProgressListener onProgressListener;


    public GlideImageLoader(ImageView imageView) {
        mImageView = new WeakReference<>(imageView);
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    public ImageView getImageView() {
        return mImageView != null ? mImageView.get() : null;
    }

    public Context getContext() {
        return getImageView() != null ? getImageView().getContext() : null;
    }

    public String getImageUrl() {
        return mImageUrlObj == null || !(mImageUrlObj instanceof String) ? null : (String) mImageUrlObj;
    }

    public void load(Object obj, int... placeholder) {
        load(obj, requestOptions(placeholder));
    }

    public void load(Object obj, RequestOptions options) {
        if (getContext() == null) {
            return;
        }
        requestBuilder(obj, options).into(getImageView());
    }

    public void load(Object obj, TransitionOptions<?, ? super Drawable> transitionOptions, int... placeholder) {
        if (getContext() == null || transitionOptions == null) {
            return;
        }
        requestBuilder(obj, requestOptions(placeholder)).transition(transitionOptions).into(getImageView());
    }

    public RequestBuilder<Drawable> requestBuilder(Object obj, RequestOptions options) {
        this.mImageUrlObj = obj;
        return Glide.with(getContext())
                .load(obj)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        mainThreadCallback(mLastBytesRead, mTotalBytes, true, e);
                        ProgressManager.removeProgressListener(internalProgressListener);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mainThreadCallback(mLastBytesRead, mTotalBytes, true, null);
                        ProgressManager.removeProgressListener(internalProgressListener);
                        return false;
                    }
                });
    }

    public RequestOptions requestOptions(@DrawableRes int... placeholder) {
        RequestOptions requestOptions = new RequestOptions();
        if (placeholder.length == 0) {
            return requestOptions;
        } else if (placeholder.length == 1) {
            return requestOptions.placeholder(placeholder[0])
                    .error(placeholder[0]);
        } else {
            return requestOptions.placeholder(placeholder[0])
                    .error(placeholder[1]);
        }
    }

    public RequestOptions requestOptions(Drawable... placeholder) {
        RequestOptions requestOptions = new RequestOptions();
        if (placeholder.length == 0) {
            return requestOptions;
        } else if (placeholder.length == 1) {
            return requestOptions.placeholder(placeholder[0])
                    .error(placeholder[0]);
        } else {
            return requestOptions.placeholder(placeholder[0])
                    .error(placeholder[1]);
        }
    }

    public RequestOptions circleRequestOptions(@DrawableRes int... placeholder) {
        return requestOptions(placeholder)
                .transform(new CircleCrop());
    }

    public RequestOptions circleRequestOptions(Drawable... placeholder) {
        return requestOptions(placeholder)
                .transform(new CircleCrop());
    }

    private void addProgressListener() {
        if (getImageUrl() == null) {
            return;
        }
        final String url = getImageUrl();
        if (!url.startsWith(HTTP)) {
            return;
        }

        internalProgressListener = new OnProgressListener() {
            @Override
            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
                if (totalBytes == 0) {
                    return;
                }
                if (!url.equals(imageUrl)) {
                    return;
                }
                if (mLastBytesRead == bytesRead && mLastStatus == isDone) {
                    return;
                }

                mLastBytesRead = bytesRead;
                mTotalBytes = totalBytes;
                mLastStatus = isDone;
                mainThreadCallback(bytesRead, totalBytes, isDone, exception);

                if (isDone) {
                    ProgressManager.removeProgressListener(this);
                }
            }
        };
        ProgressManager.addProgressListener(internalProgressListener);
    }

    private void mainThreadCallback(final long bytesRead, final long totalBytes, final boolean isDone, final GlideException exception) {
        mMainThreadHandler.post(() -> {
            final int percent = (int) ((bytesRead * 1.0f / totalBytes) * 100.0f);
            if (onProgressListener != null) {
                onProgressListener.onProgress((String) mImageUrlObj, bytesRead, totalBytes, isDone, exception);
            }

            if (onGlideImageViewListener != null) {
                onGlideImageViewListener.onProgress(percent, isDone, exception);
            }
        });
    }

    public void setOnGlideImageViewListener(OnGlideImageViewListener onGlideImageViewListener) {
        this.onGlideImageViewListener = onGlideImageViewListener;
        addProgressListener();
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
        addProgressListener();
    }
}
