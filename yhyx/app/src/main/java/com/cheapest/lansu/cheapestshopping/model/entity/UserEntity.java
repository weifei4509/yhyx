package com.cheapest.lansu.cheapestshopping.model.entity;

/*
* 文件名：User
* 描    述：用户信息
* 作    者：lansu
* 时    间：2018/4/26 11:16
* 版    权： lansus
*/
public class UserEntity {


    /**
     * id : 1
     * mobile : 15012345678
     * nickname : 张三
     * gender : 1
     * birthday : 2000-12-12
     * headpic : 1
     * headpicUrl : http://
     * thirdPartyType : 1
     * thirdPartyUid : 456asd
     * thirdPartyHeadpic : http://....
     * recommendQcodePath : 1
     * recommendQcodePathUrl : http://....
     * recommendUrl : http://xxx
     * recommendId : 1
     * recommendPid : 2
     * recommendFid : 0
     * enabled : false
     * score : 150
     * mobileCode :
     * recommendCode : xxa4s5asdaolkj
     * viped : false
     * createTime : 2018-04-12 12:12
     * aliAccount : null
     * wechatAccount : null
     * taobaoAccount : null
     * token : xxxxxxasdads
     */

    private String id;
    private String mobile;
    private String nickname;
    private int gender;
    private String birthday;
    private int headpic;
    private String headpicUrl;
    private int thirdPartyType;
    private String thirdPartyUid;
    private String thirdPartyHeadpic;
    private int recommendQcodePath;
    private String recommendQcodePathUrl;
    private String recommendUrl;
    private int recommendId;
    private int recommendPid;
    private int recommendFid;
    private boolean enabled;
    private int score;
    private String mobileCode;
    private String recommendCode;
    private int viped;
    private String createTime;
    private String aliAccount;
    private String wechatAccount;
    private String taobaoAccount;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getHeadpic() {
        return headpic;
    }

    public void setHeadpic(int headpic) {
        this.headpic = headpic;
    }

    public String getHeadpicUrl() {
        return headpicUrl;
    }

    public void setHeadpicUrl(String headpicUrl) {
        this.headpicUrl = headpicUrl;
    }

    public int getThirdPartyType() {
        return thirdPartyType;
    }

    public void setThirdPartyType(int thirdPartyType) {
        this.thirdPartyType = thirdPartyType;
    }

    public String getThirdPartyUid() {
        return thirdPartyUid;
    }

    public void setThirdPartyUid(String thirdPartyUid) {
        this.thirdPartyUid = thirdPartyUid;
    }

    public String getThirdPartyHeadpic() {
        return thirdPartyHeadpic;
    }

    public void setThirdPartyHeadpic(String thirdPartyHeadpic) {
        this.thirdPartyHeadpic = thirdPartyHeadpic;
    }

    public int getRecommendQcodePath() {
        return recommendQcodePath;
    }

    public void setRecommendQcodePath(int recommendQcodePath) {
        this.recommendQcodePath = recommendQcodePath;
    }

    public String getRecommendQcodePathUrl() {
        return recommendQcodePathUrl;
    }

    public void setRecommendQcodePathUrl(String recommendQcodePathUrl) {
        this.recommendQcodePathUrl = recommendQcodePathUrl;
    }

    public String getRecommendUrl() {
        return recommendUrl;
    }

    public void setRecommendUrl(String recommendUrl) {
        this.recommendUrl = recommendUrl;
    }

    public int getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(int recommendId) {
        this.recommendId = recommendId;
    }

    public int getRecommendPid() {
        return recommendPid;
    }

    public void setRecommendPid(int recommendPid) {
        this.recommendPid = recommendPid;
    }

    public int getRecommendFid() {
        return recommendFid;
    }

    public void setRecommendFid(int recommendFid) {
        this.recommendFid = recommendFid;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public int isViped() {
        return viped;
    }

    public void setViped(int viped) {
        this.viped = viped;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAliAccount() {
        return aliAccount;
    }

    public void setAliAccount(String aliAccount) {
        this.aliAccount = aliAccount;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public String getTaobaoAccount() {
        return taobaoAccount;
    }

    public void setTaobaoAccount(String taobaoAccount) {
        this.taobaoAccount = taobaoAccount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
