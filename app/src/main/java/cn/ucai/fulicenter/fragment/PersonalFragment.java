package cn.ucai.fulicenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.LoginActivity;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by mac-yk on 2016/10/21.
 */

public class PersonalFragment extends BaseFragment {
    Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pensonal,container,false);
        mContext=getContext();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
    protected void initView(){

    }

}
