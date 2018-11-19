package com.cheapest.lansu.cheapestshopping.model.entity;

/*
* 文件名：VipEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/5/18 14:39
* 版    权：lansus
*/
public class VipEntity {
    /**
     * id : 1
     * payNo : 20156465313456
     * payType : 1
     * amount : 99.0
     * createTime : 2018-05-15 12:11
     * customerId : 1
     */

    private int id;
    private String payNo;
    private int payType;
    private double amount;
    private String createTime;
    private int customerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
