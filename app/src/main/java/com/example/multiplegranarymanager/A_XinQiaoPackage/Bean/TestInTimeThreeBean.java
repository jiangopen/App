package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class TestInTimeThreeBean {
    private int code;
    private String msg;
    private listData data;

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

    public listData getData() {
        return data;
    }

    public void setData(listData data) {
        this.data = data;
    }

    public static class listData {
        private List<ListBody> list;



        public List<ListBody> getList() {
            return list;
        }

        public void setList(List<ListBody> list) {
            this.list = list;
        }
    }
}
