package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.History;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class GranarySetting implements Parcelable {
    private int ceng;
    private int lan;
    private int moistureCeng;
    private int moistureLan;
    private int moistureZu;
    private String name;
    private String type;
    private int zu;

    public int getCeng() {
        return ceng;
    }

    public void setCeng(int ceng) {
        this.ceng = ceng;
    }

    public int getLan() {
        return lan;
    }

    public void setLan(int lan) {
        this.lan = lan;
    }

    public int getMoistureCeng() {
        return moistureCeng;
    }

    public void setMoistureCeng(int moistureCeng) {
        this.moistureCeng = moistureCeng;
    }

    public int getMoistureLan() {
        return moistureLan;
    }

    public void setMoistureLan(int moistureLan) {
        this.moistureLan = moistureLan;
    }

    public int getMoistureZu() {
        return moistureZu;
    }

    public void setMoistureZu(int moistureZu) {
        this.moistureZu = moistureZu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getZu() {
        return zu;
    }

    public void setZu(int zu) {
        this.zu = zu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GranarySetting> CREATOR = new Creator<GranarySetting>() {
        @Override
        public GranarySetting createFromParcel(Parcel in) {
            return new GranarySetting(in);
        }

        @Override
        public GranarySetting[] newArray(int size) {
            return new GranarySetting[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(ceng);
        dest.writeInt(lan);
        dest.writeInt(moistureCeng);
        dest.writeInt(moistureLan);
        dest.writeInt(moistureZu);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(zu);
    }
    protected GranarySetting(Parcel in) {
        ceng = in.readInt();
        lan = in.readInt();
        moistureCeng = in.readInt();
        moistureLan = in.readInt();
        moistureZu = in.readInt();
        name = in.readString();
        type = in.readString();
        zu = in.readInt();
    }

}
