package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapData;

public class UpDateCommand {
    private String url;
    private CommandMapBean.Data commandMap;
    private String version;
    private String commandMapId;
    private CommandMapData data;

    public CommandMapData getData() {
        return data;
    }

    public void setData(CommandMapData data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public CommandMapBean.Data getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(CommandMapBean.Data commandMap) {
        this.commandMap = commandMap;
    }
}
