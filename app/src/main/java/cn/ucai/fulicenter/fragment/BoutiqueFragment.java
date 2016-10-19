package cn.ucai.fulicenter.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.I;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {
    @BindView(R.id.tv_bou_refresh)
    TextView tvBouRefresh;
    @BindView(R.id.bou_lv)
    RecyclerView bourv;
    @BindView(R.id.bou_srl)
    SwipeRefreshLayout bouSrl;

    BoutiqueAdapter mAdapter;
    MainActivity mContext;
    ArrayList<BoutiqueBean> mlist;
    int pageId;
    GridLayoutManager gl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, view);
        mContext= (MainActivity) getContext();
        mlist=new ArrayList<>();
        mAdapter=new BoutiqueAdapter(mlist,mContext);
        initView();
        initData();
        setListener();
        return view;
    }

    private void setListener() {
    }

    private void initData() {
        downLoadBoutique();
    }

    private void downLoadBoutique() {

    }

    private void initView() {
        gl=new GridLayoutManager(mContext, I.COLUM_NUM);
        bourv.setLayoutManager(gl);
        bourv.setHasFixedSize(true);
        bourv.setAdapter(mAdapter);

    }

}
