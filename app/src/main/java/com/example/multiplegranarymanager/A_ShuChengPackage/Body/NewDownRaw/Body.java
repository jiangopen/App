package com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw;

import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product.Value;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.SubServerParamsBXZJProfile.SubServerParamsBXZJProfile;

import java.util.List;
import java.util.Map;

/**
 * 1./session 登录
 * 需要
 * username,password
 *
 * 2./userAlertInfos 警报查询
 * 需要
 * asc,pageIndex,pageSize,unRead
 *
 * 3./batchDeviceData 设备信息
 * deviceKeyList,productKey
 *
 * 4./historyData 历史记录查询
 * asc,deviceKey,endTime,startTime
 *
 * 5./newDownRaw 02 实测发送指令
 * commandType,moduleName,profile
 *
 * 6./newDownRaw 03 实测接收数据
 * commandType,measureId
 *
 * 7./deviceparamdata 手动和智能通风切换
 * deviceKey,extraInfo,productKey
 *
 * 8./smart-wind/v1/start 通风模式判断
 * password,username,productKeyList
 *
 * */
public class Body {
    private String username;
    private String password;
    private Integer asc;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer unRead;
    private String productKey;
    private List<String> deviceKeyList;
    private String deviceKey;
    private Long endTime;
    private Long startTime;
    private String commandType;
    private String moduleName;
    private String measureId;
    private List<SubServerParamsBXZJProfile> profile;
    private Map<String, Value> extraInfo;
    private List<String> productKeyList;

    public List<String> getProductKeyList() {
        return productKeyList;
    }

    public void setProductKeyList(List<String> productKeyList) {
        this.productKeyList = productKeyList;
    }

    public Map<String, Value> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, Value> extraInfo) {
        this.extraInfo = extraInfo;
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

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public List<SubServerParamsBXZJProfile> getProfile() {
        return profile;
    }

    public void setProfile(List<SubServerParamsBXZJProfile> profile) {
        this.profile = profile;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAsc() {
        return asc;
    }

    public void setAsc(Integer asc) {
        this.asc = asc;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getUnRead() {
        return unRead;
    }

    public void setUnRead(Integer unRead) {
        this.unRead = unRead;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public List<String> getDeviceKeyList() {
        return deviceKeyList;
    }

    public void setDeviceKeyList(List<String> deviceKeyList) {
        this.deviceKeyList = deviceKeyList;
    }
}
