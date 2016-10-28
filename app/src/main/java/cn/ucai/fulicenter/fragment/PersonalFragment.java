package cn.ucai.fulicenter.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.CollectActivity;
import cn.ucai.fulicenter.activity.PersonalChildActivity;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.ResultUtils;

/**
 * Created by mac-yk on 2016/10/21.
 */

public class PersonalFragment extends BaseFragment {
    Activity mContext;
    @BindView(R.id.iv_personal_avatar)
    ImageView ivPersonalAvatar;
    @BindView(R.id.tv_personal_name)
    TextView tvPersonalName;

    UserBean user;
    String userNick;
    @BindView(R.id.tv_collect_count)
    TextView tvCollectCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        mContext = (Activity) getContext();

        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    protected void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
        UserBean user = FuLiCenterApplication.getUser();
        if (user == null) {

            MFGT.gotoLoginActivity(mContext);
        } else {
            ImageLoader.setAvatar(mContext, ImageLoader.getAvatarUrl(user), ivPersonalAvatar);
            tvPersonalName.setText(user.getMuserNick());

        }
    }

    private void syncCollectCount() {
        NetDao.getCollectCount(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null) {
                    tvCollectCount.setText(result.getMsg());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @OnClick({R.id.tv_personal_set, R.id.iv_personal_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_personal_set:
                MFGT.startActivity(mContext, new Intent(mContext, PersonalChildActivity.class));
                break;
            case R.id.iv_personal_message:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(mContext, ImageLoader.getAvatarUrl(user), ivPersonalAvatar);
            tvPersonalName.setText(user.getMuserNick());
            syncCollectCount();
            syncUserInfo();
        }
    }

    @OnClick(R.id.ll_collect)
    public void onClick() {
        MFGT.startActivity(mContext,new Intent(mContext, CollectActivity.class));
    }
    private void syncUserInfo(){
        NetDao.findUser(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, UserBean.class);
                if (result!=null){
                    UserBean u= (UserBean) result.getRetData();
                    if(!u.equals(user)){
                        UserDao dao=new UserDao(mContext);
                        boolean b=dao.saveUser(u);
                        if (b){
                            FuLiCenterApplication.setUser(u);
                            user=u;
                            ImageLoader.setAvatar(mContext, ImageLoader.getAvatarUrl(user), ivPersonalAvatar);
                            tvPersonalName.setText(user.getMuserNick());
                        }
                    }
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
