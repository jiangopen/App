package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;

public class ChannelNumberMapBody {
    private String name;
    private String inspurChannelNumber;

    public ChannelNumberMapBody(String name, String inspurChannelNumber) {
        this.name = name;
        this.inspurChannelNumber = inspurChannelNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInspurChannelNumber() {
        return inspurChannelNumber;
    }

    public void setInspurChannelNumber(String inspurChannelNumber) {
        this.inspurChannelNumber = inspurChannelNumber;
    }
}
