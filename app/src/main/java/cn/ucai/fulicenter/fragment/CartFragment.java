package cn.ucai.fulicenter.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.CartChildActivity;
import cn.ucai.fulicenter.adapter.CartAdapter;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;

/**
 * Created by mac-yk on 2016/10/21.
 */

public class CartFragment extends BaseFragment {


    Activity mContext;
    UserBean user;
    ArrayList<CartBean> mList;
    CartAdapter mAdapter;
    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;

    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    LinearLayoutManager llm;
    @BindView(R.id.rv)
    RecyclerView mRv;

    GoodsDetailsBean goods;

    cartBroadcastReceiver mReceiver;
    @BindView(R.id.tv_cart_sum_price)
    TextView tvCartSumPrice;
    @BindView(R.id.tv_cart_save_price)
    TextView tvCartSavePrice;
    @BindView(R.id.tv_nothing)
    TextView tvNothing;
    @BindView(R.id.layout_cart)
    RelativeLayout layoutCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        mContext = (Activity) this.getContext();
        user = FuLiCenterApplication.getUser();
        mList = new ArrayList<>();
        mAdapter = new CartAdapter(mContext, mList);
        super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        downloadCart();

    }

    private void setlayout(boolean b) {
        layoutCart.setVisibility(b ? View.VISIBLE : View.GONE);
        tvNothing.setVisibility(b ? View.GONE : View.VISIBLE);
        mRv.setVisibility(b ? View.VISIBLE : View.GONE);
        setPrice();
    }

    private void downloadCart() {
        if (user != null) {

            NetDao.downloadCarts(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    mTvRefresh.setVisibility(View.GONE);
                    mSrl.setRefreshing(false);

                    if (result != null) {
                        mList.clear();
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        for (CartBean c : list) {
                            c.setChecked(false);
                            mList.add(c);
                        }
                        if (mList != null) {
                            mAdapter.initData(mList);
                            Log.i("main", "下载购物车信息成功");
                            setlayout(true);
                        } else {
                            setlayout(false);
                        }

                    }
                }

                @Override
                public void onError(String error) {
                    Log.e("main", error);
                }
            });
        }

    }

    @Override
    protected void initView() {
        super.initView();
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );

        llm = new LinearLayoutManager(mContext);
        mRv.setLayoutManager(llm);
        mRv.setAdapter(mAdapter);
        setlayout(false);
    }

    private void setPrice() {
        int currency=0;
        int rank=0;
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                if (c.isChecked()) {
                    goods = c.getGoods();
                    Log.i("main", goods.getCurrencyPrice());
                    currency += getPrice(goods.getCurrencyPrice()) * c.getCount();
                    rank += getPrice(goods.getRankPrice()) * c.getCount();
                }


            }
            tvCartSumPrice.setText("合计:¥" + rank);
            tvCartSavePrice.setText("节省:¥" + (currency - rank));
        } else {
            tvCartSumPrice.setText("合计:¥0");
            tvCartSavePrice.setText("节省:¥0");
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        setPullDownListener();
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATE_CART);
        mReceiver = new cartBroadcastReceiver();
        mContext.registerReceiver(mReceiver, filter);
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                downloadCart();
            }
        });
    }


    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    @OnClick(R.id.tv_cart_buy)
    public void onClick() {
        MFGT.startActivity(mContext, new Intent(mContext, CartChildActivity.class));
    }

    class cartBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setPrice();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver == null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadCart();
    }
}
