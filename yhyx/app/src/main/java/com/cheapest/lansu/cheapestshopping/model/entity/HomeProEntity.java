package com.cheapest.lansu.cheapestshopping.model.entity;

/*
* 文件名：HomeProEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/4/27 15:09
* 版    权： lansus版权所有
*/
public class HomeProEntity {
    public HomeProEntity(String resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    private String resId;

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
}
