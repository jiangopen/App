package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;



import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.InspurChannelNumberMap;

import java.util.ArrayList;

public class GasInsectBody{
    private String inspurExtensionAddress;
    private ArrayList<InspurChannelNumberMap> inspurChannelNumberMap;

    public String getInspurExtensionAddress() {
        return inspurExtensionAddress;
    }

    public void setInspurExtensionAddress(String inspurExtensionAddress) {
        this.inspurExtensionAddress = inspurExtensionAddress;
    }

    public ArrayList<InspurChannelNumberMap> getInspurChannelNumberMap() {
        return inspurChannelNumberMap;
    }

    public void setInspurChannelNumberMap(ArrayList<InspurChannelNumberMap> inspurChannelNumberMap) {
        this.inspurChannelNumberMap = inspurChannelNumberMap;
    }
}
