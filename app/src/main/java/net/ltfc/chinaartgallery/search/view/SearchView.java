package net.ltfc.chinaartgallery.search.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.ltfc.chinaartgallery.base.view.BaseView;

import java.util.List;

/**
 * Created by zack on 2016/1/28.
 */
public interface SearchView extends BaseView {
    void onHistoryKeysLoaded(List<String> historyKeys);

    void onHotKeysLoaded(List<String> hotKeys);
}
