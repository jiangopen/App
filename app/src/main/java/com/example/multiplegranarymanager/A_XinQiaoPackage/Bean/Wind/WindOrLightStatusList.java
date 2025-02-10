package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind;


import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;

public class WindOrLightStatusList {
    private HardwareListData hardwareInfo;
    private String hardwareStatus;

    public HardwareListData getHardwareInfo() {
        return hardwareInfo;
    }

    public void setHardwareInfo(HardwareListData hardwareInfo) {
        this.hardwareInfo = hardwareInfo;
    }

    public String getHardwareStatus() {
        return hardwareStatus;
    }

    public void setHardwareStatus(String hardwareStatus) {
        this.hardwareStatus = hardwareStatus;
    }
}
