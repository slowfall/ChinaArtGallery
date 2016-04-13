package net.ltfc.chinaartgallery.search.di;

import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.di.PerActivity;
import net.ltfc.chinaartgallery.search.view.OnRecyclerViewItemClick;
import net.ltfc.chinaartgallery.search.view.SearchFragment;
import net.ltfc.chinaartgallery.search.view.SearchSuggestionRecyclerViewAdapter;
import net.ltfc.chinaartgallery.search.view.SearchView;

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
    @PerActivity
    OnRecyclerViewItemClick provideOnRecyclerViewItemClickListener() {
        return this.searchFragment;
    }

    @Provides
    @PerActivity
    @Named(Constants.NAMED_SEARCH_HISTORY)
    SearchSuggestionRecyclerViewAdapter provideHistoryViewAdapter(OnRecyclerViewItemClick listener) {
        return new SearchSuggestionRecyclerViewAdapter(Constants.NAMED_SEARCH_HISTORY, listener);
    }

    @Provides
    @PerActivity
    @Named(Constants.NAMED_SEARCH_HOT)
    public SearchSuggestionRecyclerViewAdapter provideHotViewAdapter(OnRecyclerViewItemClick listener) {
        return new SearchSuggestionRecyclerViewAdapter(Constants.NAMED_SEARCH_HOT, listener);
    }
}
