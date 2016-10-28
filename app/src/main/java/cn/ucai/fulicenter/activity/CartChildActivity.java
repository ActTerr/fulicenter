package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;

public class CartChildActivity extends BaseActivity {

    @BindView(R.id.et_cart_name)
    EditText etCartName;
    @BindView(R.id.et_cart_phone)
    EditText etCartPhone;
    @BindView(R.id.et_street_child)
    EditText etStreetChild;
    @BindView(R.id.et_cart_city)
    Spinner etCartCity;
    @BindView(R.id.pay_amount)
    TextView payAmount;

    String cartIds;
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
        cartIds=getIntent().getStringExtra("cartIds");


    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @OnClick(R.id.btn_cart_child)
    public void onClick() {

    }


}
