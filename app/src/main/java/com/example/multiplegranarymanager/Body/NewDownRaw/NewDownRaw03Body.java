package com.example.multiplegranarymanager.Body.NewDownRaw;

public class NewDownRaw03Body {
    private String commandType;
    private String measureId;

    public NewDownRaw03Body(String commandType, String measureId) {
        this.commandType = commandType;
        this.measureId = measureId;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }
}
