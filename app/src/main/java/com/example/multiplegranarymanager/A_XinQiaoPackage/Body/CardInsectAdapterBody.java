package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;

public class CardInsectAdapterBody {
    private int inspurChannelNumberId;
    private String inspurExtensionAddress;
    private String inspurChannelNumber;
    private String detectType;
    private String url;
    private String commandMapId;
    private int insect;

    public CardInsectAdapterBody(int inspurChannelNumberId, String inspurExtensionAddress, String inspurChannelNumber, String detectType, String url, String commandMapId, int insect) {
        this.inspurChannelNumberId = inspurChannelNumberId;
        this.inspurExtensionAddress = inspurExtensionAddress;
        this.inspurChannelNumber = inspurChannelNumber;
        this.detectType = detectType;
        this.url = url;
        this.commandMapId = commandMapId;
        this.insect = insect;
    }

    public int getInspurChannelNumberId() {
        return inspurChannelNumberId;
    }

    public void setInspurChannelNumberId(int inspurChannelNumberId) {
        this.inspurChannelNumberId = inspurChannelNumberId;
    }

    public String getInspurExtensionAddress() {
        return inspurExtensionAddress;
    }

    public void setInspurExtensionAddress(String inspurExtensionAddress) {
        this.inspurExtensionAddress = inspurExtensionAddress;
    }

    public String getInspurChannelNumber() {
        return inspurChannelNumber;
    }

    public void setInspurChannelNumber(String inspurChannelNumber) {
        this.inspurChannelNumber = inspurChannelNumber;
    }

    public String getDetectType() {
        return detectType;
    }

    public void setDetectType(String detectType) {
        this.detectType = detectType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommandMapId() {
        return commandMapId;
    }

    public void setCommandMapId(String commandMapId) {
        this.commandMapId = commandMapId;
    }

    public int getInsect() {
        return insect;
    }

    public void setInsect(int insect) {
        this.insect = insect;
    }
}
