package com.example.multiplegranarymanager.Bean;
public class ValueBean {
    private String Name;
    private Object Value;
    private boolean adminEditable;
    private boolean adminVisible;
    private boolean userEditable;
    private boolean userVisible;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Object getValue() {
        return Value;
    }

    public void setValue(Object value) {
        Value = value;
    }

    public boolean isAdminEditable() {
        return adminEditable;
    }

    public void setAdminEditable(boolean adminEditable) {
        this.adminEditable = adminEditable;
    }

    public boolean isAdminVisible() {
        return adminVisible;
    }

    public void setAdminVisible(boolean adminVisible) {
        this.adminVisible = adminVisible;
    }

    public boolean isUserEditable() {
        return userEditable;
    }

    public void setUserEditable(boolean userEditable) {
        this.userEditable = userEditable;
    }

    public boolean isUserVisible() {
        return userVisible;
    }

    public void setUserVisible(boolean userVisible) {
        this.userVisible = userVisible;
    }
}
