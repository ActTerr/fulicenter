package cn.ucai.fulicenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.i("MainActivity onCreate");
    }
    public void onChanged(View v){
        switch (v.getId()){
            case R.id.rbBoutique:
                break;
            case R.id.rbCart:
                break;
            case R.id.rbNewGood:
                break;
            case R.id.rbCategory:
                break;
            case R.id.rbPersonal:
                break;
        }
    }
}
