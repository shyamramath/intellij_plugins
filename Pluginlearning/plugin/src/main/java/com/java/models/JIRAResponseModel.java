package com.java.models;

public class JIRAResponseModel {

//    {"id":"10092","key":"GN-68","self":"https://shyamramath.atlassian.net/rest/api/2/issue/10092"}

    String Id;
    String key;
    String self;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }
}
