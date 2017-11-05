package com.andryyu.thread.pool;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andryyu.thread.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolActivity extends AppCompatActivity {

    private Button mBtn1, mBtn2, mBtn3, mBtn4, mBtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);

        initView();
        initClick();
    }

    private void initView() {
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn4 = (Button) findViewById(R.id.btn4);
        mBtn5 = (Button) findViewById(R.id.btn5);
    }

    private void initClick() {
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1Click();
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2Click();
            }
        });
        mBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3Click();
            }
        });
        mBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn4Click();
            }
        });
        mBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn5Click();
            }
        });
    }

    private void btn1Click() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 6, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(2));
        for (int i = 0; i < 4; i++) {
            final int temp = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    Log.d("run:", "" + temp);
                }
            };
            executor.execute(runnable);
        }
    }

    private void btn2Click() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    Log.d("run:", "" + temp);
                }
            };
            executorService.execute(runnable);
        }
    }

    private void btn3Click() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    Log.d("run:", "" + temp);
                }
            };
            executorService.execute(runnable);
        }
    }

    private void btn4Click() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    Log.d("run:", "" + temp);
                }
            };
            executorService.execute(runnable);
        }
    }

    private void btn5Click(){
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        //延迟1s执行任务
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                Log.d("run:", "schedule-Runnable");
            }
        },1, TimeUnit.SECONDS);

        //延迟2s执行任务
        ScheduledFuture<String> schedule = executorService.schedule(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "schedule-Callable";
            }
        }, 2, TimeUnit.SECONDS);
        try {
            String s = schedule.get();
            Log.d("run:", s);
        } catch (Exception e) {
            Log.d("run:", "Exception");
        }

        //表示延迟3秒后每4秒执行一次。
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.d("run:" ,"scheduleAtFixedRate");
            }
        },3, 4, TimeUnit.SECONDS);

        //表示延迟3秒后每4秒执行一次
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Log.d("run:" ,"scheduleWithFixedDelay");
            }
        }, 3, 5, TimeUnit.SECONDS);
    }
}
