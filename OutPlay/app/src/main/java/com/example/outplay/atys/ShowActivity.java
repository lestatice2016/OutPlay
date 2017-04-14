package com.example.outplay.atys;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.outplay.R;
import com.example.outplay.frgs.TestFragment;
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

public class ShowActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton fab;
    boolean judge = true;
    TestFragment testFragment;
    RecyAdapter adapter;
    int a;
    int b=1;
    boolean isUser=false;
    public static Activity replaceInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_show);

        if(BmobUser.getCurrentUser(MyUser.class)!=null){
            isUser=true;
        }

        replaceInstance=this;
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView=(RecyclerView)findViewById(R.id.show_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (judge) {
                    judge = !judge;
                    testFragment = new TestFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isuser", isUser);
                    testFragment.setArguments(bundle);
                    transaction.replace(R.id.test_pop, testFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    judge = !judge;
                    onBackPressed();//无法通过transation.remove()来进行，那样页面还是没刷新
                }
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.yellow,
                R.color.yellow,
                R.color.yellow,
                R.color.yellow);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.white);
        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadDataThread().start();
            }
        });

            Date curDate = new Date(System.currentTimeMillis());
            BmobQuery<Article> query = new BmobQuery<Article>();
            query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(curDate));
            query.addWhereEqualTo("type", 1);
            query.order("-createdAt");
            query.setLimit(9);
            setdata(query);

    }
    public void setdata(BmobQuery<Article> query) {
        query.findObjects(new FindListener<Article>() {
            @Override
            public void done(final List<Article> list, BmobException e) {
                if (e == null) {
                    a=list.size();
                    List<String> list_path;
                    GetUrlToPath getUrlToPath = new GetUrlToPath(list, ShowActivity.this);
                    getUrlToPath.setListPath();
                    list_path = getUrlToPath.getListPath();
                    adapter = new RecyAdapter(ShowActivity.this, list, list_path, new RecyAdapter.OnItemClickListener() {
                        @Override
                        public void OnClick(View v) {
                            int position = recyclerView.getChildAdapterPosition(v);
                            Intent intent = new Intent(ShowActivity.this, ContentShowActivity.class);
                            intent.putExtra("id", list.get(position).getObjectId());
                            intent.putExtra("cid", list.get(position).getId_creator());
                            intent.putExtra("isuser",isUser);
                            startActivity(intent);
                        }
                    }, new RecyAdapter.OnItemLongClickListener() {
                        @Override
                        public void OnLongClick(View v) {
                            int position = recyclerView.getChildAdapterPosition(v);
                            Intent intent = new Intent(ShowActivity.this, ContentShowActivity.class);
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

    private Handler handler = new Handler() {
            @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x101:
                    if (swipeRefreshLayout.isRefreshing()) {
                        recyclerView.removeAllViews();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);//设置不刷新
                    }
                    break;
            }
        }
    };

    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            initData();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }

        private void initData() {
            Date curDate = new Date(System.currentTimeMillis());
            BmobQuery<Article> query = new BmobQuery<Article>();
            query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(curDate));
            query.addWhereEqualTo("type", 1);
            query.order("-createdAt");
            query.setLimit(9);//当后台数据增多时，要设置返回限制
            query.setSkip(b * 9);
            b++;
            setdata(query);
        }
    }

}

