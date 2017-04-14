package com.example.outplay.atys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.outplay.R;
import com.example.outplay.ld.Article;
import com.example.outplay.ld.MyUser;
import com.example.outplay.utils.Upload_test;
import com.sendtion.xrichtext.RichTextEditor;
import com.sendtion.xrichtext.SDCardUtil;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class EditActivity extends AppCompatActivity {
    Button publish;
    Button add;
    EditText icon_headline;
    PopupMenu popupMenu = null;
    static final int CODE_CAMERA = 1;
    static final int CODE_GALLERY = 2;
    static RichTextEditor et_new_content;
    String noteContent;
    MyUser mycurrentuser;
    boolean judge=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit);

        et_new_content = (RichTextEditor) findViewById(R.id.et_new_content);
        publish = (Button) findViewById(R.id.publish);
        add = (Button) findViewById(R.id.add);
        icon_headline = (EditText) findViewById(R.id.headline);
        mycurrentuser= BmobUser.getCurrentUser(MyUser.class);

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onPopupButtonClicked((Button) v);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"照相机", "本地图片库"};
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this)
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_CAMERA:
                if (data == null) {
                    return;
                } else {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmap = bundle.getParcelable("data");
                        String path= SDCardUtil.saveToSdCard(bitmap);
                        Upload_test.pass(path);
                    }
                }
                break;
            case CODE_GALLERY:
                if (data == null) {
                    return;
                } else {
                    //尝试加入压缩图片
                    Uri uri = data.getData();
                    String path= SDCardUtil.getFilePathByUri(this, uri);
                    Upload_test.pass(path);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获得上传图片的url位置
     *
     */
    public static void getUploadUrl(String url,String path){
        et_new_content.inserItmage(url, path, 80);
    }

    public void onPopupButtonClicked(Button button) {
        popupMenu = new PopupMenu(this, button);
        getMenuInflater().inflate(R.menu.menu_edit_type, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                noteContent = getEditData(); //生成数据
                Article article=new Article();
                article.setHeadline(icon_headline.getText().toString());
                article.setContent(noteContent);
                article.setId_creator(mycurrentuser.getObjectId());
                switch (item.getItemId()) {
                    case R.id.publish_article:
                        //这一段可以移到外面去，只需要修改权限这一行代码就可以了
                        article.setType(1);//1是文章
                        article.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(EditActivity.this, "上传成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(EditActivity.this, "上传失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        judge=false;
                        break;
                    case R.id.publish_transation:
                        article.setType(2);//2是帖子
                        article.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(EditActivity.this, "上传成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(EditActivity.this, "上传失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        judge=false;
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (judge){
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                AlertDialog.Builder isExit = new AlertDialog.Builder(this)
                        .setMessage("确定要放弃此次内容的编辑？");
                isExit.setNegativeButton("取消", listener);
                isExit.setPositiveButton("确认", listener);
                isExit.create().show();
                return true;
            }
        }else{
            finish();
        }
        return false;
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    break;
                default:
                    break;
            }
        }
    };

private String getEditData() {
    List<RichTextEditor.EditData> editList =et_new_content.buildEditData();
    StringBuffer content = new StringBuffer();
    for (RichTextEditor.EditData itemData : editList) {
        if (itemData.inputStr != null) {
            content.append(itemData.inputStr);
        } else if (itemData.imagePath != null) {
            content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
        }
    }
    return content.toString();
}
}
