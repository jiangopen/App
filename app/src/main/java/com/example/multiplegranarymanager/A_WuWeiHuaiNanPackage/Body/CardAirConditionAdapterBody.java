package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body;

public class CardAirConditionAdapterBody {
    private int Tag;
    private String commandMapId;
    private String url;
    private String extensionNumber;
    private String id;
    private String inspurAddress;
    private String name;
    private String type;
    private String temp;
    private String humidity;
    private String status;
    private String coldOpenStatus;
    private String hotOpenStatus;
    private String dehumidifierOpenStatus;
    private String windOpenStatus;
    private String closeStatus;

    public CardAirConditionAdapterBody(int tag, String commandMapId, String url, String extensionNumber, String id, String inspurAddress, String name, String type, String temp, String humidity, String status, String coldOpenStatus, String hotOpenStatus, String dehumidifierOpenStatus, String windOpenStatus, String closeStatus) {
        Tag = tag;
        this.commandMapId = commandMapId;
        this.url = url;
        this.extensionNumber = extensionNumber;
        this.id = id;
        this.inspurAddress = inspurAddress;
        this.name = name;
        this.type = type;
        this.temp = temp;
        this.humidity = humidity;
        this.status = status;
        this.coldOpenStatus = coldOpenStatus;
        this.hotOpenStatus = hotOpenStatus;
        this.dehumidifierOpenStatus = dehumidifierOpenStatus;
        this.windOpenStatus = windOpenStatus;
        this.closeStatus = closeStatus;
    }

    public int getTag() {
        return Tag;
    }

    public void setTag(int tag) {
        Tag = tag;
    }

    public String getCommandMapId() {
        return commandMapId;
    }

    public void setCommandMapId(String commandMapId) {
        this.commandMapId = commandMapId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspurAddress() {
        return inspurAddress;
    }

    public void setInspurAddress(String inspurAddress) {
        this.inspurAddress = inspurAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColdOpenStatus() {
        return coldOpenStatus;
    }

    public void setColdOpenStatus(String coldOpenStatus) {
        this.coldOpenStatus = coldOpenStatus;
    }

    public String getHotOpenStatus() {
        return hotOpenStatus;
    }

    public void setHotOpenStatus(String hotOpenStatus) {
        this.hotOpenStatus = hotOpenStatus;
    }

    public String getDehumidifierOpenStatus() {
        return dehumidifierOpenStatus;
    }

    public void setDehumidifierOpenStatus(String dehumidifierOpenStatus) {
        this.dehumidifierOpenStatus = dehumidifierOpenStatus;
    }

    public String getWindOpenStatus() {
        return windOpenStatus;
    }

    public void setWindOpenStatus(String windOpenStatus) {
        this.windOpenStatus = windOpenStatus;
    }

    public String getCloseStatus() {
        return closeStatus;
    }

    public void setCloseStatus(String closeStatus) {
        this.closeStatus = closeStatus;
    }
}
