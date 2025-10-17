package com.example.hubspotdemo.controller;

import com.example.hubspotdemo.model.Contact;
import com.example.hubspotdemo.model.Company;
import com.example.hubspotdemo.model.Deal;
import com.example.hubspotdemo.model.HubSpotResponse;
import com.example.hubspotdemo.model.LineItem;
import com.example.hubspotdemo.model.Product;
import com.example.hubspotdemo.service.ContactService;
import com.example.hubspotdemo.service.CompanyService;
import com.example.hubspotdemo.service.DealService;
import com.example.hubspotdemo.service.LineItemService;
import com.example.hubspotdemo.service.ProductService;
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
    private final CompanyService companyService;
    private final ProductService productService;
    private final LineItemService lineItemService;

    @Autowired
    public HubSpotController(ContactService contactService, DealService dealService, 
                           CompanyService companyService, ProductService productService, 
                           LineItemService lineItemService) {
        this.contactService = contactService;
        this.dealService = dealService;
        this.companyService = companyService;
        this.productService = productService;
        this.lineItemService = lineItemService;
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

    @GetMapping("/contacts/{id}/deals")
    public ResponseEntity<HubSpotResponse<Deal>> getDealsByContactId(
            @PathVariable("id") String contactId) {
        HubSpotResponse<Deal> response = contactService.getDealsByContactId(contactId);
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

    // 公司相关接口
    
    @GetMapping("/companies")
    public ResponseEntity<HubSpotResponse<Company>> getAllCompanies(
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit) {
        HubSpotResponse<Company> response = companyService.getAllCompanies(properties, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/companies/paginated")
    public ResponseEntity<HubSpotResponse<Company>> getCompaniesWithPagination(
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam String after) {
        HubSpotResponse<Company> response = companyService.getCompaniesWithPagination(properties, limit, after);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") String companyId) {
        Company company = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(company);
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@RequestBody Map<String, Object> properties) {
        Company company = companyService.createCompany(properties);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> updateCompany(
            @PathVariable("id") String companyId,
            @RequestBody Map<String, Object> properties) {
        Company company = companyService.updateCompany(companyId, properties);
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Boolean> deleteCompany(@PathVariable("id") String companyId) {
        boolean deleted = companyService.deleteCompany(companyId);
        return ResponseEntity.ok(deleted);
    }

    @PostMapping("/companies/search")
    public ResponseEntity<HubSpotResponse<Company>> searchCompanies(
            @RequestBody Map<String, Object> searchRequest,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit) {
        Object filterGroups = searchRequest.get("filterGroups");
        HubSpotResponse<Company> response = companyService.searchCompanies(filterGroups, properties, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/companies/{id}/contacts")
    public ResponseEntity<HubSpotResponse<Map<String, Object>>> getCompanyContacts(
            @PathVariable("id") String companyId) {
        HubSpotResponse<Map<String, Object>> response = companyService.getCompanyContacts(companyId);
        return ResponseEntity.ok(response);
    }

    // 产品相关接口
    
    @GetMapping("/products")
    public ResponseEntity<HubSpotResponse<Product>> getAllProducts(
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit) {
        HubSpotResponse<Product> response = productService.getAllProducts(properties, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/paginated")
    public ResponseEntity<HubSpotResponse<Product>> getProductsWithPagination(
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam String after) {
        HubSpotResponse<Product> response = productService.getProductsWithPagination(properties, limit, after);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Map<String, Object> properties) {
        Product product = productService.createProduct(properties);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("id") String productId,
            @RequestBody Map<String, Object> properties) {
        Product product = productService.updateProduct(productId, properties);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") String productId) {
        boolean deleted = productService.deleteProduct(productId);
        return ResponseEntity.ok(deleted);
    }

    @PostMapping("/products/search")
    public ResponseEntity<HubSpotResponse<Product>> searchProducts(
            @RequestBody Map<String, Object> searchRequest,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit) {
        Object filterGroups = searchRequest.get("filterGroups");
        HubSpotResponse<Product> response = productService.searchProducts(filterGroups, properties, limit);
        return ResponseEntity.ok(response);
    }

    // 行项目相关接口
    
    @GetMapping("/line-items")
    public ResponseEntity<HubSpotResponse<LineItem>> getAllLineItems(
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit) {
        HubSpotResponse<LineItem> response = lineItemService.getAllLineItems(properties, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/line-items/paginated")
    public ResponseEntity<HubSpotResponse<LineItem>> getLineItemsWithPagination(
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam String after) {
        HubSpotResponse<LineItem> response = lineItemService.getLineItemsWithPagination(properties, limit, after);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/line-items/{id}")
    public ResponseEntity<LineItem> getLineItemById(@PathVariable("id") String lineItemId) {
        LineItem lineItem = lineItemService.getLineItemById(lineItemId);
        return ResponseEntity.ok(lineItem);
    }

    @PostMapping("/line-items")
    public ResponseEntity<LineItem> createLineItem(@RequestBody Map<String, Object> properties) {
        LineItem lineItem = lineItemService.createLineItem(properties);
        return ResponseEntity.ok(lineItem);
    }

    @PutMapping("/line-items/{id}")
    public ResponseEntity<LineItem> updateLineItem(
            @PathVariable("id") String lineItemId,
            @RequestBody Map<String, Object> properties) {
        LineItem lineItem = lineItemService.updateLineItem(lineItemId, properties);
        return ResponseEntity.ok(lineItem);
    }

    @DeleteMapping("/line-items/{id}")
    public ResponseEntity<Boolean> deleteLineItem(@PathVariable("id") String lineItemId) {
        boolean deleted = lineItemService.deleteLineItem(lineItemId);
        return ResponseEntity.ok(deleted);
    }

    @PostMapping("/line-items/search")
    public ResponseEntity<HubSpotResponse<LineItem>> searchLineItems(
            @RequestBody Map<String, Object> searchRequest,
            @RequestParam(required = false) List<String> properties,
            @RequestParam(defaultValue = "10") int limit) {
        Object filterGroups = searchRequest.get("filterGroups");
        HubSpotResponse<LineItem> response = lineItemService.searchLineItems(filterGroups, properties, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deals/{id}/line-items")
    public ResponseEntity<HubSpotResponse<Map<String, Object>>> getDealLineItems(
            @PathVariable("id") String dealId) {
        HubSpotResponse<Map<String, Object>> response = lineItemService.getDealLineItems(dealId);
        return ResponseEntity.ok(response);
    }
}