package net.ltfc.chinaartgallery.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.model.entities.Painting;
import net.ltfc.chinaartgallery.presenter.GalleryPresenter;
import net.ltfc.chinaartgallery.ui.adapter.PaintingListAdapter;
import net.ltfc.chinaartgallery.ui.views.GalleryView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class GalleryFragment extends BaseFragment implements GalleryView, SwipeRefreshLayout.OnRefreshListener {
    private static String CATEGORY = "category";
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    PaintingListAdapter paintingListAdapter;
    private String category;
    private GalleryPresenter galleryPresenter;
    private boolean isShownToUser = false;

    public static GalleryFragment newInstance(String category) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate", getArguments().toString());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
        }
        galleryPresenter = new GalleryPresenter(this);
        galleryPresenter.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView", category);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        paintingListAdapter = new PaintingListAdapter();
        recyclerView.setAdapter(paintingListAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isShownToUser) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onDestroyView() {
        galleryPresenter.destroy();
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d("setUserVisibleHint", "isVisibleToUser:" + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        if (!isShownToUser && isVisibleToUser && isVisible()) {
            Log.d("setUserVisibleHint", "isShownToUser:" + isShownToUser);
            isShownToUser = true;
            galleryPresenter.loadPaintingList(category);
        }
    }

    @Override
    public void onLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void onLoaded(List<Painting> paintingList) {
        swipeRefresh.setRefreshing(false);
        paintingListAdapter.setPaintingList(paintingList);
    }

    @Override
    public void onRefresh() {
        galleryPresenter.loadPaintingList(category);
    }
}
