package com.java.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.constants.AppConstants;
import com.java.models.OpenAICorporateRequestModel;
import com.java.models.OpenAIRequestModel;
import com.java.models.OpenAICorporateResponseModel;
import com.java.models.OpenAIResponseModel;
import com.java.webservice.OkHttpWebserviceCall;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.HashMap;
import java.util.Map;

public class OpenAIChatGPTImpl {

    static String OPEN_AI_KEY =System.getenv("OPEN_AI_KEY");

    /**
     * @param storyDescription
     * @return
     */
    public static  String langchainChatModel(String storyDescription){
    try {
            ChatLanguageModel model = OpenAiChatModel.withApiKey(OPEN_AI_KEY);
            String stringTemplate = "The JIRA story with acceptance criteria, story context is   {{storyDescription}}";
            PromptTemplate promptTemplate = PromptTemplate.from(stringTemplate);

            Map<String, Object> map = new HashMap<>();
            //map.put("dishType","Oven Dish");
            //map.put("ingredients","potato,tomato,feta, olive oil");
            map.put("storyDescription", storyDescription);

            Prompt prompt = promptTemplate.apply(map);
            String response = model.generate(prompt.text());
            System.out.println("Response From ChatGPT  ===" + response);

            return response;

        } catch (Exception e){
            e.printStackTrace();
            return "Communication failed with Open AI API's";
        }
    }


    public static String openAICorporateChatModel(String storyDescription){
        String openAIResponseMessage ="";
        //Create Request Model.
        String jsonData = AppConstants.OPEN_AI_CORPORATE_REQUEST_JSON_TEMPLATE;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // OpenAIRequestModel openAIRequestModel = objectMapper.readValue(jsonData, OpenAIRequestModel.class);
            OpenAICorporateRequestModel openAIRequestModel = objectMapper.readValue(jsonData, OpenAICorporateRequestModel.class);
            openAIRequestModel.getMessages().get(1).setContent(storyDescription);
            // covert Java object to JSON strings
            String requestJson = objectMapper.writeValueAsString(openAIRequestModel);
            //Create Connection Object.
            OkHttpWebserviceCall okHttpWebserviceCall =new OkHttpWebserviceCall();
            //Call API.
            String responseBody = okHttpWebserviceCall.makeCorporateCall(requestJson);
            //Read Response Body.
            OpenAIResponseModel openAIResponseModel = objectMapper.readValue(responseBody, OpenAIResponseModel.class);
            for(OpenAICorporateResponseModel.Choice choice: openAIResponseModel.getChoices()){
                openAIResponseMessage=choice.getMessage().getContent();
            }
            //Return Response.
            return openAIResponseMessage;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String openAIcall(String storyDescription){
        String openAIResponseMessage ="";
        //Create Request Model.
        String jsonData = AppConstants.OPEN_AI_REQUEST_JSON_TEMPLATE;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OpenAIRequestModel openAIRequestModel = objectMapper.readValue(jsonData, OpenAIRequestModel.class);
            openAIRequestModel.getQuery().get(1).setContent(storyDescription);
            //Create Connection Object.
            OkHttpWebserviceCall okHttpWebserviceCall =new OkHttpWebserviceCall();
            //Call API.
            String responseBody = okHttpWebserviceCall.makeCall(openAIRequestModel);
            OpenAICorporateResponseModel openAIResponseModel = objectMapper.readValue(responseBody, OpenAICorporateResponseModel.class);
            for(OpenAICorporateResponseModel.Choice choice: openAIResponseModel.getFull_model_response().getChoices()){
                System.out.println(choice.getMessage().getContent());
            }
            //Return Response.
            return openAIResponseMessage;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
