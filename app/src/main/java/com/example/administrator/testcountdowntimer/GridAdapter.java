package com.example.administrator.testcountdowntimer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import countDown.CountDownTask;
import countDown.CountDownTimers;

/**
 * Created by Kentlee on 2016/10/8.
 */
public class GridAdapter extends BaseAdapter {
    private  ArrayList<GoodsModel> list;
    private  LayoutInflater inflate;
    private  Activity activity;
    private CountDownTask mCountDownTask;
    private NewHolder holder;
    private long countDownInterval=70;//数字变动的间隔

    public GridAdapter(Activity activity, ArrayList<GoodsModel> list) {
        this.list = list;
        inflate = LayoutInflater.from(activity);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = new NewHolder();
        if(convertView==null){
            convertView = inflate.inflate(R.layout.item_grid,null);
            holder.iv_pic = (ImageView)convertView.findViewById(R.id.iv_goods_img);
            holder.tv_goods_name = (TextView)convertView.findViewById(R.id.tv_goods_name);
            holder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        }else{
            holder = (NewHolder)convertView.getTag();
        }
        if(!list.isEmpty() && position<list.size()) {
            GoodsModel goodsModel = list.get(position);
            long time = goodsModel.getTime();
            holder.tv_goods_name.setText(goodsModel.getName());
            holder.iv_pic.setBackgroundResource(goodsModel.getImg());
            long targetMillis = CountDownTask.elapsedRealtime() + time;
            startCountDown(position, targetMillis, convertView);
        }
        return convertView;
    }
    private void startCountDown(final int position, final long millis, View convertView) {
        if (mCountDownTask != null) {
            mCountDownTask.until(convertView, millis, countDownInterval, new CountDownTimers.OnCountDownListener() {
                @Override
                public void onTick(View view, long millisUntilFinished) {
                    doOnTick(position, view, millisUntilFinished);
                }

                @Override
                public void onFinish(View view) {
                    doOnFinish(position, view);
                }
            });
        }
    }

    /**
     * 计算时间并显示
     * @param position
     * @param view
     * @param millisUntilFinished
     */
    private void doOnTick(int position, View view, long millisUntilFinished) {
        int ss = 1000;
        int mi = ss * 60;
        long minute = millisUntilFinished/ mi;
        long second = (millisUntilFinished- minute * mi) / ss;
        long milliSecond = millisUntilFinished  - minute * mi - second * ss;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond >100 ? strMilliSecond.substring(0,strMilliSecond.length()-1) : "" + strMilliSecond;
        TextView textView2 = (TextView) view.findViewById(R.id.tv_time);
        textView2.setText(strMinute + " : " + strSecond + ":" + strMilliSecond);
    }

    private void doOnFinish(int position, View view) {
        TextView textView2 = (TextView) view.findViewById(R.id.tv_time);
        textView2.setText("DONE.");
        Toast.makeText(activity,position+"计时结束",Toast.LENGTH_LONG).show();
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setCountDownTask(CountDownTask countDownTask) {
        if (!Objects.equals(mCountDownTask, countDownTask)) {
            mCountDownTask = countDownTask;
            notifyDataSetChanged();
        }
    }
    class NewHolder{
        public ImageView iv_pic;
        public TextView tv_goods_name;
        public TextView tv_time;
    }

}
