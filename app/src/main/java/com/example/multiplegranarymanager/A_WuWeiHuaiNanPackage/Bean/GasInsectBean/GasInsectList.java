package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.GasInsectBean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class GasInsectList implements Parcelable {
    private Double co2;
    private Double o2;
    private Double ph3;
    private Double so2F2;
    private Double n2;
    private int insect;
    private String detectType;
    private String inspurChannelNumber;
    //这个时间是辅助显示时间，气体虫害历史数据显示之前都可以不用管
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    protected GasInsectList(Parcel in) {
        if (in.readByte() == 0) {
            co2 = null;
        } else {
            co2 = in.readDouble();
        }
        if (in.readByte() == 0) {
            o2 = null;
        } else {
            o2 = in.readDouble();
        }
        if (in.readByte() == 0) {
            ph3 = null;
        } else {
            ph3 = in.readDouble();
        }
        if (in.readByte() == 0) {
            so2F2 = null;
        } else {
            so2F2 = in.readDouble();
        }
        if (in.readByte() == 0) {
            n2 = null;
        } else {
            n2 = in.readDouble();
        }
        insect = in.readInt();
        detectType = in.readString();
        inspurChannelNumber = in.readString();
    }

    public static final Creator<GasInsectList> CREATOR = new Creator<GasInsectList>() {
        @Override
        public GasInsectList createFromParcel(Parcel in) {
            return new GasInsectList(in);
        }

        @Override
        public GasInsectList[] newArray(int size) {
            return new GasInsectList[size];
        }
    };

    public Double getN2() {
        return n2;
    }

    public void setN2(Double n2) {
        this.n2 = n2;
    }

    public Double getCo2() {
        return co2;
    }

    public void setCo2(Double co2) {
        this.co2 = co2;
    }

    public Double getO2() {
        return o2;
    }

    public void setO2(Double o2) {
        this.o2 = o2;
    }

    public Double getPh3() {
        return ph3;
    }

    public void setPh3(Double ph3) {
        this.ph3 = ph3;
    }

    public Double getSo2F2() {
        return so2F2;
    }

    public void setSo2F2(Double so2F2) {
        this.so2F2 = so2F2;
    }

    public int getInsect() {
        return insect;
    }

    public void setInsect(int insect) {
        this.insect = insect;
    }

    public String getDetectType() {
        return detectType;
    }

    public void setDetectType(String detectType) {
        this.detectType = detectType;
    }

    public String getInspurChannelNumber() {
        return inspurChannelNumber;
    }

    public void setInspurChannelNumber(String inspurChannelNumber) {
        this.inspurChannelNumber = inspurChannelNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (co2 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(co2);
        }
        if (o2 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(o2);
        }
        if (ph3 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(ph3);
        }
        if (so2F2 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(so2F2);
        }
        if (n2 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(n2);
        }
        dest.writeInt(insect);
        dest.writeString(detectType);
        dest.writeString(inspurChannelNumber);
    }
}
