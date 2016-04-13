package net.ltfc.chinaartgallery.main.presenter;

import android.content.Context;

import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.base.view.BaseView;
import net.ltfc.chinaartgallery.event.MainEvent;
import net.ltfc.chinaartgallery.main.view.MainView;
import net.ltfc.chinaartgallery.base.model.MainTabModel;
import net.ltfc.chinaartgallery.base.presenter.Presenter;

import javax.inject.Inject;
import javax.inject.Named;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by zack on 2016/1/9.
 */
public class MainPresenter implements Presenter {
    Context applicationContext;
    private MainView mainView;

    @Inject
    public MainPresenter(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void attachView(BaseView mainView) {
        this.mainView = (MainView) mainView;
    }

    public void loadTabList() {
        MainTabModel mainTabModule = new MainTabModel(applicationContext);
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
    public void onEvent(MainEvent mainEvent) {
        mainView.showTabs(mainEvent.getTabList());
    }
}
