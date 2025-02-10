package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.ProjectSetting;

import java.util.List;

public class ProjectSettingBean {
    private int code;
    private String msg;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private List<GranarySettingList> granarySettingList;
        private TimeSetting timingDetectSetting;
        private String version;

        public List<GranarySettingList> getGranarySettingList() {
            return granarySettingList;
        }

        public void setGranarySettingList(List<GranarySettingList> granarySettingList) {
            this.granarySettingList = granarySettingList;
        }

        public TimeSetting getTimingDetectSetting() {
            return timingDetectSetting;
        }

        public void setTimingDetectSetting(TimeSetting timingDetectSetting) {
            this.timingDetectSetting = timingDetectSetting;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
