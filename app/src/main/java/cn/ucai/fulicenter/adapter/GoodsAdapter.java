package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.I;

/**
 * Created by mac-yk on 2016/10/17.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    ArrayList<NewGoodsBean> mlist;
    Context context;

    public GoodsAdapter(ArrayList mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
    }
    class FooterViewHolder extends RecyclerView.ViewHolder{
        TextView footText;
        public FooterViewHolder(View itemView) {
            super(itemView);
            footText= (TextView) itemView.findViewById(R.id.tvFooter);
        }
    }
    class GoodsViewHolder extends RecyclerView.ViewHolder{
        ImageView ivGood;
        TextView tvGood1,tvGood2;
        public GoodsViewHolder(View itemView) {
            super(itemView);
            ivGood= (ImageView) itemView.findViewById(R.id.ivGoods);
            tvGood1= (TextView) itemView.findViewById(R.id.tvGood1);
            tvGood2= (TextView) itemView.findViewById(R.id.tvGood2);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder=null;
        if(viewType== I.TYPE_FOOTER){
            holder=new FooterViewHolder(View.inflate(context, R.layout.item_footer,null));
        }else {
            holder=new GoodsViewHolder(View.inflate(context,R.layout.item_goods,null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){

        }else {
            GoodsViewHolder gvh= (GoodsViewHolder) holder;
            NewGoodsBean goods=mlist.get(position);
            gvh.tvGood1.setText(goods.getGoodsName());
            gvh.tvGood2.setText(goods.getPromotePrice());
        }

    }

    @Override
    public int getItemCount() {
        return mlist==null?1:mlist.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(getItemCount()==position+1){
            return I.TYPE_FOOTER;
        }else {
            return I.TYPE_ITEM;
        }

    }
}
