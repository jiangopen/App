package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;


import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;

public class WindStatusBody {
    private int Tag;
    private String type;
    private String commandMapId;
    private String url;
    private String hardwareStatus;
    private String inspurExtensionAddress;
    private HardwareListData hardwareData;
    private String ID;

    public WindStatusBody(int tag, String type, String commandMapId, String url, String hardwareStatus, String inspurExtensionAddress, HardwareListData hardwareData, String ID) {
        Tag = tag;
        this.type = type;
        this.commandMapId = commandMapId;
        this.url = url;
        this.hardwareStatus = hardwareStatus;
        this.inspurExtensionAddress = inspurExtensionAddress;
        this.hardwareData = hardwareData;
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getTag() {
        return Tag;
    }

    public void setTag(int tag) {
        Tag = tag;
    }

    public String getCommandMapId() {
        return commandMapId;
    }

    public void setCommandMapId(String commandMapId) {
        this.commandMapId = commandMapId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHardwareStatus() {
        return hardwareStatus;
    }

    public void setHardwareStatus(String hardwareStatus) {
        this.hardwareStatus = hardwareStatus;
    }

    public String getInspurExtensionAddress() {
        return inspurExtensionAddress;
    }

    public void setInspurExtensionAddress(String inspurExtensionAddress) {
        this.inspurExtensionAddress = inspurExtensionAddress;
    }

    public HardwareListData getHardwareData() {
        return hardwareData;
    }

    public void setHardwareData(HardwareListData hardwareData) {
        this.hardwareData = hardwareData;
    }
}
