# IrregularGlideImageView

    1.基于Glide V4.0封装的图片加载库，可以监听加载图片时的进度。

    2.可以通过设置属性加载圆角矩形(四个圆角可以任意设置)图片、圆形图片以及边框。

    3.可以设置触摸图片时的颜色、透明度。
    
------------------------------------------------------------------------------------
    <p align="center"> <img src="https://raw.githubusercontent.com/FPhoenixCorneaE/IrregularGlideImageView/master/IrregularGlideImageView/image/1.jpg" alt="Sample"  width="200" height="300"> <p align="center"> <em>预览图片</em> </p> </p>
------------------------------------------------------------------------------------

    XML中使用：

    ```
    <com.sunfusheng.glideimageview.GlideImageView
            android:id="@+id/iv_image11"
            android:layout_width="150dp"
            android:layout_height="90dp"
            android:layout_margin="5dp"
            android:scaleType="centerCrop"
            app:siv_border_color="@android:color/holo_purple"
            app:siv_border_width="3dp"
            app:siv_pressed_alpha="0.7"
            app:siv_pressed_color="@android:color/holo_blue_dark"
            app:siv_radius_top_left="10dp"
            app:siv_radius_top_right="10dp"
            app:siv_shape_type="rectangle" />
    ```

    ```
    <com.sunfusheng.glideimageview.GlideImageView
            android:id="@+id/iv_image12"
            android:layout_width="150dp"
            android:layout_height="90dp"
            android:layout_margin="5dp"
            android:scaleType="centerCrop"
            app:siv_border_color="@android:color/holo_purple"
            app:siv_border_width="3dp"
            app:siv_pressed_alpha="0.7"
            app:siv_pressed_color="@android:color/holo_blue_dark"
            app:siv_radius_bottom_left="10dp"
            app:siv_radius_bottom_right="10dp"
            app:siv_shape_type="rectangle" />
    ```

    ```
    <com.sunfusheng.glideimageview.GlideImageView
            android:id="@+id/iv_image21"
            android:layout_width="150dp"
            android:layout_height="90dp"
            android:layout_margin="5dp"
            android:scaleType="centerCrop"
            app:siv_border_color="@android:color/holo_purple"
            app:siv_border_width="3dp"
            app:siv_pressed_alpha="0.7"
            app:siv_pressed_color="@android:color/holo_blue_dark"
            app:siv_radius="-10dp"
            app:siv_radius_bottom_left="10dp"
            app:siv_radius_top_left="10dp"
           app:siv_shape_type="rectangle" />
    ```


    参考自sunfusheng大神的GlideImageView：https://github.com/sfsheng0322/GlideImageView。
