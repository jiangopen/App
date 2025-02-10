package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind;

import java.util.List;

public class WindHistoryDataBean {
    private int code;
    private String msg;
    private List<Data> data;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private String commandMapId;
        private WindDataContent dataContent;
        private String dataType;
        private String date;
        private long time;

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
        }

        public WindDataContent getDataContent() {
            return dataContent;
        }

        public void setDataContent(WindDataContent dataContent) {
            this.dataContent = dataContent;
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
    }
}
