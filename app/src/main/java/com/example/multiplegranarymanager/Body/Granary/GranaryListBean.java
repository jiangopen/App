package com.example.multiplegranarymanager.Body.Granary;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GranaryListBean implements Parcelable{
    private int code;
    private String msg;
    private ArrayList<Data> data;

    protected GranaryListBean(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        data = in.createTypedArrayList(Data.CREATOR);
    }

    public static final Creator<GranaryListBean> CREATOR = new Creator<GranaryListBean>() {
        @Override
        public GranaryListBean createFromParcel(Parcel in) {
            return new GranaryListBean(in);
        }

        @Override
        public GranaryListBean[] newArray(int size) {
            return new GranaryListBean[size];
        }
    };

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeInt(data.size()); // 写入List的大小

        for (Data dataItem : data) {
            dest.writeString(dataItem.granaryName);
            dest.writeString(dataItem.granaryKey);
            dest.writeString(dataItem.url);
            dest.writeString(dataItem.commandMapId);
        }
    }



    public static class Data implements Parcelable{
        private String granaryName;
        private String granaryKey;
        private String url;
        private String commandMapId;

        public String getGranaryName() {
            return granaryName;
        }

        public void setGranaryName(String granaryName) {
            this.granaryName = granaryName;
        }

        public String getGranaryKey() {
            return granaryKey;
        }

        public void setGranaryKey(String granaryKey) {
            this.granaryKey = granaryKey;
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
            dest.writeString(granaryName);
            dest.writeString(granaryKey);
            dest.writeString(url);
            dest.writeString(commandMapId);
        }
        public Data(Parcel in) {
            granaryName = in.readString();
            granaryKey = in.readString();
            url = in.readString();
            commandMapId = in.readString();
        }

        // 实现 CREATOR 接口
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
    }
}
