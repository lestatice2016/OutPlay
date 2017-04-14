package com.example.outplay.ld;

import android.graphics.Bitmap;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 杨苒 on 2017/3/24 0024.
 */
public class MyUser extends BmobUser{
    String  headimage;

    public String getImage() {
        return headimage;
    }

    public void setImage(String image) {
        this.headimage = image;
    }
}
