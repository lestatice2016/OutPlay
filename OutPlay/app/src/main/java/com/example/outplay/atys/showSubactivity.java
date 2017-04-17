package com.example.outplay.atys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.outplay.R;
import com.example.outplay.ld.SubComments;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 该类用作显示第二页评论
 */
public class showSubactivity extends AppCompatActivity  {
    ListView listView;
    Button btn;
    EditText editText;
    ImageView view;
    TextView name_pass;
    TextView time;
    TextView comm;
    String uid;
    String uname;
    String upath;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        final String name=getIntent().getExtras().getString("name");
        final String image=getIntent().getExtras().getString("image");
        final String id_comment=getIntent().getExtras().getString("id_comment");
        final String comment=getIntent().getExtras().getString("comment");
        final String time_pass=getIntent().getExtras().getString("time");
         MyUser myCurrentUser= BmobUser.getCurrentUser(MyUser.class);

        listView=(ListView)findViewById(R.id.listview_out_2);
        btn=(Button)findViewById(R.id.btn_2);
        editText=(EditText)findViewById(R.id.edit_comment_2);
        view=(ImageView)findViewById(R.id.image_comment_head_2);
        name_pass=(TextView)findViewById(R.id.text_name_2);
        time=(TextView)findViewById(R.id.show_comment_time_2);
        comm=(TextView)findViewById(R.id.textv_2);

        editText.setHint("回复 " + name + "的评论");
        name_pass.setText(name);
        comm.setText(comment);
        time.setText(time_pass);
        Glide.with(showSubactivity.this).load(image).crossFade().centerCrop().into(view);

        BmobQuery<SubComments> query=new BmobQuery<>();
        query.addWhereEqualTo("id_comment",id_comment);
        query.order("-createdAt");
        query.findObjects(new FindListener<SubComments>() {
            @Override
            public void done(List<SubComments> list, BmobException e) {
                listView.setAdapter(new MyAdapter(list));
            }
        });
        BmobQuery<MyUser> query1=new BmobQuery<>();
        query1.getObject(myCurrentUser.getObjectId(), new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {

                if (e == null) {
                    uid = myUser.getObjectId();
                    uname = myUser.getUsername();
                    upath = myUser.getImage();
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubComments subComments=new SubComments();
                subComments.setComment(editText.getText().toString());
                subComments.setId_comment(id_comment);
                subComments.setId_commentor(uid);
                subComments.setImage_commentor(upath);
                subComments.setName_commentor(uname);
                subComments.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null){
                            Toast.makeText(showSubactivity.this, "回复评论成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    public class MyAdapter extends BaseAdapter {
        List<SubComments> list;
        public MyAdapter(List<SubComments> list){
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
            myHolder2 myHolder2;
            View view;
            if (convertView==null){
                myHolder2=new myHolder2();
                view= LayoutInflater.from(showSubactivity.this).inflate(R.layout.comment_out_item,null);
                myHolder2.textView=(TextView)view.findViewById(R.id.textv);
                myHolder2.imageView=(ImageView)view.findViewById(R.id.image_comment_head);
                myHolder2.text_name=(TextView)view.findViewById(R.id.text_name);
                myHolder2.text_time=(TextView)view.findViewById(R.id.show_comment_time);
                view.setTag(myHolder2);
            }else{
                view=convertView;
                myHolder2=(myHolder2)view.getTag();
            }
            myHolder2.textView.setText(list.get(position).getComment());
            Glide.with(showSubactivity.this).load(list.get(position).getImage_commentor()).crossFade().centerCrop().into(myHolder2.imageView);
            myHolder2.text_name.setText(list.get(position).getName_commentor());
            myHolder2.text_time.setText(list.get(position).getCreatedAt());

            return view;
        }
    }
}
class myHolder2{
    ImageView imageView;
    TextView textView;
    TextView text_name;
    TextView text_time;
}
