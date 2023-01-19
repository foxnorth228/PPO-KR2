package com.example.kr2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

class DrawThread extends Thread {
    private boolean running = false;
    private final SurfaceHolder surfaceHolder;
    private final Bitmap base;
    private final DrawView view;

    public DrawThread(SurfaceHolder surfaceHolder, DrawView view) {
        this.surfaceHolder = surfaceHolder;
        this.view = view;
        base = view.baseBitmap;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        Rect rect = new Rect(0, 0, Inst.screenWidth, Inst.screenHeight);
        Paint pen = new Paint();
        pen.setTextSize(70);
        while (running) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                if (canvas == null)
                    continue;
                canvas.drawBitmap(base, null, rect, null);
                canvas.drawText(String.format("%04d", view.count), 50, 70, pen);
                Circle ball = view.ball;
                canvas.drawCircle(ball.cx, ball.cy, ball.r, pen);
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}