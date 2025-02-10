package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean;

import java.util.List;

public class WindTestInTimeBean {
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

    public class Data {
        private String dataForInspur;
        private List<String> lightCloseStatus;
        private List<String> lightOpenStatus;
        private List<String> windCloseStatus;
        private List<String> windOpenStatus;

        public String getDataForInspur() {
            return dataForInspur;
        }

        public void setDataForInspur(String dataForInspur) {
            this.dataForInspur = dataForInspur;
        }

        public List<String> getLightCloseStatus() {
            return lightCloseStatus;
        }

        public void setLightCloseStatus(List<String> lightCloseStatus) {
            this.lightCloseStatus = lightCloseStatus;
        }

        public List<String> getLightOpenStatus() {
            return lightOpenStatus;
        }

        public void setLightOpenStatus(List<String> lightOpenStatus) {
            this.lightOpenStatus = lightOpenStatus;
        }

        public List<String> getWindCloseStatus() {
            return windCloseStatus;
        }

        public void setWindCloseStatus(List<String> windCloseStatus) {
            this.windCloseStatus = windCloseStatus;
        }

        public List<String> getWindOpenStatus() {
            return windOpenStatus;
        }

        public void setWindOpenStatus(List<String> windOpenStatus) {
            this.windOpenStatus = windOpenStatus;
        }
    }
}
