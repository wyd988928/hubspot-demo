package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.config.HubSpotConfig;
import com.example.hubspotdemo.model.Deal;
import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.service.DealService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 交易服务实现类
 */
@Service
public class DealServiceImpl extends HubSpotBaseService implements DealService {

    private static final Logger logger = LoggerFactory.getLogger(DealServiceImpl.class);
    private static final String DEALS_ENDPOINT = "/crm/v3/objects/deals";
    private static final String DEALS_SEARCH_ENDPOINT = "/crm/v3/objects/deals/search";
    private static final String CONTACTS_DEALS_ASSOCIATIONS_ENDPOINT = "/crm/v3/objects/contacts/%s/associations/deals";

    public DealServiceImpl(RestTemplate restTemplate, HubSpotConfig hubSpotConfig, ObjectMapper objectMapper) {
        super(restTemplate, hubSpotConfig, objectMapper);
    }

    @Override
    public HubSpotResponse<Deal> getAllDeals(List<String> properties, int limit) {
        logger.info("获取所有交易，属性: {}, 限制: {}", properties, limit);
        
        StringBuilder urlBuilder = new StringBuilder(DEALS_ENDPOINT);
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
                new ParameterizedTypeReference<HubSpotResponse<Deal>>() {});
    }

    @Override
    public HubSpotResponse<Deal> getDealsWithPagination(List<String> properties, int limit, String after) {
        logger.info("分页获取交易，游标: {}, 属性: {}, 限制: {}", after, properties, limit);
        
        StringBuilder urlBuilder = new StringBuilder(DEALS_ENDPOINT);
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
                new ParameterizedTypeReference<HubSpotResponse<Deal>>() {});
    }

    @Override
    public Deal getDealById(String dealId) {
        logger.info("根据 ID 获取交易: {}", dealId);
        
        String endpoint = DEALS_ENDPOINT + "/" + dealId;
        return get(endpoint, Deal.class);
    }

    @Override
    public Deal createDeal(Map<String, Object> properties) {
        logger.info("创建交易: {}", properties);
        
        Map<String, Object> requestBody = Map.of(
                "properties", properties
        );
        
        return post(DEALS_ENDPOINT, requestBody, Deal.class);
    }

    @Override
    public Deal updateDeal(String dealId, Map<String, Object> properties) {
        logger.info("更新交易: {}, 属性: {}", dealId, properties);
        
        Map<String, Object> requestBody = Map.of(
                "properties", properties
        );
        
        String endpoint = DEALS_ENDPOINT + "/" + dealId;
        return put(endpoint, requestBody, Deal.class);
    }

    @Override
    public boolean deleteDeal(String dealId) {
        if (dealId == null || dealId.trim().isEmpty()) {
            logger.warn("交易 ID 为空，无法删除交易");
            return false;
        }
        
        logger.info("删除交易: {}", dealId);
        
        try {
            String endpoint = DEALS_ENDPOINT + "/" + dealId;
            return delete(endpoint).is2xxSuccessful();
        } catch (Exception e) {
            logger.error("删除交易时出错，交易 ID: {}", dealId, e);
            return false;
        }
    }

    @Override
    public HubSpotResponse<Deal> searchDeals(Object filterGroups, List<String> properties, int limit) {
        logger.info("搜索交易，过滤条件: {}, 属性: {}, 限制: {}", filterGroups, properties, limit);
        
        Map<String, Object> requestBody = Map.of(
                "filterGroups", filterGroups,
                "properties", properties,
                "limit", limit
        );
        
        return post(DEALS_SEARCH_ENDPOINT, requestBody, 
                new ParameterizedTypeReference<HubSpotResponse<Deal>>() {});
    }

    @Override
    public HubSpotResponse<Deal> getDealsByContactId(String contactId, List<String> properties) {
        logger.info("获取联系人关联的交易，联系人 ID: {}, 属性: {}", contactId, properties);
        
        String endpoint = String.format(CONTACTS_DEALS_ASSOCIATIONS_ENDPOINT, contactId);
        
        StringBuilder urlBuilder = new StringBuilder(endpoint);
        if (properties != null && !properties.isEmpty()) {
            urlBuilder.append("?properties=");
            for (int i = 0; i < properties.size(); i++) {
                urlBuilder.append(properties.get(i));
                if (i < properties.size() - 1) {
                    urlBuilder.append(",");
                }
            }
        }
        
        return getPaginatedData(urlBuilder.toString(), 
                new ParameterizedTypeReference<HubSpotResponse<Deal>>() {});
    }
    
    @Override
    public List<Map<String, Object>> getDealProperties() {
        logger.info("获取交易自定义属性列表");
        return getObjectProperties("deals");
    }
}