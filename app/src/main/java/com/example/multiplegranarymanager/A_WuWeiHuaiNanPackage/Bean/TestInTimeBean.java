package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class TestInTimeBean {
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

    public class Data {
        public List<TestInTimeCount> list;

        public List<TestInTimeCount> getList() {
            return list;
        }

        public void setList(List<TestInTimeCount> list) {
            this.list = list;
        }
    }

    public class TestInTimeCount {
        private String commandMapId;
        private int errCode;
        private String errMsg;
        private String url;
        private TestInTimeCountData data;

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
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

        public TestInTimeCountData getData() {
            return data;
        }

        public void setData(TestInTimeCountData data) {
            this.data = data;
        }
    }

    public class TestInTimeCountData implements Parcelable {
        private int code;
        private String msg;
        private HistoryCountMultipleBean.DataContent data;

        protected TestInTimeCountData(Parcel in) {
            code = in.readInt();
            msg = in.readString();
            data = in.readParcelable(HistoryCountMultipleBean.DataContent.class.getClassLoader());
        }

        public final Creator<TestInTimeCountData> CREATOR = new Creator<TestInTimeCountData>() {
            @Override
            public TestInTimeCountData createFromParcel(Parcel in) {
                return new TestInTimeCountData(in);
            }

            @Override
            public TestInTimeCountData[] newArray(int size) {
                return new TestInTimeCountData[size];
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

        public HistoryCountMultipleBean.DataContent getData() {
            return data;
        }

        public void setData(HistoryCountMultipleBean.DataContent data) {
            this.data = data;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeInt(code);
            dest.writeString(msg);
            dest.writeParcelable(data, flags);
        }
    }
}
