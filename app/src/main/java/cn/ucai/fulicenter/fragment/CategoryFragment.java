package cn.ucai.fulicenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.adapter.CategoryAdapter;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.SpaceItemDecoration;

/**
 * Created by mac-yk on 2016/10/19.
 */

public class CategoryFragment extends BaseFragment {
    @BindView(R.id.elv)
    ExpandableListView elv;
    CategoryAdapter mAdapter;
    ArrayList<CategoryChildBean> mChildList;
    ArrayList<CategoryGroupBean> mGroupList;
    MainActivity mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, null);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getContext();
        mGroupList=new ArrayList<>();
        mChildList=new ArrayList<>();
        mAdapter=new CategoryAdapter(mContext,mChildList,mGroupList);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
    @Override
    protected void initData() {
        downloadGroup();
    }


    private void downloadGroup() {
        NetDao.downloadGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {

            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
                L.e("error:"+error);
            }
        });


    }

    @Override
    protected void initView() {



    }



}
