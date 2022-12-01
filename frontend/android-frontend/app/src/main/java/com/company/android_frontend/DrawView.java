package com.company.android_frontend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DrawView extends View {
    private Paint currentPaint;
    public boolean drawRect = false;
    public float left;
    public float top;
    public float right;
    public float bottom;

    public DrawView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        currentPaint = new Paint();
        currentPaint.setDither(true);
        currentPaint.setColor(0xFF00CC00);  // alpha.r.g.b
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);
        currentPaint.setStrokeWidth(2);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawRect) {
            canvas.drawRect(left, top, right, bottom, currentPaint);
        }
    }
}
