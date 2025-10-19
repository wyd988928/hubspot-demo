package com.example.hubspotdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * HubSpot 属性实体类，用于表示 HubSpot 中对象的属性定义
 * 对应 API: /crm/v3/properties/{objectType}
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HubSpotProperty {

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性标签（显示名称）
     */
    private String label;

    /**
     * 属性描述
     */
    private String description;

    /**
     * 属性类型
     */
    private String type;

    /**
     * 字段类型
     */
    @JsonProperty("fieldType")
    private String fieldType;

    /**
     * 组名称
     */
    @JsonProperty("groupName")
    private String groupName;

    /**
     * 组标签
     */
    @JsonProperty("groupLabel")
    private String groupLabel;

    /**
     * 是否必填
     */
    private boolean required;

    /**
     * 是否搜索可用
     */
    @JsonProperty("searchable")
    private boolean searchable;

    /**
     * 是否可排序
     */
    @JsonProperty("sortable")
    private boolean sortable;

    /**
     * 是否只读
     */
    @JsonProperty("readOnlyDefinition")
    private boolean readOnlyDefinition;

    /**
     * 是否在表单中可编辑
     */
    @JsonProperty("editableInModule")
    private boolean editableInModule;

    /**
     * 是否可归档
     */
    @JsonProperty("displayMode")
    private String displayMode;

    /**
     * 选项列表（用于枚举类型）
     */
    private List<Map<String, Object>> options;

    /**
     * 计算表达式（用于计算属性）
     */
    private String calculationFormula;

    /**
     * 引用的对象类型（用于引用属性）
     */
    @JsonProperty("referencedObjectType")
    private String referencedObjectType;

    /**
     * 外部选项参考类型
     */
    @JsonProperty("externalOptionsReferenceType")
    private String externalOptionsReferenceType;

    /**
     * 外部选项参考ID
     */
    @JsonProperty("externalOptionsReferenceId")
    private String externalOptionsReferenceId;

    /**
     * 是否显示在UI中
     */
    @JsonProperty("showCurrencySymbol")
    private boolean showCurrencySymbol;

    /**
     * 验证规则
     */
    private Map<String, Object> validation;

    /**
     * 自动创建选项
     */
    @JsonProperty("createdUserId")
    private String createdUserId;

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
     * 更新用户ID
     */
    @JsonProperty("updatedUserId")
    private String updatedUserId;

    /**
     * 是否已归档
     */
    private boolean archived;

    /**
     * 归档时间
     */
    @JsonProperty("archivedAt")
    private String archivedAt;

    /**
     * 归档用户ID
     */
    @JsonProperty("archivedUserId")
    private String archivedUserId;
}