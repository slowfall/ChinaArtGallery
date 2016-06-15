package net.ltfc.chinaartgallery.main.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.rx.SchedulersCompat;
import net.ltfc.chinaartgallery.base.model.entities.MainTab;
import net.ltfc.chinaartgallery.common.Utils;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by zack on 2016/1/11.
 */
public class MainTabModel {
    private Context context;
    private Gson gson;

    @Inject
    public MainTabModel(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    public void fetchList(Subscriber<List<MainTab>> subscriber) {
        Observable<List<MainTab>> observable = Observable.create(new Observable.OnSubscribe<List<MainTab>>() {
            @Override
            public void call(Subscriber<? super List<MainTab>> subscriber) {
                Type type = new TypeToken<List<MainTab>>() {
                }.getType();
                List<MainTab> mainTabs = gson.fromJson(Utils.readRawFile(context, R.raw.main), type);
                subscriber.onNext(mainTabs);
                subscriber.onCompleted();
            }
        });
        observable.compose(new SchedulersCompat.Transformer<List<MainTab>>())
                .subscribe(subscriber);
    }
}
