package com.example.outplay.ld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.outplay.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 杨苒 on 2017/3/11 0011.
 */
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyHolder>{
    Context context;
    private List<Article> data;//一定要定义
    private List<String> bitmapList;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    public interface OnItemClickListener{
        void OnClick(View v);
    }
    public interface OnItemLongClickListener{
        void OnLongClick(View v);
    }

    public RecyAdapter(Context context,List<Article> list,List<String> list2,
                        OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        this.context=context;
        this.data=list;
        this.bitmapList=list2;
        this.onItemClickListener=onItemClickListener;
        this.onItemLongClickListener=onItemLongClickListener;
    }


        @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        String id=data.get(position).getId_creator();
        BmobQuery<MyUser> query1 = new BmobQuery<>();
        query1.getObject(id, new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    holder.get_username.setText(myUser.getUsername());
                } else {
                    Log.d("hehe", "错误的原因" + e.getMessage());
                }
            }
        });
        holder.get_headline.setText(data.get(position).getHeadline());
        holder.get_like.setText(data.get(position).getNum_like() + "");
        String path=bitmapList.get(position);
        if (path==null){
            Bitmap b_bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
            holder.view.setImageBitmap(b_bitmap);
        }else {
            Glide.with(context).load(path).into(holder.view);
        }

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder myHolder=new MyHolder(LayoutInflater.from(context).inflate(R.layout.recy_item,parent,false),onItemClickListener ,
                onItemLongClickListener);
        return myHolder;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView get_headline;
        TextView get_username;
        TextView get_like;
        ImageView view;

        public MyHolder(View itemView,final OnItemClickListener onItemClickListener,
                         final OnItemLongClickListener onItemLongClickListener) {
            super(itemView);
            get_username=(TextView)itemView.findViewById(R.id.get_username);
            get_headline=(TextView)itemView.findViewById(R.id.tv_recy_headline);
            get_like=(TextView)itemView.findViewById(R.id.get_like);
            view=(ImageView)itemView.findViewById(R.id.image_thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListener !=null){
                        onItemClickListener.OnClick(v);
                    }
                }
            });
            itemView .setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener !=null)
                        onItemLongClickListener.OnLongClick(v);

                    return true;
                }
            });
        }
    }
}