package com.example.zqy.myapplicationtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zqy.myapplicationtest.bean.Food;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by zqy on 17-10-23.
 */

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.button2) Button add_btn;
    @BindView(R.id.button3) Button modify_btn;
    @BindView(R.id.button4) Button inquire_btn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Bmob.initialize(this, "e01990f453876ec8667dbc94fb0ff9ef");

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });

        modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyData();
            }
        });

        inquire_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                inquireData();
                Intent intent = new Intent(HomeActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addData() {
        final Food food = new Food();
        food.setFood_name("黄焖鸡米饭");
        food.setFood_price(12.5f);
        food.setFood_describe("好吃不贵");

        food.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null) {
                    Toast.makeText(HomeActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void modifyData() {
        final Food food = new Food();
        food.setFood_name("肉丝炒面");
        food.setFood_price(10f);
        food.setValue("food_describe", "便宜实惠");
        food.update("05e580a893", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(HomeActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void inquireData() {
        BmobQuery<Food> query = new BmobQuery<Food>();
        query.getObject("05e580a893", new QueryListener<Food>() {
            @Override
            public void done(Food food, BmobException e) {
                if (e == null) {
                    String name = food.getFood_name();
                    Float price = food.getFood_price();
                    Toast.makeText(HomeActivity.this, name+price, Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
