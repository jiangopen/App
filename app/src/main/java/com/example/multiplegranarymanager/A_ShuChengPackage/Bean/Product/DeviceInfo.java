package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product;

import java.util.List;
import java.util.Map;

public class DeviceInfo {
    private String createdAt;
    private String deviceKey;
    private String deviceName;
    private String deviceType;
    private String netType;
    private String nickname;
    private String productKey;
    private String updatedAt;
    private Map<String,Value> extraInfo;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Map<String, Value> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, Value> extraInfo) {
        this.extraInfo = extraInfo;
    }
}
