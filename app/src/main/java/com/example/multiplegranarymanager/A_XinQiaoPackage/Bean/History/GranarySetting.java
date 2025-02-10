package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.History;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class GranarySetting implements Parcelable{
    private int lan;
    private int ceng;
    private int zu;
    private int moistureZu;
    private int moistureCeng;
    private int moistureLan;
    private String type;
    private InfoCard infoCard;
    private LockWarn lockWarn;
    private NitrogenSetting nitrogenSetting;
    private Double tempWarnDown;
    private Double tempWarnUp;
    private Double height;
    private Double length;
    private Double width;
    private String entranceGuardPwd;
    private String videoPwd;
    private String entranceGuardPort;
    private String entranceGuardName;
    private String videoPort;
    private String moistureType;
    private String name;
    private String grainType;
    private String videoIp;
    private String entranceGuardIp;

    protected GranarySetting(Parcel in) {
        lan = in.readInt();
        ceng = in.readInt();
        zu = in.readInt();
        moistureZu = in.readInt();
        moistureCeng = in.readInt();
        moistureLan = in.readInt();
        type = in.readString();
        if (in.readByte() == 0) {
            tempWarnDown = null;
        } else {
            tempWarnDown = in.readDouble();
        }
        if (in.readByte() == 0) {
            tempWarnUp = null;
        } else {
            tempWarnUp = in.readDouble();
        }
        if (in.readByte() == 0) {
            height = null;
        } else {
            height = in.readDouble();
        }
        if (in.readByte() == 0) {
            length = null;
        } else {
            length = in.readDouble();
        }
        if (in.readByte() == 0) {
            width = null;
        } else {
            width = in.readDouble();
        }
        entranceGuardPwd = in.readString();
        videoPwd = in.readString();
        entranceGuardPort = in.readString();
        entranceGuardName = in.readString();
        videoPort = in.readString();
        moistureType = in.readString();
        name = in.readString();
        grainType = in.readString();
        videoIp = in.readString();
        entranceGuardIp = in.readString();
        infoCard = in.readParcelable(InfoCard.class.getClassLoader());
        lockWarn = in.readParcelable(LockWarn.class.getClassLoader());
        nitrogenSetting = in.readParcelable(NitrogenSetting.class.getClassLoader());
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

    public int getLan() {
        return lan;
    }

    public void setLan(int lan) {
        this.lan = lan;
    }

    public int getCeng() {
        return ceng;
    }

    public void setCeng(int ceng) {
        this.ceng = ceng;
    }

    public int getZu() {
        return zu;
    }

    public void setZu(int zu) {
        this.zu = zu;
    }

    public int getMoistureZu() {
        return moistureZu;
    }

    public void setMoistureZu(int moistureZu) {
        this.moistureZu = moistureZu;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InfoCard getInfoCard() {
        return infoCard;
    }

    public void setInfoCard(InfoCard infoCard) {
        this.infoCard = infoCard;
    }

    public LockWarn getLockWarn() {
        return lockWarn;
    }

    public void setLockWarn(LockWarn lockWarn) {
        this.lockWarn = lockWarn;
    }

    public NitrogenSetting getNitrogenSetting() {
        return nitrogenSetting;
    }

    public void setNitrogenSetting(NitrogenSetting nitrogenSetting) {
        this.nitrogenSetting = nitrogenSetting;
    }

    public Double getTempWarnDown() {
        return tempWarnDown;
    }

    public void setTempWarnDown(Double tempWarnDown) {
        this.tempWarnDown = tempWarnDown;
    }

    public Double getTempWarnUp() {
        return tempWarnUp;
    }

    public void setTempWarnUp(Double tempWarnUp) {
        this.tempWarnUp = tempWarnUp;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public String getEntranceGuardPwd() {
        return entranceGuardPwd;
    }

    public void setEntranceGuardPwd(String entranceGuardPwd) {
        this.entranceGuardPwd = entranceGuardPwd;
    }

    public String getVideoPwd() {
        return videoPwd;
    }

    public void setVideoPwd(String videoPwd) {
        this.videoPwd = videoPwd;
    }

    public String getEntranceGuardPort() {
        return entranceGuardPort;
    }

    public void setEntranceGuardPort(String entranceGuardPort) {
        this.entranceGuardPort = entranceGuardPort;
    }

    public String getEntranceGuardName() {
        return entranceGuardName;
    }

    public void setEntranceGuardName(String entranceGuardName) {
        this.entranceGuardName = entranceGuardName;
    }

    public String getVideoPort() {
        return videoPort;
    }

    public void setVideoPort(String videoPort) {
        this.videoPort = videoPort;
    }

    public String getMoistureType() {
        return moistureType;
    }

    public void setMoistureType(String moistureType) {
        this.moistureType = moistureType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrainType() {
        return grainType;
    }

    public void setGrainType(String grainType) {
        this.grainType = grainType;
    }

    public String getVideoIp() {
        return videoIp;
    }

    public void setVideoIp(String videoIp) {
        this.videoIp = videoIp;
    }

    public String getEntranceGuardIp() {
        return entranceGuardIp;
    }

    public void setEntranceGuardIp(String entranceGuardIp) {
        this.entranceGuardIp = entranceGuardIp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(lan);
        dest.writeInt(ceng);
        dest.writeInt(zu);
        dest.writeInt(moistureZu);
        dest.writeInt(moistureCeng);
        dest.writeInt(moistureLan);
        dest.writeString(type);
        if (tempWarnDown == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(tempWarnDown);
        }
        if (tempWarnUp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(tempWarnUp);
        }
        if (height == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(height);
        }
        if (length == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(length);
        }
        if (width == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(width);
        }
        dest.writeString(entranceGuardPwd);
        dest.writeString(videoPwd);
        dest.writeString(entranceGuardPort);
        dest.writeString(entranceGuardName);
        dest.writeString(videoPort);
        dest.writeString(moistureType);
        dest.writeString(name);
        dest.writeString(grainType);
        dest.writeString(videoIp);
        dest.writeString(entranceGuardIp);
        dest.writeParcelable(infoCard,flags);
        dest.writeParcelable(lockWarn,flags);
        dest.writeParcelable(nitrogenSetting,flags);
    }
}
