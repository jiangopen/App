package com.example.multiplegranarymanager.A_ShuChengPackage.Body.DeviceInfos;

import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice.DeviceData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product.DeviceInfo;

public class DeviceInfos {
    private boolean IsSelected;
    //基础信息
    private String productKey;
    private String GrainType;

    //特定信息
    private String granaryId;
    private String granaryName;
    private String moduleName;
    private String TemDeviceKey;
    private String HumDeviceKey;
    private String GasDeviceKey;
    private String WindDeviceKey;
    //特别信息
    private DeviceData TemDeviceData;
    private DeviceData HumDeviceData;
    private DeviceData GasDeviceData;
    private DeviceInfo WindDeviceData;
    //后续补充信息
    private Double TemOuter;
    private Double TemInner;
    private Double TemMax;
    private Double TemMin;
    private Double TemAve;
    private Double HumOuter;
    private Double HumInner;
    private Double ShuiFen;

    public String getWindDeviceKey() {
        return WindDeviceKey;
    }

    public void setWindDeviceKey(String windDeviceKey) {
        WindDeviceKey = windDeviceKey;
    }

    public DeviceInfo getWindDeviceData() {
        return WindDeviceData;
    }

    public void setWindDeviceData(DeviceInfo windDeviceData) {
        WindDeviceData = windDeviceData;
    }

    public boolean getSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }

    public Double getTemOuter() {
        return TemOuter;
    }

    public void setTemOuter(Double temOuter) {
        TemOuter = temOuter;
    }

    public Double getTemInner() {
        return TemInner;
    }

    public void setTemInner(Double temInner) {
        TemInner = temInner;
    }

    public Double getTemMax() {
        return TemMax;
    }

    public void setTemMax(Double temMax) {
        TemMax = temMax;
    }

    public Double getTemMin() {
        return TemMin;
    }

    public void setTemMin(Double temMin) {
        TemMin = temMin;
    }

    public Double getTemAve() {
        return TemAve;
    }

    public void setTemAve(Double temAve) {
        TemAve = temAve;
    }

    public Double getHumOuter() {
        return HumOuter;
    }

    public void setHumOuter(Double humOuter) {
        HumOuter = humOuter;
    }

    public Double getHumInner() {
        return HumInner;
    }

    public void setHumInner(Double humInner) {
        HumInner = humInner;
    }

    public Double getShuiFen() {
        return ShuiFen;
    }

    public void setShuiFen(Double shuiFen) {
        ShuiFen = shuiFen;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getGrainType() {
        return GrainType;
    }

    public void setGrainType(String grainType) {
        GrainType = grainType;
    }

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

    public String getTemDeviceKey() {
        return TemDeviceKey;
    }

    public void setTemDeviceKey(String temDeviceKey) {
        TemDeviceKey = temDeviceKey;
    }

    public String getHumDeviceKey() {
        return HumDeviceKey;
    }

    public void setHumDeviceKey(String humDeviceKey) {
        HumDeviceKey = humDeviceKey;
    }

    public String getGasDeviceKey() {
        return GasDeviceKey;
    }

    public void setGasDeviceKey(String gasDeviceKey) {
        GasDeviceKey = gasDeviceKey;
    }

    public DeviceData getTemDeviceData() {
        return TemDeviceData;
    }

    public void setTemDeviceData(DeviceData temDeviceData) {
        TemDeviceData = temDeviceData;
    }

    public DeviceData getHumDeviceData() {
        return HumDeviceData;
    }

    public void setHumDeviceData(DeviceData humDeviceData) {
        HumDeviceData = humDeviceData;
    }

    public DeviceData getGasDeviceData() {
        return GasDeviceData;
    }

    public void setGasDeviceData(DeviceData gasDeviceData) {
        GasDeviceData = gasDeviceData;
    }
}
