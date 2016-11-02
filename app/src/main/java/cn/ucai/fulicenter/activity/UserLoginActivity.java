package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MD5;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.ResultUtils;
import cn.ucai.fulicenter.utils.SharePrefrenceUtils;

public class UserLoginActivity extends BaseActivity {

    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    Activity mContext;

    String password;
    String userName;
    ProgressDialog pb;
    UserBean user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext=this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        if(FuLiCenterApplication.userName!=null){
            userName=FuLiCenterApplication.getUserName();
            etLoginName.setText(userName);
        }else {
            L.e("main","进入首选项分支");
            SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
            String name=sp.getString("name","");
            etLoginName.setText(name);
        }

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                userName=etLoginName.getText().toString().trim();
                password=etLoginPassword.getText().toString().trim();
                if(userName.matches("[a-zA-Z]\\w{5,15}")){
                    login();
                }

                break;
            case R.id.btn_register:
                MFGT.startActivity(this,new Intent(this,RegisterActivity.class));
                break;
        }
    }

    private void login() {
        L.e("main","s1"+password);
        pb=new ProgressDialog(mContext);
        pb.setMessage("login...");
        pb.show();
        NetDao.UserLogin(mContext, userName, MD5.getMessageDigest(password), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String re) {
                Result result=ResultUtils.getResultFromJson(re,UserBean.class);
                L.e("main","s2");

                if (result!=null){
                    if (result.getRetCode()==0){
                        CommonUtils.showShortToast(R.string.login);
                        user= (UserBean) result.getRetData();
                        //L.e(user.toString());
//                        SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
//                        SharedPreferences.Editor editor=sp.edit();
//                        editor.putString("name",userName);
//                        editor.commit();
                        UserDao userDao=new UserDao(mContext);
                        boolean isSuccess= userDao.saveUser(user);
                        if (isSuccess){
                            SharePrefrenceUtils.getInstance(mContext).saveUser(userName);
                            FuLiCenterApplication.setUser(user);
                            MFGT.finish(mContext);
                        }else {
                            CommonUtils.showShortToast(R.string.user_databases_error);
                        }
                        L.e("main","sp放入数据 "+userName);

                        //MFGT.gotoMainActivity(mContext);

                    }else {
                        if(result.getRetCode()==I.MSG_LOGIN_ERROR_PASSWORD){
                            CommonUtils.showShortToast(R.string.login_fail_pwderror);
                        }else if (result.getRetCode()==I.MSG_LOGIN_UNKNOW_USER){
                            CommonUtils.showShortToast(R.string.login_fail_noUser);
                        }

                    }

                }
                pb.dismiss();
            }

            @Override
            public void onError(String error) {
                pb.dismiss();
                CommonUtils.showShortToast(error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_OK&&requestCode==I.REQUEST_CODE_REGISTER){
            String name=data.getStringExtra(I.User.USER_NAME);
            etLoginName.setText(name);
        }
    }
}
