package com.example.multiplegranarymanager.Body.NewDownRaw;

import java.util.List;

public class Profile {
    private String id;
    private List<String> measure;
    private List<String> data;
    private String nickname;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
