package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.Wind;

import java.util.List;

public class WindStatusBean {
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
        private List<Object> lightStatusList;
        private List<WindStatusList> windStatusList;

        public String getDataForInspur() {
            return dataForInspur;
        }

        public void setDataForInspur(String dataForInspur) {
            this.dataForInspur = dataForInspur;
        }

        public List<Object> getLightStatusList() {
            return lightStatusList;
        }

        public void setLightStatusList(List<Object> lightStatusList) {
            this.lightStatusList = lightStatusList;
        }

        public List<WindStatusList> getWindStatusList() {
            return windStatusList;
        }

        public void setWindStatusList(List<WindStatusList> windStatusList) {
            this.windStatusList = windStatusList;
        }
    }
}
