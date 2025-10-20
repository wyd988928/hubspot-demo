package com.example.hubspotdemo.schedule;

import com.example.hubspotdemo.model.Company;
import com.example.hubspotdemo.model.HubSpotObject;
import com.example.hubspotdemo.service.CompanyService;
import com.example.hubspotdemo.model.HubSpotResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 公司数据同步定时任务类
 * 负责处理公司数据的全量同步和增量更新同步
 */
@Component
public class CompanySyncScheduler {

    private static final Logger logger = LoggerFactory.getLogger(CompanySyncScheduler.class);

    @Autowired
    private CompanyService companyService;

    /**
     * 每天凌晨0点执行全量同步
     * 使用@Scheduled注解设置定时任务
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void performFullSync() {
            logger.info("开始执行公司数据全量同步");
            try {
                // 获取所有需要的属性（这里使用空列表表示获取所有属性，根据实际需求可以指定具体属性）
                List<String> properties = Collections.emptyList();
                int limit = 100; // 每次获取的最大数量
                
                // 执行全量同步
                HubSpotResponse<Company> response = companyService.getAllObjects(properties, limit);
                
                // 处理同步结果（这里只是记录日志，实际应用中可能需要保存到数据库等操作）
                if (response != null && response.getResults() != null) {
                    logger.info("全量同步完成，成功获取 {} 条公司数据", response.getResults().size());
                    
                    // 如果有分页标记，继续获取下一页数据
                    String after = response.getPagingInfo() != null ? response.getPagingInfo().getNext().getAfter() : null;
                    while (after != null) {
                        HubSpotResponse<Company> nextPage = companyService.getObjectsWithPagination(properties, limit, after);
                        if (nextPage != null && nextPage.getResults() != null) {
                            logger.info("继续获取下一页数据，新增 {} 条公司数据", nextPage.getResults().size());
                        }
                        after = nextPage.getPagingInfo() != null ? nextPage.getPagingInfo().getNext().getAfter() : null;
                    }
                }
                
            } catch (Exception e) {
                logger.error("全量同步执行失败: {}", e.getMessage(), e);
            }
    }

    /**
     * 每5分钟执行一次增量同步
     * 使用@Scheduled注解设置定时任务
     */
    @Scheduled(cron = "0 */1 * * * *") // 每5分钟执行一次
    public void performIncrementalSync() {
        logger.info("开始执行公司数据增量同步: {}", new Date());
        try {
            // 构建增量同步的过滤条件
            // 这里假设我们要同步最近5分钟内更新的数据
            // 实际应用中可能需要根据具体需求调整过滤条件
            List<Map<String, Object>> filter = buildIncrementalFilter();
            
            // 获取所有需要的属性
            List<String> properties = Collections.emptyList();
            int limit = 100;
            
            // 执行增量同步
            HubSpotResponse<Company> response = companyService.searchObjects(filter, properties, limit);
            
            // 处理同步结果
            if (response != null && response.getResults() != null) {
                logger.info("增量同步完成，成功获取 {} 条更新的公司数据", response.getResults().size());
                
                // 处理更新的数据（这里只是记录日志，实际应用中可能需要更新数据库等操作）
                for (HubSpotObject company : response.getResults()) {
                    logger.debug("处理更新的公司数据: ID={}, Name={}", company.getId(), company.getProperties().get("name"));
                }
            }
        } catch (Exception e) {
            logger.error("增量同步执行失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 构建增量同步的过滤条件
     * 根据HubSpot API的要求构建过滤条件
     * @return 过滤条件对象
     */
    private List<Map<String, Object>> buildIncrementalFilter() {
        // 这里构建的是一个简单的过滤条件示例
        // 实际的过滤条件格式需要根据HubSpot API的具体要求进行调整
        Map<String, Object> filterGroup = new HashMap<>();
        
        // 假设我们要过滤最近5分钟内更新的数据
        Map<String, Object> filter = new HashMap<>();
        filter.put("propertyName", "hs_lastmodifieddate");
        filter.put("operator", "GT"); // GT = Greater Than
        
        // 计算5分钟前的时间并格式化为ISO 8601格式的字符串
        Date now = new Date();
        Date fiveMinutesAgoDate = new Date(now.getTime() - (48 * 60 * 60 * 1000));
        
        // 格式化为ISO 8601格式的时间字符串 (yyyy-MM-dd'T'HH:mm:ss.SSS'Z')
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String fiveMinutesAgoStr = sdf.format(fiveMinutesAgoDate);
        
        filter.put("value", fiveMinutesAgoStr);

        filterGroup.put("filters", Collections.singletonList(filter));


        return Collections.singletonList(filterGroup);
    }
}