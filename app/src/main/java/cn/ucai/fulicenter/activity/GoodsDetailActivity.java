package cn.ucai.fulicenter.activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.bean.Result2;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.FlowIndicator;
import cn.ucai.fulicenter.views.SlideAutoLoopView;

public class GoodsDetailActivity extends BaseActivity {

    @BindView(R.id.backClickArea)
    LinearLayout mBackClickArea;
    @BindView(R.id.tv_good_name_english)
    TextView mTvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView mTvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView mTvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView mTvGoodPriceCurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView mSalv;
    @BindView(R.id.indicator)
    FlowIndicator mIndicator;
    @BindView(R.id.wv_good_brief)
    WebView mWvGoodBrief;
    NewGoodsBean goods;
    CollectBean collects;
    int goodsId;
    GoodsDetailActivity mContext;
    @BindView(R.id.layout_image)
    RelativeLayout layoutImage;
    @BindView(R.id.layout_banner)
    RelativeLayout layoutBanner;
    @BindView(R.id.activity_goods_detail)
    RelativeLayout activityGoodsDetail;
    String userName;
    boolean isCollect=false;
    @BindView(R.id.iv_good_collect)
    ImageView ivGoodCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //setContentView(R.layout.activity_goods_detail);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        goods = (NewGoodsBean) getIntent().getSerializableExtra(I.GoodsDetails.KEY_GOODS);
        collects= (CollectBean) getIntent().getSerializableExtra(I.GoodsDetails.KEY_GOODS_ID);
        if (goods!=null){
            goodsId=goods.getGoodsId();
        }else {
            goodsId=collects.getGoodsId();
        }
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        userName = sp.getString("name", "fail");
        L.e("details", "goodsid=" + goodsId);

        if (goodsId == 0) {
           finish();
        }
        mContext = this;
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        NetDao.downloadGoodsDetail(mContext, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details=" + result);
                if (result != null) {
                    showGoodDetails(result);
                } else {
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details,error=" + error);
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void showGoodDetails(GoodsDetailsBean details) {
        mTvGoodNameEnglish.setText(details.getGoodsEnglishName());
        mTvGoodName.setText(details.getGoodsName());
        mTvGoodPriceCurrent.setText(details.getCurrencyPrice());
        mTvGoodPriceShop.setText(details.getShopPrice());
        mSalv.startPlayLoop(mIndicator, getAlbumImgUrl(details), getAlbumImgCount(details));
        mWvGoodBrief.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getAlbumImgCount(GoodsDetailsBean details) {
        if (details.getProperties() != null && details.getProperties().length > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if (details.getProperties() != null && details.getProperties().length > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
                urls[i] = albums[i].getImgUrl();
            }
        }
        return urls;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.backClickArea)
    public void onBackClick() {
        MFGT.finish(this);
    }

    @OnClick({R.id.iv_good_share, R.id.iv_good_collect, R.id.iv_good_cart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_good_share:
                Dialog dialog = new Dialog(this);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                //dialog.setContentView(R.layout.);


                break;
            case R.id.iv_good_collect:
                UserBean user = FuLiCenterApplication.getUser();
                if (user != null) {
                    if (isCollect ==false) {
                        Collected();
                        Log.i("main", "进入收藏分支");
                    } else {
                        cancelCollected();
                        Log.i("main", "进入cancel收藏分支");
                    }
                }
                break;
            case R.id.iv_good_cart:
                NetDao.addCart(mContext, String.valueOf(goodsId), userName, "1", I.ISCHECKED, new OkHttpUtils.OnCompleteListener<Result2>() {
                    @Override
                    public void onSuccess(Result2 result) {
                        CommonUtils.showShortToast(result.getMsg());
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showShortToast(error);
                    }
                });
                break;
        }
    }

    private void cancelCollected() {
        NetDao.deleteCollects(mContext, goodsId, userName, new OkHttpUtils.OnCompleteListener<Result2>() {
            @Override
            public void onSuccess(Result2 result) {
                if(result!=null){
                    CommonUtils.showShortToast("取消收藏");
                    isCollect=false;
                }else {
                    isCollect=true;
                }
                updateGoodsStatus();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void Collected() {

            NetDao.addCollect(mContext, String.valueOf(goodsId), userName, new OkHttpUtils.OnCompleteListener<Result2>() {
                @Override
                public void onSuccess(Result2 result) {
                    if (result!=null){
                        L.e("main", result.toString());
                        CommonUtils.showShortToast(result.getMsg());
                        isCollect = true;
                    }else {
                        isCollect=false;
                    }

                    updateGoodsStatus();
                }

                @Override
                public void onError(String error) {
                    L.e("main", error);
                    CommonUtils.showShortToast(error);
                }
            });
            updateGoodsStatus();
        }


    private void updateGoodsStatus() {
        if (isCollect==true) {
        ivGoodCollect.setImageResource(R.mipmap.bg_collect_out);
        }else {
            ivGoodCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}