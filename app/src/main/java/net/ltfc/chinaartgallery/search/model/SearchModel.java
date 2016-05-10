package net.ltfc.chinaartgallery.search.model;

import net.ltfc.chinaartgallery.base.rx.SchedulersCompat;
import net.ltfc.chinaartgallery.model.db.DaoSession;
import net.ltfc.chinaartgallery.model.db.SearchKey;
import net.ltfc.chinaartgallery.model.db.SearchKeyDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by zack on 2016/4/19.
 */
public class SearchModel {
    private List<String> historyKeys = new ArrayList<>();
    private DaoSession daoSession;

    @Inject
    public SearchModel(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public void queryHistoryKeys(Subscriber<List<String>> subscriber) {
        Observable<List<String>> observable = Observable.
                create(new Observable.OnSubscribe<List<String>>() {
                    @Override
                    public void call(Subscriber<? super List<String>> subscriber) {
                        if (historyKeys.isEmpty()) {
                            SearchKeyDao searchKeyDao = daoSession.getSearchKeyDao();
                            List<SearchKey> searchKeys = searchKeyDao.loadAll();
                            Collections.sort(searchKeys, new Comparator<SearchKey>() {
                                @Override
                                public int compare(SearchKey lhs, SearchKey rhs) {
                                    return rhs.getDate().compareTo(lhs.getDate());
                                }
                            });
                            for (SearchKey searchKey : searchKeys) {
                                historyKeys.add(searchKey.getKey());
                            }
                        }
                        subscriber.onNext(historyKeys);
                        subscriber.onCompleted();
                    }
                });
        observable.compose(new SchedulersCompat.Transformer<List<String>>()).
                subscribe(subscriber);
    }

    public String getHistoryKey(int position) {
        return historyKeys.get(position);
    }

    public void cacheHistoryKey(String historyKey) {
        //如果已经在list中，则删除，然后添加到最前面
        int containsIndex = historyKeys.indexOf(historyKey);
        if (containsIndex >= 0) {
            historyKeys.remove(containsIndex);
        }
        historyKeys.add(0, historyKey);
        //最多保留5个
        if (historyKeys.size() > 5) {
            historyKeys = historyKeys.subList(0, 5);
        }
    }

    public void deleteAllAndSaveHistoryKeys(Subscriber<Boolean> subscriber) {
        Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                SearchKeyDao searchKeyDao = daoSession.getSearchKeyDao();
                searchKeyDao.deleteAll();
                List<SearchKey> searchKeys = new ArrayList<>();
                for (String historyKey : historyKeys) {
                    SearchKey searchKey = new SearchKey();
                    searchKey.setKey(historyKey);
                    searchKey.setDate(new Date());
                    searchKeys.add(searchKey);
                }
                searchKeyDao.insertInTx(searchKeys);
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        });
        observable.compose(new SchedulersCompat.Transformer<Boolean>())
                .subscribe(subscriber);
    }
}
