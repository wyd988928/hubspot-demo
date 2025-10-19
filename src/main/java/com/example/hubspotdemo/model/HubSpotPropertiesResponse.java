package com.example.hubspotdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/**
 * HubSpot 属性列表响应实体类
 * 对应 API: /crm/v3/properties/{objectType}
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HubSpotPropertiesResponse {

    /**
     * 属性列表
     */
    private List<HubSpotProperty> results;
}