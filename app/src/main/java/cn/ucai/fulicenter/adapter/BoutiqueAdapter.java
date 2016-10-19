package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * Created by mac-yk on 2016/10/19.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    List<BoutiqueBean> mList;
    Context context;
    boolean isMore;


    public BoutiqueAdapter(List<BoutiqueBean> mlist, Context context) {
        this.mList = new ArrayList<>();
        this.mList.addAll(mlist);
        this.context = context;
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
            holder = new GoodsAdapter.FooterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_footer, parent));
        } else {
            holder = new BoutiqueViewHolder(LayoutInflater.from(context).inflate(R.layout.boutique, parent));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof GoodsAdapter.FooterViewHolder){
            GoodsAdapter.FooterViewHolder fv= (GoodsAdapter.FooterViewHolder) holder;
            fv.mTvFooter.setText(getFootString());
        }else {
            BoutiqueBean boutique=mList.get(position);
            BoutiqueViewHolder bvh= (BoutiqueViewHolder) holder;
            ImageLoader.downloadImg(context,bvh.bouIv,boutique.getImageurl());
            bvh.bouTitle.setText(boutique.getTitle());
            bvh.bouName.setText(boutique.getName());
            bvh.bouDescription.setText(boutique.getDescription());
        }
    }

    private int getFootString() {
        return isMore?R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if(mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<BoutiqueBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bou_iv)
        ImageView bouIv;
        @BindView(R.id.bou_title)
        TextView bouTitle;
        @BindView(R.id.bou_name)
        TextView bouName;
        @BindView(R.id.bou_description)
        TextView bouDescription;
        public BoutiqueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
