package com.wkz.demo

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*

class DemoActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        ll_image1.setOnClickListener(this)
        iv_image11.load("http://cdn.duitang.com/uploads/item/201410/14/20141014171627_ssXRa.gif")
        iv_image12.load("http://b-ssl.duitang.com/uploads/item/201804/06/20180406175831_v2tcn.jpeg", 0)
        iv_image21.load(R.mipmap.pic_the_running_wolf, R.mipmap.pic_the_running_wolf)
        iv_image22.load(null, android.R.color.holo_red_dark, android.R.color.holo_red_dark)
        iv_image31.load(R.mipmap.pic_wrath_of_the_lich_king)
        // 设置加载动画
        iv_image32.load(R.mipmap.pic_wrath_of_the_lich_king, GenericTransitionOptions.with { view: View? ->
            val scaleXAnim = PropertyValuesHolder.ofFloat("scaleX", 1.382f, 1f)
            val scaleYAnim = PropertyValuesHolder.ofFloat("scaleY", 1.382f, 1f)
            ObjectAnimator.ofPropertyValuesHolder(view, scaleXAnim, scaleYAnim)
                    .setDuration(200)
                    .start()
        }, android.R.color.darker_gray)
        iv_image41.load(R.mipmap.pic_listen_to_the_autumn_rain, DrawableTransitionOptions.withCrossFade(), 0)
        iv_image42.load("", android.R.color.darker_gray)
        iv_image51.load(R.mipmap.pic_the_manga_beauties, android.R.color.darker_gray)
        // 自定义RequestOptions
        iv_image52.load(
                R.mipmap.pic_the_manga_beauties,
                RequestOptions()
                        .placeholder(android.R.color.darker_gray)
                        .error(android.R.color.darker_gray)
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_image1 -> Toast.makeText(this, "Click Image1", Toast.LENGTH_SHORT).show()
            else -> {
            }
        }
    }
}