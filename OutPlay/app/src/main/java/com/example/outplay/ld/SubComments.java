package com.example.outplay.ld;

import cn.bmob.v3.BmobObject;

/**
 * Created by 杨苒 on 2017/3/28 0028.
 */
public class SubComments extends BmobObject{
    String id_comment;
    String id_commentor;
    String comment;
    String name_commentor;
    String image_commentor;

    public SubComments() {

    }

    public String getId_commentor() {
        return id_commentor;
    }

    public void setId_commentor(String id_commentor) {
        this.id_commentor = id_commentor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName_commentor() {
        return name_commentor;
    }

    public void setName_commentor(String name_commentor) {
        this.name_commentor = name_commentor;
    }

    public String getImage_commentor() {
        return image_commentor;
    }

    public void setImage_commentor(String image_commentor) {
        this.image_commentor = image_commentor;
    }

    public String getId_comment() {
        return id_comment;
    }

    public void setId_comment(String id_comment) {
        this.id_comment = id_comment;
    }
}
