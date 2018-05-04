package com.wkz.irregularglideimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sunfusheng.glideimageview.GlideImageView;

public class MainActivity extends AppCompatActivity {


    private GlideImageView mImage11Iv;
    private GlideImageView mImage12Iv;
    private GlideImageView mImage21Iv;
    private GlideImageView mImage22Iv;
    private GlideImageView mImage31Iv;
    private GlideImageView mImage41Iv;
    private GlideImageView mImage42Iv;
    private GlideImageView mImage32Iv;
    private GlideImageView mImage51Iv;
    private GlideImageView mImage52Iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        mImage11Iv = (GlideImageView) findViewById(R.id.iv_image11);
        mImage12Iv = (GlideImageView) findViewById(R.id.iv_image12);
        mImage21Iv = (GlideImageView) findViewById(R.id.iv_image21);
        mImage22Iv = (GlideImageView) findViewById(R.id.iv_image22);
        mImage31Iv = (GlideImageView) findViewById(R.id.iv_image31);
        mImage32Iv = (GlideImageView) findViewById(R.id.iv_image32);
        mImage41Iv = (GlideImageView) findViewById(R.id.iv_image41);
        mImage51Iv = (GlideImageView) findViewById(R.id.iv_image51);
        mImage52Iv = (GlideImageView) findViewById(R.id.iv_image52);

        mImage11Iv.loadImage("https://img5.duitang.com/uploads/item/201411/29/20141129013744_UJEuu.gif", 0);
        mImage12Iv.loadImage("https://img5.duitang.com/uploads/item/201411/29/20141129013744_UJEuu.gif", 0);
        mImage21Iv.loadImage("https://img5.duitang.com/uploads/item/201411/29/20141129013744_UJEuu.gif", 0);
        mImage22Iv.loadImage("https://img5.duitang.com/uploads/item/201411/29/20141129013744_UJEuu.gif", 0);
        mImage31Iv.loadImage("https://img5.duitang.com/uploads/item/201411/29/20141129013744_UJEuu.gif", 0);
        mImage32Iv.loadImage("https://img5.duitang.com/uploads/item/201411/29/20141129013744_UJEuu.gif", 0);
        mImage41Iv.loadImage("https://img5.duitang.com/uploads/item/201411/29/20141129013744_UJEuu.gif", 0);
        mImage51Iv.loadImage("https://img5.duitang.com/uploads/item/201411/29/20141129013744_UJEuu.gif", 0);
        mImage52Iv.loadImage("https://img5.duitang.com/uploads/item/201411/29/20141129013744_UJEuu.gif", 0);
    }
}
