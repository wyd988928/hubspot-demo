package com.example.hubspotdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

/**
 * 交易实体类，对应 HubSpot 的交易对象
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deal {

    /**
     * 交易 ID
     */
    private String id;

    /**
     * 交易属性
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
     * 获取交易名称
     */
    public String getDealName() {
        return properties != null ? (String) properties.get("dealname") : null;
    }

    /**
     * 设置交易名称
     */
    public void setDealName(String dealName) {
        if (properties == null) {
            properties = new java.util.HashMap<>();
        }
        properties.put("dealname", dealName);
    }

    /**
     * 获取交易金额
     */
    public String getAmount() {
        return properties != null ? (String) properties.get("amount") : null;
    }

    /**
     * 设置交易金额
     */
    public void setAmount(String amount) {
        if (properties == null) {
            properties = new java.util.HashMap<>();
        }
        properties.put("amount", amount);
    }

    /**
     * 获取交易阶段
     */
    public String getDealStage() {
        return properties != null ? (String) properties.get("dealstage") : null;
    }

    /**
     * 设置交易阶段
     */
    public void setDealStage(String dealStage) {
        if (properties == null) {
            properties = new java.util.HashMap<>();
        }
        properties.put("dealstage", dealStage);
    }

    /**
     * 获取关闭日期
     */
    public String getCloseDate() {
        return properties != null ? (String) properties.get("closedate") : null;
    }

    /**
     * 设置关闭日期
     */
    public void setCloseDate(String closeDate) {
        if (properties == null) {
            properties = new java.util.HashMap<>();
        }
        properties.put("closedate", closeDate);
    }

    /**
     * 获取概率
     */
    public String getProbability() {
        return properties != null ? (String) properties.get("probability") : null;
    }

    /**
     * 设置概率
     */
    public void setProbability(String probability) {
        if (properties == null) {
            properties = new java.util.HashMap<>();
        }
        properties.put("probability", probability);
    }
}