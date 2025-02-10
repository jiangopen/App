package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap;

public class CommandMapBean {
    private int code;
    private String msg;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String commandMapId;
        private String version;
        private CommandMapData data;

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public CommandMapData getData() {
            return data;
        }

        public void setData(CommandMapData data) {
            this.data = data;
        }
    }
}
