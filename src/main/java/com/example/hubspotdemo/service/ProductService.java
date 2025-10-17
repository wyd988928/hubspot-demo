package com.example.hubspotdemo.service;

import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.model.Product;

import java.util.List;
import java.util.Map;

/**
 * 产品服务接口
 */
public interface ProductService {

    /**
     * 获取所有产品
     * 
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @return 产品列表响应
     */
    HubSpotResponse<Product> getAllProducts(List<String> properties, int limit);

    /**
     * 分页获取产品
     * 
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @param after 分页游标
     * @return 产品列表响应
     */
    HubSpotResponse<Product> getProductsWithPagination(List<String> properties, int limit, String after);

    /**
     * 根据ID获取产品
     * 
     * @param productId 产品ID
     * @return 产品对象
     */
    Product getProductById(String productId);

    /**
     * 创建产品
     * 
     * @param properties 产品属性
     * @return 创建的产品对象
     */
    Product createProduct(Map<String, Object> properties);

    /**
     * 更新产品
     * 
     * @param productId 产品ID
     * @param properties 要更新的属性
     * @return 更新后的产品对象
     */
    Product updateProduct(String productId, Map<String, Object> properties);

    /**
     * 删除产品
     * 
     * @param productId 产品ID
     * @return 是否删除成功
     */
    boolean deleteProduct(String productId);

    /**
     * 搜索产品
     * 
     * @param filterGroups 过滤条件组
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @return 搜索结果响应
     */
    HubSpotResponse<Product> searchProducts(Object filterGroups, List<String> properties, int limit);
    
    /**
     * 获取产品的自定义属性列表
     * 
     * @return 属性列表
     */
    List<Map<String, Object>> getProductProperties();
}