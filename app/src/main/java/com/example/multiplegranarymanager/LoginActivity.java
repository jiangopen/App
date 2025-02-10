package com.example.multiplegranarymanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blanke.lib.MaterialProgressDrawable;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity;
import com.example.multiplegranarymanager.Body.LoginBody;
import com.example.multiplegranarymanager.Style.ProBtn;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.gson.Gson;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener, TextWatcher {
    EditText login_user,login_pswd;
    ImageView del_user,del_pswd;
    CheckBox box_rem;
    SharedPreferences.Editor editor;
    ProBtn login_submit;
    private Toast mToast;
    private String Token;
    private String deviceType;
    public static String username;
    private String password;
    private Wave mWaveDrawale = new Wave();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initCheckBox();
        login_submit.setLoading(false);
    }
    private void initCheckBox() {
        //读取密码
        SharedPreferences pref = getSharedPreferences("checkBoxStates", Context.MODE_PRIVATE);
        boolean isRemember = pref.getBoolean("remember_box",false);
        if (isRemember) {
            String username = pref.getString("user_name","");
            String password = pref.getString("pass_word","");
            if (!("".equals(username)&&"".equals(password))) {
                box_rem.setChecked(true);
                login_user.setText(username);
                login_pswd.setText(password);
            }
        }
    }

    private void initView() {
        //username
        login_user = findViewById(R.id.et_login_username);
        del_user = findViewById(R.id.img_login_username_del);
        //password
        login_pswd = findViewById(R.id.et_login_password);
        del_pswd = findViewById(R.id.img_login_password_del);
        //login
        login_submit = findViewById(R.id.bt_login_submit);
        login_submit.setCompoundDrawables(mWaveDrawale,null,null,null);
        //记住密码
        box_rem = findViewById(R.id.cb_login_remember);
        //点击事件
        login_user.setOnClickListener(this);
        del_user.setOnClickListener(this);
        login_pswd.setOnClickListener(this);
        del_pswd.setOnClickListener(this);
        login_submit.setOnClickListener(this);
        //其他事件
        login_user.setOnFocusChangeListener(this);
        login_user.addTextChangedListener(this);
        login_pswd.setOnFocusChangeListener(this);
        login_pswd.addTextChangedListener(this);
        //打开通知
        CheckRemin();
    }

    private void CheckRemin() {
        NotificationManagerCompat notification = NotificationManagerCompat.from(this);
        boolean isEnabled = notification.areNotificationsEnabled();
        if (!isEnabled) {
            //未打开通知
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请在“通知”中打开通知权限,否则将无法收到报警信息提醒哦!")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent = new Intent();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("android.provider.extra.APP_PACKAGE", LoginActivity.this.getPackageName());
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("app_package", LoginActivity.this.getPackageName());
                                intent.putExtra("app_uid", LoginActivity.this.getApplicationInfo().uid);
                                //startActivity(intent);
                            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + LoginActivity.this.getPackageName()));
                            } else if (Build.VERSION.SDK_INT >= 15) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", LoginActivity.this.getPackageName(), null));
                            }
                            startActivity(intent);
                        }
                    })
                    .create();
            alertDialog.show();
        }
    }
    private void showToast(String msg) {
        if (null != mToast) {
            mToast.setText(msg);
        } else {
            mToast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
    private void SaveUserData() {
        //写入密码
        SharedPreferences pref = getSharedPreferences("checkBoxStates", Context.MODE_PRIVATE);
        editor = pref.edit();
        if (box_rem.isChecked()) {
            editor.putBoolean("remember_box", true);
            editor.putString("user_name", username);
            editor.putString("pass_word", password);
        } else {
            editor.putBoolean("remember_box", false);
            editor.putString("user_name", "");
            editor.putString("pass_word", "");
        }
        editor.apply();
    }
    
    @Override
    public void afterTextChanged(Editable s) {
        username = login_user.getText().toString().trim();
        password = login_pswd.getText().toString().trim();
        //是否显示清除按钮

        if (username.length()>0){
            del_user.setVisibility(View.VISIBLE);
        } else {
            del_user.setVisibility(View.INVISIBLE);
        }
        if (password.length()>0){
            del_pswd.setVisibility(View.VISIBLE);
        } else {
            del_pswd.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_login_username:
                login_pswd.clearFocus();
                login_user.setFocusableInTouchMode(true);
                login_user.requestFocus();
                break;
            case R.id.et_login_password:
                login_user.clearFocus();
                login_pswd.setFocusableInTouchMode(true);
                login_pswd.requestFocus();
                break;
            case R.id.img_login_username_del:
                //清空用户名
                login_user.setText(null);
                break;
            case R.id.img_login_password_del:
                //清空密码
                login_pswd.setText(null);
                break;
            case R.id.bt_login_submit:
                //登录
                LoginRequest();
                break;
            default:
                break;
        }
    }

    private void LoginRequest() {
        //记住密码
        SaveUserData();
        if (username.equals("淮南粮情测温")){
            deviceType = "cloud_request_trans_huainan";
        } else if (username.equals("无为十里粮情测温")){
            deviceType = "cloud_request_trans_wuwei_shili";
        } else if (username.equals("无为土桥粮情测温")) {
            deviceType = "cloud_request_trans_wuwei_tuqiao";
        } else if (username.equals("新桥粮情测温")){
            deviceType = "cloud_request_trans_xinqiao";
        } else if (username.equals("测试粮情测温")){
            deviceType = "cloud_request_trans_test";
        } else if (username.equals("舒城粮情测温")) {
            deviceType = "sync_user_shucheng";
        } else {
            deviceType = "LQBXZJ";
        }
        //向服务器发送登录请求
        LoginBody loginBody = new LoginBody();
        String Username;
        String Password;
        if ((username.equals("新桥粮情测温")||username.equals("淮南粮情测温")||username.equals("无为十里粮情测温")||username.equals("无为土桥粮情测温")||username.equals("测试粮情测温"))&&password.equals("123456")){
            Username = "实验室轴承";
            Password = "123456";
        } else {
            Username = username;
            Password = password;
        }
        loginBody.setUsername(Username);
        loginBody.setPassword(Password);
        Gson gson = new Gson();
        String jsonData = gson.toJson(loginBody);
        OkHttpUtil.LoginPost1("session", jsonData, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                Token = (String) result;
                //取消dialog
                login_submit.setLoading(false);
                Intent intent;
                Log.d("wan", "onReqSuccess: " + username);
                if (Username.equals("实验室轴承") && !username.equals("新桥粮情测温")){
                    intent = new Intent(LoginActivity.this, com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.class);
                } else if (username.equals("新桥粮情测温")) {
                    intent = new Intent(LoginActivity.this, com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.class);
                } else if (Username.equals("舒城粮情测温")) {
                    intent = new Intent(LoginActivity.this, com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.class);
                } else {
                    intent = new Intent(LoginActivity.this, com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.class);
                }
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("Token",Token);
                intent.putExtra("deviceType",deviceType);
                startActivity(intent);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                //取消dialog
                login_submit.setLoading(false);
                if (errorMsg.equals("请求参数错误")) {
                    showToast("用户名密码不能为空");
                } else {
                    showToast(errorMsg);
                }
            }
        });
//        if (!(username.equals("新桥粮情测温") && password.equals("123456"))){
//            //向服务器发送登录请求
//            LoginBody loginBody = new LoginBody();
//            String Username;
//            String Password;
//            if ((username.equals("淮南粮情测温")||username.equals("无为十里粮情测温")||username.equals("无为土桥粮情测温")||username.equals("测试粮情测温"))&&password.equals("123456")){
//                Username = "实验室轴承";
//                Password = "123456";
//            } else {
//                Username = username;
//                Password = password;
//            }
//            loginBody.setUsername(Username);
//            loginBody.setPassword(Password);
//            Gson gson = new Gson();
//            String jsonData = gson.toJson(loginBody);
//            OkHttpUtil.LoginPost1("session", jsonData, new OkHttpUtil.ReqCallBack() {
//                @Override
//                public void onReqSuccess(Object result) throws JSONException {
//                    Token = (String) result;
//                    //取消dialog
//                    login_submit.setLoading(false);
//                    Intent intent;
//                    if (Username.equals("实验室轴承")){
//                        intent = new Intent(LoginActivity.this, com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.class);
//                    } else {
//                        intent = new Intent(LoginActivity.this, com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.class);
//                    }
//                    intent.putExtra("username",username);
//                    intent.putExtra("Token",Token);
//                    intent.putExtra("deviceType",deviceType);
//                    startActivity(intent);
//                }
//
//                @Override
//                public void onReqFailed(String errorMsg) {
//                    //取消dialog
//                    login_submit.setLoading(false);
//                    if (errorMsg.equals("请求参数错误")) {
//                        showToast("用户名密码不能为空");
//                    } else {
//                        showToast(errorMsg);
//                    }
//                }
//            });
//        } else if (username.equals("新桥粮情测温") && password.equals("123456")) {
//            //取消dialog
//            login_submit.setLoading(false);
//            Log.d("wan", "LoginRequest: "+login_submit.getLoading());
//            if (!login_submit.getLoading()) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.putExtra("username",username);
//                intent.putExtra("Token",Token);
//                intent.putExtra("deviceType",deviceType);
//                startActivity(intent);
//            } else {
//                Log.d("wan", "LoginRequest: ");
//            }
//
//        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void onGlobalLayout() {

    }
}