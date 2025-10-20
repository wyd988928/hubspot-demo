package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.cache.HubSpotPropertiesCache;
import com.example.hubspotdemo.config.HubSpotConfig;
import com.example.hubspotdemo.model.HubSpotObject;
import com.example.hubspotdemo.model.HubSpotProperty;
import com.example.hubspotdemo.model.HubSpotPropertiesResponse;
import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.service.GenericHubSpotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用 HubSpot 服务实现基类，提供所有 HubSpot 对象共享操作的默认实现
 *
 * @param <T> HubSpot 对象类型
 */
@Service
public abstract class GenericHubSpotServiceImpl<T extends HubSpotObject> extends HubSpotBaseService implements GenericHubSpotService<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final String baseEndpoint;
    private final String searchEndpoint;
    private final Class<T> objectType;
    private final HubSpotPropertiesCache propertiesCache;

    protected GenericHubSpotServiceImpl(RestTemplate restTemplate, HubSpotConfig hubSpotConfig, 
                                      ObjectMapper objectMapper, String objectTypeName, Class<T> objectType,
                                      HubSpotPropertiesCache propertiesCache) {
        super(restTemplate, hubSpotConfig, objectMapper);
        this.baseEndpoint = "/crm/v3/objects/" + objectTypeName;
        this.searchEndpoint = "/crm/v3/objects/" + objectTypeName + "/search";
        this.objectType = objectType;
        this.propertiesCache = propertiesCache;
    }

    @Override
    public HubSpotResponse<T> getAllObjects(List<String> properties, int limit) {
        String objectTypeName = baseEndpoint.substring(baseEndpoint.lastIndexOf('/') + 1);
        logger.info("获取所有 {}，属性: {}, 限制: {}", objectTypeName, properties, limit);
        
        // 如果properties为空，则从缓存中获取所有属性
        List<String> effectiveProperties = properties;
        if (properties == null || properties.isEmpty()) {
            logger.debug("属性列表为空，从缓存中获取所有可用属性");
            HubSpotPropertiesResponse propertiesResponse = propertiesCache.getPropertiesByType(objectTypeName);
            if (propertiesResponse != null && propertiesResponse.getResults() != null) {
                effectiveProperties = propertiesResponse.getResults().stream()
                        .map(HubSpotProperty::getName)
                        .toList();
                logger.debug("从缓存中获取到 {} 个属性", effectiveProperties.size());
            }
        }
        
        StringBuilder urlBuilder = new StringBuilder(baseEndpoint);
        urlBuilder.append("?limit=").append(limit);
        
        if (effectiveProperties != null && !effectiveProperties.isEmpty()) {
            urlBuilder.append("&properties=");
            for (int i = 0; i < effectiveProperties.size(); i++) {
                urlBuilder.append(effectiveProperties.get(i));
                if (i < effectiveProperties.size() - 1) {
                    urlBuilder.append(",");
                }
            }
        }
        
        return getPaginatedData(urlBuilder.toString(), 
                new ParameterizedTypeReference<HubSpotResponse<T>>() {});
    }

    @Override
    public HubSpotResponse<T> getObjectsWithPagination(List<String> properties, int limit, String after) {
        logger.info("分页获取 {}，属性: {}, 限制: {}, 游标: {}", 
                baseEndpoint.substring(baseEndpoint.lastIndexOf('/') + 1), properties, limit, after);
        
        StringBuilder urlBuilder = new StringBuilder(baseEndpoint);
        urlBuilder.append("?limit=").append(limit);
        
        if (after != null) {
            urlBuilder.append("&after=").append(after);
        }
        
        if (properties != null && !properties.isEmpty()) {
            urlBuilder.append("&properties=");
            for (int i = 0; i < properties.size(); i++) {
                urlBuilder.append(properties.get(i));
                if (i < properties.size() - 1) {
                    urlBuilder.append(",");
                }
            }
        }
        
        return getPaginatedData(urlBuilder.toString(), 
                new ParameterizedTypeReference<HubSpotResponse<T>>() {});
    }

    @Override
    public T getObjectById(String objectId) {
        logger.info("获取 {} ID: {}", baseEndpoint.substring(baseEndpoint.lastIndexOf('/') + 1), objectId);
        String objectTypeName = baseEndpoint.substring(baseEndpoint.lastIndexOf('/') + 1);

        // 如果properties为空，则从缓存中获取所有属性
        List<String> effectiveProperties = null;
        logger.debug("属性列表为空，从缓存中获取所有可用属性");
        HubSpotPropertiesResponse propertiesResponse = propertiesCache.getPropertiesByType(objectTypeName);
        if (propertiesResponse != null && propertiesResponse.getResults() != null) {
            effectiveProperties = propertiesResponse.getResults().stream()
                    .map(HubSpotProperty::getName)
                    .toList();
            logger.debug("从缓存中获取到 {} 个属性", effectiveProperties.size());
        }

        String endpoint = baseEndpoint + "/" + objectId;
        StringBuilder urlBuilder = new StringBuilder(endpoint);

        if (effectiveProperties != null && !effectiveProperties.isEmpty()) {
            urlBuilder.append("?properties=");
            for (int i = 0; i < effectiveProperties.size(); i++) {
                urlBuilder.append(effectiveProperties.get(i));
                if (i < effectiveProperties.size() - 1) {
                    urlBuilder.append(",");
                }
            }
        }
        return get(urlBuilder.toString(), objectType);
    }

    @Override
    public T createObject(Map<String, Object> properties) {
        logger.info("创建 {}", baseEndpoint.substring(baseEndpoint.lastIndexOf('/') + 1));
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("properties", properties);
        
        return post(baseEndpoint, requestBody, objectType);
    }

    @Override
    public T updateObject(String objectId, Map<String, Object> properties) {
        logger.info("更新 {} ID: {}", baseEndpoint.substring(baseEndpoint.lastIndexOf('/') + 1), objectId);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("properties", properties);
        
        String endpoint = baseEndpoint + "/" + objectId;
        return put(endpoint, requestBody, objectType);
    }

    @Override
    public boolean deleteObject(String objectId) {
        logger.info("删除 {} ID: {}", baseEndpoint.substring(baseEndpoint.lastIndexOf('/') + 1), objectId);
        
        String endpoint = baseEndpoint + "/" + objectId;
        delete(endpoint);
        return true;
    }

    @Override
    public HubSpotResponse<T> searchObjects(Object filterGroups, List<String> properties, int limit) {
        logger.info("搜索 {}，过滤条件: {}, 属性: {}, 限制: {}", 
                baseEndpoint.substring(baseEndpoint.lastIndexOf('/') + 1), filterGroups, properties, limit);
        
        Map<String, Object> requestBody = Map.of(
                "filterGroups", filterGroups,
                "properties", properties != null ? properties : List.of(),
                "limit", limit
        );
        
        return post(searchEndpoint, requestBody, 
                new ParameterizedTypeReference<HubSpotResponse<T>>() {});
    }
    
    @Override
    public HubSpotPropertiesResponse getObjectProperties() {
        String objectTypeName = baseEndpoint.substring(baseEndpoint.lastIndexOf('/') + 1);
        logger.info("获取 {} 自定义属性列表", objectTypeName);
        return getObjectProperties(objectTypeName);
    }
}