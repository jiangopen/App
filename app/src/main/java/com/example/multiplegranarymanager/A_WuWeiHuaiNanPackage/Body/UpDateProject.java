package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.ProjectSetting.ProjectSettingBean;

public class UpDateProject {
    private String url;
    private ProjectSettingBean.Data data;

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
