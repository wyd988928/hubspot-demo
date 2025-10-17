package com.example.hubspotdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

/**
 * 产品实体类，对应 HubSpot 的产品对象
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    /**
     * 产品 ID
     */
    private String id;

    /**
     * 产品属性
     */
    private Map<String, Object> properties;

    /**
     * 创建时间
     */
    @JsonProperty("createdAt")
    private String createdAt;

    /**
     * 更新时间
     */
    @JsonProperty("updatedAt")
    private String updatedAt;

    /**
     * 是否归档
     */
    @JsonProperty("archived")
    private boolean archived;

    /**
     * 获取产品名称
     */
    public String getProductName() {
        return properties != null ? (String) properties.get("name") : null;
    }

    /**
     * 获取产品价格
     */
    public String getPrice() {
        return properties != null ? (String) properties.get("price") : null;
    }
}