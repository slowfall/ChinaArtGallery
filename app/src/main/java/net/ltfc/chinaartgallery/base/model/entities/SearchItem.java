package net.ltfc.chinaartgallery.base.model.entities;


import java.util.Date;

/**
 * Created by zack on 2016/1/28.
 */
public class SearchItem {
    private String key;
    private Date date = new Date();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
