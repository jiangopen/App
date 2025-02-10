package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.History;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class LockWarn implements Parcelable {
    private Double ph3WarnDown;
    private Double o2WarnDown;
    private Double ph3WarnUp;
    private Double o2WarnUp;
    private String pwd;

    protected LockWarn(Parcel in) {
        if (in.readByte() == 0) {
            ph3WarnDown = null;
        } else {
            ph3WarnDown = in.readDouble();
        }
        if (in.readByte() == 0) {
            o2WarnDown = null;
        } else {
            o2WarnDown = in.readDouble();
        }
        if (in.readByte() == 0) {
            ph3WarnUp = null;
        } else {
            ph3WarnUp = in.readDouble();
        }
        if (in.readByte() == 0) {
            o2WarnUp = null;
        } else {
            o2WarnUp = in.readDouble();
        }
        pwd = in.readString();
    }

    public static final Creator<LockWarn> CREATOR = new Creator<LockWarn>() {
        @Override
        public LockWarn createFromParcel(Parcel in) {
            return new LockWarn(in);
        }

        @Override
        public LockWarn[] newArray(int size) {
            return new LockWarn[size];
        }
    };

    public Double getPh3WarnDown() {
        return ph3WarnDown;
    }

    public void setPh3WarnDown(Double ph3WarnDown) {
        this.ph3WarnDown = ph3WarnDown;
    }

    public Double getO2WarnDown() {
        return o2WarnDown;
    }

    public void setO2WarnDown(Double o2WarnDown) {
        this.o2WarnDown = o2WarnDown;
    }

    public Double getPh3WarnUp() {
        return ph3WarnUp;
    }

    public void setPh3WarnUp(Double ph3WarnUp) {
        this.ph3WarnUp = ph3WarnUp;
    }

    public Double getO2WarnUp() {
        return o2WarnUp;
    }

    public void setO2WarnUp(Double o2WarnUp) {
        this.o2WarnUp = o2WarnUp;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (ph3WarnDown == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(ph3WarnDown);
        }
        if (o2WarnDown == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(o2WarnDown);
        }
        if (ph3WarnUp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(ph3WarnUp);
        }
        if (o2WarnUp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(o2WarnUp);
        }
        dest.writeString(pwd);
    }
}
