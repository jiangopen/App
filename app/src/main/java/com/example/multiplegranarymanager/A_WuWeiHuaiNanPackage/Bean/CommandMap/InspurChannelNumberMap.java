package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class InspurChannelNumberMap implements Parcelable{
    private int id;
    private String inspurChannelNumber;
    private String strobeChannelNumber;
    public String getInspurChannelNumber() {
        return inspurChannelNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInspurChannelNumber(String inspurChannelNumber) {
        this.inspurChannelNumber = inspurChannelNumber;
    }

    public String getStrobeChannelNumber() {
        return strobeChannelNumber;
    }

    public void setStrobeChannelNumber(String strobeChannelNumber) {
        this.strobeChannelNumber = strobeChannelNumber;
    }
    public static final Creator<InspurChannelNumberMap> CREATOR = new Creator<InspurChannelNumberMap>() {
        @Override
        public InspurChannelNumberMap createFromParcel(Parcel source) {
            return new InspurChannelNumberMap(source);
        }

        @Override
        public InspurChannelNumberMap[] newArray(int size) {
            return new InspurChannelNumberMap[size];
        }
    };
    public InspurChannelNumberMap(Parcel source) {
        inspurChannelNumber = source.readString();
        strobeChannelNumber = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(inspurChannelNumber);
        dest.writeString(strobeChannelNumber);
    }
}
