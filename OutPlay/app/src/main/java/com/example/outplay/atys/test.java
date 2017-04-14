package com.example.outplay.atys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.outplay.R;
import com.example.outplay.ld.Comments;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


/**
     * 该类用作显示第一页评论
     */
public class test extends AppCompatActivity {
    EditText edit_comment;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final String id_article=getIntent().getExtras().getString("id");
        final String id_commentor=getIntent().getExtras().getString("cid");
        final String name=getIntent().getExtras().getString("name");
        final String path=getIntent().getExtras().getString("path");

        final ListView ll = (ListView) findViewById(R.id.listview_out);
        edit_comment=(EditText)findViewById(R.id.edit_comment);
        btn=(Button)findViewById(R.id.btn);

        BmobQuery<Comments> query=new BmobQuery<>();
        query.addWhereEqualTo("id_article", id_article);
        query.order("-createdAt");
        query.findObjects(new FindListener<Comments>() {
            @Override
            public void done(final List<Comments> list, BmobException e) {
                if (e == null) {
                    ll.setAdapter(new MyAdapter(list));

                    ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String name=list.get(position).getName_commentor();
                            Intent intent=new Intent(test.this,showSubactivity.class);
                            intent.putExtra("name",name);
                            intent.putExtra("image",list.get(position).getImagepath());
                            intent.putExtra("id_commentor",list.get(position).getId_commentor());
                            intent.putExtra("id_comment",list.get(position).getObjectId());
                            intent.putExtra("comment",list.get(position).getComment());
                            intent.putExtra("time",list.get(position).getCreatedAt());
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comments comments = new Comments();
                comments.setConment(edit_comment.getText().toString());
                comments.setId_article(id_article);
                comments.setId_commentor(id_commentor);
                comments.setName_commentor(name);
                comments.setImagepath(path);
                comments.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(test.this, "评论成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    public class MyAdapter extends BaseAdapter {
        List<Comments> list;
        public MyAdapter(List<Comments> list){
            this.list=list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            myHolder myHolder;
            View view;
            if (convertView==null){
                myHolder=new myHolder();
                view= LayoutInflater.from(test.this).inflate(R.layout.comment_out_item,null);
                myHolder.textView=(TextView)view.findViewById(R.id.textv);
                myHolder.imageView=(ImageView)view.findViewById(R.id.image_comment_head);
                myHolder.text_name=(TextView)view.findViewById(R.id.text_name);
                myHolder.text_time=(TextView)view.findViewById(R.id.show_comment_time);
                view.setTag(myHolder);
            }else{
                view=convertView;
                myHolder=(myHolder)view.getTag();
            }
            myHolder.textView.setText(list.get(position).getComment());
            Glide.with(test.this).load(list.get(position).getImagepath()).crossFade().centerCrop().into(myHolder.imageView);
            myHolder.text_name.setText(list.get(position).getName_commentor());
            myHolder.text_time.setText(list.get(position).getCreatedAt());

            return view;
        }
    }
}

class myHolder{
    ImageView imageView;
    TextView textView;
    TextView text_name;
    TextView text_time;
}

