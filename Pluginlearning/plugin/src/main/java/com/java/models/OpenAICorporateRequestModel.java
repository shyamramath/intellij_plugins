package com.java.models;

import java.util.List;

public class OpenAICorporateRequestModel {

    public String model;
    public List<OpenAIRequestModel.Query> messages;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<OpenAIRequestModel.Query> getMessages() {
        return messages;
    }

    public void setMessages(List<OpenAIRequestModel.Query> messages) {
        this.messages = messages;
    }
}
