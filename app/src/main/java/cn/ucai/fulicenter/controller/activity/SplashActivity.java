package cn.ucai.fulicenter.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.SharePrefrenceUtils;

public class SplashActivity extends AppCompatActivity {
    private long time =2000;
    SplashActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                long cost=System.currentTimeMillis()-start;
           if(time-cost>0){
               try {
                   UserBean user = FuLiCenterApplication.getUser();
                   L.e("main","fulicenter,user="+user);
                   String username = SharePrefrenceUtils.getInstance(mContext).getUser();
                   L.e("main","fulicenter,username="+username);
                   if(user==null && username!=null) {
                       UserDao dao = new UserDao(mContext);
                       user = dao.getUser(username);
                       L.e("main","database,user="+user);
                       if(user!=null){
                           FuLiCenterApplication.setUser(user);
                       }
                   }
                   Thread.sleep(time-cost);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

            }
                MFGT.gotoMainActivity(SplashActivity.this);
                MFGT.finish(SplashActivity.this);
            }
        }).start();
    }

}
