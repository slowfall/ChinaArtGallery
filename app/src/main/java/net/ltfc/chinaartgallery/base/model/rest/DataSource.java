package net.ltfc.chinaartgallery.base.model.rest;

/**
 * Created by zack on 2016/1/12.
 */
public interface DataSource {

    void getPaintingList(String category);

    void getAgeList(String age, String author);

    void search(String key);

    void getCagstoreOutline();

    void getHotSearchKeys();
}
