package cn.ucai.fulicenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.Result2;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;

public class CartChildActivity extends BaseActivity implements PaymentHandler {

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
    String[] ids=new String []{};
    UserBean user;
    private static String URL = "http://218.244.151.190/demo/charge";
    private static final String TAG = CartChildActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_cart_child);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        //设置需要使用的支付方式

        super.onCreate(savedInstanceState);
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "bfb", "jdpay_wap"});

        // 提交数据的格式，默认格式为json
        // PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";

        PingppLog.DEBUG = true;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        cartIds = getIntent().getStringExtra("cartIds");
        user = FuLiCenterApplication.getUser();
        L.e(TAG,"cartIds="+cartIds);
        if(cartIds==null || cartIds.equals("")
                || user==null){
            finish();
        }
        ids = cartIds.split(",");
        downloadCart();

    }

    private void downloadCart() {
        UserBean user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.downloadCarts(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    ArrayList<CartBean> list = ConvertUtils.array2List(result);
                    if (list == null) {
                        finish();
                    } else {
                        mList.addAll(list);
                        sumPrice();
                        payAmount.setText("合计：¥" + rankPrice);
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
        String name = etCartName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            etCartName.setError("name not null");
            etCartName.requestFocus();
            return;
        }
        String phone = etCartPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            etCartPhone.setError("phone not null");
            etCartPhone.requestFocus();
            return;
        }
        if (!phone.matches("[\\d]{11}")) {
            etCartPhone.setError("is not phone number");
            etCartPhone.requestFocus();
            return;
        }
        String area = etCartCity.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)) {
            Toast.makeText(mContext, "city not null", Toast.LENGTH_SHORT).show();
            return;
        }
        String street = etStreetChild.getText().toString();
        if (TextUtils.isEmpty(street)) {
            etStreetChild.setError("street not null");
            etStreetChild.requestFocus();
            return;
        }
        gotoStatements();

    }

    private void gotoStatements() {
        // 产生个订单号
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());

        // 计算总金额（以分为单位）
        // 构建账单json对象
        JSONObject bill = new JSONObject();

        // 自定义的额外信息 选填
        JSONObject extras = new JSONObject();
        try {
            extras.put("extra1", "extra1");
            extras.put("extra2", "extra2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            bill.put("order_no", orderNo);
            bill.put("amount", rankPrice*100);
            bill.put("extras", extras);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //壹收款: 创建支付通道的对话框
        PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), URL, this);
    }

    private void sumPrice() {
        rankPrice=0;
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                for (String id : ids) {
                    if (id.equals(String.valueOf(c.getId()))) {
                        rankPrice += getPrice(c.getGoods().getRankPrice()) * c.getCount();
                    }
                }
            }
        }
        payAmount.setText("合计¥:"+rankPrice);
    }

    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }


    @Override
    public void handlePaymentResult(Intent intent) {
        new PaymentHandler() {

            // 返回支付结果
            // @param data

            @Override
            public void handlePaymentResult(Intent data) {
                if (data != null) {

                    // result：支付结果信息
                    // code：支付结果码
                    //-2:用户自定义错误
                    //-1：失败
                    // 0：取消
                    // 1：成功
                    // 2:应用内快捷支付支付结果
                    L.e(TAG, "code=" + data.getExtras().getInt("code"));
                    if (data.getExtras().getInt("code") != 2) {
                        PingppLog.d(data.getExtras().getString("result") + "  " + data.getExtras().getInt("code"));
                    } else {
                        String result = data.getStringExtra("result");
                        try {
                            JSONObject resultJson = new JSONObject(result);
                            if (resultJson.has("error")) {
                                result = resultJson.optJSONObject("error").toString();
                            } else if (resultJson.has("success")) {
                                result = resultJson.optJSONObject("success").toString();
                            }
                            PingppLog.d("result::" + result);
                            L.e(TAG, result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    int resultCode = data.getExtras().getInt("code");
                    switch (resultCode){
                        case 1:
                            paySuccess();
                            CommonUtils.showLongToast(R.string.pingpp_title_activity_pay_sucessed);
                            break;
                        case -1:
                            CommonUtils.showLongToast(R.string.pingpp_pay_failed);
                            finish();
                            break;
                    }
                }
            }
        };
    }

    private void paySuccess() {
        for(String id :ids){
            NetDao.deleteCart(mContext, Integer.valueOf(id), new OkHttpUtils.OnCompleteListener<Result2>() {
                @Override
                public void onSuccess(Result2 result) {

                }

                @Override
                public void onError(String error) {

                }
            });
        }
        finish();
    }
}
