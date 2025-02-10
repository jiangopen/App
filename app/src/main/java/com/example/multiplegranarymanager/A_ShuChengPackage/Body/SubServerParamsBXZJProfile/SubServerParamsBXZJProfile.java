package com.example.multiplegranarymanager.A_ShuChengPackage.Body.SubServerParamsBXZJProfile;

import java.util.List;

public class SubServerParamsBXZJProfile {
    private String id; // 粮仓id
    private List<String> measure; // 测量种类，测温时：temp,temphumi
    private String nickname; // 记录温度数据的设备

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMeasure() {
        return measure;
    }

    public void setMeasure(List<String> measure) {
        this.measure = measure;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
