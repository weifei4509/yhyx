package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/*
* 文件名：ProductEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/4/27 17:19
* 版    权： 云杉智慧新能源技术有限公司版权所有
*/
public class ProductEntity {
    /**
     * data : {"datas":[{"id":3,"name":"22","type":1,"price":20,"discountPrice":49,"sellNum":11,"couponAmount":10,"iconUrl":"http://112.74.200.84:10081/attachment/ajax/visit/1"}],"total":1,"size":10,"page":1,"pages":1,"curSize":1}
     */


    /**
     * datas : [{"id":3,"name":"22","type":1,"price":20,"discountPrice":49,"sellNum":11,"couponAmount":10,"iconUrl":"http://112.74.200.84:10081/attachment/ajax/visit/1"}]
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
         * id : 3
         * name : 22
         * type : 1
         * price : 20.0
         * discountPrice : 49.0
         * sellNum : 11
         * couponAmount : 10.0
         * iconUrl : http://112.74.200.84:10081/attachment/ajax/visit/1
         */
//        "id": 8687,
//                "itemId": "568911643440",
//                "name": "越南紫薯进口番薯新鲜现挖地瓜芋头红薯小香薯5斤(中果)包邮",
//                "type": 2,
//                "price": "23.8",
//                "discountPrice": 18.80,
//                "sellNum": 11050,
//                "categoryId": 41,
//                "couponAmount": "5",
//                "mineCommision": 1.07,
//                "iconUrl": "http://img.alicdn.com/bao/uploaded/i1/3310688159/TB2ZXmKEMaTBuNjSszfXXXgfpXa_!!3310688159-0-item_pic.jpg"
        private int id;
        private String itemId;
        private String name;
        private int type;
        private double price;
        private double discountPrice;
        private int sellNum;
        private int categoryId;
        private double couponAmount;
        private double mineCommision;
        private String iconUrl;
        private String videoUrl;

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

        public double getMineCommision() {
            return mineCommision;
        }

        public void setMineCommision(double mineCommision) {
            this.mineCommision = mineCommision;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
    }
}
