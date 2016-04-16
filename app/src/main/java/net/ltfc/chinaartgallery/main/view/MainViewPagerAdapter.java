package net.ltfc.chinaartgallery.main.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.ltfc.chinaartgallery.model.entities.MainTab;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zack on 2016/3/25.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<MainTab> tabList = new ArrayList<>();

    @Inject
    public MainViewPagerAdapter(FragmentManager manager) {
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
        }
        notifyDataSetChanged();
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
