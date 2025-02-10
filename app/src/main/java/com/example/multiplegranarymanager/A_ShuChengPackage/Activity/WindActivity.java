package com.example.multiplegranarymanager.A_ShuChengPackage.Activity;

import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.pwd;
import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.username;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_ShuChengPackage.AllInterfaceClass;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.ProductDetail.ProductDetail;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.SmartWind.Data;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.SmartWind.SmartData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.SmartWind.SmartWindData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.StartBean.StartBean;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.DeviceInfos.DeviceInfos;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Body;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Headers;
import com.example.multiplegranarymanager.A_ShuChengPackage.SharedData.ShareProductDetail;
import com.example.multiplegranarymanager.A_ShuChengPackage.SharedData.ShareSmartWindInfos;
import com.example.multiplegranarymanager.A_ShuChengPackage.SharedData.SharedDeviceInfos;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.MyWebView;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WindActivity extends AppCompatActivity implements View.OnClickListener{
    Button sayying;
    MyWebView webView ;
    SmartRefreshLayout refreshLayout;
    LinearLayout wushebei1;
    ImageView select;
    TextView namecangku;
    Button tongfeng_hand;
    Button tongfeng_smart;
    RecyclerView recyclerview;
    Button big;
    Button button7;
    Button button8;
    Button button9;
    LinearLayout infos;
    private boolean flag = true;
    private String NameTag = "仓库";
    private int selectWeatherId = 0;
    private int selectModileId = 0;
    private Util1 util = new Util1();
    private Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind_2);
        initView();
        if (username.equals("舒城粮情测温")) {
            NameTag = getIntent().getStringExtra("Name");
            namecangku.setText(NameTag);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            webView.loadUrl("http://49.4.8.167:30007/#/direct-wind-shucheng");
            for (Data data : ShareSmartWindInfos.SmartWindList) {
                if (data.getGranaryName().equals(NameTag) && data.getDeviceinfo()!=null) {
                    pandaun(data);
                }
            }
            refreshLayout.setRefreshHeader(new BezierRadarHeader(WindActivity.this).setEnableHorizontalDrag(true));
        }
    }

    private void initView() {
        webView = findViewById(R.id.webView);
        refreshLayout = findViewById(R.id.refreshLayout);
        big = findViewById(R.id.big);
        namecangku = findViewById(R.id.cangkuname);
        tongfeng_hand = findViewById(R.id.btn_tongfeng_hand);
        tongfeng_smart = findViewById(R.id.btn_tongfeng_smart);
        select = findViewById(R.id.select);
        sayying = findViewById(R.id.sayying);
        select.setOnClickListener(this);
        sayying.setOnClickListener(this);
        tongfeng_hand.setOnClickListener(this);
        tongfeng_smart.setOnClickListener(this);
        big.setOnClickListener(this);
    }
    private void showAlertDialog(String[] name, String[] keys, int selectId) {
        ArrayAdapter adaptertwo = new ArrayAdapter<String>(WindActivity.this,R.layout.simple_list,name);
        AlertDialog.Builder builder = new AlertDialog.Builder(WindActivity.this);
        builder.setSingleChoiceItems(adaptertwo, selectId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cangku = name[which];
                NameTag = cangku;
                namecangku.setText(cangku);
                selectWeatherId = which;
                String tagkey = keys[which];
                for (Data data : ShareSmartWindInfos.SmartWindList) {
                    if (data.getGranaryName().equals(cangku)) {
                        pandaun(data);
                    }
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialogone = builder.create();
        View customView = LayoutInflater.from(WindActivity.this).inflate(R.layout.layout_custom,null);
        TextView textView = customView.findViewById(R.id.tv_title_password);
        textView.setText("请选择仓库");
        ImageView imageView = customView.findViewById(R.id.img_ico);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.zzz_tuichu));
        dialogone.setCustomTitle(customView);
        dialogone.show();
        dialogone.setCanceledOnTouchOutside(true);
    }

    private void pandaun(Data tagbean) {
        if (tagbean.getDeviceinfo().getSmartWindEnabled()!=null){
            if (tagbean.getDeviceinfo().getSmartWindEnabled().equals("01")){
                tongfeng_hand.setVisibility(View.VISIBLE);
                tongfeng_smart.setVisibility(View.GONE);
                sayying.setVisibility(View.VISIBLE);
                if (tagbean.getDeviceinfo().getSmartWindPatternCurrent()!=null){
                    if (tagbean.getDeviceinfo().getSmartWindPatternCurrent().equals("ZRTF")){
                        sayying.setText("自然通风");
                    } else if (tagbean.getDeviceinfo().getSmartWindPatternCurrent().equals("CDPJRTF")){
                        sayying.setText("仓顶排积热通风");
                    } else if (tagbean.getDeviceinfo().getSmartWindPatternCurrent().equals("JWTF")){
                        sayying.setText("均温通风");
                    } else if (tagbean.getDeviceinfo().getSmartWindPatternCurrent().equals("HLTF")){
                        sayying.setText("环流通风");
                    } else if (tagbean.getDeviceinfo().getSmartWindPatternCurrent().equals("SQTF")){
                        sayying.setText("散气通风");
                    } else if (tagbean.getDeviceinfo().getSmartWindPatternCurrent().equals("FJLTF")){
                        sayying.setText("防结露通风");
                    } else if (tagbean.getDeviceinfo().getSmartWindPatternCurrent().equals("JWTF")){
                        sayying.setText("降温通风");
                    } else if (tagbean.getDeviceinfo().getSmartWindPatternCurrent().equals("JSTF")){
                        sayying.setText("降水通风");
                    } else if (tagbean.getDeviceinfo().getSmartWindPatternCurrent().equals("TZTF")){
                        sayying.setText("调制通风");
                    }
                } else {
                    sayying.setText("没有合适的智能通风模式");
                }
            } else {
                tongfeng_hand.setVisibility(View.GONE);
                tongfeng_smart.setVisibility(View.VISIBLE);
                sayying.setVisibility(View.GONE);
            }
        }
    }
    private void showTongfengModileDialog(String[] modile, int selectId) {
        if (modile!=null){
            String[] modileToString = new String[modile.length];
            for (int i=0;i<modile.length;i++){
                String name = null;
                if (modile[i].equals("ZRTF")){
                    name = "自然通风";
                } else if (modile[i].equals("CDPJRTF")) {
                    name = "仓顶排积热通风";
                } else if (modile[i].equals("JWTF")) {
                    name = "均温通风";
                } else if (modile[i].equals("HLTF")) {
                    name = "环流通风";
                } else if (modile[i].equals("SQTF")) {
                    name = "散气通风";
                } else if (modile[i].equals("FJLTF")) {
                    name = "防结露通风";
                } else if (modile[i].equals("JWTF")) {
                    name = "降温通风";
                } else if (modile[i].equals("JSTF")) {
                    name = "降水通风";
                } else if (modile[i].equals("TZTF")) {
                    name = "调制通风";
                }
                modileToString[i] = name;
            }
            ArrayAdapter adapterThree = new ArrayAdapter<>(this,R.layout.simple_list,modileToString);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setSingleChoiceItems(adapterThree, selectId, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String moshi = modile[which];
                    selectModileId = which;
                    String now = "";
                    Gson json = new Gson();
                    for (Data data : ShareSmartWindInfos.SmartWindList) {
                        if (data.getGranaryName().equals(NameTag) && data.getDeviceinfo()!=null) {
                            now = data.getDeviceinfo().getSmartWindPatternCurrent();
                            data.getDeviceinfo().setSmartWindPatternCurrent(moshi);
                        }
                        for (DeviceInfos data1 : SharedDeviceInfos.DeviceInfosList) {
                            if (data1.getGranaryName().equals(NameTag)) {
                                AllInterfaceClass<ProductDetail> one = new AllInterfaceClass<>(ProductDetail.class);
                                data1.getWindDeviceData().getExtraInfo().get("smartWindInfo").setValue(data.getDeviceinfo());
                                Body body = new Body();
                                body.setDeviceKey(data1.getWindDeviceKey());
                                body.setProductKey(data1.getProductKey());
                                body.setExtraInfo(data1.getWindDeviceData().getExtraInfo());
                                Headers headers = new Headers();
                                headers.setTokenOffline(Token);
                                NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
                                newDownRawBodyTWO.setQuery(null);
                                newDownRawBodyTWO.setBody(body);
                                newDownRawBodyTWO.setHeaders(headers);
                                newDownRawBodyTWO.setMethod("PUT");
                                newDownRawBodyTWO.setUrl("/deviceparamdata");
                                String finalNow = now;
                                one.PostOne(newDownRawBodyTWO, "WindActivity/showTongfengModileDialog", new AllInterfaceClass.PostCallBack<ProductDetail>() {
                                    @Override
                                    public void onSuccess(ProductDetail zyq) {
                                        if (zyq.getCode()==200) {
                                            for (Data data : ShareSmartWindInfos.SmartWindList) {
                                                if (data.getGranaryName().equals(NameTag)) {
                                                    pandaun(data);
                                                }
                                            }
                                        } else {
                                            data.getDeviceinfo().setSmartWindPatternCurrent(finalNow);
                                            data1.getWindDeviceData().getExtraInfo().get("smartWindInfo").setValue(data.getDeviceinfo());
                                        }
                                    }

                                    @Override
                                    public void onFailure(String zyq) {
                                        util.showToast(WindActivity.this,zyq);
                                        data.getDeviceinfo().setSmartWindPatternCurrent(finalNow);
                                        data1.getWindDeviceData().getExtraInfo().get("smartWindInfo").setValue(data.getDeviceinfo());
                                    }
                                });
                            }
                        }
                    }
                    dialog.dismiss();
                }
            });
            AlertDialog dialogone = builder.create();
            View customView = LayoutInflater.from(WindActivity.this).inflate(R.layout.layout_custom,null);
            TextView textView = customView.findViewById(R.id.tv_title_password);
            textView.setText("请选择模式");
            ImageView imageView = customView.findViewById(R.id.img_ico);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.zzz_tuichu));
            dialogone.setCustomTitle(customView);
            dialogone.show();
            dialogone.setCanceledOnTouchOutside(true);
        } else {
            util.showToast(WindActivity.this,"没有其他合适的智能通风模式");
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select:
                List<String> productName = new ArrayList<>();
                List<String> productKey = new ArrayList<>();
                for (Data data : ShareSmartWindInfos.SmartWindList) {
                    productName.add(data.getGranaryName());
                    productKey.add(data.getProductkey());
                }
                Log.d("jhtzyq", "onClick: " + productName + "\n" + productKey);
                String[] name = productName.toArray(new String[productName.size()]);
                String[] keys = productKey.toArray(new String[productKey.size()]);
                Log.d("jhtzyq", "onClick: " + name + "\n" + keys);
                showAlertDialog(name,keys,selectWeatherId);
                break;
            case R.id.sayying:
                List<String> Tongfengmodile = null;
                for (Data data : ShareSmartWindInfos.SmartWindList) {
                    if (data.getGranaryName().equals(NameTag) && data.getDeviceinfo()!=null) {
                        Tongfengmodile = data.getDeviceinfo().getSmartWindPatternList();
                    }
                }
                String[] modile = null;
                if (Tongfengmodile!=null){
                    modile = Tongfengmodile.toArray(new String[Tongfengmodile.size()]);
                }
                showTongfengModileDialog(modile,selectModileId);
                break;
            case R.id.btn_tongfeng_hand:
                for (Data data : ShareSmartWindInfos.SmartWindList) {
                    if (data.getGranaryName().equals(NameTag) && data.getDeviceinfo()!=null) {
                        data.getDeviceinfo().setSmartWindEnabled("00");
                        for (DeviceInfos data1 : SharedDeviceInfos.DeviceInfosList) {
                            if (data1.getGranaryName().equals(NameTag)) {
                                AllInterfaceClass<ProductDetail> one = new AllInterfaceClass<>(ProductDetail.class);
                                DeviceInfos res = new DeviceInfos();
                                res = data1;
                                res.getWindDeviceData().getExtraInfo().get("smartWindInfo").setValue(data.getDeviceinfo());
                                Body body = new Body();
                                body.setDeviceKey(res.getWindDeviceKey());
                                body.setProductKey(res.getProductKey());
                                body.setExtraInfo(res.getWindDeviceData().getExtraInfo());
                                Headers headers =new Headers();
                                headers.setTokenOffline(Token);
                                NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
                                newDownRawBodyTWO.setBody(body);
                                newDownRawBodyTWO.setHeaders(headers);
                                newDownRawBodyTWO.setQuery(null);
                                newDownRawBodyTWO.setMethod("PUT");
                                newDownRawBodyTWO.setUrl("/deviceparamdata");
                                DeviceInfos finalRes = res;
                                one.PostOne(newDownRawBodyTWO, "WindActivity/btn_tongfeng_hand", new AllInterfaceClass.PostCallBack<ProductDetail>() {
                                    @Override
                                    public void onSuccess(ProductDetail zyq) {
                                        if (zyq.getCode()==200) {
                                            data1.setWindDeviceData(finalRes.getWindDeviceData());
                                            pandaun(data);
                                        } else {
                                            util.showToast(WindActivity.this, zyq.getMsg());
                                            data.getDeviceinfo().setSmartWindEnabled("01");
                                        }
                                    }

                                    @Override
                                    public void onFailure(String zyq) {
                                        util.showToast(WindActivity.this,zyq);
                                        data.getDeviceinfo().setSmartWindEnabled("01");
                                    }
                                });
                            }
                        }
                    }
                }
                break;
            case R.id.btn_tongfeng_smart:
                for (Data data : ShareSmartWindInfos.SmartWindList) {
                    if (data.getGranaryName().equals(NameTag) && data.getDeviceinfo()!=null) {
                        data.getDeviceinfo().setSmartWindEnabled("01");
                        for (DeviceInfos data1 : SharedDeviceInfos.DeviceInfosList) {
                            if (data1.getGranaryName().equals(NameTag)) {
                                AllInterfaceClass<ProductDetail> one = new AllInterfaceClass<>(ProductDetail.class);
                                DeviceInfos res = new DeviceInfos();
                                res = data1;
                                res.getWindDeviceData().getExtraInfo().get("smartWindInfo").setValue(data.getDeviceinfo());
                                Body body = new Body();
                                body.setDeviceKey(res.getWindDeviceKey());
                                body.setProductKey(res.getProductKey());
                                body.setExtraInfo(res.getWindDeviceData().getExtraInfo());
                                Headers headers =new Headers();
                                headers.setTokenOffline(Token);
                                NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
                                newDownRawBodyTWO.setBody(body);
                                newDownRawBodyTWO.setHeaders(headers);
                                newDownRawBodyTWO.setQuery(null);
                                newDownRawBodyTWO.setMethod("PUT");
                                newDownRawBodyTWO.setUrl("/deviceparamdata");
                                DeviceInfos finalRes = res;
                                one.PostOne(newDownRawBodyTWO, "WindActivity/btn_tongfeng_hand", new AllInterfaceClass.PostCallBack<ProductDetail>() {
                                    @Override
                                    public void onSuccess(ProductDetail zyq) {
                                        if (zyq.getCode()==200) {
                                            data1.setWindDeviceData(finalRes.getWindDeviceData());
                                            pandaun(data);
                                            AllInterfaceClass<StartBean> one = new AllInterfaceClass<>(StartBean.class);
                                            Body body = new Body();
                                            body.setPassword(pwd);
                                            body.setUsername(username);
                                            body.setProductKeyList(Collections.singletonList(data1.getProductKey()));
                                            Headers headers =new Headers();
                                            headers.setTokenOffline(Token);
                                            NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
                                            newDownRawBodyTWO.setBody(body);
                                            newDownRawBodyTWO.setHeaders(headers);
                                            newDownRawBodyTWO.setQuery(null);
                                            newDownRawBodyTWO.setMethod("POST");
                                            newDownRawBodyTWO.setUrl("/smart-wind/v1/start");
                                            one.PostOne(newDownRawBodyTWO, "WindActivity/btn_tongfeng_hand/smart-wind", new AllInterfaceClass.PostCallBack<StartBean>() {
                                                @Override
                                                public void onSuccess(StartBean zyq) {
                                                    if (zyq.getCode()==200) {
                                                        String res = gson.toJson(zyq.getData().get(0).getWindInfo());
                                                        SmartWindData two = gson.fromJson(res,SmartWindData.class);
                                                        data.setDeviceinfo(two);
                                                        data1.getWindDeviceData().getExtraInfo().get("smartWindInfo").setValue(zyq.getData().get(0));
                                                        pandaun(data);
                                                    } else {
                                                        util.showToast(WindActivity.this, zyq.getMsg());
                                                        data.getDeviceinfo().setSmartWindEnabled("00");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(String zyq) {
                                                    util.showToast(WindActivity.this, zyq);
                                                    data.getDeviceinfo().setSmartWindEnabled("00");
                                                }
                                            });
                                        } else {
                                            util.showToast(WindActivity.this, zyq.getMsg());
                                            data.getDeviceinfo().setSmartWindEnabled("00");
                                        }
                                    }

                                    @Override
                                    public void onFailure(String zyq) {
                                        util.showToast(WindActivity.this,zyq);
                                        data.getDeviceinfo().setSmartWindEnabled("00");
                                    }
                                });
                            }
                        }
                    }
                }
                break;
            case R.id.big:
                if (flag) {
                    flag = false;
                    WindActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    flag = true;
                    WindActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
            default:
                break;
        }
    }
}