package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.config.HubSpotConfig;
import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.model.LineItem;
import com.example.hubspotdemo.service.LineItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 行项目服务实现类
 */
@Service
public class LineItemServiceImpl extends GenericHubSpotServiceImpl<LineItem> implements LineItemService {

    @Autowired
    public LineItemServiceImpl(RestTemplate restTemplate, HubSpotConfig hubSpotConfig, ObjectMapper objectMapper) {
        super(restTemplate, hubSpotConfig, objectMapper, "line_items", LineItem.class);
    }

    /**
     * 获取与交易关联的行项目
     * 这是行项目特有的方法，未在通用接口中定义
     */
    @Override
    public HubSpotResponse<Map<String, Object>> getDealLineItems(String dealId) {
        logger.info("获取交易 ID: {} 关联的行项目", dealId);
        
        String endpoint = "/crm/v3/objects/deals/" + dealId + "/associations/deal_to_line_item/line_items";
        return getPaginatedData(endpoint, 
                new ParameterizedTypeReference<HubSpotResponse<Map<String, Object>>>() {});
    }
}