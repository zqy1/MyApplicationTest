package com.example.zqy.myapplicationtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zqy.myapplicationtest.bean.Food;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by zqy on 17-11-5.
 */

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.my_recyclerView) RecyclerView recyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ButterKnife.bind(this);

        initData();
        initView();
    }


    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        mAdapter = new ListAdapter(inquireData());
        mAdapter = new ListAdapter(getData());
    }


    private void initView() {
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";
//        for(int i = 0; i < 20; i++) {
//            data.add(i + temp);
//        }
        inquireData();

        data.add(temp);
        return data;
    }

    private void inquireData() {
        BmobQuery<Food> query = new BmobQuery<Food>();
        query.getObject("05e580a893", new QueryListener<Food>() {
            @Override
            public void done(Food food, BmobException e) {
                if (e == null) {
                    String name = food.getFood_name();
                    Float price = food.getFood_price();
                    String describe = food.getFood_describe();
                } else {
                    Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

}
