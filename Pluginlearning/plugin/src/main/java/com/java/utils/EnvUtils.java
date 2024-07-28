package com.java.utils;

import com.intellij.openapi.progress.ProgressIndicator;

public class EnvUtils {

//    export JIRA_API_TOKEN="ATATT3xFfGF0_gzVQ1FWqa0NwNcp9AXARBBLv7kWGRgc0PXdxwpppVRR-3-_8XawRartR_4q2Xk1PfjVyuIuQ7Zxsdhzk1HAroPmaVaTznIhw3yIlZNgGpzBPSVjHCnOf0N7y3Q3qIcXxePQsH0cWURngte0GnUX6SDUDcv26BEdj5ObYm7RcDA=AF58FA6F"
//    export JIRA_USER_NAME="shyam.ramath@gmail.com"
//    export JIRA_API_ENDPOINT="https://shyamramath.atlassian.net//rest/api/2/issue/"
//            [[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"
//    export OPEN_AI_KEY="sk-proj-lLDenPq7XONIyYcO3Oa3T3BlbkFJGSuVdv7VE8RFWlYSLt47"
//    export IS_SECURED_CHATGPT_MODE=false


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
