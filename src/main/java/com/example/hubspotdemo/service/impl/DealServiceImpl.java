package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.cache.HubSpotPropertiesCache;
import com.example.hubspotdemo.config.HubSpotConfig;
import com.example.hubspotdemo.model.Deal;
import com.example.hubspotdemo.service.DealService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 交易服务实现类
 */
@Service
public class DealServiceImpl extends GenericHubSpotServiceImpl<Deal> implements DealService {

    @Autowired
    public DealServiceImpl(RestTemplate restTemplate, HubSpotConfig hubSpotConfig, ObjectMapper objectMapper,
                         HubSpotPropertiesCache propertiesCache) {
        super(restTemplate, hubSpotConfig, objectMapper, "deals", Deal.class, propertiesCache);
    }
}