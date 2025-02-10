package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Energy;

import java.util.List;

public class EnergyBean {
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
        private List<EnergyConsumptionList> energyConsumptionList;

        public String getDataForInspur() {
            return dataForInspur;
        }

        public void setDataForInspur(String dataForInspur) {
            this.dataForInspur = dataForInspur;
        }

        public List<EnergyConsumptionList> getEnergyConsumptionList() {
            return energyConsumptionList;
        }

        public void setEnergyConsumptionList(List<EnergyConsumptionList> energyConsumptionList) {
            this.energyConsumptionList = energyConsumptionList;
        }
    }
}
