package net.ltfc.chinaartgallery.presenter;

import android.util.Log;

import net.ltfc.chinaartgallery.event.SearchHotEvent;
import net.ltfc.chinaartgallery.model.rest.CAGSource;
import net.ltfc.chinaartgallery.ui.views.SearchView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by zack on 2016/1/13.
 */
public class SearchPresenter implements Presenter {
    private SearchView searchView;
    private CAGSource cagSource;
    private String flag;

    public SearchPresenter(SearchView searchView) {
        this.searchView = searchView;
        cagSource = new CAGSource();
    }

    @Override
    public void create() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
        searchView = null;
    }

    public void loadSearchHistoryList() {
        //cagSource.getPaintingList(category);
    }

    public void loadSearchHotList() {
        cagSource.getHotSearchKeys();
    }

    @Subscribe
    public void onEvent(SearchHotEvent searchHotEvent) {
        Log.d("searchHotEvent", searchHotEvent.toString());
        searchView.onHotKeysLoaded(searchHotEvent.getSearchKeys());
    }
}
