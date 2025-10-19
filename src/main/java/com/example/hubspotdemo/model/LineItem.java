package com.example.hubspotdemo.model;

/**
 * 行项目实体类
 */
public class LineItem extends HubSpotObject {

    @Override
    public String getObjectTypeName() {
        return "line_items";
    }
}