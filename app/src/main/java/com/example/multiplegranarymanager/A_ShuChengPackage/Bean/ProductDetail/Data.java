package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.ProductDetail;

import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product.DeviceInfo;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product.Value;

import java.util.List;
import java.util.Map;

public class Data {
    private String createdAt;
    private String description;
    private String productKey;
    private String productName;
    private int productType;
    private String protocolType;
    private String typeIdentify;
    private String updatedAt;
    private List<DeviceInfo> deviceInfo;
    private Map<String, Value> extraInfo;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<DeviceInfo> getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(List<DeviceInfo> deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public Map<String, Value> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, Value> extraInfo) {
        this.extraInfo = extraInfo;
    }
}
