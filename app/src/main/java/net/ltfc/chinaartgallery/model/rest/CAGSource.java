package net.ltfc.chinaartgallery.model.rest;

import android.util.Log;

import net.ltfc.chinaartgallery.base.rx.SchedulersCompat;
import net.ltfc.chinaartgallery.event.GalleryEvent;
import net.ltfc.chinaartgallery.event.SearchHotEvent;
import net.ltfc.chinaartgallery.model.entities.Painting;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zack on 2016/1/12.
 */
public class CAGSource {
    private static CAGService cagServerAPI;

    @Inject
    public CAGSource() {
        if (cagServerAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CAGServerURL.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            cagServerAPI = retrofit.create(CAGService.class);
        }
    }

    public void getPaintingList(Subscriber<List<Painting>> subscriber, String category) {
        cagServerAPI.getPaintingList(category)
                .compose(new SchedulersCompat.Transformer<List<Painting>>())
                .subscribe(subscriber);
    }

    public void getAgeList(Subscriber<List<Painting>> subscriber, String age, String author) {
        cagServerAPI.getAgeList(age, author)
                .compose(new SchedulersCompat.Transformer<List<Painting>>())
                .subscribe(subscriber);
    }

    public void search(Subscriber<List<Painting>> subscriber, String key) {
        cagServerAPI.search(key)
                .compose(new SchedulersCompat.Transformer<List<Painting>>())
                .subscribe(subscriber);
    }

    public void getCagstoreOutline(Subscriber<List<Painting>> subscriber) {
        cagServerAPI.getCagstoreOutline()
                .compose(new SchedulersCompat.Transformer<List<Painting>>())
                .subscribe(subscriber);
    }

    public void getHotSearchKeys(Subscriber<List<String>> subscriber) {
        cagServerAPI.getHotSearchKeys()
                .compose(new SchedulersCompat.Transformer<List<String>>())
                .subscribe(subscriber);
    }

}
