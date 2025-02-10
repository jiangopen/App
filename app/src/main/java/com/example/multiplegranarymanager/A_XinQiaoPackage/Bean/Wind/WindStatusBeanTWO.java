package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind;

import java.util.List;

public class WindStatusBeanTWO {
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
        private List<WindOrLightStatusList> lightStatusList;
        private List<WindOrLightStatusList> windStatusList;
        private List<Double> pressureList;
        private List<String> n2List;
        private List<String> o2List;
        private List<String> ph3List;
        private List<QiTiaoStatuslist> valveStatusList;
        private List<String> valveCloseFlagList;
        private List<String> valveOpenFlagList;
        private List<String> blowerOpenFlagList;
        private List<String> blowerCloseFlagList;
        private List<DoorDataList> dataList;
        public List<DoorDataList> getDataList() {
            return dataList;
        }

        public void setDataList(List<DoorDataList> dataList) {
            this.dataList = dataList;
        }

        public List<String> getBlowerOpenFlagList() {
            return blowerOpenFlagList;
        }

        public void setBlowerOpenFlagList(List<String> blowerOpenFlagList) {
            this.blowerOpenFlagList = blowerOpenFlagList;
        }

        public List<String> getBlowerCloseFlagList() {
            return blowerCloseFlagList;
        }

        public void setBlowerCloseFlagList(List<String> blowerCloseFlagList) {
            this.blowerCloseFlagList = blowerCloseFlagList;
        }

        public List<String> getValveCloseFlagList() {
            return valveCloseFlagList;
        }

        public void setValveCloseFlagList(List<String> valveCloseFlagList) {
            this.valveCloseFlagList = valveCloseFlagList;
        }

        public List<String> getValveOpenFlagList() {
            return valveOpenFlagList;
        }

        public void setValveOpenFlagList(List<String> valveOpenFlagList) {
            this.valveOpenFlagList = valveOpenFlagList;
        }

        public List<QiTiaoStatuslist> getValveStatusList() {
            return valveStatusList;
        }

        public void setValveStatusList(List<QiTiaoStatuslist> valveStatusList) {
            this.valveStatusList = valveStatusList;
        }

        public List<String> getN2List() {
            return n2List;
        }

        public void setN2List(List<String> n2List) {
            this.n2List = n2List;
        }

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

        public List<Double> getPressureList() {
            return pressureList;
        }

        public void setPressureList(List<Double> pressureList) {
            this.pressureList = pressureList;
        }

        public String getDataForInspur() {
            return dataForInspur;
        }

        public void setDataForInspur(String dataForInspur) {
            this.dataForInspur = dataForInspur;
        }

        public List<WindOrLightStatusList> getLightStatusList() {
            return lightStatusList;
        }

        public void setLightStatusList(List<WindOrLightStatusList> lightStatusList) {
            this.lightStatusList = lightStatusList;
        }

        public List<WindOrLightStatusList> getWindStatusList() {
            return windStatusList;
        }

        public void setWindStatusList(List<WindOrLightStatusList> windStatusList) {
            this.windStatusList = windStatusList;
        }
    }
}
