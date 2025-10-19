package com.example.hubspotdemo.service;

import com.example.hubspotdemo.model.Company;
import com.example.hubspotdemo.model.HubSpotResponse;

import java.util.Map;

/**
 * 公司服务接口，提供对HubSpot公司API的访问
 */
public interface CompanyService extends GenericHubSpotService<Company> {
    
    /**
     * 获取公司关联的联系人
     */
    HubSpotResponse<Map<String, Object>> getCompanyContacts(String companyId);
}