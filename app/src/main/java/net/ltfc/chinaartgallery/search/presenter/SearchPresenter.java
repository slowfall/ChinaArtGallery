package net.ltfc.chinaartgallery.search.presenter;

import android.util.Log;

import net.ltfc.chinaartgallery.base.rx.BaseSubscriber;
import net.ltfc.chinaartgallery.base.rx.OnNextListener;
import net.ltfc.chinaartgallery.model.rest.CAGSource;
import net.ltfc.chinaartgallery.base.presenter.Presenter;
import net.ltfc.chinaartgallery.base.view.BaseView;
import net.ltfc.chinaartgallery.event.SearchHotEvent;
import net.ltfc.chinaartgallery.search.view.SearchView;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import rx.Subscriber;

/**
 * Created by zack on 2016/1/13.
 */
public class SearchPresenter implements Presenter<SearchView> {
    private SearchView searchView;
    private BaseSubscriber<List<String>> baseSubscriber;
    private CAGSource cagSource;

    @Inject
    public SearchPresenter(CAGSource cagSource, BaseSubscriber<List<String>> baseSubscriber) {
        this.cagSource = cagSource;
        this.baseSubscriber = baseSubscriber;
    }

    @Override
    public void create() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void attachView(SearchView view) {
        this.searchView = view;
        this.baseSubscriber.setOnNextListener(new OnNextListener<List<String>>() {

            @Override
            public void onNext(List<String> strings) {
                searchView.onHotKeysLoaded(strings);
            }
        });
    }

    @Override
    public void destroy() {
        baseSubscriber.unsubscribe();
        searchView = null;
    }

    public void loadSearchHistoryList() {
        //cagSource.getPaintingList(category);
    }

    public void loadSearchHotList() {
        cagSource.getHotSearchKeys(baseSubscriber);
    }

    @Subscribe
    public void onEvent(SearchHotEvent searchHotEvent) {
        Log.d("searchHotEvent", searchHotEvent.toString());
        searchView.onHotKeysLoaded(searchHotEvent.getSearchKeys());
    }
}
