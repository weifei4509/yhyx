package com.cheapest.lansu.cheapestshopping.model.entity;

/*
* 文件名：SplashEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/5/8 16:14
* 版    权：lansus
*/
public class SplashEntity {


    /**
     * id : 1
     * imageUrl : http://39.106.13.51:10081/attachment/ajax/visit/1
     * title : 我是文字说明
     */

    private int id;
    private String imageUrl;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
