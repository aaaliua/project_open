package com.dazhongcun.merchants.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dazhongcun.meifa.merchants.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 图像类  imageload图像的配置
 * @author Administrator
 *
 */
public class ImageOptions {
	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.placeholder_thumb)
	.showImageForEmptyUri(R.drawable.placeholder_thumb)
	.showImageOnFail(R.drawable.placeholder_thumb) 
	.cacheInMemory(true)
	.cacheOnDisc(true).considerExifParams(true)
	.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
	.displayer(new FadeInBitmapDisplayer(1500)).build();
	
	//默认的图片配置对象
	public static DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()  
	 .showImageOnLoading(R.drawable.placeholder_thumb) //设置图片在下载期间显示的图片  
	 .showImageForEmptyUri(R.drawable.placeholder_thumb)//设置图片Uri为空或是错误的时候显示的图片  
	.showImageOnFail(R.drawable.placeholder_thumb)  //设置图片加载/解码过程中错误时候显示的图片
	.cacheInMemory(true)//设置下载的图片是否缓存在内存中  
	.cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中  
	.considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
	.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示  
	.bitmapConfig(Bitmap.Config.RGB_565) //设置图片的解码类型  
//	.decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置  
	//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
	//设置图片加入缓存前，对bitmap进行设置  
	//.preProcessor(BitmapProcessor preProcessor)  
	.resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位  
	.displayer(new RoundedBitmapDisplayer(40))//是否设置为圆角，弧度为多少  
	.displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间  
	.build();//构建完成  
	
	
	
	
	
}
