package net.ltfc.chinaartgallery.base.model.entities;

import java.util.List;

/**
 * Created by zack on 2016/1/9.
 */
public class Painting {
    /**
     * _id : 5595e706aeae078967515264
     * active : true
     * activeSort : 2015-07-05
     * activeTime : 2015-07-05T03:47:16.617Z
     * advDesc : 1比1的还原复制品，原汁原味的复制品。说明珍宝馆来的立享折扣。
     * advUrl : http://item.taobao.com/item.htm?id=12780088917
     * age : 北宋
     * areaSize : 44.4x219
     * author : 王诜
     * comment :
     * commented : false
     * desc : 绢本，设色，44.4* 219.7cm，故宫博物院藏。图绘寒冬小雪后渔村山水之景色。图中雪山巍峨，松树遒劲，河面数艘渔船停泊；整个画面意境萧索，笼罩于一片空灵、静寂的氛围中。技法上，山石以侧锋短笔勾皴，边缘轮廓采用“破墨法”，墨色轻淡；长松以中锋浓墨勾画，更显其不惧严寒。
     * descUrl :
     * essence : true
     * essenceComment :
     * essenceSort : 2015-07-05
     * fileSize : 1.8448GB
     * maxlevel : 18
     * mediaType : 绢本
     * minlevel : 14
     * mylove : true
     * myloveSort : 2016-01-06
     * offlineUrl :
     * originalUrl : http://pan.baidu.com/s/1c0m8hEg
     * overallLevel : 一级-如观实物
     * ownerName : 北京故宫
     * paintingName : 渔村小雪图卷全卷
     * pixels : 61490
     * resourceLevel : 高清原拍
     * tags : ["绘画","山水","长卷"]
     * updateTime : 2016-01-06T14:12:18.715Z
     * utime : 2016-01-06T14:10:21.000Z
     * viewCnt : 1150
     * snapSize : {"height":128,"width":460}
     * size : {"height":6317,"width":97345}
     */

    private String _id;
    private boolean active;
    private String activeSort;
    private String activeTime;
    private String advDesc;
    private String advUrl;
    private String age;
    private String areaSize;
    private String author;
    private String comment;
    private boolean commented;
    private String desc;
    private String descUrl;
    private boolean essence;
    private String essenceComment;
    private String essenceSort;
    private String fileSize;
    private int maxlevel;
    private String mediaType;
    private int minlevel;
    private boolean mylove;
    private String myloveSort;
    private String offlineUrl;
    private String originalUrl;
    private String overallLevel;
    private String ownerName;
    private String paintingName;
    private String pixels;
    private String resourceLevel;
    private String updateTime;
    private String utime;
    private int viewCnt;
    /**
     * height : 128
     * width : 460
     */

    private SnapSizeEntity snapSize;
    /**
     * height : 6317
     * width : 97345
     */

    private SizeEntity size;
    private List<String> tags;

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setActiveSort(String activeSort) {
        this.activeSort = activeSort;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public void setAdvDesc(String advDesc) {
        this.advDesc = advDesc;
    }

    public void setAdvUrl(String advUrl) {
        this.advUrl = advUrl;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setAreaSize(String areaSize) {
        this.areaSize = areaSize;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCommented(boolean commented) {
        this.commented = commented;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDescUrl(String descUrl) {
        this.descUrl = descUrl;
    }

    public void setEssence(boolean essence) {
        this.essence = essence;
    }

    public void setEssenceComment(String essenceComment) {
        this.essenceComment = essenceComment;
    }

    public void setEssenceSort(String essenceSort) {
        this.essenceSort = essenceSort;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setMaxlevel(int maxlevel) {
        this.maxlevel = maxlevel;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setMinlevel(int minlevel) {
        this.minlevel = minlevel;
    }

    public void setMylove(boolean mylove) {
        this.mylove = mylove;
    }

    public void setMyloveSort(String myloveSort) {
        this.myloveSort = myloveSort;
    }

    public void setOfflineUrl(String offlineUrl) {
        this.offlineUrl = offlineUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setOverallLevel(String overallLevel) {
        this.overallLevel = overallLevel;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setPaintingName(String paintingName) {
        this.paintingName = paintingName;
    }

    public void setPixels(String pixels) {
        this.pixels = pixels;
    }

    public void setResourceLevel(String resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }

    public void setSnapSize(SnapSizeEntity snapSize) {
        this.snapSize = snapSize;
    }

    public void setSize(SizeEntity size) {
        this.size = size;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String get_id() {
        return _id;
    }

    public boolean isActive() {
        return active;
    }

    public String getActiveSort() {
        return activeSort;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public String getAdvDesc() {
        return advDesc;
    }

    public String getAdvUrl() {
        return advUrl;
    }

    public String getAge() {
        return age;
    }

    public String getAreaSize() {
        return areaSize;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public boolean isCommented() {
        return commented;
    }

    public String getDesc() {
        return desc;
    }

    public String getDescUrl() {
        return descUrl;
    }

    public boolean isEssence() {
        return essence;
    }

    public String getEssenceComment() {
        return essenceComment;
    }

    public String getEssenceSort() {
        return essenceSort;
    }

    public String getFileSize() {
        return fileSize;
    }

    public int getMaxlevel() {
        return maxlevel;
    }

    public String getMediaType() {
        return mediaType;
    }

    public int getMinlevel() {
        return minlevel;
    }

    public boolean isMylove() {
        return mylove;
    }

    public String getMyloveSort() {
        return myloveSort;
    }

    public String getOfflineUrl() {
        return offlineUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getOverallLevel() {
        return overallLevel;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getPaintingName() {
        return paintingName;
    }

    public String getPixels() {
        return pixels;
    }

    public String getResourceLevel() {
        return resourceLevel;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getUtime() {
        return utime;
    }

    public int getViewCnt() {
        return viewCnt;
    }

    public SnapSizeEntity getSnapSize() {
        return snapSize;
    }

    public SizeEntity getSize() {
        return size;
    }

    public List<String> getTags() {
        return tags;
    }

    public static class SnapSizeEntity {
        private int height;
        private int width;

        public void setHeight(int height) {
            this.height = height;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }

    public static class SizeEntity {
        private int height;
        private int width;

        public void setHeight(int height) {
            this.height = height;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }
}
