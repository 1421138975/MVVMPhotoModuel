package com.dfzt.bitmapmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.dfzt.base.activity.BaseActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.bitmapmodule.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initStatusBar() {
        //super.initStatusBar();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorAccent),0);


    }

    @Override
    protected void initData() {
        super.initData();
        //第一种方法 直接设置原图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_mv_p);
        mDataBinding.mImageView2.setImageBitmap(bitmap);


       //第二种将图片进行压缩处理 让后放到这个控件中
        ViewGroup.LayoutParams params = mDataBinding.mImageView.getLayoutParams();
        Bitmap bitmap1 = contractionBitmap(R.mipmap.icon_mv_p, params.width, params.height, false);
        mDataBinding.mImageView.setImageBitmap(bitmap1);
    }


    /**
     * 压缩图片
     * @param hasAlpha 是否需要透明度  需要的话使用ARGB_8888  不需要的话使用RGB_565
     */
    private Bitmap contractionBitmap(int id,int maxW, int maxH, boolean hasAlpha){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //这个社会为true只会获取到图片的信息 么在解码的时候将不会返回bitmap
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),id,options);
        //获取图片的高度和宽度
        int w = options.outWidth;
        int h = options.outHeight;

        //设置图片的缩放比例
        options.inSampleSize = calcuteInSampleSize(w,h,maxW,maxH);

        if (!hasAlpha){
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        //重新设置为false就能得到bitmap
        options.inJustDecodeBounds = false;
        return  BitmapFactory.decodeResource(getResources(),id,options);
    }

    /**
     *
     * @param w 图片的宽度
     * @param h 图片的高度
     * @param maxW 控件额高度
     * @param maxH 控件的宽度
     * @return
     */
    private int calcuteInSampleSize(int w, int h, int maxW, int maxH){
        //默认的缩放的比例为1
        int inSampleSize = 1;

        if (w > maxW && h > maxH) {
            //如果图片的宽度 和高度 都大于控件 就进行2倍的压缩
            inSampleSize = 2;

            while (w / inSampleSize > maxW && h / inSampleSize > maxH) {
                inSampleSize *= 2;
            }

        }

        return inSampleSize;
    }
}
