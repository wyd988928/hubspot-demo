package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.config.HubSpotConfig;
import com.example.hubspotdemo.exception.HubSpotApiException;
import com.example.hubspotdemo.model.HubSpotPropertiesResponse;
import com.example.hubspotdemo.model.HubSpotResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * HubSpot API 基础服务类，封装通用的 API 调用方法
 */
@Service
public abstract class HubSpotBaseService {

    private static final Logger logger = LoggerFactory.getLogger(HubSpotBaseService.class);

    protected final RestTemplate restTemplate;
    protected final HubSpotConfig hubSpotConfig;
    protected final ObjectMapper objectMapper;

    @Autowired
    public HubSpotBaseService(RestTemplate restTemplate, HubSpotConfig hubSpotConfig, 
                             ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.hubSpotConfig = hubSpotConfig;
        this.objectMapper = objectMapper;
    }

    /**
     * 构建完整的 API URL
     * 
     * @param endpoint API 端点
     * @return 完整的 API URL
     */
    protected String buildUrl(String endpoint) {
        return hubSpotConfig.getBaseUrl() + endpoint;
    }

    /**
     * 执行 GET 请求
     * 
     * @param endpoint API 端点
     * @param responseType 响应类型
     * @param <T> 响应数据类型
     * @return API 响应
     */
    protected <T> T get(String endpoint, Class<T> responseType) {
        String url = buildUrl(endpoint);
        logger.debug("执行 GET 请求: {}", url);
        
        try {
            ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, responseType);
            logger.debug("GET 请求成功: {}, 状态码: {}", url, responseEntity.getStatusCodeValue());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            handleApiException(e, "GET", url);
            return null; // 永远不会到达这里，handleApiException 会抛出异常
        }
    }

    /**
     * 执行 GET 请求（带参数化类型）
     * 
     * @param endpoint API 端点
     * @param responseType 响应类型引用
     * @param <T> 响应数据类型
     * @return API 响应
     */
    protected <T> T get(String endpoint, ParameterizedTypeReference<T> responseType) {
        String url = buildUrl(endpoint);
        logger.debug("执行 GET 请求: {}", url);
        
        HttpEntity<?> requestEntity = new HttpEntity<>(createHeaders());
        
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, requestEntity, responseType);
            logger.debug("GET 请求成功: {}, 状态码: {}", url, responseEntity.getStatusCodeValue());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            handleApiException(e, "GET", url);
            return null; // 永远不会到达这里，handleApiException 会抛出异常
        }
    }

    /**
     * 执行 POST 请求
     * 
     * @param endpoint API 端点
     * @param requestBody 请求体
     * @param responseType 响应类型
     * @param <T> 请求数据类型
     * @param <R> 响应数据类型
     * @return API 响应
     */
    protected <T, R> R post(String endpoint, T requestBody, Class<R> responseType) {
        String url = buildUrl(endpoint);
        logger.debug("执行 POST 请求: {}", url);
        
        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, createHeaders());
        
        try {
            ResponseEntity<R> responseEntity = restTemplate.exchange(
                    url, HttpMethod.POST, requestEntity, responseType);
            logger.debug("POST 请求成功: {}, 状态码: {}", url, responseEntity.getStatusCodeValue());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            handleApiException(e, "POST", url);
            return null; // 永远不会到达这里，handleApiException 会抛出异常
        }
    }
    
    /**
     * 执行 POST 请求（带参数化类型）
     * 
     * @param endpoint API 端点
     * @param requestBody 请求体
     * @param responseType 响应类型引用
     * @param <T> 请求数据类型
     * @param <R> 响应数据类型
     * @return API 响应
     */
    protected <T, R> R post(String endpoint, T requestBody, ParameterizedTypeReference<R> responseType) {
        String url = buildUrl(endpoint);
        logger.debug("执行 POST 请求 (带参数化类型): {}", url);
        
        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, createHeaders());
        
        try {
            ResponseEntity<R> responseEntity = restTemplate.exchange(
                    url, HttpMethod.POST, requestEntity, responseType);
            logger.debug("POST 请求成功: {}, 状态码: {}", url, responseEntity.getStatusCodeValue());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            handleApiException(e, "POST", url);
            return null; // 永远不会到达这里，handleApiException 会抛出异常
        }
    }

    /**
     * 执行 PUT 请求
     * 
     * @param endpoint API 端点
     * @param requestBody 请求体
     * @param responseType 响应类型
     * @param <T> 请求数据类型
     * @param <R> 响应数据类型
     * @return API 响应
     */
    protected <T, R> R put(String endpoint, T requestBody, Class<R> responseType) {
        String url = buildUrl(endpoint);
        logger.debug("执行 PUT 请求: {}", url);
        
        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, createHeaders());
        
        try {
            ResponseEntity<R> responseEntity = restTemplate.exchange(
                    url, HttpMethod.PUT, requestEntity, responseType);
            logger.debug("PUT 请求成功: {}, 状态码: {}", url, responseEntity.getStatusCodeValue());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            handleApiException(e, "PUT", url);
            return null; // 永远不会到达这里，handleApiException 会抛出异常
        }
    }

    /**
     * 执行 DELETE 请求
     * 
     * @param endpoint API 端点
     * @return 响应状态码
     */
    protected HttpStatusCode delete(String endpoint) {
        String url = buildUrl(endpoint);
        logger.debug("执行 DELETE 请求: {}", url);
        
        HttpEntity<?> requestEntity = new HttpEntity<>(createHeaders());
        
        try {
            ResponseEntity<Void> responseEntity = restTemplate.exchange(
                    url, HttpMethod.DELETE, requestEntity, Void.class);
            logger.debug("DELETE 请求成功: {}, 状态码: {}", url, responseEntity.getStatusCodeValue());
            return responseEntity.getStatusCode();
        } catch (HttpStatusCodeException e) {
            handleApiException(e, "DELETE", url);
            return HttpStatus.INTERNAL_SERVER_ERROR; // 永远不会到达这里，handleApiException 会抛出异常
        }
    }

    /**
     * 创建 HTTP 请求头
     * 
     * @return HTTP 请求头
     */
    protected HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        // 简化请求头，只保留必要的配置
        // headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // 直接设置Authorization，不添加Bearer前缀，与Postman保持一致
        // headers.set("Authorization", "Bearer " + hubSpotConfig.getApiKey());
        return headers;
    }

    /**
     * 处理 API 异常
     * 
     * @param e HTTP 状态码异常
     * @param method HTTP 方法
     * @param url API URL
     */
    protected void handleApiException(HttpStatusCodeException e, String method, String url) {
        int statusCode = e.getStatusCode().value();
        String errorBody = e.getResponseBodyAsString();
        
        logger.error("HubSpot API 调用失败: {} {}, 状态码: {}, 错误信息: {}", 
                method, url, statusCode, errorBody);
        
        // 尝试解析错误信息
        String errorMessage = "HubSpot API 调用失败";
        try {
            HubSpotResponse<?> errorResponse = objectMapper.readValue(errorBody, HubSpotResponse.class);
            if (errorResponse.getMessage() != null) {
                errorMessage = errorResponse.getMessage();
            }
        } catch (Exception ex) {
            // 如果解析失败，使用原始错误信息
            errorMessage = errorBody;
        }
        
        throw new HubSpotApiException(errorMessage, statusCode, errorBody);
    }

    /**
     * 获取分页数据
     * 
     * @param endpoint API 端点
     * @param responseType 响应类型
     * @param <T> 数据项类型
     * @return 响应对象
     */
    protected <T> HubSpotResponse<T> getPaginatedData(String endpoint, 
                                                     ParameterizedTypeReference<HubSpotResponse<T>> responseType) {
        return get(endpoint, responseType);
    }
    
    /**
     * 获取对象的自定义属性列表
     * 
     * @param objectType 对象类型（如contacts、companies、deals等）
     * @return 属性列表响应
     */
    protected HubSpotPropertiesResponse getObjectProperties(String objectType) {
        String endpoint = "/crm/v3/properties/" + objectType;
        String url = buildUrl(endpoint);
        logger.debug("获取对象属性: {}, URL: {}", objectType, url);
        
        try {
            ResponseEntity<HubSpotPropertiesResponse> responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(createHeaders()), HubSpotPropertiesResponse.class);
            logger.debug("获取对象属性成功: {}, 状态码: {}", objectType, responseEntity.getStatusCodeValue());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            handleApiException(e, "GET", url);
            return null; // 永远不会到达这里，handleApiException 会抛出异常
        }
    }
}