package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.NewDownRaw;

import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice.DeviceData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice.DeviceData2;

import java.util.List;

public class Data03 {
    private List<DeviceData2> data;
    private int finishTime;
    private double progress;

    public List<DeviceData2> getData() {
        return data;
    }

    public void setData(List<DeviceData2> data) {
        this.data = data;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
