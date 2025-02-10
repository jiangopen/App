package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean;

public class LockStatusBean {
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
        private int lockStatus;

        public int getLockStatus() {
            return lockStatus;
        }

        public void setLockStatus(int lockStatus) {
            this.lockStatus = lockStatus;
        }
    }
}
