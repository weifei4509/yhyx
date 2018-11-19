package com.cheapest.lansu.cheapestshopping.model.entity;

/*
* 文件名：WelfareENtity
* 描    述：
* 作    者：lansu
* 时    间：2018/5/16 14:28
* 版    权：lansus
*/
public class WelfareEntity {
    /**
     * balance : 99.99
     * totalBalance : 100.0
     * goingBalance : 21.01
     */

    private double balance;
    private double totalBalance;
    private double goingBalance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public double getGoingBalance() {
        return goingBalance;
    }

    public void setGoingBalance(double goingBalance) {
        this.goingBalance = goingBalance;
    }
}
