package cn.ucai.fulicenter.dao;

import android.content.Context;

import java.util.ArrayList;

import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.ResultUtils;

/**
 * Created by clawpo on 2016/10/17.
 */

public class NetDao {
    public static void downloadNewGoods(Context context, int catId,int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    public static void downloadGoodsDetail(Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener){
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,String.valueOf(goodsId))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }
    public static void downloadBoutique(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener){
        OkHttpUtils utils=new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
    public static void downloadGroup(Context context,OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener){
        OkHttpUtils utils=new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
        L.e("success download group");
    }
    public static void downloadChild(Context context,int parentID,OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener){
        OkHttpUtils utils=new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam("parent_id",String.valueOf(parentID))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
        L.e("success download child");
    }
    public static void downloadCategoryGoods(Context context, int catId,int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
    public static void findUser(Context context,String userName,OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils utils=new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME,userName)
                .targetClass(Result.class)
                .execute(listener);
    }
    public static void UserLogin(Context context,String userName,String passwd,OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils utils=new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,userName)
                .addParam(I.User.PASSWORD,passwd)
                .targetClass(Result.class)
                .execute(listener);

    }
    public static void UserRegister(Context context,String userName,String passwd,String userNick,OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils utils=new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,userName)
                .addParam(I.User.PASSWORD,passwd)
                .addParam(I.User.NICK,userNick)
                .targetClass(Result.class)
                .execute(listener);
    }
}