package com.example.hubspotdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

/**
 * 联系人实体类，对应 HubSpot 的联系人对象
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

    /**
     * 联系人 ID
     */
    private String id;

    /**
     * 联系人属性
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
     * 所有者 ID
     */
    @JsonProperty("ownerId")
    private String ownerId;

    /**
     * 电子邮件
     */
    public String getEmail() {
        return properties != null ? (String) properties.get("email") : null;
    }

    /**
     * 设置电子邮件
     */
    public void setEmail(String email) {
        if (properties == null) {
            properties = new java.util.HashMap<>();
        }
        properties.put("email", email);
    }

    /**
     * 获取名字
     */
    public String getFirstName() {
        return properties != null ? (String) properties.get("firstname") : null;
    }

    /**
     * 设置名字
     */
    public void setFirstName(String firstName) {
        if (properties == null) {
            properties = new java.util.HashMap<>();
        }
        properties.put("firstname", firstName);
    }

    /**
     * 获取姓氏
     */
    public String getLastName() {
        return properties != null ? (String) properties.get("lastname") : null;
    }

    /**
     * 设置姓氏
     */
    public void setLastName(String lastName) {
        if (properties == null) {
            properties = new java.util.HashMap<>();
        }
        properties.put("lastname", lastName);
    }

    /**
     * 获取电话号码
     */
    public String getPhone() {
        return properties != null ? (String) properties.get("phone") : null;
    }

    /**
     * 设置电话号码
     */
    public void setPhone(String phone) {
        if (properties == null) {
            properties = new java.util.HashMap<>();
        }
        properties.put("phone", phone);
    }
}