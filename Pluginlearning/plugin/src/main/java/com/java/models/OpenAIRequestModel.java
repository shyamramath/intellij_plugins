package com.java.models;

import java.util.List;

/**
 *
 */
public class OpenAIRequestModel {

    public String model_name;
    public String application_name;
    public String query_type;
    public List<Query>  query;

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getApplication_name() {
        return application_name;
    }

    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

    public String getQuery_type() {
        return query_type;
    }

    public void setQuery_type(String query_type) {
        this.query_type = query_type;
    }

    public List<Query> getQuery() {
        return query;
    }

    public void setQuery(List<Query> query) {
        this.query = query;
    }

    public static class Query {

          public String role;
          public String content;

          public String getRole() {
              return role;
          }

          public void setRole(String role) {
              this.role = role;
          }

          public String getContent() {
              return content;
          }

          public void setContent(String content) {
              this.content = content;
          }
      }

}
