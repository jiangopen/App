package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.History;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class NitrogenSetting implements Parcelable {
    private List<String> patternV2OpenHList;
    private String pattern;
    private List<String> patternV1OpenHList;

    protected NitrogenSetting(Parcel in) {
        patternV2OpenHList = in.createStringArrayList();
        pattern = in.readString();
        patternV1OpenHList = in.createStringArrayList();
    }

    public static final Creator<NitrogenSetting> CREATOR = new Creator<NitrogenSetting>() {
        @Override
        public NitrogenSetting createFromParcel(Parcel in) {
            return new NitrogenSetting(in);
        }

        @Override
        public NitrogenSetting[] newArray(int size) {
            return new NitrogenSetting[size];
        }
    };

    public List<String> getPatternV2OpenHList() {
        return patternV2OpenHList;
    }

    public void setPatternV2OpenHList(List<String> patternV2OpenHList) {
        this.patternV2OpenHList = patternV2OpenHList;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List<String> getPatternV1OpenHList() {
        return patternV1OpenHList;
    }

    public void setPatternV1OpenHList(List<String> patternV1OpenHList) {
        this.patternV1OpenHList = patternV1OpenHList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeStringList(patternV2OpenHList);
        dest.writeString(pattern);
        dest.writeStringList(patternV1OpenHList);
    }
}
