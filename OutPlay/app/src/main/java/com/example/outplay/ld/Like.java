package com.example.outplay.ld;

import cn.bmob.v3.BmobObject;

/**
 * Created by 杨苒 on 2017/3/29 0029.
 */
public class Like extends BmobObject {
    String id_article;
    String id_user;

    public Like(){}

    public String getId_article() {
        return id_article;
    }

    public void setId_article(String id_article) {
        this.id_article = id_article;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
