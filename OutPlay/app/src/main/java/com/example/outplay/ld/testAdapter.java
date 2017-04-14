package com.example.outplay.ld;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.outplay.R;

import java.util.List;

/**
 * Created by 杨苒 on 2017/3/15 0015.
 */
public class testAdapter extends RecyclerView.Adapter<testAdapter.MyHolder> {
    List<String> list;
    Context context;
    @Override
    public void onBindViewHolder(testAdapter.MyHolder holder, int position) {
        holder.tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder myHolder1=new MyHolder(LayoutInflater.from(context).inflate(R.layout.layout_testadapter,parent,false));
        return myHolder1;
    }

    public testAdapter(List<String> list,Context context) {
        this.list=list;
        this.context=context;
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.texto);
        } }
}
