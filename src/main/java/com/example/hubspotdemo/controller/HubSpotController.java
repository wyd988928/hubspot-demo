package com.example.hubspotdemo.controller;

import com.example.hubspotdemo.model.Contact;
import com.example.hubspotdemo.model.Deal;
import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.service.ContactService;
import com.example.hubspotdemo.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * HubSpot API 控制器，提供 REST 接口来访问 HubSpot 功能
 */
@RestController
@RequestMapping("/api/hubspot")
public class HubSpotController {

    private final ContactService contactService;
    private final DealService dealService;

    @Autowired
    public HubSpotController(ContactService contactService, DealService dealService) {
        this.contactService = contactService;
        this.dealService = dealService;
    }

    // 联系人相关接口
    
    @GetMapping("/contacts")
    public ResponseEntity<HubSpotResponse<Contact>> getAllContacts(
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit) {
        HubSpotResponse<Contact> response = contactService.getAllContacts(properties, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/contacts/paginated")
    public ResponseEntity<HubSpotResponse<Contact>> getContactsWithPagination(
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam String after) {
        HubSpotResponse<Contact> response = contactService.getContactsWithPagination(properties, limit, after);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable("id") String contactId) {
        Contact contact = contactService.getContactById(contactId);
        return ResponseEntity.ok(contact);
    }

    @PostMapping("/contacts")
    public ResponseEntity<Contact> createContact(@RequestBody Map<String, Object> properties) {
        Contact contact = contactService.createContact(properties);
        return ResponseEntity.ok(contact);
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateContact(
            @PathVariable("id") String contactId,
            @RequestBody Map<String, Object> properties) {
        Contact contact = contactService.updateContact(contactId, properties);
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Boolean> deleteContact(@PathVariable("id") String contactId) {
        boolean deleted = contactService.deleteContact(contactId);
        return ResponseEntity.ok(deleted);
    }

    @PostMapping("/contacts/search")
    public ResponseEntity<HubSpotResponse<Contact>> searchContacts(
            @RequestBody Map<String, Object> searchRequest,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit) {
        Object filterGroups = searchRequest.get("filterGroups");
        HubSpotResponse<Contact> response = contactService.searchContacts(filterGroups, properties, limit);
        return ResponseEntity.ok(response);
    }

    // 交易相关接口
    
    @GetMapping("/deals")
    public ResponseEntity<HubSpotResponse<Deal>> getAllDeals(
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit) {
        HubSpotResponse<Deal> response = dealService.getAllDeals(properties, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deals/paginated")
    public ResponseEntity<HubSpotResponse<Deal>> getDealsWithPagination(
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam String after) {
        HubSpotResponse<Deal> response = dealService.getDealsWithPagination(properties, limit, after);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deals/{id}")
    public ResponseEntity<Deal> getDealById(@PathVariable("id") String dealId) {
        Deal deal = dealService.getDealById(dealId);
        return ResponseEntity.ok(deal);
    }

    @PostMapping("/deals")
    public ResponseEntity<Deal> createDeal(@RequestBody Map<String, Object> properties) {
        Deal deal = dealService.createDeal(properties);
        return ResponseEntity.ok(deal);
    }

    @PutMapping("/deals/{id}")
    public ResponseEntity<Deal> updateDeal(
            @PathVariable("id") String dealId,
            @RequestBody Map<String, Object> properties) {
        Deal deal = dealService.updateDeal(dealId, properties);
        return ResponseEntity.ok(deal);
    }

    @DeleteMapping("/deals/{id}")
    public ResponseEntity<Boolean> deleteDeal(@PathVariable("id") String dealId) {
        boolean deleted = dealService.deleteDeal(dealId);
        return ResponseEntity.ok(deleted);
    }

    @PostMapping("/deals/search")
    public ResponseEntity<HubSpotResponse<Deal>> searchDeals(
            @RequestBody Map<String, Object> searchRequest,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit) {
        Object filterGroups = searchRequest.get("filterGroups");
        HubSpotResponse<Deal> response = dealService.searchDeals(filterGroups, properties, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/contacts/{id}/deals")
    public ResponseEntity<HubSpotResponse<Deal>> getDealsByContactId(
            @PathVariable("id") String contactId,
            @RequestParam(required = false) List<String> properties) {
        HubSpotResponse<Deal> response = dealService.getDealsByContactId(contactId, properties);
        return ResponseEntity.ok(response);
    }
}