package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.Result2;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;

/**
 * Created by mac-yk on 2016/10/27.
 */

public class CartAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<CartBean> mList;



    public CartAdapter(Context context, List<CartBean> list) {
        mContext = context;
        mList = list;
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new GoodsAdapter.FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new CartsViewHolder(View.inflate(mContext, R.layout.item_cart, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            final CartsViewHolder cvh = (CartsViewHolder) holder;
            L.e("main", mList.toString());
            final CartBean carts = mList.get(position);
            GoodsDetailsBean goods = carts.getGoods();
            L.i("main", goods.toString());
            if (goods!=null){
                ImageLoader.downloadImg(mContext, cvh.ivCartPic, goods.getGoodsThumb());
                cvh.tvCartName.setText(goods.getGoodsName());
                cvh.tvCartPrice.setText(goods.getCurrencyPrice());

            }

            cvh.ivCartAdd.setTag(position);
            cvh.tvCartAmount.setText("(" + carts.getCount() + ")");
            cvh.ivCartCheck.setChecked(carts.isChecked());
            cvh.ivCartCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    carts.setChecked(isChecked);
                    mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                }
            });

    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size()  : 0;
    }



    public void initData(ArrayList<CartBean> list) {
        mList = list;
        notifyDataSetChanged();
    }


    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView mTvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    class CartsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cart_check)
        CheckBox ivCartCheck;
        @BindView(R.id.iv_cart_pic)
        ImageView ivCartPic;
        @BindView(R.id.tv_cart_name)
        TextView tvCartName;
        @BindView(R.id.tv_cart_amount)
        TextView tvCartAmount;
        @BindView(R.id.tv_cart_price)
        TextView tvCartPrice;
        @BindView(R.id.iv_cart_add)
        ImageView ivCartAdd;
        @BindView(R.id.iv_cart_reduce)
        ImageView ivCartReduce;


        CartsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.iv_cart_add, R.id.iv_cart_reduce})
        public void onClick(View view) {
            final int position= (int) ivCartAdd.getTag();
            final CartBean cart=mList.get(position);
            switch (view.getId()) {
                case R.id.iv_cart_add:
                    NetDao.updateCartCount(mContext, cart.getId(), cart.getCount()+1, cart.isChecked(), new OkHttpUtils.OnCompleteListener<Result2>() {
                        @Override
                        public void onSuccess(Result2 result) {
                            if (result != null) {
                                if (result.isSuccess() == true) {
                                    cart.setCount(cart.getCount()+1);
                                    mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                                    tvCartAmount.setText("("+cart.getCount()+")");
                                    Log.e("main", "count++");
                                }
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                    notifyDataSetChanged();
                    break;
                case R.id.iv_cart_reduce:
                    if(cart.getCount()>1){
                        NetDao.updateCartCount(mContext, cart.getId(), cart.getCount()-1, cart.isChecked(), new OkHttpUtils.OnCompleteListener<Result2>() {
                            @Override
                            public void onSuccess(Result2 result) {
                                if (result != null) {
                                    if (result.isSuccess() == true) {

                                        Log.e("main", "count--");
                                        cart.setCount(cart.getCount()-1);
                                        mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                                        tvCartAmount.setText("("+cart.getCount()+")");
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }else {
                        NetDao.deleteCart(mContext, cart.getId(), new OkHttpUtils.OnCompleteListener<Result2>() {
                            @Override
                            public void onSuccess(Result2 result) {
                                if(result!=null && result.isSuccess()){
                                    mList.remove(position);
                                    mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }

                    break;

            }
        }


    }
}
