package com.cheapest.lansu.cheapestshopping.model.entity;

/*
* 文件名：BannerEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/5/9 16:50
* 版    权：lansus
*/
public class BannerEntity {

    /**
     * id : 1
     * image : 1
     * imageUrl : http://39.106.13.51:10081/attachment/ajax/visit/1
     * target : www.baidu.com
     * title : 去百度
     * type : 1
     * urlType : 2
     */

    private int id;
    private int image;
    private String imageUrl;
    private String target;
    private String title;
    private int type;
    private int urlType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }
}
