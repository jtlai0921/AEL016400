package idv.ron.draw2ddemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GeometricView extends View {
    private int offset = 0;
    private Paint paint = new Paint();

    public GeometricView(Context context) {
        super(context);
    }

    public GeometricView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        canvas.drawLine(10 + offset, 10, 210 + offset, 10, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(110 + offset, 140, 100, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(10 + offset, 260, 210 + offset, 460, paint);
    }
}
