package com.wkz.demo;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.wkz.shapeimageview.ShapeImageView;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ShapeImageView mIvImage11;
    private ShapeImageView mIvImage12;
    private LinearLayout mLlImage1;
    private ShapeImageView mIvImage21;
    private ShapeImageView mIvImage22;
    private ShapeImageView mIvImage31;
    private ShapeImageView mIvImage32;
    private ShapeImageView mIvImage41;
    private ShapeImageView mIvImage42;
    private ShapeImageView mIvImage51;
    private ShapeImageView mIvImage52;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mIvImage11 = (ShapeImageView) findViewById(R.id.iv_image11);
        mIvImage12 = (ShapeImageView) findViewById(R.id.iv_image12);
        mLlImage1 = (LinearLayout) findViewById(R.id.ll_image1);
        mLlImage1.setOnClickListener(this);
        mIvImage21 = (ShapeImageView) findViewById(R.id.iv_image21);
        mIvImage22 = (ShapeImageView) findViewById(R.id.iv_image22);
        mIvImage31 = (ShapeImageView) findViewById(R.id.iv_image31);
        mIvImage32 = (ShapeImageView) findViewById(R.id.iv_image32);
        mIvImage41 = (ShapeImageView) findViewById(R.id.iv_image41);
        mIvImage42 = (ShapeImageView) findViewById(R.id.iv_image42);
        mIvImage51 = (ShapeImageView) findViewById(R.id.iv_image51);
        mIvImage52 = (ShapeImageView) findViewById(R.id.iv_image52);

        mIvImage11.load("http://cdn.duitang.com/uploads/item/201410/14/20141014171627_ssXRa.gif");
        mIvImage12.load("http://b-ssl.duitang.com/uploads/item/201804/06/20180406175831_v2tcn.jpeg", 0);
        mIvImage21.load(R.mipmap.pic_the_running_wolf, R.mipmap.pic_the_running_wolf);
        mIvImage22.load(null, android.R.color.holo_red_dark, android.R.color.holo_red_dark);
        mIvImage31.load(R.mipmap.pic_wrath_of_the_lich_king);
        // 设置加载动画
        mIvImage32.load(R.mipmap.pic_wrath_of_the_lich_king, GenericTransitionOptions.with(view -> {
            PropertyValuesHolder scaleXAnim = PropertyValuesHolder.ofFloat("scaleX", 1.382f, 1f);
            PropertyValuesHolder scaleYAnim = PropertyValuesHolder.ofFloat("scaleY", 1.382f, 1f);

            ObjectAnimator.ofPropertyValuesHolder(view, scaleXAnim, scaleYAnim)
                    .setDuration(200)
                    .start();
        }), android.R.color.darker_gray);
        mIvImage41.load(R.mipmap.pic_listen_to_the_autumn_rain, DrawableTransitionOptions.withCrossFade(), 0);
        mIvImage42.load("", android.R.color.darker_gray);
        mIvImage51.load(R.mipmap.pic_the_manga_beauties, android.R.color.darker_gray);
        // 自定义RequestOptions
        mIvImage52.load(
                R.mipmap.pic_the_manga_beauties,
                new RequestOptions()
                        .placeholder(android.R.color.darker_gray)
                        .error(android.R.color.darker_gray)
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_image1:
                Toast.makeText(this, "Click Image1", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
