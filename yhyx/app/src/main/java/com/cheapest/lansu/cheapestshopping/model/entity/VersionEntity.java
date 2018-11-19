package com.cheapest.lansu.cheapestshopping.model.entity;

/*
* 文件名：VersionEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/5/8 18:00
* 版    权：lansus
*/
public class VersionEntity {
    /**
     * id : 1
     * name : 版本
     * version : 1.1.1
     * number : 2
     * machineType : 1
     * log : 本次更新的更新说明....
     * downloadUrl : url
     */

    private int id;
    private String name;
    private String version;
    private int number;
    private int machineType;
    private String log;
    private String downloadUrl;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMachineType() {
        return machineType;
    }

    public void setMachineType(int machineType) {
        this.machineType = machineType;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
