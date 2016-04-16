package net.ltfc.chinaartgallery.main.presenter;

import net.ltfc.chinaartgallery.base.presenter.Presenter;
import net.ltfc.chinaartgallery.base.rx.OnNextListener;
import net.ltfc.chinaartgallery.main.view.GalleryView;
import net.ltfc.chinaartgallery.base.rx.BaseSubscriber;
import net.ltfc.chinaartgallery.model.entities.Painting;
import net.ltfc.chinaartgallery.model.rest.CAGSource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zack on 2016/1/13.
 */
public class GalleryPresenter implements Presenter<GalleryView> {
    private CAGSource cagSource;
    private BaseSubscriber<List<Painting>> baseSubscriber;
    private GalleryView galleryView;

    @Inject
    public GalleryPresenter(CAGSource cagSource, BaseSubscriber<List<Painting>> baseSubscriber) {
        this.cagSource = cagSource;
        this.baseSubscriber = baseSubscriber;
    }

    @Override
    public void attachView(GalleryView view) {
        this.galleryView = view;
        baseSubscriber.setOnNextListener(new OnNextListener<List<Painting>>() {
            @Override
            public void onNext(List<Painting> paintings) {
                galleryView.onLoaded(paintings);
            }
        });
    }

    @Override
    public void create() {
    }

    @Override
    public void destroy() {
        baseSubscriber.unsubscribe();
        galleryView = null;

    }

    public void loadPaintingList(String category) {
        galleryView.onLoading();
        cagSource.getPaintingList(baseSubscriber, category);
    }
}
