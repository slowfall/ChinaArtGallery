package net.ltfc.chinaartgallery.ui.views;

import net.ltfc.chinaartgallery.model.entities.Painting;

import java.util.List;

/**
 * Created by zack on 2016/1/13.
 */
public interface GalleryView extends BaseView {
    void onLoading();

    void onLoaded(List<Painting> paintingList);
}
