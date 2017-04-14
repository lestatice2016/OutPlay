package com.example.outplay.atys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.outplay.R;
import com.example.outplay.ld.Article;
import com.example.outplay.ld.Collection;
import com.example.outplay.ld.Like;
import com.example.outplay.ld.MyUser;
import com.example.outplay.utils.AsyncLoad;
import com.example.outplay.utils.StringUtils;
import com.sendtion.xrichtext.RichTextView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 *
 */
public class ContentShowActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton button1;
    ImageButton button2;
    ImageButton btn_comment;
    boolean judge1=true;
    boolean judge2=true;
    ImageView show_image_head;
    TextView show_name;
    TextView show_headline;
    TextView show_time;
    String name;
    String path;
    String id_delete_like;
    String id_delete_collection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_content_show);

        final String id=getIntent().getExtras().getString("id");
        final String cid=getIntent().getExtras().getString("cid");
        final boolean isuser=getIntent().getExtras().getBoolean("isuser");


        button1=(ImageButton)findViewById(R.id.button1);
        button2=(ImageButton)findViewById(R.id.button2);
        btn_comment=(ImageButton)findViewById(R.id.btn_comment);
        show_image_head=(ImageView)findViewById(R.id.show_image_head);
        show_name=(TextView)findViewById(R.id.show_name);
        show_headline=(TextView)findViewById(R.id.show_headline);
        show_time=(TextView)findViewById(R.id.show_time);


        RichTextView tv_note_content;
        tv_note_content = (RichTextView) findViewById(R.id.tv_note_content);
        AsyncLoad asyncLoad=new AsyncLoad(tv_note_content,show_headline,show_time,ContentShowActivity.this);
        asyncLoad.execute(id);

        final BmobQuery<MyUser> query=new BmobQuery<>();
        query.getObject(cid, new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    name = myUser.getUsername();
                    show_name.setText(name);
                    path = myUser.getImage();
                    Glide.with(ContentShowActivity.this).load(path).crossFade().centerCrop().into(show_image_head);
                }
            }
        });
        if (isuser){
            final MyUser mycurrentuser= BmobUser.getCurrentUser(MyUser.class);
            //"点赞"这里仍然有问题，只能在未退出页面时取消点赞
            button1.setOnClickListener(new View.OnClickListener() {
                Article article = new Article();

                @Override
                public void onClick(View v) {
                    if (judge1) {
                        article.increment("num_like");
                        article.update(id, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                            }
                        });
                        button1.setImageResource(R.drawable.like_red);
                        Like like=new Like();
                        like.setId_article(id);
                        like.setId_user(mycurrentuser.getObjectId());
                        like.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                id_delete_like=s;
                            }
                        });

                    } else {
                        article.increment("num_like", -1);
                        article.update(id, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                            }
                        });
                        button1.setImageResource(R.drawable.like);
                        Like like=new Like();
                        like.setObjectId(id_delete_like);
                        like.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Log.d("hehe","成功");
                                }else {
                                    Log.d("hehe","失败"+e.getMessage());
                                }
                            }
                        });
                    }
                    judge1 = !judge1;
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (judge2) {
                        button2.setImageResource(R.drawable.collection_red);
                        Collection collection=new Collection();
                        collection.setId_article(id);
                        collection.setId_user(mycurrentuser.getObjectId());
                        collection.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                id_delete_collection=s;
                            }
                        });
                    } else {

                        button2.setImageResource(R.drawable.collection);
                        Collection collection=new Collection();
                        collection.setObjectId(id_delete_collection);
                        collection.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Log.d("hehe","成功");
                                }else {
                                    Log.d("hehe","失败"+e.getMessage());
                                }
                            }
                        });
                    }
                    judge2 = !judge2;
                }
            });
            btn_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ContentShowActivity.this, test.class);
                    intent.putExtra("id", id);
                    intent.putExtra("cid", cid);
                    intent.putExtra("name",name);
                    intent.putExtra("path",path);
                    startActivity(intent);

                }
            });
        }else {
            button1.setOnClickListener(this);
            button2.setOnClickListener(this);
            btn_comment.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setMessage("登录后才可进行点击操作，确定登录吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ContentShowActivity.this,WelcomeActivity.class)) ;
                ContentShowActivity.this.finish();
                ShowActivity.replaceInstance.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    public static void showEditData(RichTextView tv_note_content,String content) {
        RichTextView show_note_content=tv_note_content;
        show_note_content.clearAllLayout();
        List<String> textList = StringUtils.cutStringByImgTag(content);
        for (int i = 0; i < textList.size(); i++) {
            String text = textList.get(i);
            if (text.contains("<img")) {
                String imagePath = StringUtils.getImgSrc(text);
                show_note_content.measure(0, 0);
                show_note_content.addImageViewAtIndex(show_note_content.getLastIndex(),imagePath);
            }else{
                show_note_content.addTextViewAtIndex(show_note_content.getLastIndex(), text);
            }
        }

    }

}
