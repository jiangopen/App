package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.SmartWind;

public class WindDevice {
    private String channelNumber;
    private String channelNumberSec;
    private boolean doorOrWindow;
    private String enabled;
    private String extensionNumber;
    private String name;
    private int openAngle;
    private String picPosNo;
    private String status;
    private String type;
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getChannelNumberSec() {
        return channelNumberSec;
    }

    public void setChannelNumberSec(String channelNumberSec) {
        this.channelNumberSec = channelNumberSec;
    }

    public boolean isDoorOrWindow() {
        return doorOrWindow;
    }

    public void setDoorOrWindow(boolean doorOrWindow) {
        this.doorOrWindow = doorOrWindow;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOpenAngle() {
        return openAngle;
    }

    public void setOpenAngle(int openAngle) {
        this.openAngle = openAngle;
    }

    public String getPicPosNo() {
        return picPosNo;
    }

    public void setPicPosNo(String picPosNo) {
        this.picPosNo = picPosNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
