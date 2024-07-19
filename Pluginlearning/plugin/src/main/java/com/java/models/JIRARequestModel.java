package com.java.models;

public class JIRARequestModel {

    String titleSummary;
    String description;
    String jiraIssueType;
    String key;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleSummary() {
        return titleSummary;
    }

    public void setTitleSummary(String titleSummary) {
        this.titleSummary = titleSummary;
    }

    public String getJiraIssueType() {
        return jiraIssueType;
    }

    public void setJiraIssueType(String jiraIssueType) {
        this.jiraIssueType = jiraIssueType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
