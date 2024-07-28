package com.java.utils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intellij.openapi.progress.ProgressIndicator;
import com.java.models.JIRARequestModel;
import com.java.openai.OpenAIChatGPTImpl;
import com.java.webservice.UnirestWebServiceCall;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JiraAPIUtilities {

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

        HttpResponse<JsonNode> response = Unirest.post("https://yourdomain.atlassian.net/rest/api/2/issue/")
                .basicAuth("your.email@email.com",API_TOKEN)
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
    public static String createStories(String filePath, Boolean isSecuredModeON, ProgressIndicator indicator) throws IOException {

        Map<Integer, List<String>> map = new ReadWriteExcelSheet().readExcel(filePath);
        indicator.setText(" Reading uploaded excel file from the disk");
        FileUtils.log("Reading uploaded excel file from the disk, "+filePath);

        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            FileUtils.log(" Rows read from Spread-sheetb ->  ( "+ entry.getKey() + ": " + entry.getValue() +" ) " );

            List<String> value = entry.getValue();
            String entry1= value.get(0);
            String subject= value.get(1);
            String description= value.get(2);
            String issueType= value.get(3).trim();
            String boardName= value.get(4).trim();


            if (subject.equals("Subject")){
                FileUtils.log(" Skipping the header row from the spread-sheet ");
                continue;
            }
            JIRARequestModel model = new JIRARequestModel();
            model.setTitleSummary(subject);

            //TODO - find a better solution
            if(isSecuredModeON) {
                indicator.setText(" Secured mode is on, calling OpenAI API ....");
                FileUtils.log("Secured mode is on, calling OpenAI API , ");
                model.setDescription(OpenAIChatGPTImpl.openAICorporateChatModel(description,indicator));
            }else{
                indicator.setText(" calling OpenAI API for story description....");
                model.setDescription(OpenAIChatGPTImpl.langchainChatModel(description));
            }

            model.setJiraIssueType(issueType);
            model.setKey(boardName);
            FileUtils.log ("Model Print : "+model.toString());
            FileUtils.log ("*******************************************************************************************************************************************");
            indicator.setText(" Creating JIRA stories on the board ....");
            FileUtils.log(createJIRAIssue(model));
            FileUtils.log ("*******************************************************************************************************************************************");
        }

        FileUtils.log ("Data read from Spreadsheet : "+map);
        FileUtils.log("All Stories in the upload spread-sheet got created, totol number of stories created ("+map.size()+")");
        return "All Stories in the upload spread-sheet got created, totol number of stories created ("+map.size()+")";
    }

    /**
     *
     * @param model
     * @return
     */
    private static String createJIRAIssue(JIRARequestModel model){

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
