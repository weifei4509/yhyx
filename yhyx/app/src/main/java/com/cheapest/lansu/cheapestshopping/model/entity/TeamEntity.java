package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/*
* 文件名：TeamEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/5/3 13:48
* 版    权：
*/
public class TeamEntity {
    /**
     * datas : [{"id":1,"viped":1,"mobile":"15012341234","nickname":"张三","headpicUrl":"http://...."}]
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
         * viped : 1
         * mobile : 15012341234
         * nickname : 张三
         * headpicUrl : http://....
         */

        private int id;
        private int viped;
        private String mobile;
        private String nickname;
        private String headpicUrl;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getViped() {
            return viped;
        }

        public void setViped(int viped) {
            this.viped = viped;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadpicUrl() {
            return headpicUrl;
        }

        public void setHeadpicUrl(String headpicUrl) {
            this.headpicUrl = headpicUrl;
        }
    }
}
