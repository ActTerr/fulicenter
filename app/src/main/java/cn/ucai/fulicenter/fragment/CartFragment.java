package cn.ucai.fulicenter.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.CartChildActivity;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by mac-yk on 2016/10/21.
 */

public class CartFragment extends BaseFragment {
    @BindView(R.id.et_cart_total)
    EditText etCartTotal;
    @BindView(R.id.et_cart_save)
    EditText etCartSave;
    @BindView(R.id.rv_cart)
    RecyclerView rvCart;
    Activity mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
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

    @OnClick(R.id.btn_cart)
    public void onClick() {
        MFGT.startActivity(mContext,new Intent(mContext, CartChildActivity.class));
    }
}
