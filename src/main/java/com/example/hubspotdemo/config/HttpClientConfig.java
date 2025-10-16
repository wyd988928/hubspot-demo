package com.example.hubspotdemo.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * HTTP 客户端配置类，用于配置访问 HubSpot API 的 RestTemplate
 */
@Configuration
public class HttpClientConfig {

    private final HubSpotConfig hubSpotConfig;

    @Autowired
    public HttpClientConfig(HubSpotConfig hubSpotConfig) {
        this.hubSpotConfig = hubSpotConfig;
    }

    /**
     * 创建 OkHttpClient
     */
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                // 设置连接超时
                .connectTimeout(hubSpotConfig.getConnectTimeout(), TimeUnit.MILLISECONDS)
                // 设置读取超时
                .readTimeout(hubSpotConfig.getTimeout(), TimeUnit.MILLISECONDS)
                // 设置写入超时
                .writeTimeout(hubSpotConfig.getTimeout(), TimeUnit.MILLISECONDS)
                // 设置连接池
                .connectionPool(new ConnectionPool(20, 60, TimeUnit.SECONDS))
                // 保持连接
                .retryOnConnectionFailure(true)
                .build();
    }

    /**
     * 创建 RestTemplate 用于访问 HubSpot API
     */
    @Bean
    public RestTemplate hubSpotRestTemplate(OkHttpClient okHttpClient) {
        OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
        
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        
        // 添加拦截器，统一处理请求头、认证等
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            // 添加 API Key 到请求头
            request.getHeaders().add("Authorization", "Bearer " + hubSpotConfig.getApiKey());
            // 添加 Content-Type 头
            request.getHeaders().add("Content-Type", "application/json");
            // 添加 User-Agent 头
            request.getHeaders().add("User-Agent", "HubSpot-Demo-App/1.0");
            // System.out.println("URI: " + request.getURI());
            // System.out.println("Headers: " + request.getHeaders());
            return execution.execute(request, body);
        }));
        
        return restTemplate;
    }
}