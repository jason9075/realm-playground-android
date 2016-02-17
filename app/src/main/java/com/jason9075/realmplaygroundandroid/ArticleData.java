package com.jason9075.realmplaygroundandroid;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jason9075 on 2016/2/16.
 */
public class ArticleData extends RealmObject {

    @PrimaryKey
    private long id;

    private String title;
    private String subtitle;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

}
