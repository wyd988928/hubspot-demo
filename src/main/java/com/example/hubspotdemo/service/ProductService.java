package com.example.hubspotdemo.service;

import com.example.hubspotdemo.model.Product;

/**
 * 产品服务接口
 * 扩展通用HubSpot服务接口，实现产品特有的业务逻辑
 */
public interface ProductService extends GenericHubSpotService<Product> {
    // 产品特有的方法可以在这里添加
}