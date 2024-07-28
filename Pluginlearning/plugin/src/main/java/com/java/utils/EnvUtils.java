package com.java.utils;

import com.intellij.openapi.progress.ProgressIndicator;

public class EnvUtils {

    public static Boolean validateAllEnvVariables(ProgressIndicator indicator){

        String jiraapiToken         = System.getenv("JIRA_API_TOKEN");
        String jiraUserName         = System.getenv("JIRA_USER_NAME");
        String jiraOpenAiKey        = System.getenv("OPEN_AI_KEY");
        String jiraIsSecuredMode    = System.getenv("IS_SECURED_CHATGPT_MODE");
        String corpApiEndpoint      = System.getenv("CORP_OPEN_AI_API_ENDPOINT ");
        String corpApiToken         = System.getenv("CORP_OPEN_AI_API_TOKEN ");

        FileUtils.log("******************************** ENV Variables ***************************************************");
        FileUtils.log( "    JIRA_API_TOKEN                 : "+jiraapiToken);
        FileUtils.log( "    JIRA_USER_NAME                 : "+jiraUserName);
        FileUtils.log( "    IS_SECURED_CHATGPT_MODE        : "+jiraIsSecuredMode);
        FileUtils.log( "    CORP_OPEN_AI_API_TOKEN         : "+corpApiToken);
        FileUtils.log("**************************************************************************************************");

        Boolean envValidationFlag = true;

        StringBuilder envValidationMsg = new StringBuilder();

        if (jiraapiToken == null){
            envValidationMsg.append(" Jira API Token is not set");
            envValidationFlag=false;
        }

        if (jiraUserName == null){
            envValidationMsg.append(" Jira Username is not set ");
            envValidationFlag=false;
        }

        if (jiraOpenAiKey == null){
            envValidationMsg.append(" OpenAI-key is not set");
            envValidationFlag=false;
        }

        if (jiraIsSecuredMode == null){
            envValidationMsg.append(" Secure mode is not set");
            envValidationFlag=false;
        }

        if(Boolean.valueOf(jiraIsSecuredMode)){
            if(corpApiToken==null || corpApiEndpoint==null){
                FileUtils.log( "!!! Secure mode is on, corp api keys are not configured !!!!");
                envValidationFlag=false;
            }
        }

        if(envValidationFlag){
            indicator.setText("Validated all the env Variables, Looks good !! ");
        }else{
            indicator.setText(envValidationMsg.toString());
        }

        return envValidationFlag;
    }
}
