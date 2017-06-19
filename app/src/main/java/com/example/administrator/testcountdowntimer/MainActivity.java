package com.example.administrator.testcountdowntimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import countDown.CountDownTask;

public class MainActivity extends AppCompatActivity {
    private ArrayList<GoodsModel> dataList = new ArrayList<>();
    private GridAdapter adapter;
    private String[] goodsName={"怡宝矿泉水","打印机","特仑苏牛奶","手表"};
    private int[] goodsImg={R.mipmap.cestin,R.mipmap.printer,R.mipmap.milk,R.mipmap.watch};
    private CountDownTask mCountDownTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView grid = (GridView) findViewById(R.id.grid);
        adapter = new GridAdapter(this,dataList);
        grid.setAdapter(adapter);
        getAnnounceList();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position<dataList.size()) {
                    GoodsModel goods = (GoodsModel) parent.getItemAtPosition(position);
                    Toast.makeText(MainActivity.this,  goods.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 获取揭晓的商品信息（这里我写死了）
     */
    public void getAnnounceList(){

            for(int i=0;i<goodsName.length;i++){
                GoodsModel goodsModel = new GoodsModel();
                goodsModel.setName(goodsName[i]);
                goodsModel.setTime(i==0?20000L:50000L*i);
                goodsModel.setImg(goodsImg[i]);
                dataList.add(goodsModel);
            }
            startCountDown();

    }

    /**
     * 开始计时任务
     */
    private void startCountDown() {
        mCountDownTask = CountDownTask.create();
        adapter.setCountDownTask(mCountDownTask);
    }

    /**
     * 取消计时任务
     */
    private void cancelCountDown() {
        adapter.setCountDownTask(null);
        if(mCountDownTask!=null)
        mCountDownTask.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelCountDown();
    }
}
