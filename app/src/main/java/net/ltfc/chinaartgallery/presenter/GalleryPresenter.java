package net.ltfc.chinaartgallery.presenter;

import android.util.Log;

import net.ltfc.chinaartgallery.event.GalleryEvent;
import net.ltfc.chinaartgallery.model.rest.CAGSource;
import net.ltfc.chinaartgallery.ui.views.GalleryView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by zack on 2016/1/13.
 */
public class GalleryPresenter implements Presenter {
    private GalleryView galleryView;
    private CAGSource cagSource;
    private String flag;

    public GalleryPresenter(GalleryView galleryView) {
        this.galleryView = galleryView;
        cagSource = new CAGSource();
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
