package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body;

import java.io.Serializable;
import java.util.List;

public class CardAdapterBody implements Comparable<CardAdapterBody>, Serializable {
    private String CommandMapId;
    private String GranaryKey;
    private String GranaryName;
    private String Url;
    private String date;
    private Double humidityInner;
    private Double humidityOut;
    private Double tempInner;
    private Double tempOut;
    private List<Double> tempList;
    private boolean IsSelected;

    public CardAdapterBody(String commandMapId, String granaryKey, String granaryName, String url, String date, Double humidityInner, Double humidityOut, Double tempInner, Double tempOut, List<Double> tempList, boolean isSelected) {
        CommandMapId = commandMapId;
        GranaryKey = granaryKey;
        GranaryName = granaryName;
        Url = url;
        this.date = date;
        this.humidityInner = humidityInner;
        this.humidityOut = humidityOut;
        this.tempInner = tempInner;
        this.tempOut = tempOut;
        this.tempList = tempList;
        IsSelected = isSelected;
    }

    public boolean getSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }

    public String getCommandMapId() {
        return CommandMapId;
    }

    public void setCommandMapId(String commandMapId) {
        CommandMapId = commandMapId;
    }

    public String getGranaryKey() {
        return GranaryKey;
    }

    public void setGranaryKey(String granaryKey) {
        GranaryKey = granaryKey;
    }

    public String getGranaryName() {
        return GranaryName;
    }

    public void setGranaryName(String granaryName) {
        GranaryName = granaryName;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public Double getTempInner() {
        return tempInner;
    }

    public void setTempInner(Double tempInner) {
        this.tempInner = tempInner;
    }

    public Double getTempOut() {
        return tempOut;
    }

    public void setTempOut(Double tempOut) {
        this.tempOut = tempOut;
    }

    public List<Double> getTempList() {
        return tempList;
    }

    public void setTempList(List<Double> tempList) {
        this.tempList = tempList;
    }

    @Override
    public int compareTo(CardAdapterBody o) {
        return this.GranaryName.compareTo(o.getGranaryName());
    }
}
