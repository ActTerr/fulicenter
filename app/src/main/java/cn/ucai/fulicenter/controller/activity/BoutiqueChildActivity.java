package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.model.utils.I;
import cn.ucai.fulicenter.model.utils.MFGT;

public class BoutiqueChildActivity extends BaseActivity {

    BoutiqueBean boutiqueBean;
    TextView title;
    NewGoodsFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_boutique_child);
        ButterKnife.bind(this);
        boutiqueBean = (BoutiqueBean) getIntent().getSerializableExtra(I.Boutique.CAT_ID);
        if (boutiqueBean == null) {
            finish();
        }

        super.onCreate(savedInstanceState);
    }

    private void initFragment() {
        mFragment = new NewGoodsFragment(boutiqueBean.getId(), this);
        getSupportFragmentManager().beginTransaction().add(R.id.flfl, mFragment).commit();
    }

    protected void initView() {
        title = (TextView) findViewById(R.id.tv_common_title);
        title.setText(boutiqueBean.getTitle());
        initFragment();
    }


    @OnClick(R.id.iv_back)
    public void onClick() {
        MFGT.finish(this);
    }
}
