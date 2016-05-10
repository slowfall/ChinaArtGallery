package net.ltfc.chinaartgallery.main.presenter;

import net.ltfc.chinaartgallery.base.presenter.Presenter;
import net.ltfc.chinaartgallery.base.rx.OnNextListener;
import net.ltfc.chinaartgallery.base.rx.SchedulersCompat;
import net.ltfc.chinaartgallery.common.ToastUtils;
import net.ltfc.chinaartgallery.main.view.GalleryView;
import net.ltfc.chinaartgallery.base.rx.BaseSubscriber;
import net.ltfc.chinaartgallery.model.entities.Painting;
import net.ltfc.chinaartgallery.model.rest.CAGService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zack on 2016/1/13.
 */
public class GalleryPresenter implements Presenter<GalleryView>, OnNextListener<List<Painting>> {
    private CAGService cagService;
    @Inject
    Provider<BaseSubscriber<List<Painting>>> baseSubscriberProvider;
    private BaseSubscriber<List<Painting>> baseSubscriber;
    private GalleryView galleryView;
    private boolean isLoading = false;

    @Inject
    public GalleryPresenter(CAGService cagService) {
        this.cagService = cagService;
    }

    @Override
    public void attachView(GalleryView view) {
        this.galleryView = view;
    }

    @Override
    public void create() {
    }

    @Override
    public void destroy() {
        if (baseSubscriber != null && !baseSubscriber.isUnsubscribed()) {
            baseSubscriber.unsubscribe();
        }
        isLoading = false;
        galleryView = null;

    }

    public void loadPaintingList(String category) {
        if (!isLoading) {
            isLoading = true;
            galleryView.onLoading();
            baseSubscriber = baseSubscriberProvider.get();
            baseSubscriber.setOnNextListener(this);
            cagService.getPaintingList(category)
                    .compose(new SchedulersCompat.Transformer<List<Painting>>())
                    .subscribe(baseSubscriber);
        }
    }

    @Override
    public void onNext(List<Painting> paintings) {
        galleryView.onLoaded(paintings);
        isLoading = false;
    }
}
