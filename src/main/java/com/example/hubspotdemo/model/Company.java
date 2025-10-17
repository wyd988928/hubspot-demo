package com.example.hubspotdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

/**
 * 公司实体类，对应 HubSpot 的公司对象
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {

    /**
     * 公司 ID
     */
    private String id;

    /**
     * 公司属性
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
     * 获取公司名称
     */
    public String getCompanyName() {
        return properties != null ? (String) properties.get("company") : null;
    }

    /**
     * 获取公司域名
     */
    public String getDomain() {
        return properties != null ? (String) properties.get("domain") : null;
    }
}