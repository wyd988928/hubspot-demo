package com.example.hubspotdemo.model;

/**
 * 公司实体类
 */
public class Company extends HubSpotObject {

    @Override
    public String getObjectTypeName() {
        return "companies";
    }
}