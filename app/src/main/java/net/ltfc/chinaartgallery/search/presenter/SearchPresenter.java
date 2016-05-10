package net.ltfc.chinaartgallery.search.presenter;

import android.util.Log;

import net.ltfc.chinaartgallery.base.presenter.Presenter;
import net.ltfc.chinaartgallery.base.rx.BaseSubscriber;
import net.ltfc.chinaartgallery.base.rx.OnNextListener;
import net.ltfc.chinaartgallery.base.rx.SchedulersCompat;
import net.ltfc.chinaartgallery.model.entities.Painting;
import net.ltfc.chinaartgallery.model.rest.CAGService;
import net.ltfc.chinaartgallery.search.model.SearchModel;
import net.ltfc.chinaartgallery.search.view.SearchView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import rx.Subscriber;

/**
 * Created by zack on 2016/1/13.
 */
public class SearchPresenter implements Presenter<SearchView> {
    private CAGService cagService;
    private SearchModel searchModel;
    @Inject
    Provider<BaseSubscriber<List<String>>> stringsBaseSubscriberProvider;
    @Inject
    Provider<BaseSubscriber<List<Painting>>> paintingsBaseSubscriberProvider;
    private BaseSubscriber<List<String>> historySubscriber;
    private BaseSubscriber<List<String>> hotSubscriber;
    private Subscriber<List<Painting>> searchSubscriber;
    private SearchView searchView;
    private List<String> hotKeys;

    @Inject
    public SearchPresenter(CAGService cagService, SearchModel searchModel) {
        this.cagService = cagService;
        this.searchModel = searchModel;
    }

    @Override
    public void create() {
    }

    @Override
    public void attachView(SearchView view) {
        this.searchView = view;
    }

    @Override
    public void destroy() {
        if (hotSubscriber != null && !hotSubscriber.isUnsubscribed()) {
            hotSubscriber.unsubscribe();
        }
        if (historySubscriber != null && !historySubscriber.isUnsubscribed()) {
            historySubscriber.unsubscribe();
        }
        if (searchSubscriber != null && !searchSubscriber.isUnsubscribed()) {
            searchSubscriber.unsubscribe();
        }
        searchModel.deleteAllAndSaveHistoryKeys(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                Log.d("SearchPresenter", "deleteAllAndSaveHistoryKeys onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("SearchPresenter", "deleteAllAndSaveHistoryKeys onError");
            }

            @Override
            public void onNext(Boolean aBoolean) {
                Log.d("SearchPresenter", "deleteAllAndSaveHistoryKeys onNext");
            }
        });
        searchView = null;
    }

    public void loadHistoryKeys() {
            historySubscriber = stringsBaseSubscriberProvider.get();
            historySubscriber.setOnNextListener(new OnNextListener<List<String>>() {

                @Override
                public void onNext(List<String> historyKeys) {
                    searchView.onHistoryKeysLoaded(historyKeys);
                }
            });
            searchModel.queryHistoryKeys(historySubscriber);
    }

    public void loadHotKeys() {
        hotSubscriber = stringsBaseSubscriberProvider.get();
        hotSubscriber.setOnNextListener(new OnNextListener<List<String>>() {

            @Override
            public void onNext(List<String> hotSearchKeys) {
                hotKeys = hotSearchKeys;
                searchView.onHotKeysLoaded(hotSearchKeys);
            }
        });
        cagService.getSearchHotKeys()
                .compose(new SchedulersCompat.Transformer<List<String>>())
                .subscribe(hotSubscriber);
    }

    public String getHistoryKey(int position) {
        return searchModel.getHistoryKey(position);
    }

    public String getHotKey(int position) {
        return hotKeys.get(position);
    }

    public void search(String key) {
        searchModel.cacheHistoryKey(key);
        searchView.showProgress(true);
        searchSubscriber = new Subscriber<List<Painting>>() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onCompleted() {
                Log.d("onCompleted", "searchView:" +searchView);
                if (searchView != null) {
                    searchView.showProgress(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("onError", "searchView:" +searchView);
                if (searchView != null) {
                    searchView.showProgress(false);
                }
            }

            @Override
            public void onNext(List<Painting> paintings) {
                searchView.onSearchLoaded(paintings);
            }
        };
        cagService.search(key)
                .compose(new SchedulersCompat.Transformer<List<Painting>>())
                .subscribe(searchSubscriber);
    }
}
