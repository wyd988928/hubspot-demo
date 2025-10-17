package com.example.hubspotdemo.service;

import com.example.hubspotdemo.model.Company;
import com.example.hubspotdemo.model.HubSpotResponse;

import java.util.List;
import java.util.Map;

/**
 * 公司服务接口
 */
public interface CompanyService {

    /**
     * 获取所有公司
     * 
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @return 公司列表响应
     */
    HubSpotResponse<Company> getAllCompanies(List<String> properties, int limit);

    /**
     * 分页获取公司
     * 
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @param after 分页游标
     * @return 公司列表响应
     */
    HubSpotResponse<Company> getCompaniesWithPagination(List<String> properties, int limit, String after);

    /**
     * 根据ID获取公司
     * 
     * @param companyId 公司ID
     * @return 公司对象
     */
    Company getCompanyById(String companyId);

    /**
     * 创建公司
     * 
     * @param properties 公司属性
     * @return 创建的公司对象
     */
    Company createCompany(Map<String, Object> properties);

    /**
     * 更新公司
     * 
     * @param companyId 公司ID
     * @param properties 要更新的属性
     * @return 更新后的公司对象
     */
    Company updateCompany(String companyId, Map<String, Object> properties);

    /**
     * 删除公司
     * 
     * @param companyId 公司ID
     * @return 是否删除成功
     */
    boolean deleteCompany(String companyId);

    /**
     * 搜索公司
     * 
     * @param filterGroups 过滤条件组
     * @param properties 需要返回的属性列表
     * @param limit 返回数量限制
     * @return 搜索结果响应
     */
    HubSpotResponse<Company> searchCompanies(Object filterGroups, List<String> properties, int limit);

    /**
     * 获取与公司关联的联系人
     * 
     * @param companyId 公司ID
     * @return 关联的联系人响应
     */
    HubSpotResponse<Map<String, Object>> getCompanyContacts(String companyId);
    
    /**
     * 获取公司的自定义属性列表
     * 
     * @return 属性列表
     */
    List<Map<String, Object>> getCompanyProperties();
}