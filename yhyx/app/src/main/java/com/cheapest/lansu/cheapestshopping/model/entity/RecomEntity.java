package com.cheapest.lansu.cheapestshopping.model.entity;

/*
* 文件名：RecomEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/5/17 16:51
* 版    权：lansus
*/
public class RecomEntity {
    /**
     * directViped : 10
     * indirectViped : 9
     * directUnviped : 22
     * indirectUnviped : 15
     */
    private String mineRecommend;
    private int directViped;
    private int indirectViped;
    private int directUnviped;
    private int indirectUnviped;

    public int getDirectViped() {
        return directViped;
    }

    public void setDirectViped(int directViped) {
        this.directViped = directViped;
    }

    public int getIndirectViped() {
        return indirectViped;
    }

    public void setIndirectViped(int indirectViped) {
        this.indirectViped = indirectViped;
    }

    public int getDirectUnviped() {
        return directUnviped;
    }

    public void setDirectUnviped(int directUnviped) {
        this.directUnviped = directUnviped;
    }

    public int getIndirectUnviped() {
        return indirectUnviped;
    }

    public void setIndirectUnviped(int indirectUnviped) {
        this.indirectUnviped = indirectUnviped;
    }

    public String getMineRecommend() {
        return mineRecommend;
    }

    public void setMineRecommend(String mineRecommend) {
        this.mineRecommend = mineRecommend;
    }
}
