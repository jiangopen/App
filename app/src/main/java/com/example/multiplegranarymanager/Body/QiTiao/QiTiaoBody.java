package com.example.multiplegranarymanager.Body.QiTiao;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class QiTiaoBody implements Parcelable {
    private String productName;
    private String moudleName;
    private String granaryId;
    private String granaryFen;
    private String nickName;
    private String productKey;
    private String deviceKey;
    private String date;
    private String N2_list_00;
    private String pressure_list_01;
    private String QiTiaoStatus_list_02;
    private String FaMengStatus_list_03;
    private String FengJiStatus_list_04;
    private String N2QiJiStatus_list_05;
    private String ChunDu_list_06;
    private String LiuLiang_list_07;
    private String PressureNum_list_08;
    private String Temperature_list_09;
    private String LiuLiangNum_list_10;

    public QiTiaoBody(String productName, String moudleName, String granaryId, String granaryFen, String nickName, String productKey, String deviceKey, String date, String n2_list_00, String pressure_list_01, String qiTiaoStatus_list_02, String faMengStatus_list_03, String fengJiStatus_list_04, String n2QiJiStatus_list_05, String chunDu_list_06, String liuLiang_list_07, String pressureNum_list_08, String temperature_list_09, String liuLiangNum_list_10) {
        this.productName = productName;
        this.moudleName = moudleName;
        this.granaryId = granaryId;
        this.granaryFen = granaryFen;
        this.nickName = nickName;
        this.productKey = productKey;
        this.deviceKey = deviceKey;
        this.date = date;
        this.N2_list_00 = n2_list_00;
        this.pressure_list_01 = pressure_list_01;
        this.QiTiaoStatus_list_02 = qiTiaoStatus_list_02;
        this.FaMengStatus_list_03 = faMengStatus_list_03;
        this.FengJiStatus_list_04 = fengJiStatus_list_04;
        this.N2QiJiStatus_list_05 = n2QiJiStatus_list_05;
        this.ChunDu_list_06 = chunDu_list_06;
        this.LiuLiang_list_07 = liuLiang_list_07;
        this.PressureNum_list_08 = pressureNum_list_08;
        this.Temperature_list_09 = temperature_list_09;
        this.LiuLiangNum_list_10 = liuLiangNum_list_10;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMoudleName() {
        return moudleName;
    }

    public void setMoudleName(String moudleName) {
        this.moudleName = moudleName;
    }

    public String getGranaryId() {
        return granaryId;
    }

    public void setGranaryId(String granaryId) {
        this.granaryId = granaryId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGranaryFen() {
        return granaryFen;
    }

    public void setGranaryFen(String granaryFen) {
        this.granaryFen = granaryFen;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getN2_list_00() {
        return N2_list_00;
    }

    public void setN2_list_00(String n2_list_00) {
        N2_list_00 = n2_list_00;
    }

    public String getPressure_list_01() {
        return pressure_list_01;
    }

    public void setPressure_list_01(String pressure_list_01) {
        this.pressure_list_01 = pressure_list_01;
    }

    public String getQiTiaoStatus_list_02() {
        return QiTiaoStatus_list_02;
    }

    public void setQiTiaoStatus_list_02(String qiTiaoStatus_list_02) {
        QiTiaoStatus_list_02 = qiTiaoStatus_list_02;
    }

    public String getFaMengStatus_list_03() {
        return FaMengStatus_list_03;
    }

    public void setFaMengStatus_list_03(String faMengStatus_list_03) {
        FaMengStatus_list_03 = faMengStatus_list_03;
    }

    public String getFengJiStatus_list_04() {
        return FengJiStatus_list_04;
    }

    public void setFengJiStatus_list_04(String fengJiStatus_list_04) {
        FengJiStatus_list_04 = fengJiStatus_list_04;
    }

    public String getN2QiJiStatus_list_05() {
        return N2QiJiStatus_list_05;
    }

    public void setN2QiJiStatus_list_05(String n2QiJiStatus_list_05) {
        N2QiJiStatus_list_05 = n2QiJiStatus_list_05;
    }

    public String getChunDu_list_06() {
        return ChunDu_list_06;
    }

    public void setChunDu_list_06(String chunDu_list_06) {
        ChunDu_list_06 = chunDu_list_06;
    }

    public String getLiuLiang_list_07() {
        return LiuLiang_list_07;
    }

    public void setLiuLiang_list_07(String liuLiang_list_07) {
        LiuLiang_list_07 = liuLiang_list_07;
    }

    public String getPressureNum_list_08() {
        return PressureNum_list_08;
    }

    public void setPressureNum_list_08(String pressureNum_list_08) {
        PressureNum_list_08 = pressureNum_list_08;
    }

    public String getTemperature_list_09() {
        return Temperature_list_09;
    }

    public void setTemperature_list_09(String temperature_list_09) {
        Temperature_list_09 = temperature_list_09;
    }

    public String getLiuLiangNum_list_10() {
        return LiuLiangNum_list_10;
    }

    public void setLiuLiangNum_list_10(String liuLiangNum_list_10) {
        LiuLiangNum_list_10 = liuLiangNum_list_10;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }
    public static final Creator<QiTiaoBody> CREATOR = new Creator<QiTiaoBody>() {
        @Override
        public QiTiaoBody createFromParcel(Parcel source) {
            return new QiTiaoBody(source);
        }

        @Override
        public QiTiaoBody[] newArray(int size) {
            return new QiTiaoBody[size];
        }
    };
    public QiTiaoBody(Parcel source) {
        productName = source.readString();
        moudleName = source.readString();
        granaryId = source.readString();
        nickName = source.readString();
        productKey = source.readString();
        deviceKey = source.readString();
        date = source.readString();
        N2_list_00 = source.readString();
        pressure_list_01 = source.readString();
        QiTiaoStatus_list_02 = source.readString();
        FaMengStatus_list_03 = source.readString();
        FengJiStatus_list_04 = source.readString();
        N2QiJiStatus_list_05 = source.readString();
        ChunDu_list_06 = source.readString();
        LiuLiang_list_07 = source.readString();
        PressureNum_list_08 = source.readString();
        Temperature_list_09 = source.readString();
        LiuLiangNum_list_10 = source.readString();
    }
}
