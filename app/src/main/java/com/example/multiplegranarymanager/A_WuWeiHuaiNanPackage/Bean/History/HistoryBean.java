package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.History;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.HistoryCountMultipleBean;

import java.util.ArrayList;
import java.util.List;

public class HistoryBean {
    private int code;
    private String msg;
    private ArrayList<Data> data;

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

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        data = data;
    }

    public class Data {
        private String commandMapId;
        private String dataType;
        private String date;
        private long time;
        private HistoryCountMultipleBean.DataContent dataContent;

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

        public HistoryCountMultipleBean.DataContent getDataContent() {
            return dataContent;
        }

        public void setDataContent(HistoryCountMultipleBean.DataContent dataContent) {
            this.dataContent = dataContent;
        }
    }
}
