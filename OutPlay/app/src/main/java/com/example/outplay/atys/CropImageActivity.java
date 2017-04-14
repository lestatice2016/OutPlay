package com.example.outplay.atys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.outplay.R;
import com.example.outplay.ld.MyUser;
import com.example.outplay.utils.updateImage;
import com.sendtion.xrichtext.SDCardUtil;

import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class CropImageActivity extends AppCompatActivity {
    Button btn;
    ImageView imageView ;
    static final int CODE_CAMERA = 1;
    static final int CODE_GALLERY = 2;
    static final int PHOTO_REQUEST_CUT=3;
    MyUser myUserCurrent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_crop_image);
        btn=(Button)findViewById(R.id.alter_image);
        imageView=(ImageView) findViewById(R.id.image_head);
        myUserCurrent= BmobUser.getCurrentUser(MyUser.class);

            BmobQuery<MyUser> query=new BmobQuery<>();
            query.getObject(myUserCurrent.getObjectId(), new QueryListener<MyUser>() {
                @Override
                public void done(MyUser myUser, BmobException e) {
                    if (e == null) {
                        String path1 = myUser.getImage();
                        Glide.with(CropImageActivity.this).load(path1).into(imageView);
                    }else {
                        Toast.makeText(CropImageActivity.this, "图片加载失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"照相机", "本地图片库"};
                AlertDialog.Builder builder = new AlertDialog.Builder(CropImageActivity.this)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        chooseCamera();
                                        break;
                                    case 1:
                                        chooseGallery();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
                builder.show();
            }
        });

    }

    private void chooseCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CODE_CAMERA);
    }

    private void chooseGallery() {
        //注意权限
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, CODE_GALLERY);
    }

    @Override
    protected void  onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_CAMERA:
                if (data == null) {
                    return;
                } else {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        bitmap = bundle.getParcelable("data");
                        String path= SDCardUtil.saveToSdCard(bitmap);
                        crop(Uri.fromFile(new File(path)));
                    }
                }
                break;
            case CODE_GALLERY:
                if (data == null) {
                    return;
                } else {
                    //尝试加入压缩图片
                    Uri uri = data.getData();
                    crop(uri);
                }
                break;
            case PHOTO_REQUEST_CUT:
                Bitmap bitmap1 = data.getParcelableExtra("data");
                imageView.setImageBitmap(bitmap1);
                uploadImage(bitmap1);
                break;
            default:
                break;
        }

    }

    public void uploadImage(Bitmap bitmap){
        Log.d("hehe", "id是" + myUserCurrent.getObjectId());
        updateImage.update(bitmap,myUserCurrent.getObjectId());

    }
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


}
