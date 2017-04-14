package com.example.outplay.atys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.outplay.R;
import com.example.outplay.ld.Article;
import com.example.outplay.ld.MyUser;
import com.example.outplay.ld.RecyAdapter;
import com.example.outplay.utils.GetUrlToPath;
import com.example.outplay.utils.RecycleViewDivider;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ShowTransationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyAdapter adapter;
    boolean isUser=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_show_transation);

        if(BmobUser.getCurrentUser(MyUser.class)!=null){
            isUser=true;
        }

        recyclerView =(RecyclerView)findViewById(R.id.show_recyclerview_2);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        Date  curDate =new Date(System.currentTimeMillis());
        BmobQuery<Article> query = new BmobQuery<Article>();
        query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(curDate));
        query.addWhereEqualTo("type", 2);
        query.order("-createdAt");
        setdata(query);
    }

    public void setdata(BmobQuery<Article> query) {
        query.findObjects(new FindListener<Article>() {
            @Override
            public void done(final List<Article> list, BmobException e) {
                if (e == null) {

                    List<String> list_path;
                    GetUrlToPath getUrlToPath = new GetUrlToPath(list, ShowTransationActivity.this);

                    getUrlToPath.setListPath();
                    list_path = getUrlToPath.getListPath();
                    adapter = new RecyAdapter(ShowTransationActivity.this, list, list_path, new RecyAdapter.OnItemClickListener() {
                        @Override
                        public void OnClick(View v) {
                            int position = recyclerView.getChildAdapterPosition(v);
                            Intent intent = new Intent(ShowTransationActivity.this, ContentShowActivity.class);
                            intent.putExtra("id", list.get(position).getObjectId());
                            intent.putExtra("cid", list.get(position).getId_creator());
                            intent.putExtra("isuser",isUser);
                            startActivity(intent);
                        }
                    }, new RecyAdapter.OnItemLongClickListener() {
                        @Override
                        public void OnLongClick(View v) {
                            int position = recyclerView.getChildAdapterPosition(v);
                            Intent intent = new Intent(ShowTransationActivity.this, ContentShowActivity.class);
                            intent.putExtra("id", list.get(position).getObjectId());
                            intent.putExtra("cid", list.get(position).getId_creator());
                            intent.putExtra("isuser",isUser);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d("hehe", "失败：" + e.getMessage());
                }
            }
        });

    }
}
