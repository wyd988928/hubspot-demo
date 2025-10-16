package com.example.hubspotdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * HubSpot API 配置类，用于读取配置文件中的 HubSpot 相关配置
 */
@Configuration
@ConfigurationProperties(prefix = "hubspot.api")
@Data
public class HubSpotConfig {

    /**
     * HubSpot API 基础 URL
     */
    private String baseUrl;

    /**
     * HubSpot API Key
     */
    private String apiKey;

    /**
     * 请求超时时间（毫秒）
     */
    private int timeout;

    /**
     * 连接超时时间（毫秒）
     */
    private int connectTimeout;

}