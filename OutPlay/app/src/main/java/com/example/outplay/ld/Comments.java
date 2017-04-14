package com.example.outplay.ld;

import cn.bmob.v3.BmobObject;

/**
 * Created by 杨苒 on 2017/3/28 0028.
 */
public class Comments extends BmobObject {
    String id_article;
    String comment;
    String id_commentor;
    String name_commentor;
    String imagepath;

    public Comments(){}

    public String getName_commentor() {
        return name_commentor;
    }

    public void setName_commentor(String name_commentor) {
        this.name_commentor = name_commentor;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getId_article() {
        return id_article;
    }

    public void setId_article(String id_article) {
        this.id_article = id_article;
    }

    public String getComment() {
        return comment;
    }

    public void setConment(String comment) {
        this.comment = comment;
    }

    public String getId_commentor() {
        return id_commentor;
    }

    public void setId_commentor(String id_commentor) {
        this.id_commentor = id_commentor;
    }
}
