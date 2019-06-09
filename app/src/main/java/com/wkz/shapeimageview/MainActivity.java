package com.wkz.shapeimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GlideImageView mIvImage11;
    private GlideImageView mIvImage12;
    private LinearLayout mLlImage1;
    private GlideImageView mIvImage21;
    private GlideImageView mIvImage22;
    private GlideImageView mIvImage31;
    private GlideImageView mIvImage32;
    private GlideImageView mIvImage41;
    private GlideImageView mIvImage42;
    private GlideImageView mIvImage51;
    private GlideImageView mIvImage52;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mIvImage11 = (GlideImageView) findViewById(R.id.iv_image11);
        mIvImage12 = (GlideImageView) findViewById(R.id.iv_image12);
        mLlImage1 = (LinearLayout) findViewById(R.id.ll_image1);
        mLlImage1.setOnClickListener(this);
        mIvImage21 = (GlideImageView) findViewById(R.id.iv_image21);
        mIvImage22 = (GlideImageView) findViewById(R.id.iv_image22);
        mIvImage31 = (GlideImageView) findViewById(R.id.iv_image31);
        mIvImage32 = (GlideImageView) findViewById(R.id.iv_image32);
        mIvImage41 = (GlideImageView) findViewById(R.id.iv_image41);
        mIvImage42 = (GlideImageView) findViewById(R.id.iv_image42);
        mIvImage51 = (GlideImageView) findViewById(R.id.iv_image51);
        mIvImage52 = (GlideImageView) findViewById(R.id.iv_image52);

        mIvImage11.loadImage("https://img5.duitang.com/uploads/item/201411/29/20141129013744_UJEuu.gif", 0);
        mIvImage12.loadImage("http://b-ssl.duitang.com/uploads/item/201804/06/20180406175831_v2tcn.jpeg", 0);
        mIvImage21.loadLocalImage(R.mipmap.pic_the_running_wolf, 0);
        mIvImage22.loadLocalImage(R.mipmap.pic_the_running_wolf, 0);
        mIvImage31.loadLocalImage(R.mipmap.pic_wrath_of_the_lich_king, 0);
        mIvImage32.loadLocalImage(R.mipmap.pic_wrath_of_the_lich_king, 0);
        mIvImage41.loadLocalImage(R.mipmap.pic_listen_to_the_autumn_rain, 0);
        mIvImage51.loadLocalImage(R.mipmap.pic_the_manga_beauties, 0);
        mIvImage52.loadLocalImage(R.mipmap.pic_the_manga_beauties, 0);
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
