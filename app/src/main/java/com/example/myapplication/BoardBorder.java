package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class BoardBorder extends Square
{
    int shipsGuess=0;
    int shipsCount=0;

    public BoardBorder(int x, int y, int xEnd, int yEnd)
    {
        super(x, y, xEnd, yEnd);
    }
    public void addShip(){this.shipsCount++;}
    public void addGuess(){this.shipsGuess++;}
    public void removeGuess(){this.shipsGuess--;}
    public boolean isValid()
    {
        if(shipsGuess!=shipsCount)
            return false;
        return true;
    }

    @Override
    public void Draw(Canvas canvas, Paint paint)
    {
        Paint paint2 = new Paint();
        if(isValid())
        {
            paint2.setColor(Color.BLACK);
            paint2.setTextSize(50);
            paint2.setTextAlign(Paint.Align.CENTER);
            float textSize = paint2.getTextSize();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(x,y,xEnd,yEnd,paint);
            canvas.drawText(String.valueOf(shipsCount), x+((xEnd-x)/2), y+((yEnd-y)/2), paint2);
        }

        else
        {
            paint2.setColor(Color.RED);
            paint2.setTextSize(50);
            paint2.setTextAlign(Paint.Align.CENTER);
            float textSize = paint2.getTextSize();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(x,y,xEnd,yEnd,paint);
            canvas.drawText(String.valueOf(shipsCount), x+((xEnd-x)/2), y+((yEnd-y)/2), paint2);
        }

    }
}
