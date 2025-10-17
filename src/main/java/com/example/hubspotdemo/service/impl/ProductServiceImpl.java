package com.example.hubspotdemo.service.impl;

import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.model.Product;
import com.example.hubspotdemo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 产品服务实现类
 */
@Service
public class ProductServiceImpl extends HubSpotBaseService implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private static final String PRODUCTS_ENDPOINT = "/crm/v3/objects/products";
    private static final String PRODUCTS_SEARCH_ENDPOINT = "/crm/v3/objects/products/search";

    public ProductServiceImpl(RestTemplate restTemplate, com.example.hubspotdemo.config.HubSpotConfig hubSpotConfig, ObjectMapper objectMapper) {
        super(restTemplate, hubSpotConfig, objectMapper);
    }

    @Override
    public HubSpotResponse<Product> getAllProducts(List<String> properties, int limit) {
        logger.info("获取所有产品，属性: {}, 限制: {}", properties, limit);
        
        StringBuilder urlBuilder = new StringBuilder(PRODUCTS_ENDPOINT);
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
                new ParameterizedTypeReference<HubSpotResponse<Product>>() {});
    }

    @Override
    public HubSpotResponse<Product> getProductsWithPagination(List<String> properties, int limit, String after) {
        logger.info("分页获取产品，游标: {}, 属性: {}, 限制: {}", after, properties, limit);
        
        StringBuilder urlBuilder = new StringBuilder(PRODUCTS_ENDPOINT);
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
                new ParameterizedTypeReference<HubSpotResponse<Product>>() {});
    }

    @Override
    public Product getProductById(String productId) {
        logger.info("根据 ID 获取产品: {}", productId);
        
        String endpoint = PRODUCTS_ENDPOINT + "/" + productId;
        return get(endpoint, Product.class);
    }

    @Override
    public Product createProduct(Map<String, Object> properties) {
        logger.info("创建产品: {}", properties);
        
        Map<String, Object> requestBody = Map.of(
                "properties", properties
        );
        
        return post(PRODUCTS_ENDPOINT, requestBody, Product.class);
    }

    @Override
    public Product updateProduct(String productId, Map<String, Object> properties) {
        logger.info("更新产品: {}, 属性: {}", productId, properties);
        
        Map<String, Object> requestBody = Map.of(
                "properties", properties
        );
        
        String endpoint = PRODUCTS_ENDPOINT + "/" + productId;
        return put(endpoint, requestBody, Product.class);
    }

    @Override
    public boolean deleteProduct(String productId) {
        logger.info("删除产品: {}", productId);
        
        String endpoint = PRODUCTS_ENDPOINT + "/" + productId;
        delete(endpoint);
        return true;
    }

    @Override
    public HubSpotResponse<Product> searchProducts(Object filterGroups, List<String> properties, int limit) {
        logger.info("搜索产品，过滤条件: {}, 属性: {}, 限制: {}", filterGroups, properties, limit);
        
        Map<String, Object> requestBody = Map.of(
                "filterGroups", filterGroups,
                "properties", properties != null ? properties : List.of(),
                "limit", limit
        );
        
        return post(PRODUCTS_SEARCH_ENDPOINT, requestBody, 
                new ParameterizedTypeReference<HubSpotResponse<Product>>() {});
    }
    
    @Override
    public List<Map<String, Object>> getProductProperties() {
        logger.info("获取产品自定义属性列表");
        return getObjectProperties("products");
    }
}