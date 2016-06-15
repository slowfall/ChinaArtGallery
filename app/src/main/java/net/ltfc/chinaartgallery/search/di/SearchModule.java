package net.ltfc.chinaartgallery.search.di;

import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.base.rx.BaseSubscriber;
import net.ltfc.chinaartgallery.common.ToastUtils;
import net.ltfc.chinaartgallery.di.PerFragment;
import net.ltfc.chinaartgallery.base.model.db.DaoMaster;
import net.ltfc.chinaartgallery.base.model.db.DaoSession;
import net.ltfc.chinaartgallery.base.model.entities.Painting;
import net.ltfc.chinaartgallery.search.view.OnRecyclerViewItemClick;
import net.ltfc.chinaartgallery.search.view.SearchFragment;
import net.ltfc.chinaartgallery.search.view.SearchSuggestionRecyclerViewAdapter;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zack on 2016/3/24.
 */
@Module
public class SearchModule {
    private SearchFragment searchFragment;

    public SearchModule(SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
    }

    @Provides
    @PerFragment
    OnRecyclerViewItemClick provideOnRecyclerViewItemClickListener() {
        return this.searchFragment;
    }

    @Provides
    @Named(Constants.NAMED_SEARCH_HISTORY)
    SearchSuggestionRecyclerViewAdapter provideHistoryViewAdapter(OnRecyclerViewItemClick listener) {
        return new SearchSuggestionRecyclerViewAdapter(Constants.NAMED_SEARCH_HISTORY, listener);
    }

    @Provides
    @Named(Constants.NAMED_SEARCH_HOT)
    SearchSuggestionRecyclerViewAdapter provideHotViewAdapter(OnRecyclerViewItemClick listener) {
        return new SearchSuggestionRecyclerViewAdapter(Constants.NAMED_SEARCH_HOT, listener);
    }

    @Provides
    BaseSubscriber<List<String>> provideStringsBaseSubscriber(ToastUtils toastUtils) {
        return new BaseSubscriber<>(toastUtils);
    }

    @Provides
    BaseSubscriber<List<Painting>> providePaintingsBaseSubscriber(ToastUtils toastUtils) {
        return new BaseSubscriber<>(toastUtils);
    }

    @Provides
    @PerFragment
    DaoSession provideDaoSession(DaoMaster daoMaster) {
        return daoMaster.newSession();
    }
}
