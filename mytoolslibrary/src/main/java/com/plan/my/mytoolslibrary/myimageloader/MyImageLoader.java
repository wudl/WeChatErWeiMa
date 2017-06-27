package com.plan.my.mytoolslibrary.myimageloader;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.plan.my.mytoolslibrary.R;

/**
 * (清除指定缓存的用法)
 * 	DiskCacheUtils.removeFromCache(contentStr, imageLoader.getImageLoader().getDiskCache());
 * 	MemoryCacheUtils.removeFromCache(contentStr, imageLoader.getImageLoader().getMemoryCache());
 * Created by wudl on 2016/9/12 16:50
 * <p/>
 * 邮箱 770616344@qq.com
 */
public class MyImageLoader {



    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private ImageLoaderConfiguration config;
    private int cacheSize = 0;

    private DisplayImageOptions optionsUserAvatar;
    private DisplayImageOptions optionsNeiCunUserAvatar;//内存存储
    private DisplayImageOptions optionsNeiCunCircleUserAvatar;//内存存储
    public MyImageLoader(Context context) {
        cacheSize = getCacheSize();

        config = new ImageLoaderConfiguration.Builder(
                context)//GlobalContext.getInstance()
                .threadPriority(Thread.NORM_PRIORITY - 1).threadPoolSize(6)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(cacheSize))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                // .writeDebugLogs() // :TODO Remove for release app
                .build();

        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).build();

        optionsUserAvatar = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.a_touming)//加载图片时的图片
                .showImageForEmptyUri(R.drawable.a_touming)//没有图片资源时的默认图片
                .showImageOnFail(R.drawable.album)//加载失败时的图片
                .cacheInMemory(false)//启用内存缓存
                .cacheOnDisk(false)//启用外存缓存
                .considerExifParams(true)//启用EXIF和JPEG图像格式
                .displayer(new SimpleBitmapDisplayer())//设置显示风格
                .build();

        optionsNeiCunUserAvatar = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.a_touming)//加载图片时的图片
                .showImageForEmptyUri(R.drawable.a_touming)//没有图片资源时的默认图片
                .showImageOnFail(R.drawable.album)//加载失败时的图片album
                .cacheInMemory(true)//启用内存缓存
                .cacheOnDisk(true)//启用外存缓存
                .considerExifParams(true)//启用EXIF和JPEG图像格式
                .displayer(new SimpleBitmapDisplayer())//设置显示风格
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        optionsNeiCunCircleUserAvatar = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.a_touming)//加载图片时的图片
                .showImageForEmptyUri(R.drawable.a_touming)//没有图片资源时的默认图片
                .showImageOnFail(R.drawable.a_touming)//加载失败时的图片
                .cacheInMemory(true)//启用内存缓存
                .cacheOnDisk(true)//启用外存缓存
                .considerExifParams(true)//启用EXIF和JPEG图像格式
                .displayer(new SimpleBitmapDisplayer())//设置显示风格
                .build();
    }

    /**
     * ImageScaleType
     EXACTLY :图像将完全按比例缩小的目标大小
     EXACTLY_STRETCHED:图片会缩放到目标大小完全
     IN_SAMPLE_INT:图像将被二次采样的整数倍
     IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
     NONE:图片不会调整
     */

    public void displayNeiCunImage(String imageUrl, ImageView imageView) {
        imageUrl = imageUrl.replace("https://","http://");
        imageLoader.displayImage(imageUrl, imageView, this.optionsNeiCunUserAvatar);
    }

    /**
     * 默认先显示圆形的图
     * @param imageUrl
     * @param imageView
     */
    public void displayNeiCunCircleImage(String imageUrl, ImageView imageView) {
        imageLoader.displayImage(imageUrl, imageView, this.optionsNeiCunCircleUserAvatar);
    }

    public void init() {
        imageLoader.init(config);
    }

    public void displayAvatarImage(String imageUrl, ImageView imageView) {
        imageUrl = imageUrl.replace("https://","http://");
        imageLoader.displayImage(imageUrl, imageView, this.optionsUserAvatar);
    }

    public void displayImage(String imageUrl, ImageView imageView) {
        imageUrl = imageUrl.replace("https://","http://");
        imageLoader.displayImage(imageUrl, imageView, this.options);
    }

    public void displayImage(String imageUrl, ImageView imageView,
                             DisplayImageOptions options) {
        imageUrl = imageUrl.replace("https://","http://");
        imageLoader.displayImage(imageUrl, imageView, options);
    }

    public void displayImage(String imageUrl, ImageView imageView,
                             ImageLoadingListener listener,
                             ImageLoadingProgressListener progressListener) {
        imageUrl = imageUrl.replace("https://","http://");
        imageLoader.displayImage(imageUrl, imageView, this.optionsNeiCunUserAvatar, listener,
                progressListener);//options
    }



    public void clearDiskCache() {
        imageLoader.clearDiskCache();
    }

    public void clearMemoryCache() {
        imageLoader.clearMemoryCache();
    }



    /**
     * 获取UniversalImageLoader-ImageLoader对象
     *
     * @return
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static int getMaxMemory() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        return maxMemory;
    }

    public static int getCacheSize() {
        int maxMemory = getMaxMemory();
        int cacheSize = maxMemory / 8;
        return cacheSize;
    }



}
