package net.ltfc.chinaartgallery.event;

import net.ltfc.chinaartgallery.model.entities.MainTab;

import java.util.List;

/**
 * Created by zack on 2016/1/9.
 */
public class MainEvent {
    List<MainTab> tabList;

    public MainEvent(List<MainTab> tabList) {
        this.tabList = tabList;
    }

    public List<MainTab> getTabList() {
        return tabList;
    }
}
