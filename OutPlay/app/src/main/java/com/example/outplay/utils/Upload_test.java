package com.example.outplay.utils;

import android.util.Log;

import com.example.outplay.atys.EditActivity;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 杨苒 on 2016/12/16 0016.
 */
public class Upload_test {
    public static void pass(final String path) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {

                if (e == null) {
                    final String url= bmobFile.getFileUrl();
                    EditActivity.getUploadUrl(url,path);
                }else{
                    Log.d("hehe","出错原因"+e.getMessage());
                }
            }
        });
    }

}
