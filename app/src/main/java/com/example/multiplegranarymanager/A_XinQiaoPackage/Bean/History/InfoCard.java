package com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.History;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class InfoCard implements Parcelable {
    private String chandi;
    private String buwanshanli;
    private String diwenbaojing;
    private String duiwei;
    private String dangqianshuifen;
    private String jianceyuan;
    private String rucangriqi;
    private String cangkudengji;
    private String shouhuonianfen;
    private String rongzhongchucao;
    private String zazhi;
    private String gaowenbaojing;
    private String shijichuliang;
    private String liangshizhonglei;
    private String rucangshuifen;
    private String cunchuleixing;
    private String baoguanyuan;

    protected InfoCard(Parcel in) {
        chandi = in.readString();
        buwanshanli = in.readString();
        diwenbaojing = in.readString();
        duiwei = in.readString();
        dangqianshuifen = in.readString();
        jianceyuan = in.readString();
        rucangriqi = in.readString();
        cangkudengji = in.readString();
        shouhuonianfen = in.readString();
        rongzhongchucao = in.readString();
        zazhi = in.readString();
        gaowenbaojing = in.readString();
        shijichuliang = in.readString();
        liangshizhonglei = in.readString();
        rucangshuifen = in.readString();
        cunchuleixing = in.readString();
        baoguanyuan = in.readString();
    }

    public static final Creator<InfoCard> CREATOR = new Creator<InfoCard>() {
        @Override
        public InfoCard createFromParcel(Parcel in) {
            return new InfoCard(in);
        }

        @Override
        public InfoCard[] newArray(int size) {
            return new InfoCard[size];
        }
    };

    public String getChandi() {
        return chandi;
    }

    public void setChandi(String chandi) {
        this.chandi = chandi;
    }

    public String getBuwanshanli() {
        return buwanshanli;
    }

    public void setBuwanshanli(String buwanshanli) {
        this.buwanshanli = buwanshanli;
    }

    public String getDiwenbaojing() {
        return diwenbaojing;
    }

    public void setDiwenbaojing(String diwenbaojing) {
        this.diwenbaojing = diwenbaojing;
    }

    public String getDuiwei() {
        return duiwei;
    }

    public void setDuiwei(String duiwei) {
        this.duiwei = duiwei;
    }

    public String getDangqianshuifen() {
        return dangqianshuifen;
    }

    public void setDangqianshuifen(String dangqianshuifen) {
        this.dangqianshuifen = dangqianshuifen;
    }

    public String getJianceyuan() {
        return jianceyuan;
    }

    public void setJianceyuan(String jianceyuan) {
        this.jianceyuan = jianceyuan;
    }

    public String getRucangriqi() {
        return rucangriqi;
    }

    public void setRucangriqi(String rucangriqi) {
        this.rucangriqi = rucangriqi;
    }

    public String getCangkudengji() {
        return cangkudengji;
    }

    public void setCangkudengji(String cangkudengji) {
        this.cangkudengji = cangkudengji;
    }

    public String getShouhuonianfen() {
        return shouhuonianfen;
    }

    public void setShouhuonianfen(String shouhuonianfen) {
        this.shouhuonianfen = shouhuonianfen;
    }

    public String getRongzhongchucao() {
        return rongzhongchucao;
    }

    public void setRongzhongchucao(String rongzhongchucao) {
        this.rongzhongchucao = rongzhongchucao;
    }

    public String getZazhi() {
        return zazhi;
    }

    public void setZazhi(String zazhi) {
        this.zazhi = zazhi;
    }

    public String getGaowenbaojing() {
        return gaowenbaojing;
    }

    public void setGaowenbaojing(String gaowenbaojing) {
        this.gaowenbaojing = gaowenbaojing;
    }

    public String getShijichuliang() {
        return shijichuliang;
    }

    public void setShijichuliang(String shijichuliang) {
        this.shijichuliang = shijichuliang;
    }

    public String getLiangshizhonglei() {
        return liangshizhonglei;
    }

    public void setLiangshizhonglei(String liangshizhonglei) {
        this.liangshizhonglei = liangshizhonglei;
    }

    public String getRucangshuifen() {
        return rucangshuifen;
    }

    public void setRucangshuifen(String rucangshuifen) {
        this.rucangshuifen = rucangshuifen;
    }

    public String getCunchuleixing() {
        return cunchuleixing;
    }

    public void setCunchuleixing(String cunchuleixing) {
        this.cunchuleixing = cunchuleixing;
    }

    public String getBaoguanyuan() {
        return baoguanyuan;
    }

    public void setBaoguanyuan(String baoguanyuan) {
        this.baoguanyuan = baoguanyuan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(chandi);
        dest.writeString(buwanshanli);
        dest.writeString(diwenbaojing);
        dest.writeString(duiwei);
        dest.writeString(dangqianshuifen);
        dest.writeString(jianceyuan);
        dest.writeString(rucangriqi);
        dest.writeString(cangkudengji);
        dest.writeString(shouhuonianfen);
        dest.writeString(rongzhongchucao);
        dest.writeString(zazhi);
        dest.writeString(gaowenbaojing);
        dest.writeString(shijichuliang);
        dest.writeString(liangshizhonglei);
        dest.writeString(rucangshuifen);
        dest.writeString(cunchuleixing);
        dest.writeString(baoguanyuan);
    }
}
