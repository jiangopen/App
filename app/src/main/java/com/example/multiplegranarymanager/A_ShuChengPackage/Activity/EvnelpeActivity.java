package com.example.multiplegranarymanager.A_ShuChengPackage.Activity;

import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.Token;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multiplegranarymanager.A_ShuChengPackage.Adapter.MyAlartAdapter;
import com.example.multiplegranarymanager.A_ShuChengPackage.AllInterfaceClass;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.UserAlertInfos.AlertInfo;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.UserAlertInfos.UserAlertInfos;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Body;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Headers;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.util.InputInfo;
import com.kongzue.dialog.v2.InputDialog;
import com.kongzue.dialog.v2.Notification;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvnelpeActivity extends AppCompatActivity {
    TextView selectAll;
    SwipeRecyclerView evnelpeRecycleViewCe;
    TextView biaoji;
    TextView tvSelectNum;
    LinearLayout llMycollectionBottomDialog;
    TextView editSelect,title;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private List<AlertInfo> mDatas;
    private Map<String,String> KeyandType = new HashMap<>();
    private ArrayList<String> productKey = new ArrayList<>();
    private String productKeyOne;
    private MyAlartAdapter mAdapter;
    private SharedPreferences.Editor editor;
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evnelpe);
        selectAll = findViewById(R.id.select_all);
        evnelpeRecycleViewCe = findViewById(R.id.evnelpe_recycleView_ce);
        biaoji = findViewById(R.id.biao_ji);
        tvSelectNum = findViewById(R.id.tv_select_num);
        llMycollectionBottomDialog = findViewById(R.id.ll_mycollection_bottom_dialog);
        ActionBar actionBar = getSupportActionBar();
        //actionBar的设置(使用自定义的设置)
        if (actionBar != null) {
            actionBar.hide();
        }
        editSelect = findViewById(R.id.edit_select);
        title = findViewById(R.id.title_text);
        Intent intent = getIntent();
        productKey = (ArrayList<String>) intent.getSerializableExtra("productKey");
        mDatas = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        evnelpeRecycleViewCe.setLayoutManager(layoutManager);
        //自定义核心就是DefineLoadMoreView类
        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(this);
        evnelpeRecycleViewCe.addFooterView(loadMoreView);//添加为Footer
        evnelpeRecycleViewCe.setLoadMoreView(loadMoreView);//设置LoadMoreView更新监听
        evnelpeRecycleViewCe.setLoadMoreListener(mLoadMoreListener);//加载更多的监听
        evnelpeRecycleViewCe.setAutoLoadMore(true);
        evnelpeRecycleViewCe.loadMoreFinish(false,true);
        mAdapter = new MyAlartAdapter(this);
        evnelpeRecycleViewCe.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(mOnSwipeListener);
        productKeyOne = productKey.get(0);
        //初始化第一页数据
        BaojingOne(productKeyOne,1);
    }
    private MyAlartAdapter.OnSwipeListener mOnSwipeListener = new MyAlartAdapter.OnSwipeListener() {
        @Override
        public void onItemClickListener(int pos, AlertInfo myLiveList) {
            if (mAdapter.flage){
                if (myLiveList.isCheck){
                    mAdapter.index--;
                    myLiveList.setCheck(false);
                    selectAll.setText("全选");
                } else {
                    mAdapter.index++;
                    myLiveList.setCheck(true);
                    if (mAdapter.index == mDatas.size()){
                        selectAll.setText("全不选");
                    }
                }
                tvSelectNum.setText(mAdapter.index+"");
                mAdapter.notifyDataSetChanged();
            } else {
                //查看详情
                Intent intent = new Intent(EvnelpeActivity.this,AlarmDetailActivity.class);
                intent.putExtra("position",""+pos);
                intent.putExtra("oneAlertInfo",myLiveList);
                startActivity(intent);
            }
        }
    };

    /**
     * 加载更多。
     */
    private SwipeRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {
            page++;
            BaojingOne(productKeyOne, page);
        }
    };
    private void BaojingOne(String productKey, int page) {
        AllInterfaceClass<UserAlertInfos> one = new AllInterfaceClass<>(UserAlertInfos.class);
        Body body = new Body();
        body.setPageIndex(page);
        body.setAsc(0);
        body.setPageSize(20);
        body.setUnRead(1);
        Headers headers = new Headers();
        headers.setTokenOffline(Token);
        NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
        newDownRawBodyTWO.setQuery(null);
        newDownRawBodyTWO.setMethod("POST");
        newDownRawBodyTWO.setHeaders(headers);
        newDownRawBodyTWO.setUrl("/userAlertInfos");
        newDownRawBodyTWO.setBody(body);
        one.PostOne(newDownRawBodyTWO, "EvnelpeActivity/BaojingOne", new AllInterfaceClass.PostCallBack<UserAlertInfos>() {
            @Override
            public void onSuccess(UserAlertInfos zyq) {
                if (!zyq.getData().getAlertInfo().isEmpty()) {
                    if (page == 1) {
                        //初始加载第一页
                        mDatas.clear();
                        mDatas.addAll(zyq.getData().getAlertInfo());
                        mAdapter.notifyAdapter(mDatas,false);
                    } else {
                        //继续加载末尾增加
                        mAdapter.notifyAdapter(zyq.getData().getAlertInfo(),true);
                    }
                    //判断是否还有更多
                    if (zyq.getData().getTotal() > mDatas.size()) {
                        evnelpeRecycleViewCe.loadMoreFinish(false,true);
                    } else {
                        evnelpeRecycleViewCe.loadMoreFinish(false,false);
                    }
                } else {
                    evnelpeRecycleViewCe.loadMoreFinish(true,false);
                }
            }

            @Override
            public void onFailure(String zyq) {
                util.showToast(EvnelpeActivity.this,zyq);
                mDatas.clear();
                mAdapter.notifyDataSetChanged();;
                evnelpeRecycleViewCe.loadMoreError(0, zyq);
            }
        });
    }

    static final class DefineLoadMoreView extends LinearLayout implements SwipeRecyclerView.LoadMoreView, View.OnClickListener{
        private ProgressBar mProgressbar;
        private TextView mTvMessage;
        private SwipeRecyclerView.LoadMoreListener mLoadMoreListener;
        public DefineLoadMoreView(Context context) {
            super(context);
            setLayoutParams(new ViewGroup.LayoutParams(-1,-2));
            setGravity(Gravity.CENTER);
            setVisibility(GONE);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int minHeight = (int) (displayMetrics.density*60+0.5);
            setMinimumHeight(minHeight);
            inflate(context,R.layout.layout_fotter_loadmore,this);
            mProgressbar = findViewById(R.id.progress_bar);
            mTvMessage = findViewById(R.id.tv_message);
            setOnClickListener(this);
        }
        //马上开始回调加载更多，这里应该显示进度条
        @Override
        public void onLoading(){
            setVisibility(VISIBLE);
            mProgressbar.setVisibility(VISIBLE);
            mTvMessage.setVisibility(VISIBLE);
            mTvMessage.setText("正在努力加载，请稍等......");
        }
        @Override
        public void onLoadFinish(boolean dataEmpty,boolean hasMore){
            if (!hasMore){
                setVisibility(VISIBLE);
                if (dataEmpty){
                    mProgressbar.setVisibility(GONE);
                    mTvMessage.setVisibility(VISIBLE);
                    mTvMessage.setText("暂时没有数据");
                } else {
                    mProgressbar.setVisibility(GONE);
                    mTvMessage.setVisibility(VISIBLE);
                    mTvMessage.setText("没有更多数据");
                }
            } else {
                setVisibility(INVISIBLE);
            }
        }
        @Override
        public void onWaitToLoadMore(SwipeRecyclerView.LoadMoreListener loadMoreListener){
            this.mLoadMoreListener = loadMoreListener;
            setVisibility(VISIBLE);
            mProgressbar.setVisibility(GONE);
            mTvMessage.setVisibility(VISIBLE);
            mTvMessage.setText("点我加载更多");
        }

        @Override
        public void onLoadError(int errorCode, String errorMessage) {
            setVisibility(VISIBLE);
            mProgressbar.setVisibility(GONE);
            mTvMessage.setVisibility(VISIBLE);
            //这里不要直接设置错误信息，要根据errorCode动态设置错误数据
            mTvMessage.setText(errorMessage);
        }

        @Override
        public void onClick(View v) {
            if (mLoadMoreListener!=null){
                mLoadMoreListener.onLoadMore();
            }
        }
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event){
//        Intent intent = new Intent(EvnelpeActivity.this, MainActivity.class);
//        intent.putExtra("Token", Token);
//        startActivity(intent);
//        finish();
//        return super.onKeyDown(keyCode, event);
//    }
    /**
     * 编辑、取消编辑
     *
     * @param view
     */
    public void btnEditList(View view) {

        mAdapter.flage = !mAdapter.flage;

        if (mAdapter.flage) {
            llMycollectionBottomDialog.setVisibility(View.VISIBLE);
            editSelect.setText("取消");
            evnelpeRecycleViewCe.loadMoreError(1, "编辑状态不可加载");
        } else {
            llMycollectionBottomDialog.setVisibility(View.GONE);
            editSelect.setText("编辑");
            initialize();
            evnelpeRecycleViewCe.loadMoreFinish(false, true);
            if (mDatas.size() < 20) {//界面中数据少于二十条重新加载数据
                page = 1;
                BaojingOne(productKeyOne, 1);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 全选
     *
     * @param view
     */
    public void btnSelectAllList(View view) {
        if (mAdapter.flage) {
            if (mAdapter.index == mDatas.size()) {
                for (int i = 0; i < mDatas.size(); i++) {
                    mDatas.get(i).isCheck = false;
                }
                mAdapter.index = 0;
                selectAll.setText("全选");
            } else {
                for (int i = 0; i < mDatas.size(); i++) {
                    mDatas.get(i).isCheck = true;
                }
                mAdapter.index = mDatas.size();
                selectAll.setText("全不选");
            }
            tvSelectNum.setText(mAdapter.index + "");
            /*if (mAdapter.index != 0) {
                biaoJi.setText("标记(" + mAdapter.index + ")");
            } else {
                biaoJi.setText("标记");
            }*/
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 全不选(界面初始化，归零)
     *
     * @param /view
     */
    public void initialize() {
        //if (mAdapter.flage) {
        for (int i = 0; i < mDatas.size(); i++) {
            mDatas.get(i).isCheck = false;
        }
        mAdapter.index = 0;
        selectAll.setText("全选");
        tvSelectNum.setText(mAdapter.index + "");
        mAdapter.notifyDataSetChanged();
        //btnEditList(editSelect);
        //}
    }

    /**
     * 反选
     *
     * @param view
     */
    public void btnfanxuanList(View view) {
        if (mAdapter.flage) {
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).isCheck) {
                    mDatas.get(i).isCheck = false;
                } else {
                    mDatas.get(i).isCheck = true;
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 标记选中数据
     *
     * @param view
     */
    public void btnOperateList(View view) {
        List<String> ids = new ArrayList<>();
        if (mAdapter.flage) {//是否为编辑状态
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).isCheck) {
                    ids.add(mDatas.get(i).getAlertId());
                }
            }
            if (ids.size() == 0) {
                Toast.makeText(EvnelpeActivity.this, "请选择标记项", Toast.LENGTH_SHORT).show();
                return;
            }
            View customView = LayoutInflater.from(EvnelpeActivity.this).inflate(R.layout.layout_custom, null);
            InputDialog.show(EvnelpeActivity.this, null, null, new InputDialogOkButtonClickListener() {
                @Override
                public void onClick(Dialog dialog, String inputText) {
                    if (!inputText.equals("admin")) {
                        Notification.show(EvnelpeActivity.this, 0, "管理员密码错误");//小提示：用户名是：kongzue
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        BiaoJiStatus(productKeyOne, ids);
                        //deleteListData(ids);
                    }
                }
            }).setCustomView(customView).setInputInfo(new InputInfo().setMAX_LENGTH(20).setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)).setCanCancel(true);
            //Toast.makeText(EvnelpeActivity.this, ids.toString(), Toast.LENGTH_SHORT).show();
//            Log.e(TAG, ids.toString());
        }
    }
    //标记选中项
    private void BiaoJiStatus(String productKey, List<String> alertIds) {
//        MoreAlarmBody moreAlarmBody = new MoreAlarmBody();
//        moreAlarmBody.setAlertIds(alertIds);
//        moreAlarmBody.setProductKey(productKey);
//        Gson gson = new Gson();
//        String jsonData = gson.toJson(moreAlarmBody);
//        OkHttpUtil.Post1("api/v1/userAlertInfos", jsonData, MainActivity.Token, new OkHttpUtil.ReqCallBack() {
//            @Override
//            public void onReqSuccess(Object result) throws JSONException {
//                Toast.makeText(EvnelpeActivity.this, "成功标记" + alertIds.size() + "条为已读", Toast.LENGTH_LONG).show();
//                MainActivity.totalNumber = MainActivity.totalNumber - alertIds.size();
//                MainActivity.badge.setBadgeNumber(MainActivity.totalNumber);
//                deleteListData(alertIds);
//            }
//
//            @Override
//            public void onReqFailed(String errorMsg) {
//                Log.e(TAG, errorMsg);
//                Toast.makeText(EvnelpeActivity.this, "标记失败", Toast.LENGTH_LONG).show();
//            }
//        });
    }
}