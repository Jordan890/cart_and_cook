package com.cartandcook.selfhosted.configs;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cartandcook.ai")
public class AiProperties {
    private String baseUrl;
    private String endpoint;
    private String model;
    private String apiKey;

    public AiProperties() {
    }

    public AiProperties(String baseUrl, String endpoint, String model, String apiKey) {
        this.baseUrl = baseUrl;
        this.endpoint = endpoint;
        this.model = model;
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
