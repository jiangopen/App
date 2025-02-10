package com.example.multiplegranarymanager.Bean.Product;

import com.example.multiplegranarymanager.Bean.ValueBean;

import java.util.Date;
import java.util.Map;

public class ProductInfo {
    private Date createdAt;
    private String description;
    private Map<String, ValueBean> extraInfo;
    private String productKey;
    private String productName;
    private int productType;
    private String protocolType;
    private String typeIdentify;
    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, ValueBean> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, ValueBean> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getTypeIdentify() {
        return typeIdentify;
    }

    public void setTypeIdentify(String typeIdentify) {
        this.typeIdentify = typeIdentify;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
