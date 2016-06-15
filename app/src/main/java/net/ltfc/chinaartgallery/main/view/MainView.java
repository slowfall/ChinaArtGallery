package net.ltfc.chinaartgallery.main.view;

import net.ltfc.chinaartgallery.base.model.entities.MainTab;
import net.ltfc.chinaartgallery.base.view.BaseView;

import java.util.List;

/**
 * Created by zack on 2016/1/9.
 */
public interface MainView extends BaseView {
    void showTabs(List<MainTab> tabs);
}
