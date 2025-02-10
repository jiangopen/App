package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean;

public class ShuLiangTestInTimeBean {
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
        private String startFlag;

        public String getStartFlag() {
            return startFlag;
        }

        public void setStartFlag(String startFlag) {
            this.startFlag = startFlag;
        }
    }
}
