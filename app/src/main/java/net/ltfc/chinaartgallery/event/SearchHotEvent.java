package net.ltfc.chinaartgallery.event;

import java.util.List;

/**
 * Created by zack on 2016/1/29.
 */
public class SearchHotEvent {
    private List<String> searchKeys;

    public SearchHotEvent(List<String> searchKeys) {
        this.searchKeys = searchKeys;
    }

    public List<String> getSearchKeys() {
        return searchKeys;
    }
}
