package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;

public class CartChildActivity extends BaseActivity {

    @BindView(R.id.et_cart_name)
    EditText etCartName;
    @BindView(R.id.et_cart_phone)
    EditText etCartPhone;
    @BindView(R.id.et_cart_city)
    EditText etCartCity;
    @BindView(R.id.et_street_child)
    EditText etStreetChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_cart_child);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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

    @OnClick(R.id.btn_cart_child)
    public void onClick() {

    }
}
