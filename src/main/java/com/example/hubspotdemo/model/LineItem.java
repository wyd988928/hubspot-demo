package com.example.hubspotdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

/**
 * 行项目实体类，对应 HubSpot 的行项目对象
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem {

    /**
     * 行项目 ID
     */
    private String id;

    /**
     * 行项目属性
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
     * 获取行项目名称
     */
    public String getLineItemName() {
        return properties != null ? (String) properties.get("hs_product_name") : null;
    }

    /**
     * 获取数量
     */
    public String getQuantity() {
        return properties != null ? (String) properties.get("quantity") : null;
    }
}