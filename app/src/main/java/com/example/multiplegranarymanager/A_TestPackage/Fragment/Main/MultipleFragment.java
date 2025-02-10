package com.example.multiplegranarymanager.A_TestPackage.Fragment.Main;

import static com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.deviceType;
import static com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.username;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.multiplegranarymanager.Bean.ProductDetail.DeviceInfo;
import com.example.multiplegranarymanager.Bean.ProductDetail.ProductDetailBean;
import com.example.multiplegranarymanager.Body.ProductDetial.DeviceInfoBody;
import com.example.multiplegranarymanager.Body.ProductDetial.ParamsBody;
import com.example.multiplegranarymanager.Body.ProductDetial.ProductDetailBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.AirConditionFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.EnergyFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.GasControlFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.GasInsectFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.HumFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.LightFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.MonitorFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.SmartLockFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.TemFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.WeatherFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.WindFragment;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.SideBar;
import com.example.multiplegranarymanager.Util.Util1;
import com.example.multiplegranarymanager.Util.WonderUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MultipleFragment extends Fragment implements View.OnClickListener, TextWatcher, SideBar.OnTouchingLetterChangedListener {
    LinearLayout layout_linear;
    FrameLayout layout_fatherview;
    ImageView img_more;
    TextView txt_temperature,txt_humidity,txt_gas_insect,txt_tongfeng,txt_aircondition,txt_energy,txt_weather,txt_light,txt_gas_tightness,txt_smart_lock,txt_vedio;
    TextView selectedTextView = null;
    private boolean Img_More_Flag = true;
    private ArrayList<ProductDetailBody> productDetailBodyList = new ArrayList<>();
    private ArrayList<ParamsBody> paramsBodyList = new ArrayList<>();
    private SuoPingDialog suoPingDialog;
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private WonderUtil mWonderUtil = new WonderUtil();
    private Gson gson = new Gson();
    private String functionType = "";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_multiple, container, false);
        initView(view);
        Bundle bundle = getArguments();
        assert bundle != null;
        if (bundle.getStringArrayList("productKey")!=null){
            ArrayList<String> StringList = bundle.getStringArrayList("productKey");
            suoPingDialog = new SuoPingDialog(getContext(),"正在加载,请稍等......");
            suoPingDialog.setCancelable(false);
            suoPingDialog.show();
            initData(StringList, new OnInitDataFinishedListener() {
                @Override
                public void initDataListener(boolean success) {
                    suoPingDialog.dismiss();
                }
            });
        }

        return view;
    }
    public interface OnInitDataFinishedListener{
        void initDataListener(boolean success);
    }
    public void initData(ArrayList<String> KeyList,final OnInitDataFinishedListener listener){
        productDetailBodyList.clear();
        for (String data : KeyList){
            OkHttpUtil.Get1("api/v1/productDetail?productKey=" + data, Token, new OkHttpUtil.ReqCallBack() {
                @Override
                public void onReqSuccess(Object result) throws JSONException {
                    ProductDetailBean productDetailBean = new ProductDetailBean();
                    try {
                        productDetailBean = gson.fromJson((String) result,ProductDetailBean.class);
                    } catch (JsonSyntaxException e){
                        e.printStackTrace();
                        util.showToast(getContext(),"产品信息解析出错");
                    }
                    if (productDetailBean.getData()!=null&&productDetailBean.getData().getDeviceInfo()!=null&&productDetailBean.getData().getDeviceInfo().size()>0){
                        ArrayList<DeviceInfoBody> deviceInfoBodies = new ArrayList<>();
                        for (DeviceInfo data : productDetailBean.getData().getDeviceInfo()){
                            String moduleName = "";
                            String granaryId = "";
                            String granaryFen = "";
                            if (data.getExtraInfo().get("moduleName")!=null&&data.getExtraInfo().get("moduleName").getValue()!=null){
                                moduleName = data.getExtraInfo().get("moduleName").getValue().toString();
                            }
                            if (data.getExtraInfo().get("granaryId")!=null&&data.getExtraInfo().get("granaryId").getValue()!=null){
                                granaryId = data.getExtraInfo().get("granaryId").getValue().toString();
                            }
                            if (data.getExtraInfo().get("granaryFen")!=null&&data.getExtraInfo().get("granaryFen").getValue()!=null){
                                granaryFen = data.getExtraInfo().get("granaryFen").getValue().toString();
                            }
                            DeviceInfoBody deviceInfoBody = new DeviceInfoBody(
                                    data.getProductKey(),
                                    data.getDeviceKey(),
                                    data.getDeviceType(),
                                    data.getNickname(),
                                    moduleName,
                                    granaryId,
                                    granaryFen
                            );
                            deviceInfoBodies.add(deviceInfoBody);
                        }
                        ProductDetailBody productDetailBody = new ProductDetailBody(
                                productDetailBean.getData().getProductName(),
                                deviceInfoBodies
                        );
                        productDetailBodyList.add(productDetailBody);
                    }
                    if (listener != null){
                        listener.initDataListener(true);
                    }
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    listener.initDataListener(false);
                    util.showToast(getContext(),errorMsg);
                }
            });
        }
    }
    private void initView(View view) {
        layout_linear = view.findViewById(R.id.layout_linear);
        layout_fatherview = view.findViewById(R.id.layout_fatherview);
        img_more = view.findViewById(R.id.img_more);
        txt_temperature = view.findViewById(R.id.txt_temperature);
        txt_humidity = view.findViewById(R.id.txt_humidity);
        txt_gas_insect = view.findViewById(R.id.txt_gas_insect);
        txt_tongfeng = view.findViewById(R.id.txt_tongfeng);
        txt_aircondition = view.findViewById(R.id.txt_aircondition);
        txt_energy = view.findViewById(R.id.txt_energy);
        txt_weather = view.findViewById(R.id.txt_weather);
        txt_light = view.findViewById(R.id.txt_light);
        txt_gas_tightness = view.findViewById(R.id.txt_gas_tightness);
        txt_smart_lock = view.findViewById(R.id.txt_smart_lock);
        txt_vedio = view.findViewById(R.id.txt_vedio);

        layout_fatherview.setVisibility(View.GONE);

        img_more.setOnClickListener(this);

        txt_temperature.setOnClickListener(this);
        txt_humidity.setOnClickListener(this);
        txt_gas_insect.setOnClickListener(this);
        txt_tongfeng.setOnClickListener(this);
        txt_aircondition.setOnClickListener(this);
        txt_energy.setOnClickListener(this);
        txt_weather.setOnClickListener(this);
        txt_light.setOnClickListener(this);
        txt_gas_tightness.setOnClickListener(this);
        txt_smart_lock.setOnClickListener(this);
        txt_vedio.setOnClickListener(this);

        initChoice(username,deviceType);
    }

    private void initChoice(String userName, String deviceType) {
        if (userName.equals("舒城粮情测温")) {
            txt_aircondition.setVisibility(View.GONE);
            txt_energy.setVisibility(View.GONE);
            txt_weather.setVisibility(View.GONE);
            txt_light.setVisibility(View.GONE);
            txt_gas_tightness.setVisibility(View.GONE);
            txt_smart_lock.setVisibility(View.GONE);
            txt_vedio.setVisibility(View.GONE);
        } else if (deviceType.equals("cloud_request_trans_huainan")){
            txt_humidity.setVisibility(View.GONE);
            txt_tongfeng.setVisibility(View.GONE);
            txt_aircondition.setVisibility(View.GONE);
        } else if (deviceType.equals("cloud_request_trans_wuwei_shili")||deviceType.equals("cloud_request_trans_wuwei_tuqiao")){
            txt_gas_tightness.setVisibility(View.GONE);
            txt_smart_lock.setVisibility(View.GONE);
            txt_vedio.setVisibility(View.GONE);
        } else if (deviceType.equals("cloud_request_trans_test")){
            txt_gas_tightness.setVisibility(View.GONE);
            txt_smart_lock.setVisibility(View.GONE);
            txt_vedio.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.img_more:
                if (Img_More_Flag){
                    layout_linear.setVisibility(View.VISIBLE);
                    img_more.setImageResource(R.drawable.layout_arrow_left);
                    Img_More_Flag = false;
                } else {
                    layout_linear.setVisibility(View.GONE);
                    img_more.setImageResource(R.drawable.layout_arrow_right);
                    Img_More_Flag = true;
                }
                break;
            case R.id.txt_temperature:
                paramsBodyList.clear();
                functionType = "honen_zj_bx";
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                txt_temperature.setBackgroundColor(getResources().getColor(R.color.china_color_004_xyb));
                selectedTextView = txt_temperature;
                bundle = ReturnParams(functionType,txt_temperature.getText().toString());
                TemFragment temFragment = new TemFragment();
                temFragment.setArguments(bundle);
                transaction.replace(R.id.layout_fatherview,temFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.txt_humidity:
                paramsBodyList.clear();
                functionType = "honen_zj_bx_moisture";
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                txt_humidity.setBackgroundColor(getResources().getColor(R.color.china_color_004_xyb));
                selectedTextView = txt_humidity;
                bundle = ReturnParams(functionType,txt_humidity.getText().toString());
                HumFragment humFragment = new HumFragment();
                humFragment.setArguments(bundle);
                transaction.replace(R.id.layout_fatherview,humFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.txt_gas_insect:
                paramsBodyList.clear();
                functionType = "honen_zj_bx_air_dust";
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                txt_gas_insect.setBackgroundColor(getResources().getColor(R.color.china_color_004_xyb));
                selectedTextView = txt_gas_insect;
                bundle = ReturnParams(functionType,txt_gas_insect.getText().toString());
                GasInsectFragment gasInsectFragment = new GasInsectFragment();
                gasInsectFragment.setArguments(bundle);
                transaction.replace(R.id.layout_fatherview,gasInsectFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.txt_tongfeng:
                paramsBodyList.clear();
                functionType = "honen_zj_bx_smart_wind";
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                txt_tongfeng.setBackgroundColor(getResources().getColor(R.color.china_color_004_xyb));
                selectedTextView = txt_tongfeng;
                bundle = ReturnParams(functionType,txt_tongfeng.getText().toString());
                WindFragment windFragment = new WindFragment();
                windFragment.setArguments(bundle);
                transaction.replace(R.id.layout_fatherview,windFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.txt_aircondition:
                paramsBodyList.clear();
                functionType = "honen_zj_bx_air_conditioner";
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                txt_aircondition.setBackgroundColor(getResources().getColor(R.color.china_color_004_xyb));
                selectedTextView = txt_aircondition;
                bundle = ReturnParams(functionType,txt_aircondition.getText().toString());
                AirConditionFragment airConditionFragment = new AirConditionFragment();
                airConditionFragment.setArguments(bundle);
                transaction.replace(R.id.layout_fatherview,airConditionFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.txt_energy:
                paramsBodyList.clear();
                functionType = "";
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                txt_energy.setBackgroundColor(getResources().getColor(R.color.china_color_004_xyb));
                selectedTextView = txt_energy;
                bundle = ReturnParams(functionType,txt_energy.getText().toString());
                EnergyFragment energyFragment = new EnergyFragment();
                energyFragment.setArguments(bundle);
                transaction.replace(R.id.layout_fatherview,energyFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.txt_weather:
                paramsBodyList.clear();
                functionType = "";
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                txt_weather.setBackgroundColor(getResources().getColor(R.color.china_color_004_xyb));
                selectedTextView = txt_weather;
                bundle = ReturnParams(functionType,txt_weather.getText().toString());
                WeatherFragment weatherFragment = new WeatherFragment();
                weatherFragment.setArguments(bundle);
                transaction.replace(R.id.layout_fatherview,weatherFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.txt_light:
                paramsBodyList.clear();
                functionType = "";
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                txt_light.setBackgroundColor(getResources().getColor(R.color.china_color_004_xyb));
                selectedTextView = txt_light;
                bundle = ReturnParams(functionType,txt_light.getText().toString());
                LightFragment lightFragment = new LightFragment();
                lightFragment.setArguments(bundle);
                transaction.replace(R.id.layout_fatherview,lightFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.txt_gas_tightness:
                paramsBodyList.clear();
                functionType = "honen_zj_bx_qitiao";
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                txt_gas_tightness.setBackgroundColor(getResources().getColor(R.color.china_color_004_xyb));
                selectedTextView = txt_gas_tightness;
                bundle = ReturnParams(functionType,txt_gas_tightness.getText().toString());
                GasControlFragment gasControlFragment = new GasControlFragment();
                gasControlFragment.setArguments(bundle);
                transaction.replace(R.id.layout_fatherview,gasControlFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.txt_smart_lock:
                paramsBodyList.clear();
                functionType = "honen_zj_door";
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                txt_smart_lock.setBackgroundColor(getResources().getColor(R.color.china_color_004_xyb));
                selectedTextView = txt_smart_lock;
                bundle = ReturnParams(functionType,txt_smart_lock.getText().toString());
                SmartLockFragment smartLockFragment = new SmartLockFragment();
                smartLockFragment.setArguments(bundle);
                transaction.replace(R.id.layout_fatherview,smartLockFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.txt_vedio:
                paramsBodyList.clear();
                functionType = "";
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                txt_vedio.setBackgroundColor(getResources().getColor(R.color.china_color_004_xyb));
                selectedTextView = txt_vedio;
                bundle = ReturnParams(functionType,txt_vedio.getText().toString());
                MonitorFragment monitorFragment = new MonitorFragment();
                monitorFragment.setArguments(bundle);
                transaction.replace(R.id.layout_fatherview,monitorFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }
    public Bundle ReturnParams(String Type, String string){
        layout_fatherview.setVisibility(View.VISIBLE);
        Bundle bundle1 = new Bundle();
        if (productDetailBodyList!=null&&productDetailBodyList.size()>0){
            for (ProductDetailBody data1 : productDetailBodyList){
                for (DeviceInfoBody data2 : data1.getDeviceInfo()){
                    if (data2.getDeviceType().equals(Type)){
                        ParamsBody paramsBody = new ParamsBody(
                                data1.getProductName(),
                                data2.getProductKey(),
                                data2.getDeviceKey(),
                                data2.getDeviceType(),
                                data2.getNickName(),
                                data2.getModuleName(),
                                data2.getGranaryId(),
                                data2.getGranaryFen()
                        );
                        paramsBodyList.add(paramsBody);
                    }
                }
            }
        }
        Collections.sort(paramsBodyList, new Comparator<ParamsBody>() {
            @Override
            public int compare(ParamsBody o1, ParamsBody o2) {
                return mWonderUtil.compareTo(o1.getProductName(),o2.getProductName());
            }
        });
        bundle1.putParcelableArrayList("params",paramsBodyList);
        bundle1.putString("function",string);
        return bundle1;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTouchingLetterChanged(String s) {

    }
}