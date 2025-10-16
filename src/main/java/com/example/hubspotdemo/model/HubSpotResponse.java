package com.example.hubspotdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * HubSpot API 通用响应类
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HubSpotResponse<T> {

    /**
     * 响应状态
     */
    private String status;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 单个结果
     */
    private T result;

    /**
     * 结果列表
     */
    private List<T> results;

    /**
     * 分页信息
     */
    @JsonProperty("paging")
    private PagingInfo pagingInfo;

    /**
     * 元数据
     */
    @JsonProperty("metadata")
    private Map<String, Object> metaData;

    /**
     * 分页信息类
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PagingInfo {
        
        /**
         * 下一页信息
         */
        private NextPage next;
        
        /**
         * 下一页类
         */
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class NextPage {
            
            /**
             * 游标
             */
            private String after;
            
            /**
             * 链接
             */
            private String link;
        }
    }
}