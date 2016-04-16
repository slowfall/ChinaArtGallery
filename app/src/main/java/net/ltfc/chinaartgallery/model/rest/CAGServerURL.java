package net.ltfc.chinaartgallery.model.rest;

/**
 * Created by zack on 2016/1/12.
 */
public class CAGServerURL {
    public static final String BASE_URL = "http://ltfc.net";
    public static final String QINIUDN_BASE_URL = "http://supperdetailpainter.u.qiniudn.com";
    public static String getPaintingOutlineURL(String age, String author, String paintingName) {
        return String.format("%s/outline/%s/%s/%s", BASE_URL, age, author, paintingName);
    }

    public static String getPaintingOutlineURL(String uuid) {
        return String.format("%s/imglite.html?uuid=%s&view=webview_android#uuid=%s&view=webview_android",
                BASE_URL, uuid, uuid);
    }

    public static String getPaintingThumbnialURL(String uuid) {
        return String.format("%s/cagstore/%s/tb.jpg", QINIUDN_BASE_URL, uuid);
    }

    public static String getPaintingShareURL(String uuid) {
        return String.format("%s/img/%s",BASE_URL, uuid);
    }
}
