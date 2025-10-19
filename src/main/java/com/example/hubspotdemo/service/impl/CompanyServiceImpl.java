package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.cache.HubSpotPropertiesCache;
import com.example.hubspotdemo.config.HubSpotConfig;
import com.example.hubspotdemo.model.Company;
import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * 公司服务实现
 */
@Service
public class CompanyServiceImpl extends GenericHubSpotServiceImpl<Company> implements CompanyService {

    @Autowired
    public CompanyServiceImpl(RestTemplate restTemplate, HubSpotConfig hubSpotConfig, ObjectMapper objectMapper,
                           HubSpotPropertiesCache propertiesCache) {
        super(restTemplate, hubSpotConfig, objectMapper, "companies", Company.class, propertiesCache);
    }

    @Override
    public HubSpotResponse<Map<String, Object>> getCompanyContacts(String companyId) {
        String url = String.format("%s/crm/v3/objects/%s/%s/contacts", 
                hubSpotConfig.getBaseUrl(), "companies", companyId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(hubSpotConfig.getApiKey());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<HubSpotResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, HubSpotResponse.class);
        
        return (HubSpotResponse<Map<String, Object>>) response.getBody();
    }
}