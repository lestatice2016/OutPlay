package com.example.outplay.utils;

import android.content.Context;

import com.example.outplay.ld.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杨苒 on 2017/3/14 0014.
 */
public class GetUrlToPath {
    List<Article> uploadBeanList_1;
    Context context;
    List<String> list_path;

    public GetUrlToPath(List<Article> list,Context context1) {
        this.uploadBeanList_1=list;
        this.context=context1;
        this.list_path=new ArrayList<String>();
    }

    public List<String> getListPath(){
        return  this.list_path;
    }
    public void setListPath(){

        for (Article article:uploadBeanList_1){
            String content=article.getContent();
            getPath(content);
        }
    }
    public void getPath(String cont){

            List<String> textList = StringUtils.cutStringByImgTag(cont);
            List<String> list=new ArrayList<String>();
            for (int i = 0; i < textList.size(); i++) {
                String text = textList.get(i);
                if (text.contains("<img")) {
                    String imagePath = StringUtils.getImgSrc(text);
                    list.add(imagePath);
                }else{
                    list.add("NoImage");
                }
            }
        String b=null;
        for (String a:list){
            if (!a.equals("NoImage")){
                b=a;
            }
        }
        list_path.add(b);
    }
}
