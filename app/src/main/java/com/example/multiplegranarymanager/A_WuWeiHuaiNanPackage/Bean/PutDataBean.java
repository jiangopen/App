package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean;

import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;

public class PutDataBean {
    private String msg;
    private int code;
    private GranaryListBean.Data data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public GranaryListBean.Data getData() {
        return data;
    }

    public void setData(GranaryListBean.Data data) {
        this.data = data;
    }
}
