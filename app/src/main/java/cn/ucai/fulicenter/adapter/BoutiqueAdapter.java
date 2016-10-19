package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by mac-yk on 2016/10/19.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    List<BoutiqueBean> mList;
    Context context;


    public BoutiqueAdapter(List<BoutiqueBean> mlist, Context context) {
        this.mList = new ArrayList<>();
        this.mList.addAll(mlist);
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new BoutiqueViewHolder(LayoutInflater.from(context).inflate(R.layout.boutique,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            BoutiqueBean boutique=mList.get(position);
            BoutiqueViewHolder bvh= (BoutiqueViewHolder) holder;
            ImageLoader.downloadImg(context,bvh.bouIv,boutique.getImageurl());
            bvh.bouTitle.setText(boutique.getTitle());
            bvh.bouName.setText(boutique.getName());
            bvh.bouDescription.setText(boutique.getDescription());
            bvh.bouLl.setTag(boutique);

    }


    @Override
    public int getItemCount() {
        return mList.size();
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
        @BindView(R.id.bou_ll)
        LinearLayout bouLl;
        public BoutiqueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        @OnClick(R.id.layout_boutique)
        public void onGoodsItemClick(){
            BoutiqueBean bean = (BoutiqueBean) bouLl.getTag();
            MFGT.gotoBoutiqueLevl2Activity(context,bean);
        }

    }
}
