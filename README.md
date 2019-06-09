# ShapeImageView

   1、可以通过设置属性加载圆角矩形(四个圆角可以任意设置)图片、圆形图片以及边框。

    
------------------------------------------------------------------------------------

<p align="center"> <img src="https://github.com/FPhoenixCorneaE/ShapeImageView/blob/master/image/shape_image_view.gif" alt="预览图片"  width="320" height="480"></p>

------------------------------------------------------------------------------------

XML中使用：
-----------

```
<com.wkz.shapeimageview.ShapeImageView
            android:id="@+id/iv_image31"
            android:layout_width="150dp"
            android:layout_height="90dp"
            android:layout_margin="10dp"
            android:scaleType="centerCrop"
            android:src="@mipmap/pic_wrath_of_the_lich_king"
            app:siv_border_color="@android:color/holo_purple"
            app:siv_border_width="3dp"
            app:siv_radius="-10dp"
            app:siv_radius_bottom_right="10dp"
            app:siv_radius_top_left="10dp"
            app:siv_shape_type="rectangle" />
```
```
<com.wkz.shapeimageview.ShapeImageView
            android:id="@+id/iv_image41"
            android:layout_width="150dp"
            android:layout_height="90dp"
            android:layout_margin="10dp"
            android:scaleType="centerCrop"
            android:src="@mipmap/pic_listen_to_the_autumn_rain"
            app:siv_border_color="@android:color/holo_purple"
            app:siv_border_width="3dp"
            app:siv_radius="10dp"
            app:siv_shape_type="rectangle" />
 ```

 ```
 <com.wkz.shapeimageview.ShapeImageView
             android:id="@+id/iv_image51"
             android:layout_width="90dp"
             android:layout_height="90dp"
             android:layout_margin="10dp"
             android:scaleType="centerCrop"
             android:src="@mipmap/pic_the_manga_beauties"
             app:siv_border_color="@android:color/holo_purple"
             app:siv_border_width="3dp"
             app:siv_shape_type="circle" />
 ```
------------------------------------------------

加载图片
----------------------------------------------
```
mIvImage11.load("https://img5.duitang.com/uploads/item/201411/29/20141129013744_UJEuu.gif");
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
```
