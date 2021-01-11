package com.yuanshuai.idea.models;

public class Config {
    /**
     * showDocApi地址
     */
    private String showDocApi;
    /**
     * showDocApiKey
     */
    private String apiKey;
    /**
     * showDocApiToken
     */
    private String apiToken;

    public String getShowDocApi() {
        return showDocApi;
    }

    public void setShowDocApi(String showDocApi) {
        this.showDocApi = showDocApi;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
}
