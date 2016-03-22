package net.ltfc.chinaartgallery.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.presenter.SearchPresenter;
import net.ltfc.chinaartgallery.ui.adapter.SearchSuggestionRecyclerViewAdapter;
import net.ltfc.chinaartgallery.ui.views.SearchView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment implements SearchView {
    @Bind(R.id.searchHistoryView)
    RecyclerView searchHistoryView;
    @Bind(R.id.searchHotView)
    RecyclerView searchHotView;
    private SearchSuggestionRecyclerViewAdapter searchHistoryAdapter;
    private SearchSuggestionRecyclerViewAdapter searchHotAdapter;
    private SearchPresenter searchPresenter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        Context context = view.getContext();
        searchHistoryView.setLayoutManager(new LinearLayoutManager(context));
        searchHistoryAdapter = new SearchSuggestionRecyclerViewAdapter("history", this);
        searchHistoryView.setAdapter(searchHistoryAdapter);
        searchHotView.setLayoutManager(new LinearLayoutManager(context));
        searchHotAdapter = new SearchSuggestionRecyclerViewAdapter("hot", this);
        searchHotView.setAdapter(searchHotAdapter);
        searchPresenter = new SearchPresenter(this);
        searchPresenter.create();
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
    public void onSearchItemClick(Object object) {

    }
}
