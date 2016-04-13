package net.ltfc.chinaartgallery.main.presenter;

import android.util.Log;

import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.base.model.rest.CAGSource;
import net.ltfc.chinaartgallery.base.presenter.Presenter;
import net.ltfc.chinaartgallery.base.view.BaseView;
import net.ltfc.chinaartgallery.event.GalleryEvent;
import net.ltfc.chinaartgallery.main.view.GalleryView;

import javax.inject.Inject;
import javax.inject.Named;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by zack on 2016/1/13.
 */
public class GalleryPresenter implements Presenter {
    CAGSource cagSource;
    private GalleryView galleryView;
    private String flag;

    @Inject
    public GalleryPresenter(CAGSource cagSource) {
        this.cagSource = cagSource;
    }

    @Override
    public void attachView(BaseView galleryView) {
        this.galleryView = (GalleryView) galleryView;
    }

    @Override
    public void create() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
        galleryView = null;
    }

    public void loadPaintingList(String category) {
        flag = category;
        galleryView.onLoading();
        cagSource.getPaintingList(category);
    }

    @Subscribe
    public void onEvent(GalleryEvent galleryEvent) {
        Log.d("onEvent", "flag:" + flag + ", galleryEvent.getFlag:" + galleryEvent.getFlag());
        if (flag == galleryEvent.getFlag()) {
            galleryView.onLoaded(galleryEvent.getPaintingList());
        }
    }
}
