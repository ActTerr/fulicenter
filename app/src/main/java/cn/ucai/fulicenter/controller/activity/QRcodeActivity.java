package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.utils.MFGT;

/**
 * Created by mac-yk on 2016/10/25.
 */

public class QRcodeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.iv_qr)
    public void onClick() {
        MFGT.finish(this);
    }
}
