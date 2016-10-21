package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        if(FuLiCenterApplication.userName!=null){
            userName=FuLiCenterApplication.getUserName();
            etLoginName.setText(userName);
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
                    login(password,userName);
                }

                break;
            case R.id.btn_register:
                MFGT.startActivity(this,new Intent(this,RegisterActivity.class));
                break;
        }
    }

    private void login(String password, String userName) {
        pb=new ProgressDialog(mContext);
        pb.setMessage("login...");
        pb.show();
        NetDao.UserLogin(mContext, userName, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pb.dismiss();
                if (result!=null){
                    if (result.getRetCode()==0){
                        CommonUtils.showShortToast(R.string.login);
                        MFGT.gotoMainActivity(mContext);
                    }else {
                        CommonUtils.showShortToast("login fail");
                    }
                }
            }

            @Override
            public void onError(String error) {
                pb.dismiss();
                CommonUtils.showShortToast(error);
            }
        });
    }

}
