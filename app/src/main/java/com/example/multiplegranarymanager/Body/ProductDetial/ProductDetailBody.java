package com.example.multiplegranarymanager.Body.ProductDetial;

import java.util.List;

public class ProductDetailBody {
    private String productName;
    private List<DeviceInfoBody> deviceInfo;

    public ProductDetailBody(String productName, List<DeviceInfoBody> deviceInfo) {
        this.productName = productName;
        this.deviceInfo = deviceInfo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<DeviceInfoBody> getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(List<DeviceInfoBody> deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
