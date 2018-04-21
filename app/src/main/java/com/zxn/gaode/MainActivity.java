package com.zxn.gaode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    protected TextView tvSha;
    protected Button btnStart;
    protected TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
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
            if (shaResult.equals(releaseSha)){
                tvResult.setText("sha is same!");
            }else {
                tvResult.setText("sha is not same!!!");
            }
        }
    }

    private void initView() {
        tvSha = (TextView) findViewById(R.id.tv_sha);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(MainActivity.this);
        tvResult = (TextView) findViewById(R.id.tv_result);
    }

}
