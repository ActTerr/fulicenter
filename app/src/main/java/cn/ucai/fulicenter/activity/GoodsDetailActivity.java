package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.I;

public class GoodsDetailActivity extends AppCompatActivity {

    @BindView(R.id.goodsName)
    TextView goodsName;
    @BindView(R.id.originalPrice)
    TextView originalPrice;
    @BindView(R.id.GoodsNameCh)
    TextView GoodsNameCh;
    @BindView(R.id.currentPrice)
    TextView currentPrice;
    NewGoodsBean goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        goods= (NewGoodsBean) getIntent().getSerializableExtra(I.GoodsDetails.KEY_GOODS);
        int goodsId=goods.getId();
        CommonUtils.showShortToast(goodsId + "");
        initView();
    }

    private void initView() {
        goodsName.setText(goods.getGoodsEnglishName());
        GoodsNameCh.setText(goods.getGoodsName());
        currentPrice.setText(goods.getCurrencyPrice());
        originalPrice.setText(goods.getPromotePrice());
    }
}
