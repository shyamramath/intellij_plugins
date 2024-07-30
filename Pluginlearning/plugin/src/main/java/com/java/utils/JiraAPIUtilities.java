package com.java.utils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intellij.openapi.diagnostic.Logger;
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

    static Logger logger= Logger.getInstance(JiraAPIUtilities.class);

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

        indicator.setText(" Reading the file from the location, "+filePath);
        Map<Integer, List<String>> map = new ReadWriteExcelSheet().readExcel(filePath);
//        logger.info("Successfully read all the data from the spreadsheet ");
//        logger.info("Contents read from Spreadsheet : "+map);
        
        logger.info("Successfully read all the data from the spreadsheet ");
        logger.info("Contents read from Spreadsheet : "+map);
        
        int count=0;
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            logger.info(" Rows read from Spread-sheet ->  ( "+ entry.getKey() + ": " + entry.getValue() +" ) " );
            List<String> value = entry.getValue();
            String entry1= value.get(0);
            String subject= value.get(1);
            String description= value.get(2);
            String issueType= value.get(3).trim();
            String boardName= value.get(4).trim();

            if (subject.equals("Subject")){
                logger.info(" Skipping the header row from the spread-sheet ");
                continue;
            }

            JIRARequestModel model = new JIRARequestModel();
            model.setTitleSummary(subject);

            //TODO - find a better solution
            if(isSecuredModeON) {
                indicator.setText(" Secured mode is on, calling OpenAI API ....");
                logger.info(" Secured mode is on, calling Corporate OpenAI API , ");
                model.setDescription(OpenAIChatGPTImpl.openAICorporateChatModel(description,indicator));
            }else{
                logger.info(" Secured mode is disabled, calling OpenAI API , ");
                indicator.setText(" calling openAI API for story description....");
                model.setDescription(OpenAIChatGPTImpl.langchainChatModel(description));
            }
   
            model.setJiraIssueType(issueType);
            model.setKey(boardName);

            if(! model.getDescription().equals("ERROR")) {
                logger.info("*******************************************************************************************************************************************");
                indicator.setText(" Creating JIRA stories on the board ....");
                logger.info(createJIRAIssue(model));
                logger.info("*******************************************************************************************************************************************");
                count++;
            }else{
                logger.info("Failed to create Stories in JIRA board from the uploaded spread-sheet , totol number of stories created ("+count+")");
                return "Error occured while creating the JIRA stories, please read the logs for more details ";
            }
        }
        logger.info("All Stories in the upload spread-sheet got created, totol number of stories created ("+count+")");
        return "All Stories in the upload spread-sheet got created, totol number of stories created ("+count+")";
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

        logger.info(payload.toString());
        String responseBody = UnirestWebServiceCall.callJIRAApi(payload.toString());
        logger.info(responseBody);
        return responseBody;
    }
}
