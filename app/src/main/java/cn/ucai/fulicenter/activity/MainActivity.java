package cn.ucai.fulicenter.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import java.lang.reflect.Array;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.fragment.FragmentGoods;
import cn.ucai.fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rbBoutique) RadioButton rbBoutique;
    @BindView(R.id.rbCart) RadioButton rbCart;
    @BindView(R.id.rbNewGood) RadioButton rbNewGood;
    @BindView(R.id.rbCategory) RadioButton rbCategory;
    @BindView(R.id.rbPersonal) RadioButton rbPersonal;
    int index;
    RadioButton[] btns;
    FragmentGoods fd;
    Fragment[] fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
        initView();
        initFragment();
    }

    private void initFragment() {
        fragments=new Fragment[5];
        fd=new FragmentGoods();
        FragmentManager manger=getSupportFragmentManager();
        FragmentTransaction transaction = manger.beginTransaction();
        transaction.add(R.id.fragment_container,fd)
                .show(fd)
                .commit();
    }

    private void initView() {
        btns= new RadioButton[]{rbBoutique,rbCart,rbNewGood,rbCategory,rbPersonal};
    }

    private void setRadioButtonStatus() {
        for(int i=0;i<btns.length;i++){
            if(i==index){
                btns[i].setChecked(true);
            }else {
                btns[i].setChecked(false);
            }
        }
    }

    public void onCheckedChange(View v){
        Log.i("main","success");
        switch (v.getId()){
            case R.id.rbBoutique:
                index=0;
                break;
            case R.id.rbCart:
                index=1;
                break;
            case R.id.rbNewGood:
                index=2;
                break;
            case R.id.rbCategory:
                index=3;
                break;
            case R.id.rbPersonal:
                index=4;
                break;
        }
        setRadioButtonStatus();
    }

}
