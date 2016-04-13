package net.ltfc.chinaartgallery.base.presenter;

import net.ltfc.chinaartgallery.base.view.BaseView;

/**
 * Created by zack on 2016/1/12.
 */
public interface Presenter {
    void create();
    void attachView(BaseView view);
    void destroy();
}
