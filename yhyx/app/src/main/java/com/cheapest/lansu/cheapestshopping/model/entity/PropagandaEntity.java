package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/*
* 文件名：PropagandaEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/5/21 10:20
* 版    权：lansus
*/
public class PropagandaEntity {
    /**
     * datas : [{"id":1,"title":"闻君有两意，故来相决绝","images":["",""],"createTime":"2018-05-12"}]
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
         * title : 闻君有两意，故来相决绝
         * images : ["",""]
         * createTime : 2018-05-12
         */

        private int id;
        private String title;
        private String createTime;
        private List<String> images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
