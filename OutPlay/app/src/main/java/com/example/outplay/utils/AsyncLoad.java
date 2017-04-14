package com.example.outplay.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.outplay.atys.ContentShowActivity;
import com.example.outplay.ld.Article;
import com.sendtion.xrichtext.RichTextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 杨苒 on 2017/3/9 0009.
 */
public class AsyncLoad extends AsyncTask<String,Integer,Object>{
    RichTextView tv_note_content;
    TextView show_headline;
    TextView show_time;
    Context context;
    public AsyncLoad(RichTextView tv_note_content,
                     TextView headline,TextView time,Context context){
        this.tv_note_content=tv_note_content;
        this.show_headline=headline;
        this.show_time=time;
        this.context=context;
    }
    @Override
    protected Object doInBackground(String... params) {
        BmobQuery<Article> query=new BmobQuery<Article>();
        query.getObject(params[0], new QueryListener<Article>() {
            @Override
            public void done(Article article, BmobException e) {

                if (e==null){
                    show_headline.setText(article.getHeadline());
                    show_time.setText(article.getCreatedAt().toString());
                    String  cont=article.getContent();
                    ContentShowActivity.showEditData(tv_note_content, cont);
                }else{
                }
            }
        });
        return null;
    }

}
