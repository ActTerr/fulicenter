package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.IntentSender;
import android.support.v7.widget.RecyclerView;
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
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.Result2;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;

/**
 * Created by mac-yk on 2016/10/26.
 */

public class CollectAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<CollectBean> mList;

    boolean isMore;

    public CollectAdapter(Context mContext, List<CollectBean> mList) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
        this.mList.addAll(mList);

    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new CollectViewHolder(View.inflate(mContext, R.layout.item_collect, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder vh = (FooterViewHolder) holder;
            vh.mTvFooter.setText(getFootString());
        } else {
            CollectViewHolder cvh = (CollectViewHolder) holder;
            CollectBean goods = mList.get(position);
            ImageLoader.downloadImg(mContext, cvh.ivCollect, goods.getGoodsThumb());
            cvh.tvCollect.setText(goods.getGoodsName());
            cvh.ivDelete.setTag(goods);

        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    private int getFootString() {
        return isMore ? R.string.load_more : R.string.no_more;
    }


    class CollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_collect)
        ImageView ivCollect;
        @BindView(R.id.tv_collect)
        TextView tvCollect;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.item_collect)
        public void onClick() {
            CollectBean goods= (CollectBean) ivDelete.getTag();
            MFGT.gotoGoodsDetailsActivity(mContext,goods);
        }

        @OnClick(R.id.iv_delete)
        public void delete() {
            final CollectBean collects = (CollectBean) ivDelete.getTag();
            final int goodsId = collects.getGoodsId();
            NetDao.deleteCollects(mContext, goodsId, FuLiCenterApplication.getUser().getMuserName(), new OkHttpUtils.OnCompleteListener<Result2>() {
                @Override
                public void onSuccess(Result2 result) {
                    mList.remove(collects);
                    CommonUtils.showShortToast(result.getMsg());
                    
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
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

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


}
