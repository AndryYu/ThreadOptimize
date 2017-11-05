package com.andryyu.thread.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andryyu.thread.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntentServiceActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String ACTION_TYPE_SERVICE = "action.type.service";
    public final static String ACTION_TYPE_THREAD = "action.type.thread";
    @BindView(R.id.tv_service_status)
    TextView tvServiceStatus;
    @BindView(R.id.tv_thread_status)
    TextView tvThreadStatus;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    private LocalBroadcastManager mLocalBroadcastManager;
    private MyBroadcastReceiver mBroadcastReceiver;
    private Toolbar mToolbar;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        mToolbar = this.findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("");
        mTitleTextView = this.findViewById(R.id.toolbar_title);
        mTitleTextView.setText("IntentService的使用");
        this.setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvServiceStatus.setText("服务状态：未运行");
        tvThreadStatus.setText("线程状态：未运行");
        progressBar.setMax(100);
        progressBar.setProgress(0);
        tvProgress.setText(0 + "%");
    }

    private void initListener() {
        tvStart.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                Intent startIntent = new Intent(this, MyIntentService.class);
                startService(startIntent);
                break;
            case R.id.tv_cancel:
                Intent stopIntent = new Intent(this, MyIntentService.class);
                stopService(stopIntent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TYPE_SERVICE);
        intentFilter.addAction(ACTION_TYPE_THREAD);
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ACTION_TYPE_SERVICE:
                    tvServiceStatus.setText("服务状态：" + intent.getStringExtra("status"));
                    break;
                case ACTION_TYPE_THREAD:
                    int progress = intent.getIntExtra("progress", 0);
                    tvThreadStatus.setText("线程状态：" + intent.getStringExtra("status"));
                    progressBar.setProgress(progress);
                    tvProgress.setText(progress + "%");
                    break;
            }
        }
    }
}
