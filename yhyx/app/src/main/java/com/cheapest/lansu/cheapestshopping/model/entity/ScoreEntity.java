package com.cheapest.lansu.cheapestshopping.model.entity;

/*
* 文件名：ScoreEntity
* 描    述：积分数据实体
* 作    者：lansu
* 时    间：2018/5/28 12:47
* 版    权：lansus
*/
public class ScoreEntity {

    /**
     * convertedAmount : 5.5
     * currentScore : 105
     * goingScore : 201
     */

    private double convertedAmount;
    private int currentScore;
    private int goingScore;

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getGoingScore() {
        return goingScore;
    }

    public void setGoingScore(int goingScore) {
        this.goingScore = goingScore;
    }
}
