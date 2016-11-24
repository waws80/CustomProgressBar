package thanatos.customprogressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义进度条
 */

public class CustomProgressBar extends View {

    private static final String TAG="thanatos";
    private Paint mPaint_bg=null;
    private Paint mPaint=null;
    private Paint mPaint_arc=null;
    private Paint mPaint_text=null;
    //进度条最小值
    private int progress;
    //背景园颜色
    private int bgCirColor;
    //圆环颜色
    private int cirColor;
    //圆弧颜色
    private int arcColor;
    //文本字体颜色
    private int textColor;
    //圆环宽度
    private float cirStokeWidth;
    //圆弧宽度
    private float arcStokeWidth;
    //文本字体大小
    private float textSize;
    //文字样式
    private int textType;
    //进度条类型
    private BarType progressType;

    private float arcType_interval_interval;
    private float arcType_interval_width;

    enum BarType{
        DEFAULT,
        INTERVAL;
    }



    public CustomProgressBar(Context context) {
        this(context,null);
    }


    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);

        //背景圆颜色
        bgCirColor=typedArray.getColor(R.styleable.CustomProgressBar_bgCirColor,Color.WHITE);

        //设置圆环颜色
        cirColor = typedArray.getColor(R.styleable.CustomProgressBar_circleColor, Color.RED);

        //设置圆弧颜色
        arcColor = typedArray.getColor(R.styleable.CustomProgressBar_arcColor, Color.WHITE);

        //设置文本颜色
        textColor = typedArray.getColor(R.styleable.CustomProgressBar_textColor, Color.RED);

        //设置圆环宽度
        cirStokeWidth = typedArray.getFloat(R.styleable.CustomProgressBar_cirStokeWidth, 20f);

        //设置圆弧宽度
        arcStokeWidth= typedArray.getFloat(R.styleable.CustomProgressBar_arcStokeWidth, 22f);

        //设置文本字体大小
        textSize = typedArray.getDimension(R.styleable.CustomProgressBar_textSize, 40f);

        //设置文本文字类型
        textType=typedArray.getInt(R.styleable.CustomProgressBar_textType,0);

        //设置进度初始值
        progress = typedArray.getInteger(R.styleable.CustomProgressBar_value, 0);
        //设置进度条样式
        int index=typedArray.getInteger(R.styleable.CustomProgressBar_arcType,0);

        //设置间隔进度条的间隔
        arcType_interval_interval=typedArray.getFloat(R.styleable.CustomProgressBar_arcType_interval_interval,10f);
        //设置每一个间隔的宽度
        arcType_interval_width=typedArray.getFloat(R.styleable.CustomProgressBar_arcType_interval_width,10f);

        setBgCirColor(bgCirColor);
        setCirColor(cirColor);
        setArcColor(arcColor);
        setTextColor(textColor);
        setCirStokeWidth(cirStokeWidth);
        setArcStokeWidth(arcStokeWidth);
        setTextSize(textSize);
        setTextType(textType);
        setProgress(progress);
        setProgressType(BarType.DEFAULT);
        if (index==1){
            setProgressType(BarType.INTERVAL);
            setArcType_interval_interval(arcType_interval_interval);
            setArcType_interval_width(arcType_interval_width);
        }

        typedArray.recycle();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        float mX=getWidth()/2;
        float mY=getHeight()/2;
        float mCirRadius=mX-getCirStokeWidth()/2-4;
        float mArcRadius=mX-getArcStokeWidth()/2-4;
        //背景圆
        canvas.drawCircle(mX,mY,mCirRadius,mPaint_bg);
        //初始圆环
        canvas.drawCircle(mX,mY,mCirRadius,mPaint);
        @SuppressLint("DrawAllocation")
        RectF oval = new RectF(mX-mArcRadius, mY-mArcRadius, getWidth()-(mX-mArcRadius),getHeight()-(mY-mArcRadius));
        float angle=3.6f*getProgress();
        //扇形进度条
        canvas.drawArc(oval,-90,angle,false,mPaint_arc);
        //文字进度
        canvas.drawText(getProgress()+"%",mX,mY+getTextSize()/3,mPaint_text);
    }

    private void init() {
        //背景园
        mPaint_bg=new Paint();
        mPaint_bg.setColor(getBgCirColor());
        mPaint_bg.setAntiAlias(true);
        //圆环画笔
        mPaint=new Paint();
        mPaint.setColor(getCirColor());
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(getCirStokeWidth());
        mPaint.setAntiAlias(true);
        //圆弧画笔

        mPaint_arc=new Paint();
        mPaint_arc.setColor(getArcColor());
        mPaint_arc.setStyle(Paint.Style.STROKE);
        mPaint_arc.setStrokeWidth(getArcStokeWidth()+2);
        mPaint_arc.setAntiAlias(true);
        if (progressType==BarType.INTERVAL){
            PathEffect effects = new DashPathEffect(new float[] { getArcType_interval_width(), getArcType_interval_interval()}, 1f);
            mPaint_arc.setPathEffect(effects);
        }

        //文字画笔
        mPaint_text=new Paint();
        mPaint_text.setColor(getTextColor());
        mPaint_text.setAntiAlias(true);
        mPaint_text.setTextAlign(Paint.Align.CENTER);
        mPaint_text.setTextSize(getTextSize());
        mPaint_text.setTypeface(Typeface.defaultFromStyle(getTextType()));

    }


    public int getProgress() {
        return progress;
    }

    public void setProgress(int min) {
        this.progress = min;
        postInvalidate();
    }

    public int getCirColor() {
        return cirColor;
    }

    public void setCirColor(int cirColor) {
        this.cirColor = cirColor;
    }

    public int getArcColor() {
        return arcColor;
    }

    public void setArcColor(int arcColor) {
        this.arcColor = arcColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getCirStokeWidth() {
        return cirStokeWidth;
    }

    public void setCirStokeWidth(float cirStokeWidth) {
        this.cirStokeWidth = cirStokeWidth;
    }

    public float getArcStokeWidth() {
        return arcStokeWidth;
    }

    public void setArcStokeWidth(float arcStokeWidth) {
        this.arcStokeWidth = arcStokeWidth;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getBgCirColor() {
        return bgCirColor;
    }

    public void setBgCirColor(int bgCirColor) {
        this.bgCirColor = bgCirColor;
    }


    public int getTextType() {
        return textType;
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public BarType getProgressType() {
        return progressType;
    }

    public void setProgressType(BarType progressType) {
        this.progressType = progressType;
    }

    public float getArcType_interval_interval() {
        return arcType_interval_interval;
    }

    public void setArcType_interval_interval(float arcType_interval_interval) {
        this.arcType_interval_interval = arcType_interval_interval;
    }

    public float getArcType_interval_width() {
        return arcType_interval_width;
    }

    public void setArcType_interval_width(float arcType_interval_width) {
        this.arcType_interval_width = arcType_interval_width;
    }
}
