package com.example.multiplegranarymanager.A_ShuChengPackage.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CropBean {
    @SerializedName("小麦")
    private Map<String,Double> xiaomai;
    @SerializedName("稻谷")
    private Map<String,Double> daogu;
    @SerializedName("大米")
    private Map<String,Double> dami;
    @SerializedName("玉米")
    private Map<String,Double> yumi;
    @SerializedName("黍子")
    private Map<String,Double> shuzi;
    @SerializedName("大豆")
    private Map<String,Double> dadou;

    public Map<String, Double> getXiaomai() {
        return xiaomai;
    }

    public void setXiaomai(Map<String, Double> xiaomai) {
        this.xiaomai = xiaomai;
    }

    public Map<String, Double> getDaogu() {
        return daogu;
    }

    public void setDaogu(Map<String, Double> daogu) {
        this.daogu = daogu;
    }

    public Map<String, Double> getDami() {
        return dami;
    }

    public void setDami(Map<String, Double> dami) {
        this.dami = dami;
    }

    public Map<String, Double> getYumi() {
        return yumi;
    }

    public void setYumi(Map<String, Double> yumi) {
        this.yumi = yumi;
    }

    public Map<String, Double> getShuzi() {
        return shuzi;
    }

    public void setShuzi(Map<String, Double> shuzi) {
        this.shuzi = shuzi;
    }

    public Map<String, Double> getDadou() {
        return dadou;
    }

    public void setDadou(Map<String, Double> dadou) {
        this.dadou = dadou;
    }
}
