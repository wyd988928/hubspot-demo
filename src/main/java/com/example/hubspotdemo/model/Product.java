package com.example.hubspotdemo.model;

/**
 * 产品实体类
 */
public class Product extends HubSpotObject {

    @Override
    public String getObjectTypeName() {
        return "products";
    }
}