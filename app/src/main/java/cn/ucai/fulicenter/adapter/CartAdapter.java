package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
    boolean isMore;
    GoodsDetailsBean goods;
    CartBean carts;
    int count = 1;
    int amount;
    int save;

    boolean isChceked;
    public CartAdapter(Context context, List<CartBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public int getSave() {
        return save;
    }

    public void setSave(int save) {
        this.save = save;
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
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            GoodsAdapter.FooterViewHolder vh = (GoodsAdapter.FooterViewHolder) holder;
            vh.mTvFooter.setText(getFootString());
        } else {
            CartsViewHolder cvh = (CartsViewHolder) holder;
            carts = mList.get(position);
            goods = carts.getGoods();
            L.i("main", goods.toString());
            ImageLoader.downloadImg(mContext, cvh.ivCartPic, goods.getGoodsThumb());
            cvh.tvCartName.setText(goods.getGoodsName());
            cvh.tvCartPrice.setText(goods.getCurrencyPrice());
            amount += getPrice(goods.getCurrencyPrice());
            save += getPrice(goods.getPromotePrice()) - getPrice(goods.getCurrencyPrice());
            if (isChceked) {
                cvh.ivCartCheck.setImageResource(R.mipmap.checkbox_pressed);
            } else {
                cvh.ivCartCheck.setImageResource(R.mipmap.checkbox_normal);
            }
            cvh.tvCartAmount.setText("(" + count + ")");

        }
    }

    private void findgoodsdetail() {
        int goodsId = carts.getGoodsId();
        NetDao.findGoodsDetail(mContext, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                goods = result;
                Log.i("main", "成功下载商品详情");
            }

            @Override
            public void onError(String error) {
                L.i("main", error);
            }
        });

    }

    private int getFootString() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CartBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
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
        ImageView ivCartCheck;
        @BindView(R.id.iv_cart_pic)
        ImageView ivCartPic;
        @BindView(R.id.tv_cart_name)
        TextView tvCartName;
        @BindView(R.id.tv_cart_amount)
        TextView tvCartAmount;
        @BindView(R.id.tv_cart_price)
        TextView tvCartPrice;

        CartsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.iv_cart_add, R.id.iv_cart_reduce})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_cart_add:
                    count++;
                    notifyDataSetChanged();
                    break;
                case R.id.iv_cart_reduce:
                    count--;
                    notifyDataSetChanged();
                    break;
            }
        }
        @OnClick(R.id.iv_cart_check)
        public void onClick() {
            isChceked=!isChceked;
        }

    }
}
