package net.ltfc.chinaartgallery.main.view;

import net.ltfc.chinaartgallery.base.model.entities.Painting;
import net.ltfc.chinaartgallery.base.view.BaseView;

import java.util.List;

/**
 * Created by zack on 2016/1/13.
 */
public interface GalleryView extends BaseView {
    void onLoading();

    void onLoaded(List<Painting> paintingList);
}
