package com.example.outplay.atys;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.outplay.R;
import com.example.outplay.ld.Collection;
import com.example.outplay.ld.Like;
import com.example.outplay.ld.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class ExitActivity extends AppCompatActivity {
    ImageButton imageButton;
    TextView like;
    TextView keep;
    TextView alter;
    TextView exit;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_exit);

        imageButton=(ImageButton)findViewById(R.id.headimage);
        alter=(TextView)findViewById(R.id.alter);
        keep=(TextView)findViewById(R.id.keep);
        like=(TextView)findViewById(R.id.like);
        username=(TextView)findViewById(R.id.show_username);
        exit=(TextView)findViewById(R.id.exit);


        boolean isuser=getIntent().getExtras().getBoolean("isuser");
        if (isuser){
            final MyUser myUser= BmobUser.getCurrentUser(MyUser.class);
            String id=myUser.getObjectId();
            new AsyncLoadifo(ExitActivity.this,imageButton,username).execute(id);

            BmobQuery<Like> query = new BmobQuery<Like>();
            query.addWhereEqualTo("id_user", id);
            query.order("-createdAt");
            final ArrayList<String> list_id_art=new ArrayList<String>();
            query.findObjects(new FindListener<Like>() {
                @Override
                public void done(List<Like> list, BmobException e) {
                    if (e == null) {
                        for (Like like : list) {
                            list_id_art.add(like.getId_article());
                        }
                    } else {
                        Log.d("hehe", "失败：" + e.getMessage());
                    }
                }
            });

            BmobQuery<Collection> query1=new BmobQuery<>();
            query1.addWhereEqualTo("id_user", id);
            query1.order("-createdAt");
            final ArrayList<String> list_id_art_2=new ArrayList<String>();
            query1.findObjects(new FindListener<Collection>() {
                @Override
                public void done(List<Collection> list, BmobException e) {
                    if (e == null) {
                        for (Collection collection : list) {
                            list_id_art_2.add(collection.getId_article());
                        }
                    } else {
                        Log.d("hehe", "失败：" + e.getMessage());
                    }
                }
            });


            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ExitActivity.this, CropImageActivity.class));
                }
            });
            alter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ExitActivity.this,MyselfActivity.class)) ;
                }
            });

            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myUser.logOut();
                    startActivity(new Intent(ExitActivity.this, WelcomeActivity.class));
                    finish();
                    ShowActivity.replaceInstance.finish();
                }
            });
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ExitActivity.this,MyLikeActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putStringArrayList("list",list_id_art);
                    intent.putExtras(bundle);
                    startActivity(intent) ;
                }
            });
            keep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ExitActivity.this,MyCollectionActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putStringArrayList("list2",list_id_art_2);
                    intent.putExtras(bundle);
                    startActivity(intent) ;
                }
            });
        }else {
            username.setText("登录/注册");
            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(ExitActivity.this, WelcomeActivity.class));
                    finish();
                    ShowActivity.replaceInstance.finish();
                }
            });
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExitActivity.this.finish();
                    ShowActivity.replaceInstance.finish();
                }
            });
        }
    }
}
class AsyncLoadifo extends AsyncTask<String,Integer,Object>{
    Context context;
    ImageButton imageButton;
    TextView textView;

    public AsyncLoadifo(Context context,ImageButton imageButton,TextView textView) {
        this.context = context;
        this.imageButton=imageButton;
        this.textView=textView;
    }

    @Override
    protected Object doInBackground(String... params) {
        BmobQuery<MyUser> bmobQuery=new BmobQuery();
        bmobQuery.getObject(params[0], new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    String path = myUser.getImage();
                    Glide.with(context).load(path).into(imageButton);
//                    Glide.with(context).load(path).crossFade().centerCrop().into(imageButton);
                    textView.setText(myUser.getUsername());
                } else {
                    Toast.makeText(context, "加载失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return null;
    }
}
