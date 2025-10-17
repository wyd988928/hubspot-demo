package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.model.Company;
import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 公司服务实现类
 */
@Service
public class CompanyServiceImpl extends HubSpotBaseService implements CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private static final String COMPANIES_ENDPOINT = "/crm/v3/objects/companies";
    private static final String COMPANIES_SEARCH_ENDPOINT = "/crm/v3/objects/companies/search";
    private static final String COMPANIES_CONTACTS_ASSOCIATIONS_ENDPOINT = "/crm/v3/objects/companies/%s/associations/contacts";

    public CompanyServiceImpl(RestTemplate restTemplate, com.example.hubspotdemo.config.HubSpotConfig hubSpotConfig, ObjectMapper objectMapper) {
        super(restTemplate, hubSpotConfig, objectMapper);
    }

    @Override
    public HubSpotResponse<Company> getAllCompanies(List<String> properties, int limit) {
        logger.info("获取所有公司，属性: {}, 限制: {}", properties, limit);
        
        StringBuilder urlBuilder = new StringBuilder(COMPANIES_ENDPOINT);
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
                new ParameterizedTypeReference<HubSpotResponse<Company>>() {});
    }

    @Override
    public HubSpotResponse<Company> getCompaniesWithPagination(List<String> properties, int limit, String after) {
        logger.info("分页获取公司，游标: {}, 属性: {}, 限制: {}", after, properties, limit);
        
        StringBuilder urlBuilder = new StringBuilder(COMPANIES_ENDPOINT);
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
                new ParameterizedTypeReference<HubSpotResponse<Company>>() {});
    }

    @Override
    public Company getCompanyById(String companyId) {
        logger.info("根据 ID 获取公司: {}", companyId);
        
        String endpoint = COMPANIES_ENDPOINT + "/" + companyId;
        return get(endpoint, Company.class);
    }

    @Override
    public Company createCompany(Map<String, Object> properties) {
        logger.info("创建公司: {}", properties);
        
        Map<String, Object> requestBody = Map.of(
                "properties", properties
        );
        
        return post(COMPANIES_ENDPOINT, requestBody, Company.class);
    }

    @Override
    public Company updateCompany(String companyId, Map<String, Object> properties) {
        logger.info("更新公司: {}, 属性: {}", companyId, properties);
        
        Map<String, Object> requestBody = Map.of(
                "properties", properties
        );
        
        String endpoint = COMPANIES_ENDPOINT + "/" + companyId;
        return put(endpoint, requestBody, Company.class);
    }

    @Override
    public boolean deleteCompany(String companyId) {
        logger.info("删除公司: {}", companyId);
        
        String endpoint = COMPANIES_ENDPOINT + "/" + companyId;
        delete(endpoint);
        return true;
    }

    @Override
    public HubSpotResponse<Company> searchCompanies(Object filterGroups, List<String> properties, int limit) {
        logger.info("搜索公司，过滤条件: {}, 属性: {}, 限制: {}", filterGroups, properties, limit);
        
        Map<String, Object> requestBody = Map.of(
                "filterGroups", filterGroups,
                "properties", properties != null ? properties : List.of(),
                "limit", limit
        );
        
        return post(COMPANIES_SEARCH_ENDPOINT, requestBody, 
                new ParameterizedTypeReference<HubSpotResponse<Company>>() {});
    }

    @Override
    public HubSpotResponse<Map<String, Object>> getCompanyContacts(String companyId) {
        logger.info("获取公司 {} 的联系人", companyId);
        
        String endpoint = String.format(COMPANIES_CONTACTS_ASSOCIATIONS_ENDPOINT, companyId);
        return get(endpoint, new ParameterizedTypeReference<HubSpotResponse<Map<String, Object>>>() {});
    }
    
    @Override
    public List<Map<String, Object>> getCompanyProperties() {
        logger.info("获取公司自定义属性列表");
        return getObjectProperties("companies");
    }
}