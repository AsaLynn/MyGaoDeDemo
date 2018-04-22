package com.zxn.gaode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.zxn.gaode.R;
import com.zxn.gaode.mapdemo.MainActivity;
import com.zxn.gaode.utils.Utils;

public class EnterActivity extends AppCompatActivity implements View.OnClickListener {


    protected TextView tvSha;
    protected Button btnStart;
    protected TextView tvResult;
    protected Button btnMap;
    protected Button btnMapDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_enter);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_start) {
            String shaResult = Utils.getCertificateSHA1Fingerprint(this);
            String releaseSha = "99:6A:4D:11:EF:F9:18:E4:83:08:C0:C9:3E:BE:AE:FC:61:74:A4:E1";
            LogUtils.i("shaResult".concat(shaResult));
            LogUtils.i("releaseSha".concat(releaseSha));
            tvSha.setText(shaResult);
            if (shaResult.equals(releaseSha)) {
                tvResult.setText("sha is same!");
            } else {
                tvResult.setText("sha is not same!!!");
            }
        } else if (view.getId() == R.id.btn_map) {
            startActivity(new Intent(this, MyMapActivity.class));
        } else if (view.getId() == R.id.btn_map_demo) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void initView() {
        tvSha = (TextView) findViewById(R.id.tv_sha);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(EnterActivity.this);
        tvResult = (TextView) findViewById(R.id.tv_result);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnMap.setOnClickListener(EnterActivity.this);
        btnMapDemo = (Button) findViewById(R.id.btn_map_demo);
        btnMapDemo.setOnClickListener(EnterActivity.this);
    }

}
