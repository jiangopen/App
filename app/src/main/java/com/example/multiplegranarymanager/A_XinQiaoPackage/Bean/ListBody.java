package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean;

public class ListBody {
    private String commandMapId;
    private TestInTimeTwoBean data;
    private int errCode;
    private String errMsg;
    private String url;

    public String getCommandMapId() {
        return commandMapId;
    }

    public void setCommandMapId(String commandMapId) {
        this.commandMapId = commandMapId;
    }

    public TestInTimeTwoBean getData() {
        return data;
    }

    public void setData(TestInTimeTwoBean data) {
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
