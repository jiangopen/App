package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Energy;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;

public class EnergyConsumptionList {
    private Float aI;
    private Float aU;
    private Float activeElectricEnergy;
    private Float bI;
    private Float bU;
    private Float cI;
    private Float cU;
    private Float frequency;
    private HardwareListData hardwareInfo;
    private Float powerFactor;

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

    public HardwareListData getHardwareInfo() {
        return hardwareInfo;
    }

    public void setHardwareInfo(HardwareListData hardwareInfo) {
        this.hardwareInfo = hardwareInfo;
    }

    public Float getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(Float powerFactor) {
        this.powerFactor = powerFactor;
    }
}
