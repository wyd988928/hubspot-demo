package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.model.Contact;
import com.example.hubspotdemo.model.Deal;
import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 联系人服务实现类
 */
@Service
public class ContactServiceImpl extends HubSpotBaseService implements ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);
    private static final String CONTACTS_ENDPOINT = "/crm/v3/objects/contacts";
    private static final String CONTACTS_SEARCH_ENDPOINT = "/crm/v3/objects/contacts/search";

    public ContactServiceImpl(RestTemplate restTemplate, com.example.hubspotdemo.config.HubSpotConfig hubSpotConfig, ObjectMapper objectMapper) {
        super(restTemplate, hubSpotConfig, objectMapper);
    }

    @Override
    public HubSpotResponse<Contact> getAllContacts(List<String> properties, int limit) {
        logger.info("获取所有联系人，属性: {}, 限制: {}", properties, limit);
        
        StringBuilder urlBuilder = new StringBuilder(CONTACTS_ENDPOINT);
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
                new ParameterizedTypeReference<HubSpotResponse<Contact>>() {});
    }

    @Override
    public HubSpotResponse<Contact> getContactsWithPagination(List<String> properties, int limit, String after) {
        logger.info("分页获取联系人，游标: {}, 属性: {}, 限制: {}", after, properties, limit);
        
        StringBuilder urlBuilder = new StringBuilder(CONTACTS_ENDPOINT);
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
                new ParameterizedTypeReference<HubSpotResponse<Contact>>() {});
    }

    @Override
    public Contact getContactById(String contactId) {
        logger.info("根据 ID 获取联系人: {}", contactId);
        
        String endpoint = CONTACTS_ENDPOINT + "/" + contactId;
        return get(endpoint, Contact.class);
    }

    @Override
    public Contact createContact(Map<String, Object> properties) {
        logger.info("创建联系人: {}", properties);
        
        Map<String, Object> requestBody = Map.of(
                "properties", properties
        );
        
        return post(CONTACTS_ENDPOINT, requestBody, Contact.class);
    }

    @Override
    public Contact updateContact(String contactId, Map<String, Object> properties) {
        logger.info("更新联系人: {}, 属性: {}", contactId, properties);
        
        Map<String, Object> requestBody = Map.of(
                "properties", properties
        );
        
        String endpoint = CONTACTS_ENDPOINT + "/" + contactId;
        return put(endpoint, requestBody, Contact.class);
    }

    @Override
    public boolean deleteContact(String contactId) {
        logger.info("删除联系人: {}", contactId);
        
        String endpoint = CONTACTS_ENDPOINT + "/" + contactId;
        return delete(endpoint).is2xxSuccessful();
    }

    @Override
    public HubSpotResponse<Contact> searchContacts(Object filterGroups, List<String> properties, int limit) {
        logger.info("搜索联系人，过滤条件: {}, 属性: {}, 限制: {}", filterGroups, properties, limit);
        
        Map<String, Object> requestBody = Map.of(
                "filterGroups", filterGroups,
                "properties", properties,
                "limit", limit
        );
        
        return post(CONTACTS_SEARCH_ENDPOINT, requestBody, 
                new ParameterizedTypeReference<HubSpotResponse<Contact>>() {});
    }

    @Override
    public HubSpotResponse<Deal> getDealsByContactId(String contactId) {
        logger.info("根据联系人ID获取相关交易: {}", contactId);
        
        String endpoint = "/crm/v3/objects/contacts/" + contactId + "/associations/deals";
        return getPaginatedData(endpoint, 
                new ParameterizedTypeReference<HubSpotResponse<Deal>>() {});
    }
    
    @Override
    public List<Map<String, Object>> getContactProperties() {
        logger.info("获取联系人自定义属性列表");
        return getObjectProperties("contacts");
    }
}