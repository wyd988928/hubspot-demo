package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.model.LineItem;
import com.example.hubspotdemo.service.LineItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 行项目服务实现类
 */
@Service
public class LineItemServiceImpl extends HubSpotBaseService implements LineItemService {

    private static final Logger logger = LoggerFactory.getLogger(LineItemServiceImpl.class);
    private static final String LINE_ITEMS_ENDPOINT = "/crm/v3/objects/line_items";
    private static final String LINE_ITEMS_SEARCH_ENDPOINT = "/crm/v3/objects/line_items/search";
    private static final String DEALS_LINE_ITEMS_ASSOCIATIONS_ENDPOINT = "/crm/v3/objects/deals/%s/associations/line_items";

    public LineItemServiceImpl(RestTemplate restTemplate, com.example.hubspotdemo.config.HubSpotConfig hubSpotConfig, ObjectMapper objectMapper) {
        super(restTemplate, hubSpotConfig, objectMapper);
    }

    @Override
    public HubSpotResponse<LineItem> getAllLineItems(List<String> properties, int limit) {
        logger.info("获取所有行项目，属性: {}, 限制: {}", properties, limit);
        
        StringBuilder urlBuilder = new StringBuilder(LINE_ITEMS_ENDPOINT);
        urlBuilder.append("?limit=").append(limit);
        
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
                new ParameterizedTypeReference<HubSpotResponse<LineItem>>() {});
    }

    @Override
    public HubSpotResponse<LineItem> getLineItemsWithPagination(List<String> properties, int limit, String after) {
        logger.info("分页获取行项目，游标: {}, 属性: {}, 限制: {}", after, properties, limit);
        
        StringBuilder urlBuilder = new StringBuilder(LINE_ITEMS_ENDPOINT);
        urlBuilder.append("?limit=").append(limit);
        urlBuilder.append("&after=").append(after);
        
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
                new ParameterizedTypeReference<HubSpotResponse<LineItem>>() {});
    }

    @Override
    public LineItem getLineItemById(String lineItemId) {
        logger.info("根据 ID 获取行项目: {}", lineItemId);
        
        String endpoint = LINE_ITEMS_ENDPOINT + "/" + lineItemId;
        return get(endpoint, LineItem.class);
    }

    @Override
    public LineItem createLineItem(Map<String, Object> properties) {
        logger.info("创建行项目: {}", properties);
        
        Map<String, Object> requestBody = Map.of(
                "properties", properties
        );
        
        return post(LINE_ITEMS_ENDPOINT, requestBody, LineItem.class);
    }

    @Override
    public LineItem updateLineItem(String lineItemId, Map<String, Object> properties) {
        logger.info("更新行项目: {}, 属性: {}", lineItemId, properties);
        
        Map<String, Object> requestBody = Map.of(
                "properties", properties
        );
        
        String endpoint = LINE_ITEMS_ENDPOINT + "/" + lineItemId;
        return put(endpoint, requestBody, LineItem.class);
    }

    @Override
    public boolean deleteLineItem(String lineItemId) {
        logger.info("删除行项目: {}", lineItemId);
        
        String endpoint = LINE_ITEMS_ENDPOINT + "/" + lineItemId;
        delete(endpoint);
        return true;
    }

    @Override
    public HubSpotResponse<LineItem> searchLineItems(Object filterGroups, List<String> properties, int limit) {
        logger.info("搜索行项目，过滤条件: {}, 属性: {}, 限制: {}", filterGroups, properties, limit);
        
        Map<String, Object> requestBody = Map.of(
                "filterGroups", filterGroups,
                "properties", properties != null ? properties : List.of(),
                "limit", limit
        );
        
        return post(LINE_ITEMS_SEARCH_ENDPOINT, requestBody, 
                new ParameterizedTypeReference<HubSpotResponse<LineItem>>() {});
    }

    @Override
    public HubSpotResponse<Map<String, Object>> getDealLineItems(String dealId) {
        logger.info("获取交易 {} 的行项目", dealId);
        
        String endpoint = String.format(DEALS_LINE_ITEMS_ASSOCIATIONS_ENDPOINT, dealId);
        return get(endpoint, new ParameterizedTypeReference<HubSpotResponse<Map<String, Object>>>() {});
    }
    
    @Override
    public List<Map<String, Object>> getLineItemProperties() {
        logger.info("获取行项目自定义属性列表");
        return getObjectProperties("line_items");
    }
}