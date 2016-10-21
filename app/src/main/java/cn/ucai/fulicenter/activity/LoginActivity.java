package cn.ucai.fulicenter.activity;

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
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        super.initView();
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
                String Username=etLoginName.getText().toString();
                String Passwd=etLoginPassword.getText().toString();
                NetDao.UserLogin(mContext, Username, Passwd, new OkHttpUtils.OnCompleteListener<Result>() {
                    @Override
                    public void onSuccess(Result result) {

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
                break;
            case R.id.btn_register:
                MFGT.startActivity(this,new Intent(this,RegisterActivity.class));
                break;
        }
    }
}
