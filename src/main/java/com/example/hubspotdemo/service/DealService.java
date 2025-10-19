package com.example.hubspotdemo.service;

import com.example.hubspotdemo.model.Deal;

/**
 * 交易服务接口，定义与 HubSpot 交易相关的操作
 */
public interface DealService extends GenericHubSpotService<Deal> {

    // 可以在这里定义交易特有的方法
    // 继承了GenericHubSpotService中的所有通用方法
}