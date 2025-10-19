package com.example.hubspotdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

/**
 * HubSpot 对象的抽象基类，所有 HubSpot 实体类的父类
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HubSpotObject {

    /**
     * 对象 ID
     */
    private String id;

    /**
     * 对象属性
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
     * 获取对象类型名称
     * 
     * @return 对象类型名称（如 contacts、companies、deals 等）
     */
    public String getObjectTypeName(){
        return null;
    };
}