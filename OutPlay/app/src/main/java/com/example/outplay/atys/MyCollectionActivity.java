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
import com.example.outplay.ld.RecyAdapter;
import com.example.outplay.utils.GetUrlToPath;
import com.example.outplay.utils.RecycleViewDivider;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyCollectionActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_my_collection);

        recyclerView=(RecyclerView)findViewById(R.id.show_recyclerview_5);
        List<String> list_id=getIntent().getExtras().getStringArrayList("list2");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));


        BmobQuery<Article> query1=new BmobQuery<>();
        query1.addWhereContainedIn("objectId", list_id);
        setdata(query1);
    }

    public void setdata(BmobQuery<Article> query) {
        query.findObjects(new FindListener<Article>() {
            @Override
            public void done(final List<Article> list, BmobException e) {
                if (e == null) {

                    List<String> list_path;
                    GetUrlToPath getUrlToPath = new GetUrlToPath(list, MyCollectionActivity.this);

                    getUrlToPath.setListPath();
                    list_path = getUrlToPath.getListPath();
                    adapter = new RecyAdapter(MyCollectionActivity.this, list,list_path, new RecyAdapter.OnItemClickListener() {
                        @Override
                        public void OnClick(View v) {
                            int position = recyclerView.getChildAdapterPosition(v);
                            Intent intent = new Intent(MyCollectionActivity.this, ContentShowActivity.class);
                            intent.putExtra("id", list.get(position).getObjectId());
                            intent.putExtra("cid", list.get(position).getId_creator());
                            startActivity(intent);
                        }
                    }, new RecyAdapter.OnItemLongClickListener() {
                        @Override
                        public void OnLongClick(View v) {
                            int position = recyclerView.getChildAdapterPosition(v);
                            Intent intent = new Intent(MyCollectionActivity.this, ContentShowActivity.class);
                            intent.putExtra("id", list.get(position).getObjectId());
                            intent.putExtra("cid", list.get(position).getId_creator());
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
