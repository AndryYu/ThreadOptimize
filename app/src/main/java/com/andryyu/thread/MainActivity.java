package com.andryyu.thread;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andryyu.thread.async.AsyncTaskActivity;
import com.andryyu.thread.handler.HandlerThreadActivity;
import com.andryyu.thread.pool.ThreadPoolActivity;
import com.andryyu.thread.service.IntentServiceActivity;

public class MainActivity extends AppCompatActivity {

    public static final String[] datas = new String[]{"AsyncTask使用", "HandlerThread使用", "ThreadPool使用", "IntentService使用"};
    private ListView mListView;
    private Toolbar mToolbar;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = this.findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("");
        mTitleTextView = this.findViewById(R.id.toolbar_title);
        mTitleTextView.setText("性能优化之多线程");
        this.setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.this.finish();
            }
        });

        mListView = this.findViewById(R.id.listView);
        mListView.setAdapter(new MainAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doClick(position);
            }
        });
    }

    private void doClick(int position) {
        switch (position) {
            /*Activity agentWeb*/
            case 0:
                startActivity(new Intent(this, AsyncTaskActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, HandlerThreadActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, ThreadPoolActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, IntentServiceActivity.class));
                break;
            default:
                break;
        }
    }

    public class MainAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.length;
        }

        @Override
        public Object getItem(int position) {
            return datas[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder;
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                View mView = MainActivity.this.getLayoutInflater().inflate(R.layout.listview_main, parent, false);
                mViewHolder.mTextView = (TextView) mView.findViewById(R.id.content);
                mView.setTag(mViewHolder);
                convertView = mView;
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            mViewHolder.mTextView.setText(datas[position]);
            return convertView;
        }
    }

    class ViewHolder {
        TextView mTextView;
    }
}
