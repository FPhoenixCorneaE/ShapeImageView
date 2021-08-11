package com.fphoenixcorneae.shapeimageview.demo

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.fphoenixcorneae.shapeimageview.demo.databinding.ActivityMainBinding

class DemoActivity : AppCompatActivity(), View.OnClickListener {

    private var mViewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding!!.root)
        initView()
    }

    private fun initView() {
        mViewBinding?.apply {
            llImage1.setOnClickListener(this@DemoActivity)
            ivImage11.load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fphotocdn.sohu.com%2F20150718%2Fmp23250420_1437174410328_2.gif&refer=http%3A%2F%2Fphotocdn.sohu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1631181009&t=9c70172901b6d324c532b29e75b8a5c9")
            ivImage12.load("http://b-ssl.duitang.com/uploads/item/201804/06/20180406175831_v2tcn.jpeg", 0)
            ivImage21.load(R.mipmap.pic_the_running_wolf, R.mipmap.pic_the_running_wolf)
            ivImage22.load(null, android.R.color.holo_red_dark, android.R.color.holo_red_dark)
            ivImage31.load(R.mipmap.pic_wrath_of_the_lich_king)
            // 设置加载动画
            ivImage32.load(R.mipmap.pic_wrath_of_the_lich_king, GenericTransitionOptions.with { view: View? ->
                val scaleXAnim = PropertyValuesHolder.ofFloat("scaleX", 1.382f, 1f)
                val scaleYAnim = PropertyValuesHolder.ofFloat("scaleY", 1.382f, 1f)
                ObjectAnimator.ofPropertyValuesHolder(view, scaleXAnim, scaleYAnim)
                    .setDuration(200)
                    .start()
            }, android.R.color.darker_gray)
            ivImage41.load(R.mipmap.pic_listen_to_the_autumn_rain, DrawableTransitionOptions.withCrossFade(), 0)
            ivImage42.load("", android.R.color.darker_gray)
            ivImage51.load(R.mipmap.pic_the_manga_beauties, android.R.color.darker_gray)
            // 自定义RequestOptions
            ivImage52.load(
                R.mipmap.pic_the_manga_beauties,
                RequestOptions()
                    .placeholder(android.R.color.darker_gray)
                    .error(android.R.color.darker_gray)
            )
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.llImage1 -> Toast.makeText(this, "Click Image1", Toast.LENGTH_SHORT).show()
            else -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding = null
    }
}