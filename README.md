# ShapeImageView

### 可以通过设置属性加载圆角矩形(四个圆角可以任意设置)、圆形、心形、三角形、星形图片以及边框。


[![](https://jitpack.io/v/FPhoenixCorneaE/ShapeImageView.svg)](https://jitpack.io/#FPhoenixCorneaE/ShapeImageView)
    
------------------------------------------------------------------------------------

<p align="center"> <img src="https://github.com/FPhoenixCorneaE/ShapeImageView/blob/master/image/shape_image_view.gif" alt="预览图片"  width="320" height="480"></p>

------------------------------------------------------------------------------------

How to include it in your project:
--------------
**Step 1.** Add the JitPack repository to your build file
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

**Step 2.** Add the dependency
```groovy
dependencies {
	implementation("com.github.FPhoenixCorneaE:ShapeImageView:$latest")
}
```

XML中使用：
-----------
#### 圆角矩形
```xml
<com.fphoenixcorneae.shapeimageview.ShapeImageView
    android:id="@+id/ivImage31"
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
#### 圆角矩形
```xml
<com.fphoenixcorneae.shapeimageview.ShapeImageView
    android:id="@+id/ivImage41"
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
#### 圆形
 ```xml
 <com.fphoenixcorneae.shapeimageview.ShapeImageView
    android:id="@+id/ivImage51"
    android:layout_width="90dp"
    android:layout_height="90dp"
    android:layout_margin="10dp"
    android:scaleType="centerCrop"
    android:src="@mipmap/pic_the_manga_beauties"
    app:siv_border_color="@android:color/holo_purple"
    app:siv_border_width="3dp"
    app:siv_shape_type="circle" />
 ```
#### 心形
```xml
<com.fphoenixcorneae.shapeimageview.ShapeImageView
    android:id="@+id/ivImage61"
    android:layout_width="100dp"
    android:layout_height="100dp"
    android:layout_margin="10dp"
    android:scaleType="centerCrop"
    android:src="@mipmap/pic_the_manga_beauties"
    app:siv_border_color="@android:color/holo_purple"
    app:siv_border_width="3dp"
    app:siv_shape_type="heart" />
```
#### 星形
```xml
<com.fphoenixcorneae.shapeimageview.ShapeImageView
    android:id="@+id/ivImage62"
    android:layout_width="100dp"
    android:layout_height="100dp"
    android:layout_margin="10dp"
    android:scaleType="centerCrop"
    android:src="@mipmap/pic_the_manga_beauties"
    app:siv_border_color="@android:color/holo_purple"
    app:siv_border_width="3dp"
    app:siv_shape_type="star" />
```
#### 三角形
```xml
<com.fphoenixcorneae.shapeimageview.ShapeImageView
    android:id="@+id/ivImage71"
    android:layout_width="100dp"
    android:layout_height="100dp"
    android:layout_margin="10dp"
    android:background="@android:color/black"
    android:scaleType="centerCrop"
    android:src="@android:color/holo_red_dark"
    app:siv_border_color="@android:color/holo_purple"
    app:siv_border_width="3dp"
    app:siv_shape_type="triangle" />
```
------------------------------------------------

加载图片
----------------------------------------------
```kotlin
ivImage11.load("http://cdn.duitang.com/uploads/item/201410/14/20141014171627_ssXRa.gif")
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
```
