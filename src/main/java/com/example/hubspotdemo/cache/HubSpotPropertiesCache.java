package com.example.hubspotdemo.cache;

import com.example.hubspotdemo.model.HubSpotPropertiesResponse;
import com.example.hubspotdemo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HubSpot 属性缓存类，用于缓存各种 HubSpot 对象的属性集合
 */
@Component
@Slf4j
public class HubSpotPropertiesCache {

    /**
     * HubSpot 对象类型枚举
     */
    public enum ObjectType {
        COMPANIES("companies"),
        CONTACTS("contacts"),
        DEALS("deals"),
        PRODUCTS("products"),
        LINE_ITEMS("line-items");

        private final String value;

        ObjectType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        /**
         * 根据字符串值获取枚举类型
         * @param value 对象类型字符串
         * @return 对应的枚举类型
         * @throws IllegalArgumentException 如果找不到对应的枚举类型
         */
        public static ObjectType fromValue(String value) {
            for (ObjectType type : values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("不支持的对象类型: " + value);
        }
    }

    // 缓存存储，使用 ConcurrentHashMap 保证线程安全
    private final Map<ObjectType, HubSpotPropertiesResponse> propertiesCache = new ConcurrentHashMap<>();

    // 各种服务的注入（使用非final字段，支持setter注入）
    private CompanyService companyService;
    private ContactService contactService;
    private DealService dealService;
    private ProductService productService;
    private LineItemService lineItemService;

    /**
     * 构造函数
     */
    public HubSpotPropertiesCache() {
        // 空构造函数，避免循环依赖
    }

    /**
     * 设置公司服务
     */
    @Autowired
    @Lazy
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * 设置联系人服务
     */
    @Autowired
    @Lazy
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * 设置交易服务
     */
    @Autowired
    @Lazy
    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }

    /**
     * 设置产品服务
     */
    @Autowired
    @Lazy
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 设置行项目服务
     */
    @Autowired
    @Lazy
    public void setLineItemService(LineItemService lineItemService) {
        this.lineItemService = lineItemService;
    }

    /**
     * 根据对象类型获取属性列表，如果缓存中没有则从服务中获取
     * @param objectType 对象类型字符串
     * @return 属性列表响应
     */
    public HubSpotPropertiesResponse getPropertiesByType(String objectType) {
        ObjectType type = ObjectType.fromValue(objectType);
        return getPropertiesByType(type);
    }

    /**
     * 根据对象类型枚举获取属性列表，如果缓存中没有则从服务中获取
     * @param objectType 对象类型枚举
     * @return 属性列表响应
     */
    public HubSpotPropertiesResponse getPropertiesByType(ObjectType objectType) {
        // 先从缓存中获取
        HubSpotPropertiesResponse properties = propertiesCache.get(objectType);
        
        // 如果缓存中没有，则从服务中获取并缓存
        if (properties == null) {
            log.info("缓存中没有 {} 的属性列表，从服务中获取...", objectType.getValue());
            properties = fetchPropertiesFromService(objectType);
            if (properties != null) {
                propertiesCache.put(objectType, properties);
                log.info("成功缓存 {} 的属性列表，包含 {} 个属性", 
                        objectType.getValue(), 
                        properties.getResults() != null ? properties.getResults().size() : 0);
            }
        } else {
            log.debug("从缓存中获取 {} 的属性列表", objectType.getValue());
        }
        
        return properties;
    }

    /**
     * 从对应的服务中获取属性列表
     * @param objectType 对象类型枚举
     * @return 属性列表响应
     */
    private HubSpotPropertiesResponse fetchPropertiesFromService(ObjectType objectType) {
        switch (objectType) {
            case COMPANIES:
                return companyService.getObjectProperties();
            case CONTACTS:
                return contactService.getObjectProperties();
            case DEALS:
                return dealService.getObjectProperties();
            case PRODUCTS:
                return productService.getObjectProperties();
            case LINE_ITEMS:
                return lineItemService.getObjectProperties();
            default:
                throw new IllegalArgumentException("不支持的对象类型: " + objectType.getValue());
        }
    }

    /**
     * 清除指定对象类型的缓存
     * @param objectType 对象类型枚举
     */
    public void clearCache(ObjectType objectType) {
        propertiesCache.remove(objectType);
        log.info("已清除 {} 的属性列表缓存", objectType.getValue());
    }

    /**
     * 清除所有对象类型的缓存
     */
    public void clearAllCache() {
        propertiesCache.clear();
        log.info("已清除所有对象类型的属性列表缓存");
    }

    /**
     * 刷新指定对象类型的缓存
     * @param objectType 对象类型枚举
     * @return 更新后的属性列表响应
     */
    public HubSpotPropertiesResponse refreshCache(ObjectType objectType) {
        clearCache(objectType);
        return getPropertiesByType(objectType);
    }
}