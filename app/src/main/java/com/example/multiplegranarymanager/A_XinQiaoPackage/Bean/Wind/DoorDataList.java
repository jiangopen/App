package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind;

import java.util.List;

public class DoorDataList {
    private List<String> doorStatusList;
    private List<String> fumigationStatusList;
    private List<Double> o2List;
    private List<Double> ph3List;
    private List<Double> n2List;

    public List<Double> getN2List() {
        return n2List;
    }

    public void setN2List(List<Double> n2List) {
        this.n2List = n2List;
    }

    public List<String> getDoorStatusList() {
        return doorStatusList;
    }

    public void setDoorStatusList(List<String> doorStatusList) {
        this.doorStatusList = doorStatusList;
    }

    public List<String> getFumigationStatusList() {
        return fumigationStatusList;
    }

    public void setFumigationStatusList(List<String> fumigationStatusList) {
        this.fumigationStatusList = fumigationStatusList;
    }

    public List<Double> getO2List() {
        return o2List;
    }

    public void setO2List(List<Double> o2List) {
        this.o2List = o2List;
    }

    public List<Double> getPh3List() {
        return ph3List;
    }

    public void setPh3List(List<Double> ph3List) {
        this.ph3List = ph3List;
    }
}
