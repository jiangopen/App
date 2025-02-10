package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;

public class EnergyBody {
    private String GranaryName;
    private String name;
    private Float aI;
    private Float aU;
    private Float activeElectricEnergy;
    private Float bI;
    private Float bU;
    private Float cI;
    private Float cU;
    private Float frequency;
    private Float powerFactor;

    public EnergyBody(String granaryName, String name, Float aI, Float aU, Float activeElectricEnergy, Float bI, Float bU, Float cI, Float cU, Float frequency, Float powerFactor) {
        GranaryName = granaryName;
        this.name = name;
        this.aI = aI;
        this.aU = aU;
        this.activeElectricEnergy = activeElectricEnergy;
        this.bI = bI;
        this.bU = bU;
        this.cI = cI;
        this.cU = cU;
        this.frequency = frequency;
        this.powerFactor = powerFactor;
    }

    public String getGranaryName() {
        return GranaryName;
    }

    public void setGranaryName(String granaryName) {
        GranaryName = granaryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getaI() {
        return aI;
    }

    public void setaI(Float aI) {
        this.aI = aI;
    }

    public Float getaU() {
        return aU;
    }

    public void setaU(Float aU) {
        this.aU = aU;
    }

    public Float getActiveElectricEnergy() {
        return activeElectricEnergy;
    }

    public void setActiveElectricEnergy(Float activeElectricEnergy) {
        this.activeElectricEnergy = activeElectricEnergy;
    }

    public Float getbI() {
        return bI;
    }

    public void setbI(Float bI) {
        this.bI = bI;
    }

    public Float getbU() {
        return bU;
    }

    public void setbU(Float bU) {
        this.bU = bU;
    }

    public Float getcI() {
        return cI;
    }

    public void setcI(Float cI) {
        this.cI = cI;
    }

    public Float getcU() {
        return cU;
    }

    public void setcU(Float cU) {
        this.cU = cU;
    }

    public Float getFrequency() {
        return frequency;
    }

    public void setFrequency(Float frequency) {
        this.frequency = frequency;
    }

    public Float getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(Float powerFactor) {
        this.powerFactor = powerFactor;
    }
}
