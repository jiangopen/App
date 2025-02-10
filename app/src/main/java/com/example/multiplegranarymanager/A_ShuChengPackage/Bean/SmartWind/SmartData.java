package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.SmartWind;

import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product.Value;

import java.util.Map;

public class SmartData {
    private String deviceName;
    private String deviceKey;
    private String deviceType;
    private String netType;
    private String nickName;
    private String productKey;
    private Map<String, Value> extraInfo;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    };

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public Map<String, Value> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, Value> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public SmartData(String deviceName, String deviceKey, String deviceType, String netType, String nickName, String productKey, Map<String, Value> extraInfo) {
        this.deviceName = deviceName;
        this.deviceKey = deviceKey;
        this.deviceType = deviceType;
        this.netType = netType;
        this.nickName = nickName;
        this.productKey = productKey;
        this.extraInfo = extraInfo;
    }
}
