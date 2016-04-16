package net.ltfc.chinaartgallery.main.presenter;

import net.ltfc.chinaartgallery.base.presenter.Presenter;
import net.ltfc.chinaartgallery.base.rx.BaseSubscriber;
import net.ltfc.chinaartgallery.base.rx.OnNextListener;
import net.ltfc.chinaartgallery.event.MainEvent;
import net.ltfc.chinaartgallery.main.view.MainView;
import net.ltfc.chinaartgallery.main.model.MainTabModel;
import net.ltfc.chinaartgallery.model.entities.MainTab;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by zack on 2016/1/9.
 */
public class MainPresenter implements Presenter<MainView> {
    private MainTabModel mainTabModel;
    private BaseSubscriber<List<MainTab>> baseSubscriber;
    private MainView mainView;

    @Inject
    public MainPresenter(MainTabModel mainTabModel, BaseSubscriber<List<MainTab>> baseSubscriber) {
        this.mainTabModel = mainTabModel;
        this.baseSubscriber = baseSubscriber;
    }

    @Override
    public void attachView(MainView view) {
        this.mainView = view;
        this.baseSubscriber.setOnNextListener(new OnNextListener<List<MainTab>>() {

            @Override
            public void onNext(List<MainTab> mainTabs) {
                mainView.showTabs(mainTabs);
            }
        });
    }

    public void loadTabList() {
        mainTabModel.fetchList(baseSubscriber);
    }

    @Override
    public void create() {
    }

    @Override
    public void destroy() {
        baseSubscriber.unsubscribe();
        mainView = null;
    }
}
