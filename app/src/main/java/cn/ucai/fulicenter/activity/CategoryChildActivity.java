package cn.ucai.fulicenter.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.GoodsAdapter;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.I;

/**
 * Created by mac-yk on 2016/10/20.
 */

public class CategoryChildActivity extends BaseActivity {
    Context mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activtiy_category_child);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        mAdapter = new GoodsAdapter(mContext, mList);
        int id=getIntent().getIntExtra(I.Category.KEY_CAT_ID,0);
        super.onCreate(savedInstanceState);
    }
}
