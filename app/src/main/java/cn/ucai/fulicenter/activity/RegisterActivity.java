package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.dao.NetDao;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
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

    }
}
