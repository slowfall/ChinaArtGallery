package net.ltfc.chinaartgallery.main.presenter;

import net.ltfc.chinaartgallery.base.presenter.Presenter;
import net.ltfc.chinaartgallery.base.rx.BaseSubscriber;
import net.ltfc.chinaartgallery.base.rx.OnNextListener;
import net.ltfc.chinaartgallery.main.view.MainView;
import net.ltfc.chinaartgallery.main.model.MainTabModel;
import net.ltfc.chinaartgallery.base.model.entities.MainTab;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Created by zack on 2016/1/9.
 */
public class MainPresenter implements Presenter<MainView>, OnNextListener<List<MainTab>> {
    private MainTabModel mainTabModel;
    @Inject
    Provider<BaseSubscriber<List<MainTab>>> baseSubscriberProvider;
    private BaseSubscriber<List<MainTab>> baseSubscriber;
    private MainView mainView;
    private boolean isLoading = false;

    @Inject
    public MainPresenter(MainTabModel mainTabModel) {
        this.mainTabModel = mainTabModel;
    }

    @Override
    public void attachView(MainView view) {
        this.mainView = view;
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
        mainView = null;
    }

    public void loadTabList() {
        if (!isLoading) {
            isLoading = true;
            baseSubscriber = baseSubscriberProvider.get();
            baseSubscriber.setOnNextListener(this);
            mainTabModel.fetchList(baseSubscriber);
        }
    }

    @Override
    public void onNext(List<MainTab> mainTabs) {
        mainView.showTabs(mainTabs);
        isLoading = false;
    }
}
