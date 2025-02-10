package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter;

import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.deviceType;
import static com.example.multiplegranarymanager.Body.Granary.GranaryListBean.Data;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.PutDataBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.GranaryBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.util.InputInfo;
import com.kongzue.dialog.v2.InputDialog;
import com.kongzue.dialog.v2.Notification;

import org.json.JSONException;

import java.util.ArrayList;

public class SettingDataAdapter extends MultiLayoutAdapter<Data> implements SectionIndexer, View.OnClickListener{
    private ArrayList<Data> datas;
    private ArrayList<Data> refreshdatas;
    public int flag = 0;
    private Context context;
    Gson gson = new Gson();
    private SuoPingDialog suoPingDialog;
    private View customView;
    private Util1 util = new Util1();
    private OnItemClickListener mListener;
    private OnItemClickListener2 mListener2;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public void setOnItemClickListener2(OnItemClickListener2 listener2) {
        mListener2 = listener2;
    }
    public SettingDataAdapter(ArrayList<Data> mDatas, int[] layoutIds, Context context) {
        super(mDatas, layoutIds);
        this.datas = mDatas;
        this.refreshdatas = mDatas;
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    public int getPositionForSection(int section) {
        for (int i=0;i<getItemCount();i++){
            char firstChar = datas.get(i).getGranaryName().charAt(0);
            if (firstChar == section){
                return i;
            }
        }
        return -1;
    }
    @Override
    public int getSectionForPosition(int position) {
        Data item = datas.get(position);
        return item.getGranaryName().charAt(0);
    }
    @Override
    public void onClick(View v) {
        String position = (String) v.getTag();
        Data item = null;
        for (Data data : datas){
            if (data.getGranaryKey().equals(position)){
                item = data;
            }
        }
        int type = 0;
        if (v.getId()==R.id.txt_edit) {
            Log.d("zyq", "onClick: txt_edit          "+item.getGranaryName());
            type = 1;
        } else if (v.getId()==R.id.txt_project_info) {
            Log.d("zyq", "onClick: txt_project_info  "+item.getGranaryName());
            type = 2;
        } else if (v.getId()==R.id.txt_delete) {
            Log.d("zyq", "onClick: txt_delete        "+item.getGranaryName());
            type = 3;
        } else if (v.getId()==R.id.txt_commmand_info) {
            Log.d("zyq", "onClick: txt_commmand_info "+item.getGranaryName());
            type = 4;
        }
        TestTypeTree(type,item);
    }

    private void TestTypeTree(int type, Data item) {
        customView = LayoutInflater.from(context).inflate(R.layout.layout_custom, null);
        if (type!=0 && item!=null){
            InputDialog.show(context, null, null, new InputDialogOkButtonClickListener() {
                @Override
                public void onClick(Dialog dialog, String inputText) {
                    if (!inputText.equals("honen")) {
                        Notification.show(context,0,"管理员密码错误");
                    } else {
                        dialog.dismiss();
                        //自定义Dialog Title样式
                        LayoutInflater layoutInflater = LayoutInflater.from(context);
                        View view = layoutInflater.inflate(R.layout.dialog_custom_title,null);
                        final TextView textView = view.findViewById(R.id.modify_title);
                        if (type == 1){
                            textView.setText("编辑粮仓信息["+item.getGranaryName()+"]");
                            final View view1 = layoutInflater.inflate(R.layout.dialog_custom_down_rate_clear,null);
                            final EditText et_Name = view1.findViewById(R.id.et_nHour);
                            final EditText et_url = view1.findViewById(R.id.et_nMinute);
                            final EditText et_id = view1.findViewById(R.id.et_nSecond);
                            String name,url,id;
                            try {
                                name = item.getGranaryName();
                                url = item.getUrl();
                                id = item.getCommandMapId();
                                et_Name.setText(name);
                                et_id.setText(id);
                                et_url.setText(url);
                            } catch (Exception e) {
                                e.printStackTrace();
                                util.showToast(context,"获取仓库信息错误");
                            }
                            new AlertDialog
                                    .Builder(context)
                                    .setCustomTitle(view)
                                    .setView(view1)
                                    .setCancelable(false)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            String newName = et_Name.getText().toString();
                                            String newurl = et_url.getText().toString();
                                            String newid = et_id.getText().toString();
                                            if (newName.equals("")||newurl.equals("")||newid.equals("")) {
                                                util.showToast(context,"内容不能为空");
                                            } else {
                                                suoPingDialog = new SuoPingDialog(context, "正在配置，请稍等......");
                                                suoPingDialog.setCancelable(false);
                                                suoPingDialog.show();
                                                GranaryBody body = new GranaryBody();
                                                body.setGranaryName(newName);
                                                body.setUrl(newurl);
                                                body.setCommandMapId(newid);
                                                body.setGranaryKey(item.getGranaryKey());
                                                NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
                                                Body.setUrl("/granary");
                                                Body.setMethod("PUT");
                                                Body.setBody(body);
                                                Body.setQuery(null);
                                                Body.setHeaders(null);
                                                String jsondata = gson.toJson(Body);
                                                UpToYunUpdata(jsondata,item);
                                            }
                                        }
                                    })
                                    .setNegativeButton("取消",null)
                                    .show();
                        } else if (type == 2) {
                            if (mListener != null) {
                                mListener.OnItemClick(item.getCommandMapId(), item.getUrl());
                            }
                        } else if (type == 3) {
                            textView.setText("删除粮仓["+item.getGranaryName()+"]");
                            final View view2 = layoutInflater.inflate(R.layout.dialog_custom_down_rate_delete,null);
                            new AlertDialog
                                    .Builder(context)
                                    .setCustomTitle(view)
                                    .setView(view2)
                                    .setCancelable(false)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            suoPingDialog = new SuoPingDialog(context, "正在配置，请稍等......");
                                            suoPingDialog.setCancelable(false);
                                            suoPingDialog.show();
                                            NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
                                            Body.setBody(null);
                                            Body.setHeaders(null);
                                            Body.setMethod("DELETE");
                                            Body.setUrl("/granary");
                                            Body.setQuery(item.getGranaryKey());
                                            String jsondata = gson.toJson(Body);
                                            UpToYunDelete(jsondata,item);
                                        }
                                    })
                                    .setNegativeButton("取消",null)
                                    .show();
                        } else if (type == 4) {
                            if (mListener2 != null) {
                                mListener2.OnItemClick2(item.getCommandMapId(),item.getUrl());
                            }
                        } else {
                            util.showToast(context,"请重新选择！");
                        }
                    }
                }
            }).setCustomView(customView).setInputInfo(new InputInfo().setMAX_LENGTH(10).setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)).setCanCancel(true);
        } else {
            util.showToast(context,"请重新选择！");
        }
    }

    private void UpToYunDelete(String jsondata, Data item) {
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                PutDataBean putDataBean = new PutDataBean();
                try {
                    putDataBean = gson.fromJson((String) result,PutDataBean.class);
                } catch (JsonSyntaxException e) {
                    util.showToast(context,"修改错误");
                    e.printStackTrace();
                    return;
                }
                if (putDataBean.getCode()==200) {
                    item.setUrl(null);
                    item.setGranaryKey(null);
                    item.setGranaryName(null);
                    item.setCommandMapId(null);
                    util.showToast(context,"修改成功");
                } else {
                    util.showToast(context,"修改错误");
                }
                suoPingDialog.dismiss();
                notifyDataSetChanged();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                util.showToast(context,errorMsg);
            }
        });
    }

    private void UpToYunUpdata(String jsondata, Data item) {
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata,  new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                PutDataBean putDataBean = new PutDataBean();
                try {
                    putDataBean = gson.fromJson((String) result,PutDataBean.class);
                } catch (JsonSyntaxException e) {
                    util.showToast(context,"修改错误");
                    e.printStackTrace();
                    return;
                }
                if (putDataBean.getData()!=null) {
                    item.setGranaryName(putDataBean.getData().getGranaryName());
                    item.setCommandMapId(putDataBean.getData().getCommandMapId());
                    item.setUrl(putDataBean.getData().getUrl());
                    util.showToast(context,"修改成功");
                } else {
                    util.showToast(context,"修改错误");
                }
                suoPingDialog.dismiss();
                notifyDataSetChanged();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                util.showToast(context,errorMsg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0:datas.size();
    }
//    public Object getItem(int i){
//        return datas == null ? null : datas.get(i);
//    }
    public void setFreshDates(ArrayList<Data> datas){
        this.refreshdatas = datas;
    }
    public ArrayList<Data> getFreshDates(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }

    @Override
    protected void coverts(BaseViewHolder holder, Data item, int position, int itemType) {
        if (item.getGranaryName() != null){
            holder.setText(R.id.txt_name,item.getGranaryName());
        } else {
            holder.setText(R.id.txt_name,"——");
        }
        if (item.getCommandMapId() != null){
            holder.setText(R.id.txt_id,item.getCommandMapId());
        } else {
            holder.setText(R.id.txt_id,"——");
        }
        if (item.getUrl() != null){
            holder.setText(R.id.txt_address,item.getUrl());
        } else {
            holder.setText(R.id.txt_address,"——");
        }
        TextView txt_edit = holder.getView(R.id.txt_edit);
        TextView txt_project_info = holder.getView(R.id.txt_project_info);
        TextView txt_delete = holder.getView(R.id.txt_delete);
        TextView txt_commmand_info = holder.getView(R.id.txt_commmand_info);
        txt_edit.setTag(item.getGranaryKey());
        txt_project_info.setTag(item.getGranaryKey());
        txt_delete.setTag(item.getGranaryKey());
        txt_commmand_info.setTag(item.getGranaryKey());
        txt_edit.setOnClickListener(this);
        txt_project_info.setOnClickListener(this);
        txt_delete.setOnClickListener(this);
        txt_commmand_info.setOnClickListener(this);
    }

    public interface OnItemClickListener {
        void OnItemClick(String commandMapId, String url);
    }
    public interface OnItemClickListener2 {
        void OnItemClick2(String commandMapId, String url);
    }
}
