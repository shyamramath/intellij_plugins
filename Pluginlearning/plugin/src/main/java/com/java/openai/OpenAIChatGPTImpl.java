package com.java.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.java.constants.AppConstants;
import com.java.models.OpenAIGenaralRequestModel;
import com.java.models.OpenAICorporateRequestModel;
import com.java.models.OpenAIGeneralResponseModel;
import com.java.models.OpenAIResponseModel;
import com.java.utils.FileUtils;
import com.java.webservice.OkHttpWebserviceCall;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.HashMap;
import java.util.Map;

public class OpenAIChatGPTImpl {

     static Logger log = Logger.getInstance(OpenAIChatGPTImpl.class);
    /**
     * @param storyDescription
     * @return
     */
    public static  String langchainChatModel(String storyDescription){
    try {
            String response ="";
            log.info(" Connecting to openAI API .....");
            ChatLanguageModel model = OpenAiChatModel.withApiKey(AppConstants.OPEN_AI_KEY);
            log.info(" Connected with a valid API key.");
            String stringTemplate = "The JIRA story with acceptance criteria, story context is   {{storyDescription}}";
            PromptTemplate promptTemplate = PromptTemplate.from(stringTemplate);
            log.info(" The JIRA story with acceptance criteria, story context is, "+storyDescription);
            Map<String, Object> map = new HashMap<>();
            map.put("storyDescription", storyDescription);
            Prompt prompt = promptTemplate.apply(map);
            log.info(" Prompting openai to get the in-detailed story .");
            response = model.generate(prompt.text());
            log.info(" Response received from openAI  =, "+response);
            return response;
        } catch (Exception e){
            log.error(" ERROR !!!!! Communication failed with Open AI API's, "+e.getMessage());
            return "ERROR";
        }
    }

    /**
     *
     * @param storyDescription
     * @return
     */
    public static String openAICorporateChatModel(String storyDescription, ProgressIndicator indicator){
        String openAIResponseMessage ="";
        //Create Request Model.
        String jsonData = AppConstants.OPEN_AI_REQUEST_JSON_TEMPLATE;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(" Converting JSON to Java Object --------");
            OpenAICorporateRequestModel openAIRequestModel = objectMapper.readValue(jsonData, OpenAICorporateRequestModel.class);
            openAIRequestModel.getQuery().get(1).setContent(storyDescription);
            log.info(" Successfully created the request Object ......");
            // covert Java object to JSON strings
            String requestJson = objectMapper.writeValueAsString(openAIRequestModel);
            //Create Connection Object.
            OkHttpWebserviceCall okHttpWebserviceCall =new OkHttpWebserviceCall();
            //Call API.
            String responseBody = okHttpWebserviceCall.makeCorporateOpenAICall(requestJson,indicator);
            log.info(" Response from  Corporate openAI response  --------" + responseBody);
            //Read Response Body.
            OpenAIGeneralResponseModel openAIResponseModel = objectMapper.readValue(responseBody, OpenAIGeneralResponseModel.class);
            for(OpenAIGeneralResponseModel.Choice choice: openAIResponseModel.getFull_model_response().getChoices()){
                if(choice!=null) {
                    openAIResponseMessage = choice.getMessage().getContent();
                }
            }
            //Return Response.
            return openAIResponseMessage;

        } catch (Exception  e) {
            indicator.setText(e.getMessage());
            log.error(e);
            log.error(" openAICorporateChatModel() ERROR !!!!! Communication failed with Open AI API's, "+e.getMessage());
            return "ERROR";
        }
    }

    @Deprecated
    public static String openAIcall(String storyDescription){
        String openAIResponseMessage ="";
        //Create Request Model.
        String jsonData = AppConstants.OPEN_AI_REQUEST_JSON_TEMPLATE;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OpenAIGenaralRequestModel openAIRequestModel = objectMapper.readValue(jsonData, OpenAIGenaralRequestModel.class);
            openAIRequestModel.getMessages().get(1).setContent(storyDescription);
            // covert Java object to JSON strings
            String requestJson = objectMapper.writeValueAsString(openAIRequestModel);
            //Create Connection Object.
            OkHttpWebserviceCall okHttpWebserviceCall =new OkHttpWebserviceCall();
            //Call API.
            String responseBody = okHttpWebserviceCall.makeOpenAIAPICall(requestJson);
            OpenAIGeneralResponseModel openAIResponseModel = objectMapper.readValue(responseBody, OpenAIGeneralResponseModel.class);
            for(OpenAIGeneralResponseModel.Choice choice: openAIResponseModel.getFull_model_response().getChoices()){
                System.out.println(choice.getMessage().getContent());
            }
            //Return Response.
            return openAIResponseMessage;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
