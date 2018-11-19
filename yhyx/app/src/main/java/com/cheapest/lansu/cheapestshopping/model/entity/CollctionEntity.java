package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/*
* 文件名：CollctionEntitiy
* 描    述：
* 作    者：lansu
* 时    间：2018/5/10 18:07
* 版    权：lansus
*/
public class CollctionEntity {
    /**
     * datas : [{"currentId":1,"id":3,"name":"22","type":1,"price":20,"discountPrice":49,"sellNum":11,"couponAmount":10,"iconUrl":"http://112.74.200.84:10081/attachment/ajax/visit/1"}]
     * total : 1
     * size : 10
     * page : 1
     * pages : 1
     * curSize : 1
     */

    private int total;
    private int size;
    private int page;
    private int pages;
    private int curSize;
    private List<DatasBean> datas;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurSize() {
        return curSize;
    }

    public void setCurSize(int curSize) {
        this.curSize = curSize;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * currentId : 1
         * id : 3
         * name : 22
         * type : 1
         * price : 20.0
         * discountPrice : 49.0
         * sellNum : 11
         * couponAmount : 10.0
         * iconUrl : http://112.74.200.84:10081/attachment/ajax/visit/1
         */

        private int currentId;
        private int id;
        private String name;
        private int type;
        private double price;
        private double discountPrice;
        private int sellNum;
        private double couponAmount;
        private String iconUrl;
        private String mineCommision;

        public int getCurrentId() {
            return currentId;
        }

        public void setCurrentId(int currentId) {
            this.currentId = currentId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(double discountPrice) {
            this.discountPrice = discountPrice;
        }

        public int getSellNum() {
            return sellNum;
        }

        public void setSellNum(int sellNum) {
            this.sellNum = sellNum;
        }

        public double getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(double couponAmount) {
            this.couponAmount = couponAmount;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getMineCommision() {
            return mineCommision;
        }

        public void setMineCommision(String mineCommision) {
            this.mineCommision = mineCommision;
        }
    }
}
