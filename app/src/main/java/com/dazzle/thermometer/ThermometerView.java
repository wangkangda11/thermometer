package com.dazzle.thermometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class ThermometerView extends View {
    private static final String TAG = "Thermometer";

    Context mContext;
    private String temperature = "35";
    private final float highTemp = 60;

    private Paint backgroundPaint;
    private Paint realityPaint;
    //margin 最大值不超过控件高度的三分之一
    private int margin = (int) getResources().getDimension(R.dimen.dpi_10);
    public ThermometerView(Context context) {
        super(context);
    }

    public ThermometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //背景的画笔
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);//抗锯齿
        backgroundPaint.setColor(Color.parseColor("#F2DED7"));
        //实际温度的画笔
        realityPaint = new Paint();
        realityPaint.setAntiAlias(true);//抗锯齿
        realityPaint.setTextSize(getResources().getDimension(R.dimen.spi_12));
    }

    public ThermometerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTemp(Context mcontext,String temp){
        mContext = mcontext;
        temperature = temp;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight()/2;
        Log.e(TAG,"width=  "+width);
        Log.e(TAG,"height=  "+height + "getMeasuredHeight = "+ getMeasuredHeight()+ ", getHeight() =" + getHeight());
        //温度的数值
        float fTemp = Float.parseFloat(temperature);
        //相信Cpu的温度应该不会超过94度了
        if(fTemp >94){
            fTemp =94;
        }
        Log.e(TAG,"fTemp = "+fTemp);
        //温度文字
        String T = fTemp + "°C";
//        T="";
        //圆开始的位置
        int CircleStart = getTextWidth(realityPaint,T)+margin*2;
        Log.e(TAG,"CircleStart =  "+CircleStart);
        //矩形开始的位置
        int RectStart = CircleStart+margin;
        Log.e(TAG,"RectStart =  "+RectStart);

        //除去圆左边的宽度 ==>> 就是温度所要表示的总进度100%
        int sywidth =  width - CircleStart;
        //温度矩形的进度就等于温度除以总进度然后乘以总宽度
        float v = (fTemp/100) * sywidth;
        Log.e(TAG,"V = "+v);

        int CircleR = margin*3/2;
        //背景的绘制
        canvas.drawRect(RectStart, height-margin, width-margin, height+margin, backgroundPaint);
        canvas.drawCircle(width-margin, height, margin, backgroundPaint);
        //根据温度的绘制
        //fTemp小于定义的高温则显示正常的绿色,否则显示高温红色
        if(fTemp < highTemp){
            realityPaint.setColor(Color.parseColor("#3DB475"));
            canvas.drawText(T,0,getMeasuredHeight()*2/3,realityPaint);
            canvas.drawCircle(CircleStart,height,CircleR,realityPaint);
            canvas.drawRect(RectStart, height-margin, v+CircleStart, height+margin, realityPaint);
        }else{
            realityPaint.setColor(Color.parseColor("#F65402"));
            canvas.drawText(T,0,getMeasuredHeight()*2/3,realityPaint);
            canvas.drawCircle(CircleStart,height,CircleR,realityPaint);
            canvas.drawRect(RectStart, height-margin, v+CircleStart, height+margin, realityPaint);
        }

    }
    //计算文字宽度
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

}
