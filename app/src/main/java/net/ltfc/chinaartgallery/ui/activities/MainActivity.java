package net.ltfc.chinaartgallery.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.model.entities.MainTab;
import net.ltfc.chinaartgallery.presenter.MainPresenter;
import net.ltfc.chinaartgallery.presenter.MainPresenterImpl;
import net.ltfc.chinaartgallery.ui.common.StatusBarCompat;
import net.ltfc.chinaartgallery.ui.fragments.GalleryFragment;
import net.ltfc.chinaartgallery.ui.views.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));

        mainPresenter = new MainPresenterImpl(this);
        mainPresenter.create();
        mainPresenter.loadTabList();
    }

    @Override
    protected void onDestroy() {
        mainPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showTabs(List<MainTab> tabs) {
        viewPagerAdapter.setTabList(tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
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
