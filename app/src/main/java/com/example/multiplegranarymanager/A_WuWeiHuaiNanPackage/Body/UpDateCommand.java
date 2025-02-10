package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap.CommandMapBean;

public class UpDateCommand {
    private String url;
    private CommandMapBean.Data commandMap;

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
