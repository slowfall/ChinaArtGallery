package net.ltfc.chinaartgallery.main.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.main.di.DaggerMainComponent;
import net.ltfc.chinaartgallery.main.di.MainComponent;
import net.ltfc.chinaartgallery.main.presenter.MainPresenter;
import net.ltfc.chinaartgallery.base.model.entities.MainTab;
import net.ltfc.chinaartgallery.base.view.BaseFragment;

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
                .mainModule(getMainModule())
                .build();
        mainComponent.inject(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        viewPager.setAdapter(viewPagerAdapter);
        mainPresenter.create();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainPresenter.attachView(this);
        if (savedInstanceState == null) {
            mainPresenter.loadTabList();
        }
    }

    @Override
    public void showTabs(List<MainTab> tabs) {
        viewPagerAdapter.setTabList(tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainPresenter.destroy();
        ButterKnife.unbind(this);
    }

}
