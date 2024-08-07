package com.java.models;

public class OpenAIGeneralResponseModel {

    private int code;
    private int dlp_execution_time_ms;
    private String error;
    private FullModelResponse full_model_response;
    private int openai_execution_time_ms;
    private Query[] query;
    private String query_id;
    private int total_execution_time_ms;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDlp_execution_time_ms() {
        return dlp_execution_time_ms;
    }

    public void setDlp_execution_time_ms(int dlp_execution_time_ms) {
        this.dlp_execution_time_ms = dlp_execution_time_ms;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public FullModelResponse getFull_model_response() {
        return full_model_response;
    }

    public void setFull_model_response(FullModelResponse full_model_response) {
        this.full_model_response = full_model_response;
    }

    public int getOpenai_execution_time_ms() {
        return openai_execution_time_ms;
    }

    public void setOpenai_execution_time_ms(int openai_execution_time_ms) {
        this.openai_execution_time_ms = openai_execution_time_ms;
    }

    public Query[] getQuery() {
        return query;
    }

    public void setQuery(Query[] query) {
        this.query = query;
    }

    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }

    public int getTotal_execution_time_ms() {
        return total_execution_time_ms;
    }

    public void setTotal_execution_time_ms(int total_execution_time_ms) {
        this.total_execution_time_ms = total_execution_time_ms;
    }

    public static class FullModelResponse {
        private Choice[] choices;
        private long created;
        private String id;
        private String model;
        private String object;
        private PromptFilterResult[] prompt_filter_results;
        private String system_fingerprint;
        private Usage usage;

        // Getters and Setters


        public Choice[] getChoices() {
            return choices;
        }

        public void setChoices(Choice[] choices) {
            this.choices = choices;
        }

        public long getCreated() {
            return created;
        }

        public void setCreated(long created) {
            this.created = created;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public PromptFilterResult[] getPrompt_filter_results() {
            return prompt_filter_results;
        }

        public void setPrompt_filter_results(PromptFilterResult[] prompt_filter_results) {
            this.prompt_filter_results = prompt_filter_results;
        }

        public String getSystem_fingerprint() {
            return system_fingerprint;
        }

        public void setSystem_fingerprint(String system_fingerprint) {
            this.system_fingerprint = system_fingerprint;
        }

        public Usage getUsage() {
            return usage;
        }

        public void setUsage(Usage usage) {
            this.usage = usage;
        }
    }


    public static class Choice {
        private ContentFilterResults content_filter_results;
        private String finish_reason;
        private int index;
        private Object logprobs;
        private Message message;

        // Getters and Setters

        public ContentFilterResults getContent_filter_results() {
            return content_filter_results;
        }

        public void setContent_filter_results(ContentFilterResults content_filter_results) {
            this.content_filter_results = content_filter_results;
        }

        public String getFinish_reason() {
            return finish_reason;
        }

        public void setFinish_reason(String finish_reason) {
            this.finish_reason = finish_reason;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Object getLogprobs() {
            return logprobs;
        }

        public void setLogprobs(Object logprobs) {
            this.logprobs = logprobs;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

    public static class ContentFilterResults {
        private FilterResult hate;
        private FilterResult self_harm;
        private FilterResult sexual;
        private FilterResult violence;

        // Getters and Setters

        public FilterResult getHate() {
            return hate;
        }

        public void setHate(FilterResult hate) {
            this.hate = hate;
        }

        public FilterResult getSelf_harm() {
            return self_harm;
        }

        public void setSelf_harm(FilterResult self_harm) {
            this.self_harm = self_harm;
        }

        public FilterResult getSexual() {
            return sexual;
        }

        public void setSexual(FilterResult sexual) {
            this.sexual = sexual;
        }

        public FilterResult getViolence() {
            return violence;
        }

        public void setViolence(FilterResult violence) {
            this.violence = violence;
        }
    }

    public static class FilterResult {
        private boolean filtered;
        private String severity;

        // Getters and Setters

        public boolean isFiltered() {
            return filtered;
        }

        public void setFiltered(boolean filtered) {
            this.filtered = filtered;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }
    }

    public static class Message {
        private String content;
        private Object function_call;
        private String role;
        private Object tool_calls;

        // Getters and Setters


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getFunction_call() {
            return function_call;
        }

        public void setFunction_call(Object function_call) {
            this.function_call = function_call;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public Object getTool_calls() {
            return tool_calls;
        }

        public void setTool_calls(Object tool_calls) {
            this.tool_calls = tool_calls;
        }
    }

    public static class PromptFilterResult {
        private ContentFilterResults content_filter_results;
        private int prompt_index;

        // Getters and Setters

        public ContentFilterResults getContent_filter_results() {
            return content_filter_results;
        }

        public void setContent_filter_results(ContentFilterResults content_filter_results) {
            this.content_filter_results = content_filter_results;
        }

        public int getPrompt_index() {
            return prompt_index;
        }

        public void setPrompt_index(int prompt_index) {
            this.prompt_index = prompt_index;
        }
    }

    public static class Usage {
        private int completion_tokens;
        private int prompt_tokens;
        private int total_tokens;

        // Getters and Setters


        public int getCompletion_tokens() {
            return completion_tokens;
        }

        public void setCompletion_tokens(int completion_tokens) {
            this.completion_tokens = completion_tokens;
        }

        public int getPrompt_tokens() {
            return prompt_tokens;
        }

        public void setPrompt_tokens(int prompt_tokens) {
            this.prompt_tokens = prompt_tokens;
        }

        public int getTotal_tokens() {
            return total_tokens;
        }

        public void setTotal_tokens(int total_tokens) {
            this.total_tokens = total_tokens;
        }
    }

    public static class Query {
        private String content;

        private String role;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
