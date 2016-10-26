package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * Created by mac-yk on 2016/10/26.
 */

public class CollectAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<NewGoodsBean> mList;

    public CollectAdapter(Context mContext, List<NewGoodsBean> mList) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
        this.mList.addAll(mList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new CollectViewHolder(View.inflate(mContext, R.layout.item_collect, parent));

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollectViewHolder cvh= (CollectViewHolder) holder;
        NewGoodsBean goods=mList.get(position);
        ImageLoader.downloadImg(mContext,cvh.ivCollect,goods.getGoodsThumb());
        cvh.tvCollect.setText(goods.getGoodsName());
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


    static class CollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_collect)
        ImageView ivCollect;
        @BindView(R.id.tv_collect)
        TextView tvCollect;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
    public void initData(ArrayList<NewGoodsBean> list){
        if(mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }
}
