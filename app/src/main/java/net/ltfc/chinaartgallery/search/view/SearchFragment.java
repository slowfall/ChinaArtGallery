package net.ltfc.chinaartgallery.search.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.base.view.BaseFragment;
import net.ltfc.chinaartgallery.common.ToastUtils;
import net.ltfc.chinaartgallery.main.view.PaintingListAdapter;
import net.ltfc.chinaartgallery.model.entities.Painting;
import net.ltfc.chinaartgallery.search.di.DaggerSearchComponent;
import net.ltfc.chinaartgallery.search.di.SearchComponent;
import net.ltfc.chinaartgallery.search.di.SearchModule;
import net.ltfc.chinaartgallery.search.presenter.SearchPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchFragment extends BaseFragment implements SearchView, OnRecyclerViewItemClick,
        android.support.v7.widget.SearchView.OnQueryTextListener, android.support.v7.widget.SearchView.OnCloseListener {
    @Inject
    ToastUtils toastUtils;
    @Inject
    SearchPresenter searchPresenter;
    @Inject
    @Named(Constants.NAMED_SEARCH_HISTORY)
    SearchSuggestionRecyclerViewAdapter searchHistoryAdapter;
    @Inject
    @Named(Constants.NAMED_SEARCH_HOT)
    SearchSuggestionRecyclerViewAdapter searchHotAdapter;
    @Inject
    PaintingListAdapter searchResultAdapter;

    @Bind(R.id.searchHistoryView)
    RecyclerView searchHistoryView;
    @Bind(R.id.searchHotView)
    RecyclerView searchHotView;
    @Bind(R.id.searchSuggestionLayout)
    LinearLayout searchSuggestionLayout;
    @Bind(R.id.searchResultView)
    RecyclerView searchResultView;
    @Bind(R.id.searchResultLayout)
    LinearLayout searchResultLayout;
    @Bind(R.id.searchProgress)
    ProgressBar searchProgress;

    OnSearchKeyClickListener onSearchKeyClickListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onSearchKeyClickListener = (OnSearchKeyClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSearchKeyClickListener");
        }
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
        Log.d("onCreate", "SearchFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        Context context = view.getContext();
        searchHistoryView.setLayoutManager(new LinearLayoutManager(context));
        searchHistoryView.setAdapter(searchHistoryAdapter);
        searchHotView.setLayoutManager(new LinearLayoutManager(context));
        searchHotView.setAdapter(searchHotAdapter);
        searchResultView.setLayoutManager(new LinearLayoutManager(context));
        searchResultView.setAdapter(searchResultAdapter);
        searchPresenter.create();
        searchPresenter.attachView(this);
        Log.d("onCreateView", "SearchFragment");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchPresenter.loadHistoryKeys();
        searchPresenter.loadHotKeys();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        Log.d("onDestroyView", "SearchFragment");
    }

    @Override
    public void onDestroy() {
        searchPresenter.destroy();
        super.onDestroy();
        Log.d("onDestroy", "SearchFragment");
    }

    private void setSearchResultLayoutVisibility(boolean visible) {
        if (searchResultLayout != null) {
            searchResultLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    private void setSearchSuggestionLayoutVisibility(boolean visible) {
        if (searchSuggestionLayout != null) {
            searchSuggestionLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            searchProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            searchProgress.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            searchProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });
        } else {
            searchProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onHistoryKeysLoaded(List<String> historyKeys) {
        Log.d("onHistoryKeysLoaded", "SearchFragment historyKeys.size=" + historyKeys.size());
        searchHistoryAdapter.setSearchSuggestionList(historyKeys);
    }

    @Override
    public void onHotKeysLoaded(List<String> hotKeys) {
        Log.d("onHotKeysLoaded", "SearchFragment");
        searchHotAdapter.setSearchSuggestionList(hotKeys);
    }

    @Override
    public void onSearchLoaded(List<Painting> paintings) {
        Log.d("onHistoryKeysLoaded", "SearchFragment historyKeys.size=" + paintings.size());
        searchResultAdapter.setPaintingList(paintings);
        setSearchResultLayoutVisibility(true);
        setSearchSuggestionLayoutVisibility(false);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter) {
        int position;
        String searchKey = null;
        Log.d("onItemClick", "adapter:" + adapter);
        if (adapter == searchHotAdapter) {
            position = searchHotView.getChildAdapterPosition(view);
            searchKey = searchPresenter.getHotKey(position);
            Log.d("onItemClick", "searchHotAdapter position:" + position);
        } else if (adapter == searchHistoryAdapter) {
            position = searchHistoryView.getChildAdapterPosition(view);
            searchKey = searchPresenter.getHistoryKey(position);
            Log.d("onItemClick", "searchHistoryAdapter position:" + position);
        }
        Log.d("onItemClick", "searchKey is:" + searchKey);
        if (!TextUtils.isEmpty(searchKey)) {
            onSearchKeyClickListener.onSearchKeyClick(searchKey);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("SearchFragment", "SearchView onQueryTextSubmit");
        if (TextUtils.isEmpty(query)) {
            toastUtils.showShort(R.string.tips_input_search_keyword);
        } else {
            searchPresenter.search(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            setSearchSuggestionLayoutVisibility(true);
            setSearchResultLayoutVisibility(false);
            Log.d("SearchFragment", "SearchView onQueryTextChange newText isEmpty");
            searchPresenter.loadHistoryKeys();
        }
        return true;
    }

    @Override
    public boolean onClose() {
        Log.d("SearchFragment", "SearchView onClose");
        searchPresenter.loadHistoryKeys();
        return true;
    }

    public interface OnSearchKeyClickListener{
        void onSearchKeyClick(String searchKey);
    }
}
