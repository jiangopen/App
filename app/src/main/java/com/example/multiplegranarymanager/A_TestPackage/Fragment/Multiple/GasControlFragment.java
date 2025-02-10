package com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple;

import static com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.deviceType;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_TestPackage.Activity.GasControlActivity;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.CardQiTiaoDataAdapter;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.Bean.NewDownRaw.MeasureId02Bean;
import com.example.multiplegranarymanager.Bean.QiTiao.QiTiaoBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRaw02Body;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRaw03Body;
import com.example.multiplegranarymanager.Body.NewDownRaw.Profile;
import com.example.multiplegranarymanager.Body.ProductDetial.ParamsBody;
import com.example.multiplegranarymanager.Body.QiTiao.QiTiaoBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.CustomEditText;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GasControlFragment extends Fragment implements View.OnClickListener{
    TextView mFootView;
    TextView text;
    TextView txt_more;//详情
    LinearLayout Wushebei;
    SmartRefreshLayout refreshLayout;
    CustomEditText Search_Input;
    RecyclerView mListView;
    private final String[] TXT_TITLES = new String[]{"智能充氮","气密性检测"};
    private PagerAdapter adapter;
    private int positione = -1;
    private View ve;
    private QiTiaoBody iteme;
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    public static SuoPingDialog suoPingDialog_Gas_Control;
    private HeaderViewAdapter headerViewAdapter;
    private CardQiTiaoDataAdapter adapter_QiTiao;
    private ArrayList<QiTiaoBody> QiTiaoBodyList = new ArrayList<>();
    private CardQiTiaoDataAdapter.OnItemClickListener MyItemClickListener = new CardQiTiaoDataAdapter.OnItemClickListener() {
        @Override
        public void OnItemClick(View v, QiTiaoBody item, int position) {
            if (positione == position){
                Log.d("zyq", "positione"+positione);
                ve = null;
                iteme = null;
                positione = -1;
            } else {
                Log.d("zyq", "positione"+positione);
                ve = v;
                iteme = item;
                positione = position;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple_gas_control, container, false);
        text = view.findViewById(R.id.text1);
        txt_more = view.findViewById(R.id.txt_more);
        Wushebei = view.findViewById(R.id.shebei);
        refreshLayout = view.findViewById(R.id.layout_refresh);
        Search_Input = view.findViewById(R.id.school_friend_member_search_input);
        mListView = view.findViewById(R.id.member);
        Bundle bundle = getArguments();
        assert bundle != null;
        if (bundle.getParcelableArrayList("params")!=null){
            String function = bundle.getString("function");
            ArrayList<ParamsBody> bodies = bundle.getParcelableArrayList("params");
            Log.d("jht", "bodies: "+bodies.size());
            initData(bodies, new OnInitDataFinishedListener() {
                @Override
                public void OnInitDataListener(boolean success) {
                    if (success) {
                        Wushebei.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                        refreshView(getContext(),QiTiaoBodyList);
                    } else {
                        refreshLayout.setVisibility(View.GONE);
                        Wushebei.setVisibility(View.VISIBLE);
                    }
                }
            });
            text.setText("氮气气调");
        }
        txt_more.setOnClickListener(this);
        return view;
    }

    private void refreshView(Context context, ArrayList<QiTiaoBody> List) {
        View vfoot = LayoutInflater.from(context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        int[] layoutIds = new int[]{R.layout.adapter_card_qitiao};
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        mListView.setLayoutManager(layoutManager);
        adapter_QiTiao = new CardQiTiaoDataAdapter(List,layoutIds,context);
        headerViewAdapter = new HeaderViewAdapter(adapter_QiTiao);
        headerViewAdapter.addFooterView(vfoot);
        mListView.setAdapter(headerViewAdapter);
        adapter_QiTiao.setmOnItemClickListener(MyItemClickListener);
    }

    public interface OnInitDataFinishedListener {
        void OnInitDataListener(boolean success);
    }
    public void initData(ArrayList<ParamsBody> Bodies, final OnInitDataFinishedListener listener){
        final Handler handler = new Handler(Looper.getMainLooper());
        //下发实测耗时，所以需要锁屏操作
        suoPingDialog_Gas_Control = new SuoPingDialog(getContext(),"正在实测，请稍等......");
        suoPingDialog_Gas_Control.setCancelable(true);
        suoPingDialog_Gas_Control.show();
        int i = 0;
        for (ParamsBody data : Bodies){
            i++;
            Profile profile = new Profile();
            profile.setId(data.getGranaryId());
            List<String> measure = new ArrayList<>();
            measure.add("qitiaoread");
            profile.setMeasure(measure);
            profile.setData(null);
            profile.setNickname(data.getNickName());
            List<Profile> infos = new ArrayList<>();
            infos.add(profile);
            NewDownRaw02Body newDownRawBody = new NewDownRaw02Body(
                    "02",
                    data.getModuleName(),
                    infos
            );
            String jsonData = gson.toJson(newDownRawBody);
            OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=5", jsonData, Token, new OkHttpUtil.ReqCallBack() {
                @Override
                public void onReqSuccess(Object result) throws JSONException {
                    MeasureId02Bean measureId02Bean = new MeasureId02Bean();
                    try {
                        measureId02Bean = gson.fromJson((String) result,MeasureId02Bean.class);
                    } catch (JsonSyntaxException e){
                        e.printStackTrace();
                        util.showToast(getContext(),"GasControlFragment读取出错");
                    }
                    if (measureId02Bean.getData()!=null&&measureId02Bean.getData().getMeasureId()!=null){
                        String measureId = measureId02Bean.getData().getMeasureId();
                        final Runnable pollRunnable = new Runnable() {
                            @Override
                            public void run() {
                                NewDownRaw03Body newDownRaw03Body = new NewDownRaw03Body(
                                        "03",
                                        measureId
                                );
                                String gsonData = gson.toJson(newDownRaw03Body);
                                OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=5", gsonData, Token, new OkHttpUtil.ReqCallBack() {
                                    @Override
                                    public void onReqSuccess(Object result) throws JSONException {
                                        QiTiaoBean qiTiaoRead = new QiTiaoBean();
                                        try {
                                            qiTiaoRead = gson.fromJson((String) result, QiTiaoBean.class);
                                        } catch (JsonSyntaxException e){
                                            e.printStackTrace();
                                            util.showToast(getContext(),"获取数据出错");
                                        }
                                        if (qiTiaoRead.getData().getProgress()<1){
                                            return;
                                        } else {
                                            if (qiTiaoRead.getData().getData()!=null&&qiTiaoRead.getData().getData().size()>0){
                                                QiTiaoBody body = new QiTiaoBody(
                                                        data.getProductName(),
                                                        data.getModuleName(),
                                                        data.getGranaryId(),
                                                        data.getGranaryFen(),
                                                        data.getNickName(),
                                                        data.getProductKey(),
                                                        data.getDeviceKey(),
                                                        qiTiaoRead.getData().getData().get(0).getDate(),
                                                        qiTiaoRead.getData().getData().get(0).getHardwareData().get(0),
                                                        qiTiaoRead.getData().getData().get(0).getHardwareData().get(1),
                                                        qiTiaoRead.getData().getData().get(0).getHardwareData().get(2),
                                                        qiTiaoRead.getData().getData().get(0).getHardwareData().get(3),
                                                        qiTiaoRead.getData().getData().get(0).getHardwareData().get(4),
                                                        qiTiaoRead.getData().getData().get(0).getHardwareData().get(5),
                                                        qiTiaoRead.getData().getData().get(0).getHardwareData().get(6),
                                                        qiTiaoRead.getData().getData().get(0).getHardwareData().get(7),
                                                        qiTiaoRead.getData().getData().get(0).getHardwareData().get(8),
                                                        qiTiaoRead.getData().getData().get(0).getHardwareData().get(9),
                                                        qiTiaoRead.getData().getData().get(0).getHardwareData().get(10)
                                                );
                                                QiTiaoBodyList.add(body);
                                            }
                                        }
                                        suoPingDialog_Gas_Control.dismiss();
                                        adapter_QiTiao.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onReqFailed(String errorMsg) {
                                        suoPingDialog_Gas_Control.dismiss();
                                        listener.OnInitDataListener(false);
                                        util.showToast(getContext(),errorMsg);
                                    }
                                });
                            }
                        };
                        handler.postDelayed(pollRunnable,2000);
                    }
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    suoPingDialog_Gas_Control.dismiss();
                    listener.OnInitDataListener(false);
                    util.showToast(getContext(),errorMsg);
                }
            });
        }
        if (i==Bodies.size()){
            listener.OnInitDataListener(true);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_more:
                if (positione!=-1){
                    Intent intent = new Intent(getContext(), GasControlActivity.class);
                    intent.putExtra("productName",QiTiaoBodyList.get(positione).getProductName());
                    intent.putExtra("moudleName",QiTiaoBodyList.get(positione).getMoudleName());
                    intent.putExtra("granaryId",QiTiaoBodyList.get(positione).getGranaryId());
                    intent.putExtra("granaryFen",QiTiaoBodyList.get(positione).getGranaryFen());
                    intent.putExtra("nickName",QiTiaoBodyList.get(positione).getNickName());
                    intent.putExtra("productKey",QiTiaoBodyList.get(positione).getProductKey());
                    intent.putExtra("deviceKey",QiTiaoBodyList.get(positione).getDeviceKey());
                    intent.putExtra("date",QiTiaoBodyList.get(positione).getDate());
                    intent.putExtra("N2_list_00",QiTiaoBodyList.get(positione).getN2_list_00());
                    intent.putExtra("pressure_list_01",QiTiaoBodyList.get(positione).getPressure_list_01());
                    intent.putExtra("QiTiaoStatus_list_02",QiTiaoBodyList.get(positione).getQiTiaoStatus_list_02());
                    intent.putExtra("FaMengStatus_list_03",QiTiaoBodyList.get(positione).getFaMengStatus_list_03());
                    intent.putExtra("FengJiStatus_list_04",QiTiaoBodyList.get(positione).getFengJiStatus_list_04());
                    intent.putExtra("N2QiJiStatus_list_05",QiTiaoBodyList.get(positione).getN2QiJiStatus_list_05());
                    intent.putExtra("ChunDu_list_06",QiTiaoBodyList.get(positione).getChunDu_list_06());
                    intent.putExtra("LiuLiang_list_07",QiTiaoBodyList.get(positione).getLiuLiang_list_07());
                    intent.putExtra("PressureNum_list_08",QiTiaoBodyList.get(positione).getPressureNum_list_08());
                    intent.putExtra("Temperature_list_09",QiTiaoBodyList.get(positione).getTemperature_list_09());
                    intent.putExtra("LiuLiangNum_list_10",QiTiaoBodyList.get(positione).getLiuLiangNum_list_10());
                    startActivity(intent);
                } else {
                    Log.d("zyq", "onClick: 请选择一个仓 请选择一个仓");
                    util.showToast(getContext(),"请选择一个仓");
                }
                break;
            default:
                break;
        }
    }
}