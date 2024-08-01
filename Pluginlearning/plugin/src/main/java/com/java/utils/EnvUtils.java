package com.java.utils;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;

public class EnvUtils {

    static Logger log = Logger.getInstance(EnvUtils.class);
    
    public static Boolean validateAllEnvVariables(ProgressIndicator indicator){

        String jiraapiToken         = System.getProperty("JIRA_API_TOKEN");
        String jiraUserName         = System.getProperty("JIRA_USER_NAME");
        String openAiKey            = System.getProperty("OPEN_AI_KEY");
        String jiraIsSecuredMode    = System.getProperty("IS_SECURED_CHATGPT_MODE");
        String corpApiEndpoint      = System.getProperty("CORP_OPEN_AI_API_ENDPOINT");
        String corpApiToken         = System.getProperty("CORP_OPEN_AI_API_TOKEN");

        log.info("******************************** ENV Variables ***************************************************");
        log.info( "    JIRA_API_TOKEN                       : "+jiraapiToken);
        log.info( "    JIRA_USER_NAME                       : "+jiraUserName);
        log.info( "    OPEN_AI_KEY                          : "+openAiKey);
        log.info( "    IS_SECURED_CHATGPT_MODE              : "+jiraIsSecuredMode);
        log.info( "    CORP_OPEN_AI_API_TOKEN               : "+corpApiToken);
        log.info( "    CORP_OPEN_AI_API_ENDPOINT            : "+corpApiEndpoint);
        log.info("**************************************************************************************************");

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

        if (openAiKey == null){
            envValidationMsg.append(" OpenAI-key is not set");
            envValidationFlag=false;
        }

        if (jiraIsSecuredMode == null){
            envValidationMsg.append(" Secure mode is not set");
            envValidationFlag=false;
        }

        if(Boolean.valueOf(jiraIsSecuredMode)){
            if(corpApiToken==null || corpApiEndpoint==null){
                log.debug( "!!! Secure mode is on, corp api keys are not configured !!!!");
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
