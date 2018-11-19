package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/*
* 文件名：MessageEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/5/12 11:37
* 版    权：lansus
*/
public class MessageEntity {
    /**
     * datas : [{"id":1,"customerId":1,"role":1,"content":"消息内容","createTime":"2017-12-12","readed":0,"type":2,"msgId":1}]
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
         * customerId : 1
         * role : 1
         * content : 消息内容
         * createTime : 2017-12-12
         * readed : 0
         * type : 2
         * msgId : 1
         */

        private int id;
        private int customerId;
        private int role;
        private String content;
        private String createTime;
        private int readed;
        private int type;
        private int msgId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getReaded() {
            return readed;
        }

        public void setReaded(int readed) {
            this.readed = readed;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMsgId() {
            return msgId;
        }

        public void setMsgId(int msgId) {
            this.msgId = msgId;
        }
    }
}
