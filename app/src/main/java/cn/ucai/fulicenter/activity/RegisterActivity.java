package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.et_register_nick)
    EditText etRegisterNick;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.et_register_cpwd)
    EditText etRegisterCpwd;

    String userName;
    String password;
    String cofirmPassword;
    String userNick;
    Activity mContext;
    ProgressDialog pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_register);
        mContext=this;
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        userName=etRegisterName.getText().toString().trim();
        userNick=etRegisterNick.getText().toString().trim();
        password=etRegisterPwd.getText().toString().trim();
        cofirmPassword=etRegisterCpwd.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            etRegisterName.requestFocus();
            return;
        }else if(!userName.matches("[a-zA-Z]\\w{5,15}")){
            CommonUtils.showShortToast(R.string.illegal_user_name);
            etRegisterName.requestFocus();
            return;
        }else if(TextUtils.isEmpty(userNick)){
            CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
            etRegisterNick.requestFocus();
            return;
        }else if(TextUtils.isEmpty(password)){
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            etRegisterPwd.requestFocus();
            return;
        }else if(TextUtils.isEmpty(cofirmPassword)){
            CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
            etRegisterCpwd.requestFocus();
            return;
        }else if(!password.equals(cofirmPassword)){
            CommonUtils.showShortToast(R.string.two_input_password);
            etRegisterCpwd.requestFocus();
            return;
        }
        register();
    }

    private void register() {
        pb=new ProgressDialog(mContext);
        pb.setMessage("register...");
        pb.show();
        NetDao.UserRegister(mContext, userName, password, userNick, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if(result!=null){
                    pb.dismiss();
                    if(result.getRetCode()==0){
                        CommonUtils.showShortToast(R.string.register_success);
                        FuLiCenterApplication.setUserName(userName);
                        MFGT.startActivity(mContext,new Intent(mContext,UserLoginActivity.class));
                        MFGT.finish(mContext);
                    }else {

                        CommonUtils.showShortToast(R.string.register_fail_exists);
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
