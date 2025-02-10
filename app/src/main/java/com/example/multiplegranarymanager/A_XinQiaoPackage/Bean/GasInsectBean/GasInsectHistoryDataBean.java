package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.GasInsectBean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.HistoryCountMultipleBean;

import java.util.List;

public class GasInsectHistoryDataBean implements Parcelable {
    private int code;
    private String msg;
    private List<Data> data;

    protected GasInsectHistoryDataBean(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        data = in.createTypedArrayList(Data.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GasInsectHistoryDataBean> CREATOR = new Creator<GasInsectHistoryDataBean>() {
        @Override
        public GasInsectHistoryDataBean createFromParcel(Parcel in) {
            return new GasInsectHistoryDataBean(in);
        }

        @Override
        public GasInsectHistoryDataBean[] newArray(int size) {
            return new GasInsectHistoryDataBean[size];
        }
    };

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data implements Parcelable {
        private String commandMapId;
        private HistoryCountMultipleBean.DataContent dataContent;
        private String dataType;
        private String date;
        private long time;

        protected Data(Parcel in) {
            commandMapId = in.readString();
            dataContent = in.readParcelable(HistoryCountMultipleBean.DataContent.class.getClassLoader());
            dataType = in.readString();
            date = in.readString();
            time = in.readLong();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
        }

        public HistoryCountMultipleBean.DataContent getDataContent() {
            return dataContent;
        }

        public void setDataContent(HistoryCountMultipleBean.DataContent dataContent) {
            this.dataContent = dataContent;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(commandMapId);
            dest.writeParcelable(dataContent, flags);
            dest.writeString(dataType);
            dest.writeString(date);
            dest.writeLong(time);
        }
    }
}
