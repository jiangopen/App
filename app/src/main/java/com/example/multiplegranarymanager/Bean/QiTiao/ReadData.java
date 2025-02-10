package com.example.multiplegranarymanager.Bean.QiTiao;

import java.util.List;

public class ReadData {
    private String productKey;
    private String deviceKey;
    private String deviceName;
    private long time;
    private String date;
    private List<String> hardwareData;

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getHardwareData() {
        return hardwareData;
    }

    public void setHardwareData(List<String> hardwareData) {
        this.hardwareData = hardwareData;
    }
}
