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

