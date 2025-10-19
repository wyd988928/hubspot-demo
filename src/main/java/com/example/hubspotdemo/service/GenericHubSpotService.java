package com.example.hubspotdemo.service;

import com.example.hubspotdemo.model.HubSpotObject;
import com.example.hubspotdemo.model.HubSpotPropertiesResponse;
import com.example.hubspotdemo.model.HubSpotResponse;

import java.util.List;
import java.util.Map;

/**
 * 通用 HubSpot 服务接口，定义所有 HubSpot 对象共享的操作方法
 *
 * @param <T> HubSpot 对象类型
 */
public interface GenericHubSpotService<T extends HubSpotObject> {

    /**
     * 获取所有对象
     * 
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @return 对象列表响应
     */
    HubSpotResponse<T> getAllObjects(List<String> properties, int limit);

    /**
     * 分页获取对象
     * 
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @param after 分页游标
     * @return 对象列表响应
     */
    HubSpotResponse<T> getObjectsWithPagination(List<String> properties, int limit, String after);

    /**
     * 根据ID获取对象
     * 
     * @param objectId 对象ID
     * @return 对象实例
     */
    T getObjectById(String objectId);

    /**
     * 创建对象
     * 
     * @param properties 对象属性
     * @return 创建的对象实例
     */
    T createObject(Map<String, Object> properties);

    /**
     * 更新对象
     * 
     * @param objectId 对象ID
     * @param properties 要更新的属性
     * @return 更新后的对象实例
     */
    T updateObject(String objectId, Map<String, Object> properties);

    /**
     * 删除对象
     * 
     * @param objectId 对象ID
     * @return 是否删除成功
     */
    boolean deleteObject(String objectId);

    /**
     * 搜索对象
     * 
     * @param filterGroups 过滤条件组
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @return 搜索结果响应
     */
    HubSpotResponse<T> searchObjects(Object filterGroups, List<String> properties, int limit);
    
    /**
     * 获取对象的自定义属性列表
     * 
     * @return 属性列表响应
     */
    HubSpotPropertiesResponse getObjectProperties();
}