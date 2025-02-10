package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product;

public class Value {
    private String Name;
    private Object Value;
    private Boolean adminEditable;
    private Boolean adminVisible;
    private Boolean userEditable;
    private Boolean userVisible;

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

    public Boolean getAdminEditable() {
        return adminEditable;
    }

    public void setAdminEditable(Boolean adminEditable) {
        this.adminEditable = adminEditable;
    }

    public Boolean getAdminVisible() {
        return adminVisible;
    }

    public void setAdminVisible(Boolean adminVisible) {
        this.adminVisible = adminVisible;
    }

    public Boolean getUserEditable() {
        return userEditable;
    }

    public void setUserEditable(Boolean userEditable) {
        this.userEditable = userEditable;
    }

    public Boolean getUserVisible() {
        return userVisible;
    }

    public void setUserVisible(Boolean userVisible) {
        this.userVisible = userVisible;
    }
}
