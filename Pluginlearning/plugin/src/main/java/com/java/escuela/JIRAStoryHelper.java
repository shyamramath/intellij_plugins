package com.java.escuela;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.java.constants.AppConstants;
import com.java.models.JIRARequestModel;
import com.java.openai.OpenAIChatGPTImpl;
import com.java.webservice.UnirestWebServiceCall;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
        String API_TOKEN = "NULL";
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

    /**
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String createStories(String filePath) throws IOException {
        Map<Integer, List<String>> map = new FastexcelHelper().readExcel(filePath);

        StringBuilder responseMsg = new StringBuilder();
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            List<String> value = entry.getValue();
            String entry1= value.get(0);
            String entry2= value.get(1);
            String entry3= value.get(2);
            String entry4= value.get(3);
            String entry5= value.get(4);

            JIRARequestModel model = new JIRARequestModel();
            model.setTitleSummary(value.get(1));
            model.setDescription(OpenAIChatGPTImpl.chatModel(value.get(2)));
            model.setJiraIssueType(value.get(3));
            model.setKey(value.get(4));
            System.out.println(" Model Print : "+model.toString());
            responseMsg.append(createJIRAIssue(model));
        }
        responseMsg.append("Yaaay !!! "+ map.size() +" Stories Created Successfully");
        System.out.println(map);
        return responseMsg.toString();
    }

    /**
     *
     * @param model
     * @return
     */
    public static String createJIRAIssue(JIRARequestModel model){

        JsonNodeFactory jnf = JsonNodeFactory.instance;
        ObjectNode payload = jnf.objectNode();
        ObjectNode fields  = payload.putObject("fields");
        ObjectNode project  = fields.putObject("project");
        project.put("key", model.getKey());
        fields.put("summary", model.getTitleSummary());
        fields.put("description", model.getDescription());
        ObjectNode issueType = fields.putObject("issuetype");
        issueType.put("name", model.getJiraIssueType());

        System.out.println(payload.toString());
        String responseBody = UnirestWebServiceCall.callJIRAApi(payload.toString());
        System.out.println(responseBody);
        return responseBody;
    }
}
