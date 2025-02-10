package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.AirCondition;

import java.util.List;

public class AirConditionBean {
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
        private List<AirConditionerStatsList> airConditionerStatsList;
        private String dataForInspur;

        public List<AirConditionerStatsList> getAirConditionerStatsList() {
            return airConditionerStatsList;
        }

        public void setAirConditionerStatsList(List<AirConditionerStatsList> airConditionerStatsList) {
            this.airConditionerStatsList = airConditionerStatsList;
        }

        public String getDataForInspur() {
            return dataForInspur;
        }

        public void setDataForInspur(String dataForInspur) {
            this.dataForInspur = dataForInspur;
        }
    }
}
