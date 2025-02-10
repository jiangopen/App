package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.GranarySettingData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind.DoorDataList;

import java.util.List;

public class SmartLockBody {
    private int Tag;
    private HardwareListData hardwareListData;
    private String url;
    private int hardwareStatus;
    private String commandMapId;
    private GranarySettingData granarySettingData;
    private List<String> n2List;
    private List<String> o2List;
    private List<String> ph3List;

    public List<String> getO2List() {
        return o2List;
    }

    public void setO2List(List<String> o2List) {
        this.o2List = o2List;
    }

    public List<String> getPh3List() {
        return ph3List;
    }

    public void setPh3List(List<String> ph3List) {
        this.ph3List = ph3List;
    }

    public List<String> getN2List() {
        return n2List;
    }

    public void setN2List(List<String> n2List) {
        this.n2List = n2List;
    }

    public GranarySettingData getGranarySettingData() {
        return granarySettingData;
    }

    public void setGranarySettingData(GranarySettingData granarySettingData) {
        this.granarySettingData = granarySettingData;
    }

    public int getTag() {
        return Tag;
    }

    public void setTag(int tag) {
        Tag = tag;
    }

    public HardwareListData getHardwareListData() {
        return hardwareListData;
    }

    public void setHardwareListData(HardwareListData hardwareListData) {
        this.hardwareListData = hardwareListData;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHardwareStatus() {
        return hardwareStatus;
    }

    public void setHardwareStatus(int hardwareStatus) {
        this.hardwareStatus = hardwareStatus;
    }

    public String getCommandMapId() {
        return commandMapId;
    }

    public void setCommandMapId(String commandMapId) {
        this.commandMapId = commandMapId;
    }

}





