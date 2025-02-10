package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.GasInsectBean.GasInsectList;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.History.GranarySetting;

import java.util.ArrayList;
import java.util.List;

public class HistoryCountMultipleBean {
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
        private List<HistoryCountMultiple> list;

        public List<HistoryCountMultiple> getList() {
            return list;
        }

        public void setList(List<HistoryCountMultiple> list) {
            this.list = list;
        }
    }

    public class HistoryCountMultiple {
        private String commandMapId;
        private int errCode;
        private String errMsg;
        private String url;
        private List<CountData> data;

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

        public List<CountData> getData() {
            return data;
        }

        public void setData(List<CountData> data) {
            this.data = data;
        }
    }

    public class CountData {
        private String commandMapId;
        private String dataType;
        private String date;
        private long time;
        private DataContent dataContent;

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
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

        public DataContent getDataContent() {
            return dataContent;
        }

        public void setDataContent(DataContent dataContent) {
            this.dataContent = dataContent;
        }
    }

    public static class DataContent implements Parcelable{
        private String dataForInspur;
        private Double humidityInner;
        private Double humidityOut;
        private Double tempInner;
        private Double tempOut;
        private GranarySetting granarySetting;
        private List<Double> tempList;
        private List<GasInsectList> gasInsectList;
        private String errFlag;
        private Double height;
        private Double mass;
        private String measureCount;
        private Double pointCount;
        private List<PointList> pointList;
        private Double progress;
        private String scanStatus;
        private String version;
        private Double volume;

        public String getErrFlag() {
            return errFlag;
        }

        public void setErrFlag(String errFlag) {
            this.errFlag = errFlag;
        }

        public Double getHeight() {
            return height;
        }

        public void setHeight(Double height) {
            this.height = height;
        }

        public Double getMass() {
            return mass;
        }

        public void setMass(Double mass) {
            this.mass = mass;
        }

        public String getMeasureCount() {
            return measureCount;
        }

        public void setMeasureCount(String measureCount) {
            this.measureCount = measureCount;
        }

        public Double getPointCount() {
            return pointCount;
        }

        public void setPointCount(Double pointCount) {
            this.pointCount = pointCount;
        }

        public List<PointList> getPointList() {
            return pointList;
        }

        public void setPointList(List<PointList> pointList) {
            this.pointList = pointList;
        }

        public Double getProgress() {
            return progress;
        }

        public void setProgress(Double progress) {
            this.progress = progress;
        }

        public String getScanStatus() {
            return scanStatus;
        }

        public void setScanStatus(String scanStatus) {
            this.scanStatus = scanStatus;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Double getVolume() {
            return volume;
        }

        public void setVolume(Double volume) {
            this.volume = volume;
        }

        protected DataContent(Parcel in) {
            dataForInspur = in.readString();
            if (in.readByte() == 0) {
                humidityInner = null;
            } else {
                humidityInner = in.readDouble();
            }
            if (in.readByte() == 0) {
                humidityOut = null;
            } else {
                humidityOut = in.readDouble();
            }
            if (in.readByte() == 0) {
                tempInner = null;
            } else {
                tempInner = in.readDouble();
            }
            if (in.readByte() == 0) {
                tempOut = null;
            } else {
                tempOut = in.readDouble();
            }
            granarySetting = in.readParcelable(GranarySetting.class.getClassLoader());
            gasInsectList = in.createTypedArrayList(GasInsectList.CREATOR);
            // 读取tempList
            int tempListSize = in.readInt();
            if (tempListSize > 0) {
                tempList = new ArrayList<>(tempListSize);
                for (int i = 0; i < tempListSize; i++) {
                    tempList.add(in.readDouble());
                }
            } else {
                tempList = null;
            }
        }

        public static final Creator<DataContent> CREATOR = new Creator<DataContent>() {
            @Override
            public DataContent createFromParcel(Parcel in) {
                return new DataContent(in);
            }

            @Override
            public DataContent[] newArray(int size) {
                return new DataContent[size];
            }
        };

        public String getDataForInspur() {
            return dataForInspur;
        }

        public void setDataForInspur(String dataForInspur) {
            this.dataForInspur = dataForInspur;
        }

        public Double getHumidityInner() {
            return humidityInner;
        }

        public void setHumidityInner(Double humidityInner) {
            this.humidityInner = humidityInner;
        }

        public Double getHumidityOut() {
            return humidityOut;
        }

        public void setHumidityOut(Double humidityOut) {
            this.humidityOut = humidityOut;
        }

        public Double getTempInner() {
            return tempInner;
        }

        public void setTempInner(Double tempInner) {
            this.tempInner = tempInner;
        }

        public Double getTempOut() {
            return tempOut;
        }

        public void setTempOut(Double tempOut) {
            this.tempOut = tempOut;
        }

        public GranarySetting getGranarySetting() {
            return granarySetting;
        }

        public void setGranarySetting(GranarySetting granarySetting) {
            this.granarySetting = granarySetting;
        }

        public List<Double> getTempList() {
            return tempList;
        }

        public void setTempList(List<Double> tempList) {
            this.tempList = tempList;
        }

        public List<GasInsectList> getGasInsectList() {
            return gasInsectList;
        }

        public void setGasInsectList(List<GasInsectList> gasInsectList) {
            this.gasInsectList = gasInsectList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(dataForInspur);
            if (humidityInner == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(humidityInner);
            }
            if (humidityOut == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(humidityOut);
            }
            if (tempInner == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(tempInner);
            }
            if (tempOut == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(tempOut);
            }
            dest.writeParcelable(granarySetting, flags);
            dest.writeTypedList(gasInsectList);
            // 写入tempList
            if (tempList!= null) {
                dest.writeInt(tempList.size());
                for (Double value : tempList) {
                    dest.writeDouble(value);
                }
            } else {
                dest.writeInt(0);
            }
            // 写入 gasInsectList 的大小
            if (gasInsectList!= null) {
                dest.writeInt(gasInsectList.size());
                for (GasInsectList gasInsect : gasInsectList) {
                    if (gasInsect == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeParcelable(gasInsect, flags);
                    }
                }
            }
        }
    }
}
