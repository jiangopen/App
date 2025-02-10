package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.GasInsectBean;

public class GasInsectTestInTimeBean {
    private int code;
    private String msg;
    private GasInsectInTimeData data;

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

    public GasInsectInTimeData getData() {
        return data;
    }

    public void setData(GasInsectInTimeData data) {
        this.data = data;
    }

    public class GasInsectInTimeData {
        private String commandMapId;
        private String dataType;
        private String date;
        private long time;
        private GasInsectData dataContent;

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public GasInsectData getDataContent() {
            return dataContent;
        }

        public void setDataContent(GasInsectData dataContent) {
            this.dataContent = dataContent;
        }
    }
}
