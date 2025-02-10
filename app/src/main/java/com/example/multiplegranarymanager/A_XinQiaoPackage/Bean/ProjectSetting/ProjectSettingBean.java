package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ProjectSetting;

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
        private String managerUrl;
        private String fullManageUrl;
        private SystemFuncSetting systemFuncSetting;

        public String getManagerUrl() {
            return managerUrl;
        }

        public void setManagerUrl(String managerUrl) {
            this.managerUrl = managerUrl;
        }

        public String getFullManageUrl() {
            return fullManageUrl;
        }

        public void setFullManageUrl(String fullManageUrl) {
            this.fullManageUrl = fullManageUrl;
        }

        public SystemFuncSetting getSystemFuncSetting() {
            return systemFuncSetting;
        }

        public void setSystemFuncSetting(SystemFuncSetting systemFuncSetting) {
            this.systemFuncSetting = systemFuncSetting;
        }

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
