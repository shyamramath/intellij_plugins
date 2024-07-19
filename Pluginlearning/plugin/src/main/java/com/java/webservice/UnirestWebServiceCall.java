package com.java.webservice;

import com.java.constants.AppConstants;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class UnirestWebServiceCall {

    public static String callJIRAApi(String payload){
        HttpResponse<JsonNode> response = Unirest.post(AppConstants.JIRA_URL)
                .basicAuth(AppConstants.JIRA_USERNAME,AppConstants.JIRA_API_TOKEN)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(payload)
                .asJson();
        return String.valueOf(response.getBody());
    }

}
