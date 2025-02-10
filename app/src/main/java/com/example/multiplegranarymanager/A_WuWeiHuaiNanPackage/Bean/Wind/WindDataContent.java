package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.Wind;

public class WindDataContent {
    private CurData curData;
    private String pattern;
    private Boolean serverAutoScan;
    private VentilationSetting ventilationSetting;

    public CurData getCurData() {
        return curData;
    }

    public void setCurData(CurData curData) {
        this.curData = curData;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Boolean getServerAutoScan() {
        return serverAutoScan;
    }

    public void setServerAutoScan(Boolean serverAutoScan) {
        this.serverAutoScan = serverAutoScan;
    }

    public VentilationSetting getVentilationSetting() {
        return ventilationSetting;
    }

    public void setVentilationSetting(VentilationSetting ventilationSetting) {
        this.ventilationSetting = ventilationSetting;
    }
}
