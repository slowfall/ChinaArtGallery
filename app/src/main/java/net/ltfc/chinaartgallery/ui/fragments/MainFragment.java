package net.ltfc.chinaartgallery.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.model.entities.MainTab;
import net.ltfc.chinaartgallery.presenter.MainPresenter;
import net.ltfc.chinaartgallery.ui.views.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFragment extends BaseFragment implements MainView {

    ViewPagerAdapter viewPagerAdapter;
    MainPresenter mainPresenter;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        mainPresenter = new MainPresenter(this);
        mainPresenter.create();
        mainPresenter.loadTabList();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showTabs(List<MainTab> tabs) {
        viewPagerAdapter.setTabList(tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<MainTab> tabList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return GalleryFragment.newInstance(tabList.get(position).getCategory());
        }

        public void setTabList(List<MainTab> tabList) {
            if (tabList != null && tabList.size() > 0) {
                this.tabList.clear();
                this.tabList.addAll(tabList);
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return tabList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position).getTitle();
        }
    }
}
