package net.ltfc.chinaartgallery.search.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.base.view.BaseFragment;
import net.ltfc.chinaartgallery.search.di.DaggerSearchComponent;
import net.ltfc.chinaartgallery.search.di.SearchComponent;
import net.ltfc.chinaartgallery.search.di.SearchModule;
import net.ltfc.chinaartgallery.search.presenter.SearchPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchFragment extends BaseFragment implements SearchView, OnRecyclerViewItemClick {
    @Inject
    SearchPresenter searchPresenter;
    @Inject
    @Named(Constants.NAMED_SEARCH_HOT)
    SearchSuggestionRecyclerViewAdapter searchHotAdapter;
    @Inject
    @Named(Constants.NAMED_SEARCH_HISTORY)
    SearchSuggestionRecyclerViewAdapter searchHistoryAdapter;

    @Bind(R.id.searchHistoryView)
    RecyclerView searchHistoryView;
    @Bind(R.id.searchHotView)
    RecyclerView searchHotView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchComponent searchComponent = DaggerSearchComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .fragmentModule(getFragmentModule())
                .searchModule(new SearchModule(this)).build();
        searchComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        Context context = view.getContext();
        searchHotView.setLayoutManager(new LinearLayoutManager(context));
        searchHotView.setAdapter(searchHotAdapter);
        searchHistoryView.setLayoutManager(new LinearLayoutManager(context));
        searchHistoryView.setAdapter(searchHistoryAdapter);
        searchPresenter.create();
        searchPresenter.attachView(this);
        searchPresenter.loadSearchHotList();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onHistoryKeysLoaded(List<String> historyKeys) {

    }

    @Override
    public void onHotKeysLoaded(List<String> hotKeys) {
        searchHotAdapter.setSearchSuggestionList(hotKeys);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter) {
        Log.d("onItemClick", "adapter:" + adapter);
        if (adapter == searchHotAdapter) {
            Log.d("onItemClick", "searchHotAdapter position:" + searchHotView.getChildAdapterPosition(view));
        } else if (adapter == searchHistoryAdapter) {
            Log.d("onItemClick", "searchHistoryAdapter position:" + searchHistoryView.getChildAdapterPosition(view));
        }
    }
}
