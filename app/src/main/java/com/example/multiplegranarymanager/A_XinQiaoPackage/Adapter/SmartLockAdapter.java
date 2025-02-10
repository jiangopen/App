package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.SmartLockActivity.OPEN_DOOR;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.SmartLockActivity.commandMapId;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.SmartLockActivity.url;
import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.WindTestInTimeBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.SmartLockBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindStatusBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewSmartLockBody;
import com.example.multiplegranarymanager.Body.NewDownRaw.QueryBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.v2.InputDialog;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SmartLockAdapter extends MultiLayoutAdapter<SmartLockBody> implements SectionIndexer, View.OnClickListener {
    private ArrayList<SmartLockBody> datas;
    private ArrayList<SmartLockBody> refreshdatas;
    private Context context;
    private int notifactionType = TYPE_NORMAL;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    public SmartLockAdapter(ArrayList<SmartLockBody> mDataList, int[] layoutIds, Context context) {
        super(mDataList, layoutIds);
        this.datas = mDataList;
        this.refreshdatas = mDataList;
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        SmartLockBody item = getItem(position-1);
//        NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
//        newDownRawBody.setUrl("/measure");
//        newDownRawBody.setMethod("POST");
//        newDownRawBody.setQuery(null);
//        newDownRawBody.setHeaders(null);
//        CountMultiple countMultiple = new CountMultiple();
//        countMultiple.setCommandMapId(item.getCommandMapId());
//        countMultiple.setMeasureType("-1");
//        countMultiple.setUrl(item.getUrl());
//        String jsonData = "AA55"+OPEN_DOOR+"58"+"FFFF"+"FFFF0D0A";
//        ArrayList<String> ids = new ArrayList<>();
//        CountMultiple.CommandJson commandJson = new CountMultiple.CommandJson();
//        ids.add(item.getHardwareListData().getId());
//        commandJson.setIds(ids);
//        countMultiple.setCommandJson(commandJson);
//        countMultiple.setCommandContent(jsonData);
//        newDownRawBody.setBody(countMultiple);
//        String jsondata = gson.toJson(newDownRawBody);
        DecimalFormat df = new DecimalFormat("#.##");
        double doubleValue;
        if (v.getId() == R.id.txt_open) {
            if (item.getHardwareStatus() == 1) {
                util.showToast(context,"已开，请不要重复操作！");
            } else if (item.getHardwareStatus() == 0) {
                String warning = "";
                if (item.getO2List()!=null && item.getN2List()!=null && item.getPh3List()!=null) {
                    if (Double.valueOf(item.getO2List().get(0))<item.getGranarySettingData().getLockWarn().getO2WarnDown() || Double.valueOf(item.getO2List().get(0))>item.getGranarySettingData().getLockWarn().getO2WarnUp()) {
                        String value = item.getO2List().get(0);
                        doubleValue = Double.parseDouble(value);
                        warning += "氧气:" + df.format(doubleValue) + "%";
                    }
                    for (String data : item.getPh3List()) {
                        if (Double.valueOf(data)<item.getGranarySettingData().getLockWarn().getPh3WarnDown() || Double.valueOf(data)>item.getGranarySettingData().getLockWarn().getPh3WarnDown()) {
                            String value = data;
                            doubleValue = Double.parseDouble(value);
                            warning += "磷化氢:" + df.format(doubleValue) + "ppm";
                        }
                    }

                } else {
                    warning = "仓内气体含量未知，请谨慎操作！";
                }
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view =  layoutInflater.inflate(R.layout.dialog_custom_title,null);
                final TextView textView = view.findViewById(R.id.modify_title);
                textView.setText("警告");
                View view1 =  layoutInflater.inflate(R.layout.dialog_alert,null);
                final TextView textView1 = view1.findViewById(R.id.txt_info);
                if (warning.equals("")){
                    textView1.setText("确定开门？");
                } else {
                    textView1.setText("注意！仓内气体浓度异常,"+warning+",是否继续开门？");
                }

                new AlertDialog.Builder(context).setCustomTitle(view).setView(view1).setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View customview = LayoutInflater.from(context).inflate(R.layout.layout_custom,null);
                        TextView Text = customview.findViewById(R.id.tv_title_password);
                        Text.setText("请输入门锁密码");
                        InputDialog.show(context, null, null, new InputDialogOkButtonClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String inputText) {
                                if (inputText.equals(item.getGranarySettingData().getLockWarn().getPwd())) {
                                    NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                                    newDownRawBody.setUrl("/hc/remoteControlGate");
                                    newDownRawBody.setMethod("GET");
                                    QueryBody queryBody = new QueryBody();
                                    queryBody.setAction("1");
                                    newDownRawBody.setQuery(queryBody);
                                    newDownRawBody.setHeaders(null);
                                    NewSmartLockBody params = new NewSmartLockBody();
                                    params.setCommandMapId(commandMapId);
                                    params.setUrl(url);
                                    params.setIp(item.getGranarySettingData().getEntranceGuardIp());
                                    params.setName(item.getGranarySettingData().getEntranceGuardName());
                                    params.setPwd(item.getGranarySettingData().getEntranceGuardPwd());
                                    params.setPort(item.getGranarySettingData().getEntranceGuardPort());
                                    newDownRawBody.setBody(params);
//                                    String jsondata = gson.toJson(newDownRawBody);
                                    String jsondata = new GsonBuilder().disableHtmlEscaping().create().toJson(newDownRawBody);
                                    Log.d("jht", "onClick: "+jsondata);
                                    dialog.dismiss();
                                    initOnDoor(jsondata, new OnInitOnDoorFinishedListener() {
                                        @Override
                                        public void OnInitOnDoorListener(boolean success) {
                                            suoPingDialog.dismiss();
                                            if (success) {
                                                item.setHardwareStatus(1);
                                                notifyDataSetChanged();
                                            } else {
                                                util.showToast(context,"门锁开启失败");
                                            }
                                        }
                                    });
                                } else {
                                    util.showToast(context,"密码不正确");
                                }
                            }
                        }).setCanCancel(false).setDefaultInputHint("请输入密码").setCustomView(customview);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
//                initTestInTime(jsondata, new OnInitTestInTimeFinishedListener() {
//                    @Override
//                    public void OnInitTestInTimeListener(boolean success, String flag) {
//                        if (success && flag.equals("01")) {
//
//
//                            notifyDataSetChanged();
//                        } else {
//                            util.showToast(context,"操作异常，请稍后再试！");
//                        }
//                    }
//                });
            }
        } else if (v.getId() == R.id.txt_close) {
            if (item.getHardwareStatus() == 0) {
                util.showToast(context,"已关，请不要重复操作！");
            } else if (item.getHardwareStatus() == 1) {
                NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                newDownRawBody.setUrl("/hc/remoteControlGate");
                newDownRawBody.setMethod("GET");
                QueryBody queryBody = new QueryBody();
                queryBody.setAction("0");
                newDownRawBody.setQuery(queryBody);
                newDownRawBody.setHeaders(null);
                NewSmartLockBody params = new NewSmartLockBody();
//                params.setCommandMapId(commandMapId);
                params.setUrl(url);
                params.setIp(item.getGranarySettingData().getEntranceGuardIp());
                params.setName(item.getGranarySettingData().getEntranceGuardName());
                params.setPwd(item.getGranarySettingData().getEntranceGuardPwd());
                params.setPort(item.getGranarySettingData().getEntranceGuardPort());
                newDownRawBody.setBody(params);
//                String jsondata = gson.toJson(newDownRawBody);
                String jsondata = new GsonBuilder().disableHtmlEscaping().create().toJson(newDownRawBody);
                initOnDoor(jsondata, new OnInitOnDoorFinishedListener() {
                    @Override
                    public void OnInitOnDoorListener(boolean success) {
                        suoPingDialog.dismiss();
                        if (success) {
                            item.setHardwareStatus(0);
                            notifyDataSetChanged();
                        } else {
                            util.showToast(context,"门锁关闭失败");
                        }
                    }
                });
            }
        }
    }
    public interface OnInitOnDoorFinishedListener {
        void OnInitOnDoorListener(boolean success);
    }
    public void initOnDoor(String jsondata, final OnInitOnDoorFinishedListener listener) {
        suoPingDialog = new SuoPingDialog(context,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindTestInTimeBean windTestInTimeBean = new WindTestInTimeBean();
                try {
                    windTestInTimeBean = gson.fromJson((String) result,WindTestInTimeBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitOnDoorListener(false);
                    e.printStackTrace();
                    util.showToast(context,"操作出错");
                    return;
                }
                if (windTestInTimeBean.getCode() == 200) {
                    listener.OnInitOnDoorListener(true);
                } else {
                    listener.OnInitOnDoorListener(false);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitOnDoorListener(false);
                util.showToast(context,errorMsg);
            }
        });
    }
    public interface OnInitTestInTimeFinishedListener {
        void OnInitTestInTimeListener(boolean success,String flag);
    }
    public void initTestInTime(String jsondata, final OnInitTestInTimeFinishedListener listener) {
        suoPingDialog = new SuoPingDialog(context,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindTestInTimeBean windTestInTimeBean = new WindTestInTimeBean();
                try {
                    windTestInTimeBean = gson.fromJson((String) result,WindTestInTimeBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitTestInTimeListener(false,"00");
                    e.printStackTrace();
                    util.showToast(context,"照明操作出错");
                    return;
                }
                if (windTestInTimeBean.getData()!=null && windTestInTimeBean.getData().getOpenFlagList()!=null && windTestInTimeBean.getData().getOpenFlagList().size()>0) {
                    if (windTestInTimeBean.getData().getOpenFlagList().get(0).equals("00")) {
                        listener.OnInitTestInTimeListener(true,"01");
                    } else {
                        listener.OnInitTestInTimeListener(true,"00");
                    }
                } else {
                    listener.OnInitTestInTimeListener(false,"00");
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitTestInTimeListener(false,"00");
                util.showToast(context,errorMsg);
            }
        });
    }
    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i=0;i<getItemCount();i++){
            int firstChar = datas.get(i).getHardwareListData().getName().charAt(0);
            if (firstChar == sectionIndex){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        SmartLockBody item = datas.get(position);
        return item.getHardwareListData().getName().charAt(0);
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }
    public void setFreshDates(ArrayList<SmartLockBody> datas){
        this.refreshdatas = datas;
    }

    public ArrayList<SmartLockBody> getFreshDates(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }
    @Override
    protected void coverts(BaseViewHolder holder, SmartLockBody item, int position, int itemType) {
        if (item!=null) {
            DecimalFormat df = new DecimalFormat("#.##");
            double doubleValue;
            double doubleValue1;
            double doubleValue2;
            if (item.getHardwareListData().getName()!=null) {
                holder.setText(R.id.name,item.getHardwareListData().getName());
            } else {
                holder.setText(R.id.name,"——");
            }

            if (item.getHardwareStatus()==1){
                holder.setText(R.id.txt_door_status,"开启");
            } else if (item.getHardwareStatus()==0) {
                holder.setText(R.id.txt_door_status,"关闭");
            } else {
                holder.setText(R.id.txt_door_status,"未知");
            }
            if (item.getO2List()!=null && item.getO2List().size()>0) {
                String value = item.getO2List().get(0);
                doubleValue = Double.parseDouble(value);
                holder.setText(R.id.txt_o2_1_num, df.format(doubleValue)+"%");
            } else {
                holder.setText(R.id.txt_o2_1_num,"——");
            }
            if (item.getN2List()!=null && item.getN2List().size()>0) {
                String value = item.getN2List().get(0);
                doubleValue = Double.parseDouble(value);
                holder.setText(R.id.txt_o2_2_num,df.format(doubleValue)+"%");
            } else {
                holder.setText(R.id.txt_o2_2_num,"——");
            }
            if (item.getPh3List()!=null && item.getPh3List().size()>0) {
                String value1 = item.getPh3List().get(0);
                doubleValue1 = Double.parseDouble(value1);
                String value2 = item.getPh3List().get(1);
                doubleValue2 = Double.parseDouble(value2);
                holder.setText(R.id.txt_ph3_1_num,df.format(doubleValue1)+"ppm");
                holder.setText(R.id.txt_ph3_2_num,df.format(doubleValue2)+"ppm");
            } else {
                holder.setText(R.id.txt_ph3_1_num,"——");
                holder.setText(R.id.txt_ph3_2_num,"——");
            }

//            if (item.getDataList().getFumigationStatusList()!=null && item.getDataList().getFumigationStatusList().size()>0) {
//                if (item.getDataList().getFumigationStatusList().get(0).equals("00")) {
//                    holder.setText(R.id.txt_xun_status,"关闭");
//                } else if (item.getDataList().getFumigationStatusList().get(0).equals("01")) {
//                    holder.setText(R.id.txt_xun_status,"开启");
//                } else {
//                    holder.setText(R.id.txt_xun_status,"——");
//                }
//            }
//
//            if (item.getDataList().getDoorStatusList()!=null && item.getDataList().getDoorStatusList().size()>0) {
//                if (item.getDataList().getDoorStatusList().get(0).equals("00")) {
//                    holder.setText(R.id.txt_door_status,"关闭");
//                } else if (item.getDataList().getDoorStatusList().get(0).equals("01")) {
//                    holder.setText(R.id.txt_door_status,"开启");
//                } else {
//                    holder.setText(R.id.txt_door_status,"——");
//                }
//            }
//
//            if (item.getDataList().getO2List()!=null && item.getDataList().getO2List().size()>0) {
//                if (item.getDataList().getO2List().get(0)!=null) {
//                    holder.setText(R.id.txt_o2_1_num,item.getDataList().getO2List().get(0).toString()+"%");
//                } else {
//                    holder.setText(R.id.txt_o2_1_num,"——");
//                }
//                if (item.getDataList().getO2List().get(1)!=null) {
//                    holder.setText(R.id.txt_o2_2_num,item.getDataList().getO2List().get(1).toString()+"%");
//                } else {
//                    holder.setText(R.id.txt_o2_2_num,"——");
//                }
//                if (item.getDataList().getPh3List().get(0)!=null) {
//                    holder.setText(R.id.txt_ph3_1_num,item.getDataList().getPh3List().get(0).toString()+"%");
//                } else {
//                    holder.setText(R.id.txt_ph3_1_num,"——");
//                }
//                if (item.getDataList().getPh3List().get(1)!=null) {
//                    holder.setText(R.id.txt_ph3_2_num,item.getDataList().getPh3List().get(1).toString()+"%");
//                } else {
//                    holder.setText(R.id.txt_ph3_2_num,"——");
//                }
//            }
        }
        TextView txt_open = holder.getView(R.id.txt_open);
        TextView txt_close = holder.getView(R.id.txt_close);
        txt_open.setTag(item.getTag());
        txt_close.setTag(item.getTag());

        txt_open.setOnClickListener(this);
        txt_close.setOnClickListener(this);
    }
}
