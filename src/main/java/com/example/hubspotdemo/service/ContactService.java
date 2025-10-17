package com.example.hubspotdemo.service;

import com.example.hubspotdemo.model.Contact;
import com.example.hubspotdemo.model.Deal;
import com.example.hubspotdemo.model.HubSpotResponse;
import java.util.List;
import java.util.Map;

/**
 * 联系人服务接口，定义与 HubSpot 联系人相关的操作
 */
public interface ContactService {

    /**
     * 获取所有联系人
     * 
     * @param properties 要获取的联系人属性列表
     * @param limit 每页记录数
     * @return 联系人列表响应
     */
    HubSpotResponse<Contact> getAllContacts(List<String> properties, int limit);

    /**
     * 获取分页联系人数据
     * 
     * @param properties 要获取的联系人属性列表
     * @param limit 每页记录数
     * @param after 分页游标
     * @return 联系人列表响应
     */
    HubSpotResponse<Contact> getContactsWithPagination(List<String> properties, int limit, String after);

    /**
     * 根据 ID 获取联系人
     * 
     * @param contactId 联系人 ID
     * @return 联系人对象
     */
    Contact getContactById(String contactId);

    /**
     * 创建联系人
     * 
     * @param properties 联系人属性
     * @return 创建的联系人对象
     */
    Contact createContact(Map<String, Object> properties);

    /**
     * 更新联系人
     * 
     * @param contactId 联系人 ID
     * @param properties 联系人属性
     * @return 更新后的联系人对象
     */
    Contact updateContact(String contactId, Map<String, Object> properties);

    /**
     * 删除联系人
     * 
     * @param contactId 联系人 ID
     * @return 是否删除成功
     */
    boolean deleteContact(String contactId);

    /**
     * 搜索联系人
     * 
     * @param filterGroups 过滤条件组
     * @param properties 要获取的联系人属性列表
     * @param limit 每页记录数
     * @return 联系人列表响应
     */
    HubSpotResponse<Contact> searchContacts(Object filterGroups, List<String> properties, int limit);
    
    /**
     * 根据联系人ID获取相关交易
     * 
     * @param contactId 联系人ID
     * @return 交易列表响应
     */
    HubSpotResponse<Deal> getDealsByContactId(String contactId);
    
    /**
     * 获取联系人的自定义属性列表
     * 
     * @return 属性列表
     */
    List<Map<String, Object>> getContactProperties();
}