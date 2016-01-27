package net.ltfc.chinaartgallery.presenter;

import net.ltfc.chinaartgallery.event.MainEvent;
import net.ltfc.chinaartgallery.model.MainTabModule;
import net.ltfc.chinaartgallery.ui.views.MainView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by zack on 2016/1/9.
 */
public class MainPresenter implements Presenter {
    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

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
    public void onEvent(MainEvent mainEvent) {
        mainView.showTabs(mainEvent.getTabList());
    }
}
