package cn.ucai.fulicenter.application;

import android.app.Application;

import cn.ucai.fulicenter.bean.UserBean;

/**
 * Created by mac-yk on 2016/10/17.
 */

public class FuLiCenterApplication extends Application {
    public static  FuLiCenterApplication application;
    private static FuLiCenterApplication instance;
    public  static String userName;
    public static UserBean userbean;
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        instance=this;
    }


    public static FuLiCenterApplication getInstance(){
        if(instance==null){
            instance=new FuLiCenterApplication();
        }
        return instance;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String name) {
        userName = name;

    }

    public static void setUser(UserBean user) {
        userbean=user;
    }
    public static UserBean getUser(){
        return  userbean;
    }
}
