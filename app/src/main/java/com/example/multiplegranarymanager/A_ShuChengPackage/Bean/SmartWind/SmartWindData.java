package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.SmartWind;

import java.util.List;

public class SmartWindData {
    private List<ExtensionChannelNumberMap> extensionChannelNumberMapList;
    private String smartWindEnabled;
    private String smartWindPatternCurrent;
    private List<String> smartWindPatternList;
    private String version;
    private List<WindDevice> windDeviceList;

    public List<ExtensionChannelNumberMap> getExtensionChannelNumberMapList() {
        return extensionChannelNumberMapList;
    }

    public void setExtensionChannelNumberMapList(List<ExtensionChannelNumberMap> extensionChannelNumberMapList) {
        this.extensionChannelNumberMapList = extensionChannelNumberMapList;
    }

    public String getSmartWindEnabled() {
        return smartWindEnabled;
    }

    public void setSmartWindEnabled(String smartWindEnabled) {
        this.smartWindEnabled = smartWindEnabled;
    }

    public String getSmartWindPatternCurrent() {
        return smartWindPatternCurrent;
    }

    public void setSmartWindPatternCurrent(String smartWindPatternCurrent) {
        this.smartWindPatternCurrent = smartWindPatternCurrent;
    }

    public List<String> getSmartWindPatternList() {
        return smartWindPatternList;
    }

    public void setSmartWindPatternList(List<String> smartWindPatternList) {
        this.smartWindPatternList = smartWindPatternList;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<WindDevice> getWindDeviceList() {
        return windDeviceList;
    }

    public void setWindDeviceList(List<WindDevice> windDeviceList) {
        this.windDeviceList = windDeviceList;
    }
}
