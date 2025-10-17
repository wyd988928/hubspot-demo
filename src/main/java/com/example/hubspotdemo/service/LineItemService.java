package com.example.hubspotdemo.service;

import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.model.LineItem;

import java.util.List;
import java.util.Map;

/**
 * 行项目服务接口
 */
public interface LineItemService {

    /**
     * 获取所有行项目
     * 
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @return 行项目列表响应
     */
    HubSpotResponse<LineItem> getAllLineItems(List<String> properties, int limit);

    /**
     * 分页获取行项目
     * 
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @param after 分页游标
     * @return 行项目列表响应
     */
    HubSpotResponse<LineItem> getLineItemsWithPagination(List<String> properties, int limit, String after);

    /**
     * 根据ID获取行项目
     * 
     * @param lineItemId 行项目ID
     * @return 行项目对象
     */
    LineItem getLineItemById(String lineItemId);

    /**
     * 创建行项目
     * 
     * @param properties 行项目属性
     * @return 创建的行项目对象
     */
    LineItem createLineItem(Map<String, Object> properties);

    /**
     * 更新行项目
     * 
     * @param lineItemId 行项目ID
     * @param properties 要更新的属性
     * @return 更新后的行项目对象
     */
    LineItem updateLineItem(String lineItemId, Map<String, Object> properties);

    /**
     * 删除行项目
     * 
     * @param lineItemId 行项目ID
     * @return 是否删除成功
     */
    boolean deleteLineItem(String lineItemId);

    /**
     * 搜索行项目
     * 
     * @param filterGroups 过滤条件组
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @return 搜索结果响应
     */
    HubSpotResponse<LineItem> searchLineItems(Object filterGroups, List<String> properties, int limit);

    /**
     * 获取与交易关联的行项目
     * 
     * @param dealId 交易ID
     * @return 关联的行项目响应
     */
    HubSpotResponse<Map<String, Object>> getDealLineItems(String dealId);
    
    /**
     * 获取行项目的自定义属性列表
     * 
     * @return 属性列表
     */
    List<Map<String, Object>> getLineItemProperties();
}