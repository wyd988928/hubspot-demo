package com.example.hubspotdemo.service;

import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.model.LineItem;

import java.util.Map;

/**
 * 行项目服务接口，提供对HubSpot行项目API的访问
 */
public interface LineItemService extends GenericHubSpotService<LineItem> {
    
    /**
     * 获取交易关联的行项目
     */
    HubSpotResponse<Map<String, Object>> getDealLineItems(String dealId);
}