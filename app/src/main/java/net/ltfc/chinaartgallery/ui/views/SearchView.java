package net.ltfc.chinaartgallery.ui.views;

import java.util.List;

/**
 * Created by zack on 2016/1/28.
 */
public interface SearchView extends BaseView {
    void onHistoryKeysLoaded(List<String> historyKeys);
    void onHotKeysLoaded(List<String> hotKeys);
    void onSearchItemClick(Object object);
}
