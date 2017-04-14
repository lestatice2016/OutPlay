package com.example.outplay.ld;

import cn.bmob.v3.BmobObject;

/**
 * Created by 杨苒 on 2017/3/25 0025.
 */
public class Article extends BmobObject{
    String id_creator;
    String headline;
    String content;
    Integer num_like=0;
    Integer type;

    public Article(){}

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNum_like() {
        return num_like;
    }

    public void setNum_like(int num_like) {
        this.num_like = num_like;
    }

    public String getId_creator() {
        return id_creator;
    }

    public void setId_creator(String id_creator) {
        this.id_creator = id_creator;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
