package com.example.hubspotdemo.model;

/**
 * 交易实体类
 */
public class Deal extends HubSpotObject {

    @Override
    public String getObjectTypeName() {
        return "deals";
    }
}