package net.ltfc.chinaartgallery.ui.views;

import net.ltfc.chinaartgallery.model.entities.MainTab;

import java.util.List;

/**
 * Created by zack on 2016/1/9.
 */
public interface MainView extends BaseView {
    void showTabs(List<MainTab> tabs);
}
