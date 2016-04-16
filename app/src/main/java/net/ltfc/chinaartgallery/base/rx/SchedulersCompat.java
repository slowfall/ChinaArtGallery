package net.ltfc.chinaartgallery.base.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zack on 2016/4/17.
 */public class SchedulersCompat {

    public static class Transformer<T> implements Observable.Transformer<T, T> {

        @Override
        public Observable<T> call(Observable<T> tObservable) {
            return tObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

}
