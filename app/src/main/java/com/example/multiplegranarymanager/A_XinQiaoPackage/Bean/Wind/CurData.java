package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind;

import java.util.List;

public class CurData {
    private Double humidityInner;
    private Double humidityOut;
    private Double tempAve;
    private Float tempDiffMax;
    private Double tempInner;
    private List<Double> tempList;
    private Double tempMax;
    private Double tempMin;
    private Double tempOut;
    private int tempOutMinusTempInner;

    public Double getHumidityInner() {
        return humidityInner;
    }

    public void setHumidityInner(Double humidityInner) {
        this.humidityInner = humidityInner;
    }

    public Double getHumidityOut() {
        return humidityOut;
    }

    public void setHumidityOut(Double humidityOut) {
        this.humidityOut = humidityOut;
    }

    public Double getTempAve() {
        return tempAve;
    }

    public void setTempAve(Double tempAve) {
        this.tempAve = tempAve;
    }

    public Float getTempDiffMax() {
        return tempDiffMax;
    }

    public void setTempDiffMax(Float tempDiffMax) {
        this.tempDiffMax = tempDiffMax;
    }

    public Double getTempInner() {
        return tempInner;
    }

    public void setTempInner(Double tempInner) {
        this.tempInner = tempInner;
    }

    public List<Double> getTempList() {
        return tempList;
    }

    public void setTempList(List<Double> tempList) {
        this.tempList = tempList;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempOut() {
        return tempOut;
    }

    public void setTempOut(Double tempOut) {
        this.tempOut = tempOut;
    }

    public int getTempOutMinusTempInner() {
        return tempOutMinusTempInner;
    }

    public void setTempOutMinusTempInner(int tempOutMinusTempInner) {
        this.tempOutMinusTempInner = tempOutMinusTempInner;
    }
}
