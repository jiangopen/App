package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.StartBean;

import java.util.List;
import java.util.Map;

public class DataBean {
    private Map<String,Object> device;
    private Map<String,Object> moistureData;
    private List<String> newWindPatterns;
    private Map<String,Object> product;
    private Map<String,Object> tempData;
    private Map<String,Object> windInfo;

    public Map<String, Object> getDevice() {
        return device;
    }

    public void setDevice(Map<String, Object> device) {
        this.device = device;
    }

    public Map<String, Object> getMoistureData() {
        return moistureData;
    }

    public void setMoistureData(Map<String, Object> moistureData) {
        this.moistureData = moistureData;
    }

    public List<String> getNewWindPatterns() {
        return newWindPatterns;
    }

    public void setNewWindPatterns(List<String> newWindPatterns) {
        this.newWindPatterns = newWindPatterns;
    }

    public Map<String, Object> getProduct() {
        return product;
    }

    public void setProduct(Map<String, Object> product) {
        this.product = product;
    }

    public Map<String, Object> getTempData() {
        return tempData;
    }

    public void setTempData(Map<String, Object> tempData) {
        this.tempData = tempData;
    }

    public Map<String, Object> getWindInfo() {
        return windInfo;
    }

    public void setWindInfo(Map<String, Object> windInfo) {
        this.windInfo = windInfo;
    }
}
