package net.ltfc.chinaartgallery.event;

/**
 * Created by zack on 2016/1/12.
 */
public class GalleryEvent<T> {
    private String flag;
    private T object;

    public GalleryEvent(T object) {
        this(null, object);
    }

    public GalleryEvent(String flag, T object) {
        this.flag = flag;
        this.object = object;
    }

    public String getFlag() {
        return flag;
    }

    public T getObject() {
        return object;
    }
}
