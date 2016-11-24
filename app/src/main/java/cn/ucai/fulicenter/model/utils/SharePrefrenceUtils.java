package cn.ucai.fulicenter.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mac-yk on 2016/10/25.
 */

public class SharePrefrenceUtils {
    private static SharePrefrenceUtils instance;
    private SharedPreferences msp;
    private SharedPreferences.Editor editor;

    private static final String SHARE_NAME = "saveUserInfo";
    public static final String SHARE_KEY_USER_NAME = "share_key_user_name";

    public SharePrefrenceUtils(Context context) {
       msp=context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        editor=msp.edit();
    }

    public static SharePrefrenceUtils getInstance(Context context){
        if (instance==null){
         instance=new SharePrefrenceUtils(context);
        }
        return instance;
    }
    public void saveUser(String userName ){
        editor.putString(SHARE_KEY_USER_NAME,userName);
        editor.commit();
    }
    public String getUser(){
        return msp.getString(SHARE_KEY_USER_NAME,null);

    }
    public void removeUser(){
        editor.remove(SHARE_KEY_USER_NAME);
        editor.commit();
    }
}
