package net.ltfc.chinaartgallery.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.view.BaseFragment;
import net.ltfc.chinaartgallery.main.di.DaggerMainComponent;
import net.ltfc.chinaartgallery.main.di.MainComponent;
import net.ltfc.chinaartgallery.main.di.MainModule;
import net.ltfc.chinaartgallery.main.presenter.MainPresenter;
import net.ltfc.chinaartgallery.base.model.entities.MainTab;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFragment extends BaseFragment implements MainView {
    @Inject
    MainViewPagerAdapter viewPagerAdapter;
    @Inject
    MainPresenter mainPresenter;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainComponent mainComponent = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .fragmentModule(getFragmentModule())
                .mainModule(new MainModule())
                .build();
        mainComponent.inject(this);
        mainPresenter.attachView(this);
        Log.d("onCreate", "mainFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        mainPresenter.create();
        Log.d("onCreateView", "mainFragment");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainPresenter.loadTabList();
        Log.d("onViewCreated", "mainFragment");
    }

    @Override
    public void showTabs(List<MainTab> tabs) {
        Log.d("showTabs", "mainFragment");
        viewPagerAdapter.setTabList(tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroyView", "mainFragment");
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.destroy();
        Log.d("onDestroy", "mainFragment");
    }
}
