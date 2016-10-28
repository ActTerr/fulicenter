package cn.ucai.fulicenter.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.OkHttpUtils;

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
    int rankPrice;
    Context mContext;
    ArrayList<CartBean> mList;
    String []ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_cart_child);
        ButterKnife.bind(this);
        mContext=this;
        mList=new ArrayList<>();
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
        downloadCart();

    }

    private void downloadCart() {
        UserBean user= FuLiCenterApplication.getUser();
        if(user!=null){
            NetDao.downloadCarts(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    ArrayList<CartBean> list= ConvertUtils.array2List(result);
                    if (list==null){
                        finish();
                    }else{
                        mList.addAll(list);
                        sumPrice();
                        payAmount.setText("合计：¥"+rankPrice);
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }


    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @OnClick(R.id.btn_cart_child)
    public void onClick() {
        String name=etCartName.getText().toString();
        if(TextUtils.isEmpty(name)){
            etCartName.setError("name not null");
            etCartName.requestFocus();
            return;
        }
        String phone=etCartPhone.getText().toString();
        if (TextUtils.isEmpty(phone)){
            etCartPhone.setError("phone not null");
            etCartPhone.requestFocus();
            return;
        }
        if(!phone.matches("[\\d]{11}")){
            etCartPhone.setError("is not phone number");
            etCartPhone.requestFocus();
            return;
        }
        String area=etCartCity.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)){
            Toast.makeText(mContext, "city not null", Toast.LENGTH_SHORT).show();
            return;
        }
        String street=etStreetChild.getText().toString();
        if (TextUtils.isEmpty(street)){
            etStreetChild.setError("street not null");
            etStreetChild.requestFocus();
            return;
        }

    }

    private void sumPrice(){
        if(mList!=null&&mList.size()>0){
            for(CartBean c:mList){
                for(String id:ids){
                    if(id.equals(c.getId())){
                    rankPrice+=getPrice(c.getGoods().getRankPrice())*c.getCount();
                    }
                }
            }
        }

    }
    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    private void onPay(){
        NetDao.Pay(mContext, 1, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
