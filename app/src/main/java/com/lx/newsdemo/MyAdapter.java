package com.lx.newsdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by lixiang on 2017/3/20.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Data.NewslistBean> datas;
    private DisplayImageOptions options;

    public MyAdapter(Context context, List<Data.NewslistBean> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
        options =new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.ic_launcher)//下载期间
                .showImageForEmptyUri(R.mipmap.ic_launcher)//下载错误
                .showImageOnFail(R.mipmap.ic_launcher)//解码错误
                .cacheInMemory(true)//内存
                .cacheOnDisk(true)//内存卡
                .displayer(new RoundedBitmapDisplayer(20))//圆角
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//编码方式
                .bitmapConfig(Bitmap.Config.RGB_565)//图片质量
                .handler(new Handler())
                .build();
    }


    public void addData(List<Data.NewslistBean> data){
        datas.addAll(0,data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }
    @Override
    public Data.NewslistBean getItem(int i) {
        return datas.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Temp temp=null;
        if(view==null){
            temp = new Temp();
            view = inflater.inflate(R.layout.list_item,null);
            temp.iv = (ImageView) view.findViewById(R.id.item_iv);
            temp.title = (TextView) view.findViewById(R.id.item_title);
            temp.time = (TextView) view.findViewById(R.id.item_time);
            view.setTag(temp);
        }else {
            temp = (Temp) view.getTag();
        }
        Data.NewslistBean data = datas.get(i);
        temp.title.setText(data.getTitle());
        temp.time.setText(data.getCtime());
        ImageLoader.getInstance().displayImage(data.getPicUrl(),temp.iv,options);
        return view;
    }
    private class Temp{
        private ImageView iv;
        private TextView title,time;
    }
}
