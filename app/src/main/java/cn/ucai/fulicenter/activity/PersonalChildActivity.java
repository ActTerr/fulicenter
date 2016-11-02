package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.utils.ResultUtils;
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
    OnSetAvatarListener mOnSetAvatarListener;
    EditText etNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_personal_child);
        ButterKnife.bind(this);
        mContext = this;
        userName = SharePrefrenceUtils.getInstance(this).getUser();

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {

        super.initData();
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            finish();
            return;
        }
        showInfo();
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
                Log.i("main", userName);
                mOnSetAvatarListener = new OnSetAvatarListener(mContext, R.id.activity_personal_child, user.getMuserName(),
                        I.AVATAR_TYPE_USER_PATH);

                break;
            case R.id.iv_per_child_user:
                CommonUtils.showShortToast("不能修改用户名");
                break;
            case R.id.btn_exit:
                SharePrefrenceUtils.getInstance(mContext).removeUser();
                FuLiCenterApplication.setUser(null);
                MFGT.gotoLoginActivity(mContext);
                break;
            case R.id.iv_per_child_nick:
                final Dialog dialog = new Dialog(this);
                dialog.setTitle("修改昵称");
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                dialog.findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etNick = (EditText) dialog.findViewById(R.id.et_dialog);
                        updateNick();
                        dialog.dismiss();
                    }
                });
        }
    }


    private void updateNick() {

        NetDao.updateNick(mContext, userName, etNick.getText().toString(), new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result == null) {
                    CommonUtils.showShortToast("error");

                } else {
                    if (result.isRetMsg() && result.getRetCode() == 0) {
                        user.setMuserNick(etNick.getText().toString());
                        FuLiCenterApplication.setUser(user);
                        tvPerChildNick.setText(user.getMuserNick());
                        CommonUtils.showShortToast("update nick success");

                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }

    private void showInfo() {
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(mContext, ImageLoader.getAvatarUrl(user), ivPerAvatar);
            Log.i("main",user.getMuserNick());
            tvPerChildNick.setText(user.getMuserNick());
            tvPerChildUser.setText(user.getMuserName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!=RESULT_OK){
            return;
        }
        mOnSetAvatarListener.setAvatar(requestCode,data,ivPerAvatar);
        if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            Log.i("main","执行更新头像");
            updateAvatar();
        }
    }

    private void updateAvatar() {
        File file = new File(OnSetAvatarListener.getAvatarPath(mContext,
                user.getMavatarPath()+"/"+user.getMuserName()
                        +I.AVATAR_SUFFIX_JPG));
        final ProgressDialog dialog=new ProgressDialog(mContext);
        dialog.setMessage("update Avatar");
        dialog.show();
        NetDao.updateAvatar(mContext, user.getMuserName(),I.AVATAR_TYPE_USER_PATH, file, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s,UserBean.class);
                if (result!=null){
                   UserBean user= (UserBean) result.getRetData();
                    if (result.isRetMsg()){
                        FuLiCenterApplication.setUser(user);
                        ImageLoader.setAvatar(mContext,ImageLoader.getAvatarUrl(user),ivPerAvatar);
                        CommonUtils.showShortToast("update Avatar success");
                    }

                }else {
                    CommonUtils.showShortToast("update Avatar fail");
                }
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
