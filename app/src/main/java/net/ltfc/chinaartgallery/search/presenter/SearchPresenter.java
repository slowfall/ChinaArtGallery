package net.ltfc.chinaartgallery.search.presenter;

import android.util.Log;

import net.ltfc.chinaartgallery.base.model.rest.CAGSource;
import net.ltfc.chinaartgallery.base.presenter.Presenter;
import net.ltfc.chinaartgallery.base.view.BaseView;
import net.ltfc.chinaartgallery.event.SearchHotEvent;
import net.ltfc.chinaartgallery.search.view.SearchView;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by zack on 2016/1/13.
 */
public class SearchPresenter implements Presenter {
    private SearchView searchView;
    private CAGSource cagSource;
    private String flag;

    @Inject
    public SearchPresenter(CAGSource cagSource) {
        this.cagSource = cagSource;
    }

    @Override
    public void create() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void attachView(BaseView view) {
        this.searchView = (SearchView) view;
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
