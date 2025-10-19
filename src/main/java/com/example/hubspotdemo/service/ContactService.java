package com.example.hubspotdemo.service;

import com.example.hubspotdemo.model.Contact;
import com.example.hubspotdemo.model.Deal;
import com.example.hubspotdemo.model.HubSpotResponse;

/**
 * 联系人服务接口
 */
public interface ContactService extends GenericHubSpotService<Contact> {
    
    /**
     * 获取联系人关联的交易
     */
    HubSpotResponse<Deal> getDealsByContactId(String contactId);
}