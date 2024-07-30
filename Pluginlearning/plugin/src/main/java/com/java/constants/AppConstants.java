package com.java.constants;

public class AppConstants {


    public static String PLUGIN_NAME = "JIRAAIAutomation";
    public static String JIRA_API_TOKEN    = "";
    public static String JIRA_USERNAME     = "shyam.ramath@gmail.com";
    public static String JIRA_API_ENDPOINT = "https://shyamramath.atlassian.net//rest/api/2/issue/";

    public static String OPEN_AI_KEY                = System.getProperty("OPEN_AI_KEY");
    public static String OPEN_AI_API_TOKEN          =  System.getProperty("OPEN_AI_API_TOKEN");
    public static String OPEN_AI_API_ENDPOINT       =  "https://api.openai.com/v1/chat/completions"; // System.getenv("OPEN_AI_API_ENDPOINT");

    public static String CORP_OPEN_AI_API_TOKEN     =  System.getProperty("CORP_OPEN_AI_API_TOKEN");
    public static String CORP_OPEN_AI_API_ENDPOINT  =  System.getProperty("CORP_OPEN_AI_API_ENDPOINT");
    public static String JIRA_AI_LOG_PATH           =  System.getProperty("JIRA_AI_LOG_PATH");


    public static String OPEN_AI_REQUEST_JSON_TEMPLATE = """
            {
                "model_name": "gpt-4",
                "application_name": "HACKATHON",
                "query_type": "query",
                "query": [
                    {
                        "role": "system",
                        "content": "Please elaborate the user content"
                    },
                    {
                        "role": "user",
                        "content": ""
                    }
                ]
            }
            """;


    public static String OPEN_AI_CORPORATE_REQUEST_JSON_TEMPLATE = """
            
            {
                "model": "gpt-4o",
                "messages": [
                  {
                    "role": "system",
                    "content": "You are a helpful assistant."
                  },
                  {
                    "role": "user",
                    "content": "Please elaborate this JIRA story, JDK 11 to JDK 17 migrations"
                  }
                ]
              }
            
            """;

    public static String OPEN_AI_RESPONSE_JSON_TEMPLATE = """
            {

                "code": 200,
                "dlp_execution_time_ms": 0,

                "error": null,
                        
                "full_model_response": {
                        
                    "choices": [
                        
                        {
                        
                            "content_filter_results": {
                        
                                "hate": {
                        
                                    "filtered": false,
                        
                                    "severity": "safe"
                        
                                },
                        
                                "self_harm": {
                        
                                    "filtered": false,
                        
                                    "severity": "safe"
                        
                                },
                        
                                "sexual": {
                        
                                    "filtered": false,
                        
                                    "severity": "safe"
                        
                                },
                        
                                "violence": {
                        
                                    "filtered": false,
                        
                                    "severity": "safe"
                        
                                }
                        
                            },
                        
                            "finish_reason": "stop",
                        
                            "index": 0,
                        
                            "logprobs": null,
                        
                            "message": {
                        
                                "content": "",
                        
                                "function_call": null,
                        
                                "role": "assistant",
                        
                                "tool_calls": null
                        
                            }
                        
                        }
                        
                    ],
                        
                    "created": 1721942724,
                        
                    "id": "",
                        
                    "model": "",
                        
                    "object": "",
                        
                    "prompt_filter_results": [
                        
                        {
                        
                            "content_filter_results": {
                        
                                "hate": {
                        
                                    "filtered": false,
                        
                                    "severity": "safe"
                        
                                },
                        
                                "self_harm": {
                        
                                    "filtered": false,
                        
                                    "severity": "safe"
                        
                                },
                        
                                "sexual": {
                        
                                    "filtered": false,
                        
                                    "severity": "safe"
                        
                                },
                        
                                "violence": {
                        
                                    "filtered": false,
                        
                                    "severity": "safe"
                        
                                }
                        
                            },
                        
                            "prompt_index": 0
                        
                        }
                        
                    ],
                        
                    "system_fingerprint": null,
                        
                    "usage": {
                        
                        "completion_tokens": 370,
                        
                        "prompt_tokens": 36,
                        
                        "total_tokens": 406
                        
                    }
                        
                },
                        
                "openai_execution_time_ms": 14614,
                        
                "query": [
                        
                    {
                        
                        "content": "",
                        
                        "role": ""
                        
                    },
                        
                    {
                        
                        "content": "",
                        
                        "role": "user"
                        
                    }
                        
                ],
                        
                "query_id": "",
                        
                "total_execution_time_ms": 14614
                        
            }
            
            """;

}
