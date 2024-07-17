package com.java.escuela;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intellij.util.IconUtil;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class JIRAStoryHelper {

    /**
     *
     * @return
     */
    public static String jiraCreateIssue(String titleSummary, String description, String jiraIssueType){

        String jiraIssue = "";
        JsonNodeFactory jnf = JsonNodeFactory.instance;
        ObjectNode payload = jnf.objectNode();
        ObjectNode fields  = payload.putObject("fields");
        ObjectNode project  = fields.putObject("project");

        project.put("key", "GN");
        fields.put("summary", titleSummary);
        fields.put("description", description);

        ObjectNode issueType = fields.putObject("issuetype");
        issueType.put("name", "Bug");

        System.out.println(payload.toString());

        String API_TOKEN = "add_your_jira_token";
        //HttpsResponse<JsonNode>  = Unirest.
       HttpResponse<JsonNode> response = Unirest.post("https://shyamramath.atlassian.net//rest/api/2/issue/")
                .basicAuth("shyam.ramath@gmail.com",API_TOKEN)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(payload.toString())
                .asJson();

        String responseBody =String.valueOf(response.getBody());
        System.out.println(responseBody);
        return jiraIssue;
    }

}
