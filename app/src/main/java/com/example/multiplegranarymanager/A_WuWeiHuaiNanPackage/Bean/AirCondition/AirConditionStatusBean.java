package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.AirCondition;

import java.util.List;

public class AirConditionStatusBean {
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
        private List<String> closeStatus;
        private List<String> coldOpenStatus;
        private List<String> dehumidifierOpenStatus;
        private List<String> hotOpenStatus;
        private List<String> windOpenStatus;
        private String dataForInspur;

        public List<String> getCloseStatus() {
            return closeStatus;
        }

        public void setCloseStatus(List<String> closeStatus) {
            this.closeStatus = closeStatus;
        }

        public List<String> getColdOpenStatus() {
            return coldOpenStatus;
        }

        public void setColdOpenStatus(List<String> coldOpenStatus) {
            this.coldOpenStatus = coldOpenStatus;
        }

        public List<String> getDehumidifierOpenStatus() {
            return dehumidifierOpenStatus;
        }

        public void setDehumidifierOpenStatus(List<String> dehumidifierOpenStatus) {
            this.dehumidifierOpenStatus = dehumidifierOpenStatus;
        }

        public List<String> getHotOpenStatus() {
            return hotOpenStatus;
        }

        public void setHotOpenStatus(List<String> hotOpenStatus) {
            this.hotOpenStatus = hotOpenStatus;
        }

        public List<String> getWindOpenStatus() {
            return windOpenStatus;
        }

        public void setWindOpenStatus(List<String> windOpenStatus) {
            this.windOpenStatus = windOpenStatus;
        }

        public String getDataForInspur() {
            return dataForInspur;
        }

        public void setDataForInspur(String dataForInspur) {
            this.dataForInspur = dataForInspur;
        }
    }
}
