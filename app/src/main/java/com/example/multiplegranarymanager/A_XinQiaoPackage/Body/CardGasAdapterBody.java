package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;

public class CardGasAdapterBody {
    private int inspurChannelNumberId;
    private String inspurExtensionAddress;
    private String inspurChannelNumber;
    private String detectType;
    private String url;
    private String commandMapId;
    private Double O2;
    private Double N2;
    private Double PH3;
    private Double CO2;

    public CardGasAdapterBody(int inspurChannelNumberId, String inspurExtensionAddress, String inspurChannelNumber, String detectType, String url, String commandMapId, Double o2, Double n2, Double PH3, Double CO2) {
        this.inspurChannelNumberId = inspurChannelNumberId;
        this.inspurExtensionAddress = inspurExtensionAddress;
        this.inspurChannelNumber = inspurChannelNumber;
        this.detectType = detectType;
        this.url = url;
        this.commandMapId = commandMapId;
        O2 = o2;
        N2 = n2;
        this.PH3 = PH3;
        this.CO2 = CO2;
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

    public Double getO2() {
        return O2;
    }

    public void setO2(Double o2) {
        O2 = o2;
    }

    public Double getN2() {
        return N2;
    }

    public void setN2(Double n2) {
        N2 = n2;
    }

    public Double getPH3() {
        return PH3;
    }

    public void setPH3(Double PH3) {
        this.PH3 = PH3;
    }

    public Double getCO2() {
        return CO2;
    }

    public void setCO2(Double CO2) {
        this.CO2 = CO2;
    }
}
