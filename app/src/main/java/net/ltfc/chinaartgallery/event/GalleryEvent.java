package net.ltfc.chinaartgallery.event;

import net.ltfc.chinaartgallery.base.model.entities.Painting;

import java.util.List;

/**
 * Created by zack on 2016/1/12.
 */
public class GalleryEvent {
    private String flag;
    private List<Painting> paintingList;

    public GalleryEvent(List<Painting> object) {
        this(null, object);
    }

    public GalleryEvent(String flag, List<Painting> object) {
        this.flag = flag;
        this.paintingList = object;
    }

    public String getFlag() {
        return flag;
    }

    public List<Painting> getPaintingList() {
        return paintingList;
    }
}
