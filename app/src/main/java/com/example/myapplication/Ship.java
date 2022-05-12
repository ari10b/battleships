package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Ship extends Square {

    public Ship(int x, int y, int xEnd, int yEnd)
    {
        super(x, y, xEnd, yEnd);
    }

    @Override
    public void setColor(String t){this.color=t;}

    @Override
    public int getKind(){return this.kind;}

    @Override
    public void changeKind()
    {
        if(this.kind>=2)
            this.kind=0;
        else
            this.kind++;
    }

    @Override
    public void Draw(Canvas canvas, Paint paint)
    {
        if(color=="black") paint.setColor(Color.BLACK);
        else if(color=="red") paint.setColor(Color.RED);
        else if(color=="cyan"||color=="water") paint.setColor(Color.rgb(204, 255, 255));
        else paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, xEnd, yEnd, paint);
    }
}
