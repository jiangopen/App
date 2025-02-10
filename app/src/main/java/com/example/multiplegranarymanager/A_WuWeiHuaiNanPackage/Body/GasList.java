package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body;

public class GasList {
    private String name;
    private String time;
    private Double o2;
    private Double n2;
    private Double ph3;

    public GasList(String name, String time, Double o2, Double n2, Double ph3) {
        this.name = name;
        this.time = time;
        this.o2 = o2;
        this.n2 = n2;
        this.ph3 = ph3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getO2() {
        return o2;
    }

    public void setO2(Double o2) {
        this.o2 = o2;
    }

    public Double getN2() {
        return n2;
    }

    public void setN2(Double n2) {
        this.n2 = n2;
    }

    public Double getPh3() {
        return ph3;
    }

    public void setPh3(Double ph3) {
        this.ph3 = ph3;
    }
}
