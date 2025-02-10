package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.UserAlertInfos;

import java.util.List;

public class Data {
    private List<AlertInfo> alertInfo;
    private int current;
    private int total;

    public List<AlertInfo> getAlertInfo() {
        return alertInfo;
    }

    public void setAlertInfo(List<AlertInfo> alertInfo) {
        this.alertInfo = alertInfo;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
