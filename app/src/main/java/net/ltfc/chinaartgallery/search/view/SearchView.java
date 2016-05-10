package net.ltfc.chinaartgallery.search.view;

import net.ltfc.chinaartgallery.base.view.BaseView;
import net.ltfc.chinaartgallery.model.entities.Painting;

import java.util.List;

/**
 * Created by zack on 2016/1/28.
 */
public interface SearchView extends BaseView {
    void onHistoryKeysLoaded(List<String> historyKeys);

    void onHotKeysLoaded(List<String> hotKeys);

    void onSearchLoaded(List<Painting> paintings);

    void showProgress(boolean show);
}
