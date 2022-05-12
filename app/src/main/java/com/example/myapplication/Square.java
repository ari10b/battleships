package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Square
{

    int x, y, xEnd, yEnd;
    int kind; //0-normal, 1-water, 2-ship
    String color;
    boolean isShip;
    boolean isBorder;

    public Square(int x, int y, int xEnd, int yEnd)
    {
        this.x = x;
        this.y = y;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        color = "white";
        isShip = false;
        kind = 0;
    }

    //Gets-int
    public int getX(){return x;}
    public int getY(){return y;}
    public int getKind(){return kind;}
    //Gets-boolean
    public boolean getIsShip(){return isShip;}
    public boolean getIsBorder(){return isBorder;}
    //Gets-String
    public String getColor(){return color;}

    //Sets-int
    public void setX(int t){x=t;}
    public void setY(int t){y=t;}
    public void setKind(int t)
    {
        if(t>=0&&t<=2)
            kind=t;
        else
            kind=0;
    }
    //Sets-boolean
    public void setIsShip(boolean t){isShip=t;}
    public void setIsBorder(boolean t){isBorder=t;}
    //Sets-String
    public void setColor(String t){color=t;}

    //Change the kind of the square
    public void changeKind()
    {
        if(kind>=2)
            kind=0;
        else
            kind++;
    }

    //function that draw the rectangle
    public void Draw(Canvas canvas, Paint paint)
    {
        if(color=="black") paint.setColor(Color.BLACK);
        else if(color=="red") paint.setColor(Color.RED);
        else if(color=="cyan"||color=="water") paint.setColor(Color.rgb(204, 255, 255));
        else paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x,y,xEnd,yEnd,paint);
    }

    //function that draw the rectangle border
    public void DrawBorder(Canvas canvas, Paint paint)
    {
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(x,y,xEnd,yEnd,paint);
    }
}