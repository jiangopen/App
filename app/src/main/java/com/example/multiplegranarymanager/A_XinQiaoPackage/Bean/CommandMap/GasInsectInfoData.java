package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap;

import java.util.ArrayList;
import java.util.List;

public class GasInsectInfoData {
    private String gasChannelNumber;
    private String gasExtensionNumber;
    private List<ChannelNumber> gasPumpChannelNumberList;
    private String gasPumpExtensionNumber;
    private String insectExtensionNumber;
    private List<ChannelNumber> insectPumpChannelNumberList;
    private String insectPumpExtensionNumber;
    private String inspurAddress;
    private ArrayList<InspurChannelNumberMap> inspurChannelNumberMap;
    private int readDelayTime;
    private String strobeExtensionNumber;

    public String getGasChannelNumber() {
        return gasChannelNumber;
    }

    public void setGasChannelNumber(String gasChannelNumber) {
        this.gasChannelNumber = gasChannelNumber;
    }

    public String getGasExtensionNumber() {
        return gasExtensionNumber;
    }

    public void setGasExtensionNumber(String gasExtensionNumber) {
        this.gasExtensionNumber = gasExtensionNumber;
    }

    public List<ChannelNumber> getGasPumpChannelNumberList() {
        return gasPumpChannelNumberList;
    }

    public void setGasPumpChannelNumberList(List<ChannelNumber> gasPumpChannelNumberList) {
        this.gasPumpChannelNumberList = gasPumpChannelNumberList;
    }

    public String getGasPumpExtensionNumber() {
        return gasPumpExtensionNumber;
    }

    public void setGasPumpExtensionNumber(String gasPumpExtensionNumber) {
        this.gasPumpExtensionNumber = gasPumpExtensionNumber;
    }

    public String getInsectExtensionNumber() {
        return insectExtensionNumber;
    }

    public void setInsectExtensionNumber(String insectExtensionNumber) {
        this.insectExtensionNumber = insectExtensionNumber;
    }

    public List<ChannelNumber> getInsectPumpChannelNumberList() {
        return insectPumpChannelNumberList;
    }

    public void setInsectPumpChannelNumberList(List<ChannelNumber> insectPumpChannelNumberList) {
        this.insectPumpChannelNumberList = insectPumpChannelNumberList;
    }

    public String getInsectPumpExtensionNumber() {
        return insectPumpExtensionNumber;
    }

    public void setInsectPumpExtensionNumber(String insectPumpExtensionNumber) {
        this.insectPumpExtensionNumber = insectPumpExtensionNumber;
    }

    public String getInspurAddress() {
        return inspurAddress;
    }

    public void setInspurAddress(String inspurAddress) {
        this.inspurAddress = inspurAddress;
    }

    public ArrayList<InspurChannelNumberMap> getInspurChannelNumberMap() {
        return inspurChannelNumberMap;
    }

    public void setInspurChannelNumberMap(ArrayList<InspurChannelNumberMap> inspurChannelNumberMap) {
        this.inspurChannelNumberMap = inspurChannelNumberMap;
    }

    public int getReadDelayTime() {
        return readDelayTime;
    }

    public void setReadDelayTime(int readDelayTime) {
        this.readDelayTime = readDelayTime;
    }

    public String getStrobeExtensionNumber() {
        return strobeExtensionNumber;
    }

    public void setStrobeExtensionNumber(String strobeExtensionNumber) {
        this.strobeExtensionNumber = strobeExtensionNumber;
    }
}
