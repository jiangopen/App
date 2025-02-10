package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.SmartWind;

public class Data {
    private String granaryId;
    private String granaryName;
    private String moduleName;
    private String productkey;
    private String devicekey;
    private SmartWindData deviceinfo;

    public String getGranaryId() {
        return granaryId;
    }

    public void setGranaryId(String granaryId) {
        this.granaryId = granaryId;
    }

    public String getGranaryName() {
        return granaryName;
    }

    public void setGranaryName(String granaryName) {
        this.granaryName = granaryName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getProductkey() {
        return productkey;
    }

    public void setProductkey(String productkey) {
        this.productkey = productkey;
    }

    public String getDevicekey() {
        return devicekey;
    }

    public void setDevicekey(String devicekey) {
        this.devicekey = devicekey;
    }

    public SmartWindData getDeviceinfo() {
        return deviceinfo;
    }

    public void setDeviceinfo(SmartWindData deviceinfo) {
        this.deviceinfo = deviceinfo;
    }
}
