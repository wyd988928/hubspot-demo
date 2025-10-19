package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.cache.HubSpotPropertiesCache;
import com.example.hubspotdemo.config.HubSpotConfig;
import com.example.hubspotdemo.model.Product;
import com.example.hubspotdemo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 产品服务实现类
 */
@Service
public class ProductServiceImpl extends GenericHubSpotServiceImpl<Product> implements ProductService {

    @Autowired
    public ProductServiceImpl(RestTemplate restTemplate, HubSpotConfig hubSpotConfig, ObjectMapper objectMapper,
                            HubSpotPropertiesCache propertiesCache) {
        super(restTemplate, hubSpotConfig, objectMapper, "products", Product.class, propertiesCache);
    }
}