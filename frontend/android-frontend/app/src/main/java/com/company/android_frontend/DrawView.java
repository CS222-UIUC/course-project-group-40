package com.company.android_frontend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class DrawView extends androidx.appcompat.widget.AppCompatImageView {
    private Paint currentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public boolean drawRect = false;
    public float left;
    public float top;
    public float right;
    public float bottom;

    public DrawView(Context context) {
        super(context);
        invalidate();
    }

    public DrawView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        currentPaint.setColor(Color.BLUE);  // alpha.r.g.b
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);
        currentPaint.setStrokeWidth(2);
        invalidate();
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (drawRect) {
            canvas.drawRect(left, top, right, bottom, currentPaint);
        }
    }
}
