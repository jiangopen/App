package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice;

import java.util.List;
import java.util.Map;

public class DeviceData2 {
    private String date;
    private String deviceKey;
    private String deviceName;
    private Double humidity;
    private Double humidity_out;
    private String productKey;
    private Double temp;
    private Double temp_out;
    private long time;
    private Map<String,String> extraInfo;
    private List<Double> cableTemp;
    private List<Double> humidityList;
    private List<Double> co2;
    private List<Double> co2_bottom;
    private List<Double> n2;
    private List<Double> o2;
    private List<Double> o2_bottom;
    private List<Double> dust;

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

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getHumidity_out() {
        return humidity_out;
    }

    public void setHumidity_out(Double humidity_out) {
        this.humidity_out = humidity_out;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTemp_out() {
        return temp_out;
    }

    public void setTemp_out(Double temp_out) {
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

    public List<Double> getCableTemp() {
        return cableTemp;
    }

    public void setCableTemp(List<Double> cableTemp) {
        this.cableTemp = cableTemp;
    }

    public List<Double> getHumidityList() {
        return humidityList;
    }

    public void setHumidityList(List<Double> humidityList) {
        this.humidityList = humidityList;
    }

    public List<Double> getCo2() {
        return co2;
    }

    public void setCo2(List<Double> co2) {
        this.co2 = co2;
    }

    public List<Double> getCo2_bottom() {
        return co2_bottom;
    }

    public void setCo2_bottom(List<Double> co2_bottom) {
        this.co2_bottom = co2_bottom;
    }

    public List<Double> getN2() {
        return n2;
    }

    public void setN2(List<Double> n2) {
        this.n2 = n2;
    }

    public List<Double> getO2() {
        return o2;
    }

    public void setO2(List<Double> o2) {
        this.o2 = o2;
    }

    public List<Double> getO2_bottom() {
        return o2_bottom;
    }

    public void setO2_bottom(List<Double> o2_bottom) {
        this.o2_bottom = o2_bottom;
    }

    public List<Double> getDust() {
        return dust;
    }

    public void setDust(List<Double> dust) {
        this.dust = dust;
    }
}
