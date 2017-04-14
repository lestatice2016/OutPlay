package com.example.outplay.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.outplay.ld.MyUser;
import com.sendtion.xrichtext.SDCardUtil;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 杨苒 on 2017/3/24 0024.
 */
public class updateImage {
    public static void update(Bitmap bitmap, final String id) {
        final MyUser myUser = new MyUser();
        String path = SDCardUtil.saveToSdCard(bitmap);
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    myUser.setImage(bmobFile.getFileUrl());
                    myUser.update(id, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                            } else {
                                Log.d("hehe", "失败" + e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }
}