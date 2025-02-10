package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapData;

import java.util.List;

public class CountMultiple {
    private String commandMapId;
    private Integer count;
    private String dataType;
    private String url;
    private String commandContent;
    private String measureType;
    private CommandJson commandJson;
//    private CommandMapData commandMap;
    private CommandMapBean.Data commandMap;
    private String version;

    public CommandMapBean.Data getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(CommandMapBean.Data commandMap) {
        this.commandMap = commandMap;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public CommandJson getCommandJson() {
        return commandJson;
    }

    public void setCommandJson(CommandJson commandJson) {
        this.commandJson = commandJson;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public String getCommandContent() {
        return commandContent;
    }

    public void setCommandContent(String commandContent) {
        this.commandContent = commandContent;
    }

    public String getCommandMapId() {
        return commandMapId;
    }

    public void setCommandMapId(String commandMapId) {
        this.commandMapId = commandMapId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class CommandJson {
        private List<String> ids;

        public List<String> getIds() {
            return ids;
        }

        public void setIds(List<String> ids) {
            this.ids = ids;
        }
    }
}