package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.utils.SharePrefrenceUtils;

public class PersonalChildActivity extends BaseActivity {

    @BindView(R.id.iv_per_child_user)
    ImageView ivPerChildUser;
    @BindView(R.id.iv_per_avatar)
    ImageView ivPerAvatar;

    Activity mContext;
    UserBean user;
    @BindView(R.id.tv_per_child_user)
    TextView tvPerChildUser;
    @BindView(R.id.tv_per_child_nick)
    TextView tvPerChildNick;
    String userName;
    String userNick;
    OnSetAvatarListener mOnSetAvatarListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_personal_child);
        ButterKnife.bind(this);
        mContext = this;
        userName= SharePrefrenceUtils.getInstance(this).getUser();

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

    @OnClick({R.id.iv_per_child_back, R.id.iv_per_child_nick, R.id.iv_per_child_qrc, R.id.iv_per_avatar, R.id.iv_per_child_user, R.id.btn_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_per_child_back:
                MFGT.finish(mContext);
                break;
            case R.id.iv_per_child_qrc:
                MFGT.startActivity(mContext, new Intent(mContext, QRcodeActivity.class));
                break;
            case R.id.iv_per_avatar:
                mOnSetAvatarListener=new OnSetAvatarListener(mContext,R.layout.popu_show_avatar,userName,user.getMavatarPath());

                break;
            case R.id.iv_per_child_user:
                CommonUtils.showShortToast("不能修改用户名");
                break;
            case R.id.btn_exit:
                FuLiCenterApplication.setUser(null);
                FuLiCenterApplication.setUserName(null);
                MFGT.gotoLoginActivity(mContext);
                break;
            case R.id.iv_per_child_nick:
                final Dialog dialog=new Dialog(this);
                dialog.setTitle("修改昵称");
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                dialog.findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       EditText t= (EditText) dialog.findViewById(R.id.et_dialog);
                        Log.i("main",t.getText().toString());
                        dialog.dismiss();
                        NetDao.updateNick(mContext, userName, t.getText().toString(), new OkHttpUtils.OnCompleteListener<Result>() {
                            @Override
                            public void onSuccess(Result result) {
                                if(result==null){
                                    CommonUtils.showShortToast("error");

                                }else {
                                    if (result.isRetMsg()&&result.getRetCode()==0){
                                        CommonUtils.showShortToast("update nick success");

                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }
                });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }

    private void showInfo() {
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(mContext,ImageLoader.getAvatarUrl(user),ivPerAvatar);
            tvPerChildNick.setText(user.getMuserNick());
            tvPerChildUser.setText(user.getMuserName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void updateAvatar(){
        File file=new File(OnSetAvatarListener.getAvatarPath(mContext,OnSetAvatarListener.getAvatarPath(mContext,".jpg")));
        NetDao.updateAvatar(mContext, user.getMuserName(), user.getMavatarPath(),file, new OkHttpUtils.OnCompleteListener<Result>() {
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
    }
}
