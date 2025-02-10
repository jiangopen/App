package com.example.multiplegranarymanager.Body.ProductDetial;

public class DeviceInfoBody {
    private String productKey;
    private String deviceKey;
    private String deviceType;
    private String nickName;
    private String moduleName;
    private String granaryId;
    private String granaryFen;

    public DeviceInfoBody(String productKey, String deviceKey, String deviceType, String nickName, String moduleName, String granaryId, String granaryFen) {
        this.productKey = productKey;
        this.deviceKey = deviceKey;
        this.deviceType = deviceType;
        this.nickName = nickName;
        this.moduleName = moduleName;
        this.granaryId = granaryId;
        this.granaryFen = granaryFen;
    }

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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getGranaryId() {
        return granaryId;
    }

    public void setGranaryId(String granaryId) {
        this.granaryId = granaryId;
    }

    public String getGranaryFen() {
        return granaryFen;
    }

    public void setGranaryFen(String granaryFen) {
        this.granaryFen = granaryFen;
    }
}
