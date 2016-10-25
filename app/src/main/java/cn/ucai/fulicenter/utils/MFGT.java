package cn.ucai.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.activity.CategoryChildActivity;
import cn.ucai.fulicenter.activity.GoodsDetailActivity;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.activity.UserLoginActivity;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;


public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        startActivity(context,intent);
    }

    public static void gotoGoodsDetailsActivity(Context context, NewGoodsBean goods){
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS,goods);
        startActivity(context,intent);
    }

    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean bean){
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID,bean);
        startActivity(context,intent);
    }
    public static void gotoCategoryChildActivity(Context context,int childId){
        Intent intent=new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.Category.KEY_CAT_ID,childId);
        startActivity(context,intent);
    }
    public static void gotoCategoryChildActivity(Context context, int childId, ArrayList<CategoryChildBean> list,String goupname){
        Intent intent=new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.Category.KEY_CAT_ID,childId);
        intent.putExtra(I.CategoryGroup.NAME,goupname);
        intent.putExtra(I.CategoryChild.ID,list);
        startActivity(context,intent);
    }
    public static void gotoLoginActivity(Context context){
        Intent intent=new Intent();
        intent.setClass(context, UserLoginActivity.class);
        startActivity(context,intent);
    }

}
