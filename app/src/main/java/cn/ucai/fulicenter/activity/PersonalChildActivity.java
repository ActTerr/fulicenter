package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;

public class PersonalChildActivity extends BaseActivity {

    @BindView(R.id.iv_per_child_user)
    ImageView ivPerChildUser;
    @BindView(R.id.iv_per_avatar)
    ImageView ivPerAvatar;

    Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_personal_child);
        ButterKnife.bind(this);
        mContext=this;

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @OnClick({R.id.iv_per_child_back, R.id.iv_per_child_qrc,R.id.iv_per_avatar,R.id.iv_per_child_user,R.id.btn_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_per_child_back:
                MFGT.finish(mContext);
                break;
            case R.id.iv_per_child_qrc:
                MFGT.startActivity(mContext, new Intent(mContext, QRcodeActivity.class));
                break;
            case R.id.iv_per_avatar:
                UserBean user = FuLiCenterApplication.getUser();
                NetDao.updateAvatar(mContext, user.getMuserName(), user.getMavatarPath(), new OkHttpUtils.OnCompleteListener<Result>() {
                    @Override
                    public void onSuccess(Result result) {
                        if (result.getRetCode() == 0) {
                            CommonUtils.showShortToast("success");
                        } else {
                            CommonUtils.showShortToast("error" + result.getRetCode());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
                break;
            case R.id.iv_per_child_user:
                CommonUtils.showShortToast("不能修改用户名");
                break;
            case R.id.btn_exit:
                FuLiCenterApplication.setUser(null);
                FuLiCenterApplication.setUserName(null);
                MFGT.gotoLoginActivity(mContext);
        }
    }





}
