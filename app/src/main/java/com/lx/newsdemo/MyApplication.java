package com.lx.newsdemo;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by lixiang on 2017/3/20.
 */

public class MyApplication extends Application{
    private static MyApplication myApplication;
    public static MyApplication getInstance(){
        if(myApplication==null){
            myApplication=new MyApplication();
        }
        return myApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480,600)//缓存图片大小
                .threadPoolSize(5)//线程数
                .threadPriority(Thread.NORM_PRIORITY)//线程优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()//缓存在内存
                .memoryCache(new LruMemoryCache(2*1024*1024))//缓存大小
                .memoryCacheSizePercentage(13)
                .diskCache(new UnlimitedDiskCache(getCacheDir()))
                .diskCacheFileCount(30)//换成数量
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(this))
                .imageDecoder(new BaseImageDecoder(true))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs()//打印错误信息
                .build();
        ImageLoader.getInstance().init(configuration);
    }

}
