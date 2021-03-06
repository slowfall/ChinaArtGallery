package net.ltfc.chinaartgallery.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.base.view.OnRecyclerViewItemClickListener;
import net.ltfc.chinaartgallery.detail.view.DetailActivity;
import net.ltfc.chinaartgallery.main.di.DaggerMainComponent;
import net.ltfc.chinaartgallery.main.di.MainComponent;
import net.ltfc.chinaartgallery.main.di.MainModule;
import net.ltfc.chinaartgallery.main.presenter.GalleryPresenter;
import net.ltfc.chinaartgallery.base.model.entities.Painting;
import net.ltfc.chinaartgallery.base.view.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class GalleryFragment extends BaseFragment implements GalleryView, SwipeRefreshLayout.OnRefreshListener,
        OnRecyclerViewItemClickListener {
    private static String CATEGORY = "category";
    @Inject
    GalleryPresenter galleryPresenter;
    @Inject
    PaintingListAdapter paintingListAdapter;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private String category;

    public static GalleryFragment newInstance(String category) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("onCreate", getArguments().toString());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
        }
        MainComponent mainComponent = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .fragmentModule(getFragmentModule())
                .mainModule(new MainModule(this))
                .build();
        mainComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("onCreateView", category);
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);

        galleryPresenter.create();
        galleryPresenter.attachView(this);

        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(paintingListAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("onResume", category);
        setUserVisibleHint(true);
    }

    @Override
    public void onDestroyView() {
        Log.i("onDestroyView", category);
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("onDestroy", category);
        galleryPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.i("setUserVisibleHint", category + ", isVisibleToUser:" + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        if (paintingListAdapter != null && paintingListAdapter.getItemCount() == 0
                && isVisibleToUser && isVisible()) {
            Log.i("setUserVisibleHint", category + " is loading.");
            galleryPresenter.loadPaintingList(category);
        }
    }

    @Override
    public void onLoading() {
        Log.i("onLoading", "category:" + category);
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void onLoaded(List<Painting> paintingList) {
        Log.i("onLoaded", "category:" + category);
        paintingListAdapter.setPaintingList(paintingList);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        galleryPresenter.loadPaintingList(category);
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, View view, int position, long id) {
        Log.i("onItemClick", "position:" + position + ", id:" + id);
        Painting painting = paintingListAdapter.getItem(position);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(Constants.KEY_PAINTING, painting);
        startActivity(intent);
    }
}
