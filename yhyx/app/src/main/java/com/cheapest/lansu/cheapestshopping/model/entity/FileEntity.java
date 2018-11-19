package com.cheapest.lansu.cheapestshopping.model.entity;

/*
* 文件名：FileEntity
* 描    述：
* 作    者：lansu
* 时    间：2018/5/10 15:52
* 版    权：lansus
*/
public class FileEntity {
    /**
     * code : 0
     * message :
     * id : 5
     * value : /files/upload/2017/11/21/1.jpg-37904136-5868-4283-a3be-baf05576b30c/1.jpg
     * url : http://39.106.13.51:10081/attachment/ajax/visit/5
     * fileName : 1.jpg
     * size : 3349
     */

    private int code;
    private String message;
    private int id;
    private String value;
    private String url;
    private String fileName;
    private int size;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
