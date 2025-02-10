package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;

public class ShuLiangBody {
    private String name;
    private Double height;
    private Double mass;
    private Double volume;
    private String date;
    private String baoguanyuan;
    private String grainType;
    private Double All_v;
    private Double Cha_v;
    private String type;

    public ShuLiangBody(String name, Double height, Double mass, Double volume, String date, String baoguanyuan, String grainType, Double all_v, Double cha_v, String type) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.volume = volume;
        this.date = date;
        this.baoguanyuan = baoguanyuan;
        this.grainType = grainType;
        All_v = all_v;
        Cha_v = cha_v;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBaoguanyuan() {
        return baoguanyuan;
    }

    public void setBaoguanyuan(String baoguanyuan) {
        this.baoguanyuan = baoguanyuan;
    }

    public String getGrainType() {
        return grainType;
    }

    public void setGrainType(String grainType) {
        this.grainType = grainType;
    }

    public Double getAll_v() {
        return All_v;
    }

    public void setAll_v(Double all_v) {
        All_v = all_v;
    }

    public Double getCha_v() {
        return Cha_v;
    }

    public void setCha_v(Double cha_v) {
        Cha_v = cha_v;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
