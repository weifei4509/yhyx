package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/*
* 文件名：BrandEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/5/14 13:04
* 版    权：lansus
*/
public class BrandEntity {
    /**
     * datas : [{"id":1,"name":"李宁","commodityList":[{"id":1,"name":"11","type":1,"price":20,"discountPrice":48,"sellNum":12,"couponAmount":10,"iconUrl":"http://112.74.200.84:10081/attachment/ajax/visit/1"}]}]
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
         * id : 1
         * name : 李宁
         * commodityList : [{"id":1,"name":"11","type":1,"price":20,"discountPrice":48,"sellNum":12,"couponAmount":10,"iconUrl":"http://112.74.200.84:10081/attachment/ajax/visit/1"}]
         */

        private int id;
        private String name;
        private List<ProductEntity.DatasBean> commodityList;

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

        public List<ProductEntity.DatasBean> getCommodityList() {
            return commodityList;
        }

        public void setCommodityList(List<ProductEntity.DatasBean> commodityList) {
            this.commodityList = commodityList;
        }

    }
}
