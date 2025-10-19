package com.example.hubspotdemo.model;

/**
 * 联系人实体类
 */
public class Contact extends HubSpotObject {

    @Override
    public String getObjectTypeName() {
        return "contacts";
    }
}