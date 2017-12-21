package com.yswl.priv.h5vedioapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.yswl.priv.h5vedioapp.bean.Website;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private static final String TAG = GuideActivity.class.getSimpleName();
    RecyclerView mRecyclerView;
    MyAdapter myAdapter;

    static int[] icons = {R.mipmap.icon_youkulogo, R.mipmap.icon_aqylogo, R.mipmap.icon_qqlogo
    };
    static String[] urls = {"http://www.youku.com", "http://www.iqiyi.com/","https://v.qq.com/"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setTitle("视频解析");
        mRecyclerView = (RecyclerView) findViewById(R.id.guide_rec_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        myAdapter = new MyAdapter(getDummyDatas());
        mRecyclerView.setAdapter(myAdapter);
    }

    List<Website> getDummyDatas() {
        List<Website> datas = new ArrayList<>();
        for (int i = 0; i < icons.length; i++)
            datas.add(new Website(icons[i], urls[i]));
        return datas;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
