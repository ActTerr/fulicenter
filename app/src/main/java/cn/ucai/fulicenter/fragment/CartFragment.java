package cn.ucai.fulicenter.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
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
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.SpaceItemDecoration;

/**
 * Created by mac-yk on 2016/10/21.
 */

public class CartFragment extends BaseFragment {
    @BindView(R.id.et_cart_total)
    TextView etCartTotal;
    @BindView(R.id.et_cart_save)
    TextView etCartSave;
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

    private void downloadCart() {
        if (user != null) {
            NetDao.downloadCarts(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    if (result != null) {
                        mList = ConvertUtils.array2List(result);
                        mAdapter.addData(mList);
                        Log.i("main", "下载购物车信息成功");
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
        llm=new LinearLayoutManager(mContext);
        mRv.setLayoutManager(llm);
        mRv.setAdapter(mAdapter);
        etCartTotal.setText("¥"+mAdapter.getAmount());
        etCartSave.setText("¥"+mAdapter.getSave());
    }

    @Override
    protected void setListener() {
        super.setListener();

    }

    @OnClick(R.id.btn_cart)
    public void onClick() {
        MFGT.startActivity(mContext, new Intent(mContext, CartChildActivity.class));
    }

}
