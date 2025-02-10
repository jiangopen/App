package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ProjectSetting.GranarySettingList;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ProjectSetting.ProjectSettingBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ProjectSetting.SystemFuncSetting;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ProjectSetting.TimeSetting;

import java.util.List;

public class UpDateProject {
    private String url;
    private ProjectSettingBean.Data data;
    private String version;
    private String managerUrl;
    private TimeSetting timingDetectSetting;
    private List<GranarySettingList> granarySettingList;
    private SystemFuncSetting systemFuncSetting;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getManagerUrl() {
        return managerUrl;
    }

    public void setManagerUrl(String managerUrl) {
        this.managerUrl = managerUrl;
    }

    public TimeSetting getTimingDetectSetting() {
        return timingDetectSetting;
    }

    public void setTimingDetectSetting(TimeSetting timingDetectSetting) {
        this.timingDetectSetting = timingDetectSetting;
    }

    public List<GranarySettingList> getGranarySettingList() {
        return granarySettingList;
    }

    public void setGranarySettingList(List<GranarySettingList> granarySettingList) {
        this.granarySettingList = granarySettingList;
    }

    public SystemFuncSetting getSystemFuncSetting() {
        return systemFuncSetting;
    }

    public void setSystemFuncSetting(SystemFuncSetting systemFuncSetting) {
        this.systemFuncSetting = systemFuncSetting;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProjectSettingBean.Data getData() {
        return data;
    }

    public void setData(ProjectSettingBean.Data data) {
        this.data = data;
    }
}
