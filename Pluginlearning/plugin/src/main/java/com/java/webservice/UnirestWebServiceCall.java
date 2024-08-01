package com.java.webservice;

import com.java.constants.AppConstants;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class UnirestWebServiceCall {

    public static String callJIRAApi(String payload){

        /**
         * JIRA API call to create stories
         */
        HttpResponse<JsonNode> response = Unirest.post(System.getProperty("JIRA_API_ENDPOINT"))
                .basicAuth(System.getProperty("JIRA_USER_NAME"),System.getProperty("JIRA_API_TOKEN"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(payload)
                .asJson();
        return String.valueOf(response.getBody());
    }

}
