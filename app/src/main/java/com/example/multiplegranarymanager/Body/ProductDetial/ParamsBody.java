package com.example.multiplegranarymanager.Body.ProductDetial;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ParamsBody implements Parcelable{
    private String productName;
    private String productKey;
    private String deviceKey;
    private String deviceType;
    private String nickName;
    private String moduleName;
    private String granaryId;
    private String granaryFen;

    public ParamsBody(String productName, String productKey, String deviceKey, String deviceType, String nickName, String moduleName, String granaryId, String granaryFen) {
        this.productName = productName;
        this.productKey = productKey;
        this.deviceKey = deviceKey;
        this.deviceType = deviceType;
        this.nickName = nickName;
        this.moduleName = moduleName;
        this.granaryId = granaryId;
        this.granaryFen = granaryFen;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getGranaryId() {
        return granaryId;
    }

    public void setGranaryId(String granaryId) {
        this.granaryId = granaryId;
    }

    public String getGranaryFen() {
        return granaryFen;
    }

    public void setGranaryFen(String granaryFen) {
        this.granaryFen = granaryFen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }

    public static final Creator<ParamsBody> CREATOR = new Creator<ParamsBody>() {
        @Override
        public ParamsBody createFromParcel(Parcel source) {
            return new ParamsBody(source);
        }

        @Override
        public ParamsBody[] newArray(int size) {
            return new ParamsBody[size];
        }
    };

    public ParamsBody(Parcel source) {
        productName = source.readString();
        productKey = source.readString();
        deviceKey = source.readString();
        deviceType = source.readString();
        nickName = source.readString();
        moduleName = source.readString();
        granaryId = source.readString();
        granaryFen = source.readString();
    }
}
