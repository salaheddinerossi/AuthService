package com.example.authservice.response;

public class PresignedUrlResponse {
    private String url;

    // Constructor
    public PresignedUrlResponse(String url) {
        this.url = url;
    }

    // Getter and Setter
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
