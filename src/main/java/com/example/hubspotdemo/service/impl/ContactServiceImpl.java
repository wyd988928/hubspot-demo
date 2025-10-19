package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.config.HubSpotConfig;
import com.example.hubspotdemo.model.Contact;
import com.example.hubspotdemo.model.Deal;
import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 联系人服务实现类
 */
@Service
public class ContactServiceImpl extends GenericHubSpotServiceImpl<Contact> implements ContactService {

    @Autowired
    public ContactServiceImpl(RestTemplate restTemplate, HubSpotConfig hubSpotConfig, ObjectMapper objectMapper) {
        super(restTemplate, hubSpotConfig, objectMapper, "contacts", Contact.class);
    }

    /**
     * 根据联系人ID获取相关交易
     * 这是联系人特有的方法，未在通用接口中定义
     */
    @Override
    public HubSpotResponse<Deal> getDealsByContactId(String contactId) {
        logger.info("获取联系人 ID: {} 关联的交易", contactId);
        
        String endpoint = "/crm/v3/objects/contacts/" + contactId + "/associations/contact_to_deal/deals";
        return getPaginatedData(endpoint, 
                new ParameterizedTypeReference<HubSpotResponse<Deal>>() {});
    }
}