package com.example.multiplegranarymanager.Body.NewDownRaw;

import java.util.List;

public class NewDownRaw02Body {
    private String commandType;
    private String moduleName;
    private List<Profile> profile;

    public NewDownRaw02Body(String commandType, String moduleName, List<Profile> profile) {
        this.commandType = commandType;
        this.moduleName = moduleName;
        this.profile = profile;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<Profile> getProfile() {
        return profile;
    }

    public void setProfile(List<Profile> profile) {
        this.profile = profile;
    }
}
