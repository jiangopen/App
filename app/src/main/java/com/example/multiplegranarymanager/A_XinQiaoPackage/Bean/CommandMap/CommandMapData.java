package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap;

import java.util.List;

public class CommandMapData {
    private List<CommandListData> commandList;
    private GasInsectInfoData gasInsectInfo;
    private String granaryId;
    private GranarySettingData granarySetting;
    private List<HardwareListData> hardwareList;
    private String moduleName;

    public List<CommandListData> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<CommandListData> commandList) {
        this.commandList = commandList;
    }

    public GasInsectInfoData getGasInsectInfo() {
        return gasInsectInfo;
    }

    public void setGasInsectInfo(GasInsectInfoData gasInsectInfo) {
        this.gasInsectInfo = gasInsectInfo;
    }

    public String getGranaryId() {
        return granaryId;
    }

    public void setGranaryId(String granaryId) {
        this.granaryId = granaryId;
    }

    public GranarySettingData getGranarySetting() {
        return granarySetting;
    }

    public void setGranarySetting(GranarySettingData granarySetting) {
        this.granarySetting = granarySetting;
    }

    public List<HardwareListData> getHardwareList() {
        return hardwareList;
    }

    public void setHardwareList(List<HardwareListData> hardwareList) {
        this.hardwareList = hardwareList;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
