package com.java.openai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.HashMap;
import java.util.Map;

public class OpenAIChatGPTImpl {

    static String OPEN_AI_KEY =System.getenv("OPEN_AI_KEY");
    public static  String  chatModel(String storyDescription){
        System.out.println( "********* API call to ChatGPT *********");
        ChatLanguageModel model= OpenAiChatModel.withApiKey(OPEN_AI_KEY);
        //String stringTemplate =" Create a recipe for the {{dishType}} with following ingredients : {{ingredients}}";
        String stringTemplate ="The JIRA story with acceptance criteria, story context is   {{storyDescription}}";
        PromptTemplate promptTemplate =  PromptTemplate.from(stringTemplate);
        Map<String,Object> map= new HashMap<>();
        //map.put("dishType","Oven Dish");
        //map.put("ingredients","potato,tomato,feta, olive oil");
        map.put("storyDescription",storyDescription);
        Prompt prompt = promptTemplate.apply(map);
        String response = model.generate(prompt.text());
        System.out.println("Response From ChatGPT  ===" +response);
        return response;
    }

}
