package com.java.models;

import java.util.List;

public class OpenAIGenaralRequestModel {

    public String model;
    public List<OpenAICorporateRequestModel.Query> messages;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<OpenAICorporateRequestModel.Query> getMessages() {
        return messages;
    }

    public void setMessages(List<OpenAICorporateRequestModel.Query> messages) {
        this.messages = messages;
    }
}
