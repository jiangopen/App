package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean;

import java.util.List;

public class HistoryCountMultipleTwoBean {
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
        private List<DataList> list;

        public List<DataList> getList() {
            return list;
        }

        public void setList(List<DataList> list) {
            this.list = list;
        }
    }

    public class DataList {
        private DataListData data;
        private int errCode;
        private String errMsg;
        private String url;
        private String commandMapId;

        public DataListData getData() {
            return data;
        }

        public void setData(DataListData data) {
            this.data = data;
        }

        public int getErrCode() {
            return errCode;
        }

        public void setErrCode(int errCode) {
            this.errCode = errCode;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
        }
    }

    public class DataListData {
        private String date;
        private HistoryCountMultipleBean.DataContent dataContent;
        private String dataType;
        private long time;
        private String commandMapId;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public HistoryCountMultipleBean.DataContent getDataContent() {
            return dataContent;
        }

        public void setDataContent(HistoryCountMultipleBean.DataContent dataContent) {
            this.dataContent = dataContent;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
        }
    }
}
