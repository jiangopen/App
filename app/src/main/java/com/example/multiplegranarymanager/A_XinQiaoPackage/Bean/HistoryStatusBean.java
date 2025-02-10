package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class HistoryStatusBean {
    private int code;
    private String msg;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<StatusList> list;

        public List<StatusList> getList() {
            return list;
        }

        public void setList(List<StatusList> list) {
            this.list = list;
        }
    }

    public class StatusList implements Parcelable{
        private StatusListData data;
        private int errCode;
        private String errMsg;
        private String url;
        private String commandMapId;

        protected StatusList(Parcel in) {
            data = in.readParcelable(StatusListData.class.getClassLoader());
            errCode = in.readInt();
            errMsg = in.readString();
            url = in.readString();
            commandMapId = in.readString();
        }

        public final Creator<StatusList> CREATOR = new Creator<StatusList>() {
            @Override
            public StatusList createFromParcel(Parcel in) {
                return new StatusList(in);
            }

            @Override
            public StatusList[] newArray(int size) {
                return new StatusList[size];
            }
        };

        public StatusListData getData() {
            return data;
        }

        public void setData(StatusListData data) {
            this.data = data;
        }

        public int getErrCode() {
            return errCode;
        }

        public void setErrCode(int errCode) {
            this.errCode = errCode;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeParcelable(data, flags);
            dest.writeInt(errCode);
            dest.writeString(errMsg);
            dest.writeString(url);
            dest.writeString(commandMapId);
        }
    }

    public class StatusListData implements Parcelable{
        private String date;
        private String dataType;
        private String commandMapId;
        private Long time;
        private DataContent dataContent;

        protected StatusListData(Parcel in) {
            date = in.readString();
            dataType = in.readString();
            commandMapId = in.readString();
            if (in.readByte() == 0) {
                time = null;
            } else {
                time = in.readLong();
            }
            dataContent = in.readParcelable(DataContent.class.getClassLoader());
        }

        public final Creator<StatusListData> CREATOR = new Creator<StatusListData>() {
            @Override
            public StatusListData createFromParcel(Parcel in) {
                return new StatusListData(in);
            }

            @Override
            public StatusListData[] newArray(int size) {
                return new StatusListData[size];
            }
        };

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public DataContent getDataContent() {
            return dataContent;
        }

        public void setDataContent(DataContent dataContent) {
            this.dataContent = dataContent;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(date);
            dest.writeString(dataType);
            dest.writeString(commandMapId);
            if (time == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeLong(time);
            }
            dest.writeParcelable(dataContent, flags);
        }
    }

    public static class DataContent implements Parcelable {
        private int volume;
        private int pointCount;
        private int mass;
        private int progress;
        private int height;
        private String errFlag;
        private String scanStatus;
        private String measureCount;
        private String version;

        protected DataContent(Parcel in) {
            volume = in.readInt();
            pointCount = in.readInt();
            mass = in.readInt();
            progress = in.readInt();
            height = in.readInt();
            errFlag = in.readString();
            scanStatus = in.readString();
            measureCount = in.readString();
            version = in.readString();
        }

        public static Creator<DataContent> CREATOR = new Creator<DataContent>() {
            @Override
            public DataContent createFromParcel(Parcel in) {
                return new DataContent(in);
            }

            @Override
            public DataContent[] newArray(int size) {
                return new DataContent[size];
            }
        };

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public int getPointCount() {
            return pointCount;
        }

        public void setPointCount(int pointCount) {
            this.pointCount = pointCount;
        }

        public int getMass() {
            return mass;
        }

        public void setMass(int mass) {
            this.mass = mass;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getErrFlag() {
            return errFlag;
        }

        public void setErrFlag(String errFlag) {
            this.errFlag = errFlag;
        }

        public String getScanStatus() {
            return scanStatus;
        }

        public void setScanStatus(String scanStatus) {
            this.scanStatus = scanStatus;
        }

        public String getMeasureCount() {
            return measureCount;
        }

        public void setMeasureCount(String measureCount) {
            this.measureCount = measureCount;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeInt(volume);
            dest.writeInt(pointCount);
            dest.writeInt(mass);
            dest.writeInt(progress);
            dest.writeInt(height);
            dest.writeString(errFlag);
            dest.writeString(scanStatus);
            dest.writeString(measureCount);
            dest.writeString(version);
        }
    }
}
