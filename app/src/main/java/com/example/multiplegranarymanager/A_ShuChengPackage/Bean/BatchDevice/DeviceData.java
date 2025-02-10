package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice;

import java.util.List;
import java.util.Map;

public class DeviceData {
    private String date;
    private String deviceKey;
    private String deviceName;
    private String humidity;
    private String humidity_out;
    private String productKey;
    private String temp;
    private String temp_out;
    private long time;
    private Map<String,String> extraInfo;
    private List<String> cableTemp;
    private List<String> humidityList;
    private List<String> co2;
    private List<String> co2_bottom;
    private List<String> n2;
    private List<String> o2;
    private List<String> o2_bottom;
    private List<String> dust;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getHumidity_out() {
        return humidity_out;
    }

    public void setHumidity_out(String humidity_out) {
        this.humidity_out = humidity_out;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp_out() {
        return temp_out;
    }

    public void setTemp_out(String temp_out) {
        this.temp_out = temp_out;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Map<String, String> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, String> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public List<String> getCableTemp() {
        return cableTemp;
    }

    public void setCableTemp(List<String> cableTemp) {
        this.cableTemp = cableTemp;
    }

    public List<String> getHumidityList() {
        return humidityList;
    }

    public void setHumidityList(List<String> humidityList) {
        this.humidityList = humidityList;
    }

    public List<String> getCo2() {
        return co2;
    }

    public void setCo2(List<String> co2) {
        this.co2 = co2;
    }

    public List<String> getCo2_bottom() {
        return co2_bottom;
    }

    public void setCo2_bottom(List<String> co2_bottom) {
        this.co2_bottom = co2_bottom;
    }

    public List<String> getN2() {
        return n2;
    }

    public void setN2(List<String> n2) {
        this.n2 = n2;
    }

    public List<String> getO2() {
        return o2;
    }

    public void setO2(List<String> o2) {
        this.o2 = o2;
    }

    public List<String> getO2_bottom() {
        return o2_bottom;
    }

    public void setO2_bottom(List<String> o2_bottom) {
        this.o2_bottom = o2_bottom;
    }

    public List<String> getDust() {
        return dust;
    }

    public void setDust(List<String> dust) {
        this.dust = dust;
    }
}
