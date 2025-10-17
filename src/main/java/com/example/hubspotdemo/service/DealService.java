package com.example.hubspotdemo.service;

import com.example.hubspotdemo.model.Deal;
import com.example.hubspotdemo.model.HubSpotResponse;
import java.util.List;
import java.util.Map;

/**
 * 交易服务接口，定义与 HubSpot 交易相关的操作
 */
public interface DealService {

    /**
     * 获取所有交易
     * 
     * @param properties 要获取的交易属性列表
     * @param limit 每页记录数
     * @return 交易列表响应
     */
    HubSpotResponse<Deal> getAllDeals(List<String> properties, int limit);

    /**
     * 获取分页交易数据
     * 
     * @param properties 要获取的交易属性列表
     * @param limit 每页记录数
     * @param after 分页游标
     * @return 交易列表响应
     */
    HubSpotResponse<Deal> getDealsWithPagination(List<String> properties, int limit, String after);

    /**
     * 根据 ID 获取交易
     * 
     * @param dealId 交易 ID
     * @return 交易对象
     */
    Deal getDealById(String dealId);

    /**
     * 创建交易
     * 
     * @param properties 交易属性
     * @return 创建的交易对象
     */
    Deal createDeal(Map<String, Object> properties);

    /**
     * 更新交易
     * 
     * @param dealId 交易 ID
     * @param properties 交易属性
     * @return 更新后的交易对象
     */
    Deal updateDeal(String dealId, Map<String, Object> properties);

    /**
     * 删除交易
     * 
     * @param dealId 交易 ID
     * @return 是否删除成功
     */
    boolean deleteDeal(String dealId);

    /**
     * 搜索交易
     * 
     * @param filterGroups 过滤条件组
     * @param properties 要获取的交易属性列表
     * @param limit 每页记录数
     * @return 交易列表响应
     */
    HubSpotResponse<Deal> searchDeals(Object filterGroups, List<String> properties, int limit);

    /**
     * 获取与联系人关联的交易
     * 
     * @param contactId 联系人 ID
     * @param properties 要获取的交易属性列表
     * @return 交易列表响应
     */
    HubSpotResponse<Deal> getDealsByContactId(String contactId, List<String> properties);
    
    /**
     * 获取交易的自定义属性列表
     * 
     * @return 属性列表
     */
    List<Map<String, Object>> getDealProperties();
}