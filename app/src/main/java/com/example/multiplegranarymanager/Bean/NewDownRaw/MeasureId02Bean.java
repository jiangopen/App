package com.example.multiplegranarymanager.Bean.NewDownRaw;

public class MeasureId02Bean {
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
        private String measureId;

        public String getMeasureId() {
            return measureId;
        }

        public void setMeasureId(String measureId) {
            this.measureId = measureId;
        }
    }
}
