package net.ltfc.chinaartgallery.presenter;

import android.util.Log;

import net.ltfc.chinaartgallery.event.GalleryEvent;
import net.ltfc.chinaartgallery.model.MainTabModule;
import net.ltfc.chinaartgallery.model.entities.MainTab;
import net.ltfc.chinaartgallery.ui.views.MainView;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by zack on 2016/1/9.
 */
public class MainPresenterImpl implements MainPresenter {
    private MainView mainView;
    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void loadTabList() {
        MainTabModule mainTabModule = new MainTabModule(mainView.getContext());
        mainTabModule.fetchList();
    }

    @Override
    public void create() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(GalleryEvent<List<MainTab>> galleryEvent) {
        if (galleryEvent.getFlag() == null) {
            Log.d("onEvent", "flag:" + galleryEvent.getFlag());
            mainView.showTabs(galleryEvent.getObject());
        }
    }
}
