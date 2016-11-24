package cn.ucai.fulicenter.controller.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.activity.MainActivity;
import cn.ucai.fulicenter.controller.adapter.CategoryAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.dao.NetDao;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by mac-yk on 2016/10/19.
 */

public class CategoryFragment extends BaseFragment {
    @BindView(R.id.elv)
    ExpandableListView elv;
    CategoryAdapter mAdapter;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    MainActivity mContext;
    int index;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, null);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getContext();
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        mAdapter = new CategoryAdapter(mContext, mGroupList, mChildList);
        L.e("success init");
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initData() {
        downloadGroup();

    }

    private void downloadChild(int id, final int in) {

            NetDao.downloadChild(mContext, id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
                @Override
                public void onSuccess(CategoryChildBean[] result) {
                    Log.e("fragment"," "+in);
                    if (result != null && result.length > 0) {
                        ArrayList<CategoryChildBean> list = ConvertUtils.array2List(result);
                        L.e("list",list.toString());
                        L.e("listsize",list.size()+"");
                        mChildList.set(in,list);

                    }
                    mAdapter.initDataChild(mChildList);
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showShortToast(error);
                    L.e("error:" + error);
                }
            });
    }


    private void downloadGroup() {
        NetDao.downloadGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                L.e("downloadGroup onSuccess");
                if (result != null && result.length > 0) {
                    mGroupList = ConvertUtils.array2List(result);
                    mAdapter.initDataGroup(mGroupList);
                    for (int i=0;i<mGroupList.size();i++) {
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        CategoryGroupBean groupbean=mGroupList.get(i);
                        int id=groupbean.getId();
                        downloadChild(id,index);
                        index++;
                    }
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
                L.e("error:" + error);
            }
        });


    }

    @Override
    protected void initView() {
        elv.setGroupIndicator(null);
        elv.setAdapter(mAdapter);
    }

}
