package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.solver.state.Dimension;

import java.util.Random;


public class BoardGame extends View {

    private Paint paint;
    private Paint bgPaint;
    Square[][] sqr = new Square[11][11];
    int t=0;
    int size1=0; int size2=0; int size3=0; int size4=0; int countWater=0;

    public BoardGame(Context context){
        super(context);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(12);
        bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);
        for (int i=0; i<11; i++)
        {
            for(int j=0; j<11; j++)
            {
                int i2 = i*100, j2= j*100;
                if(i==0||j==0)
                    sqr[i][j] = new BoardBorder(i2, j2, i2+100, j2+100);
                else
                    sqr[i][j] = new Square(i2, j2, i2+100, j2+100);
            }
        }

        Random rnd = new Random();
        int size=3, count=1;
        boolean horizontal = false;
        boolean vertical = false;
        int i=0,i2=0,j=0,j2=0;
        int length = size;
        int borderLength = length+2;
        int rand = rnd.nextInt(2);
        while(count<11)
        {
            i = rnd.nextInt(10)+1;
            i2 = i;
            j = rnd.nextInt(10)+1;
            j2 = i;
            length=size;
            borderLength = length+2;
            rand = rnd.nextInt(2);

            if(rand==1)
                horizontal = true;
            else
                vertical = true;

            if(horizontal)
            {
                while(i+size>=11)
                {
                    i = rnd.nextInt(10)+1;
                    i2 = i;
                }
            }
            else
            {
                while(j+size>=11)
                {
                    j = rnd.nextInt(10)+1;
                    j2 = j;
                }
            }
            if(!sqr[i][j].getIsBorder()&&!sqr[i][j].getIsShip())
            {
                if(horizontal&&!sqr[i+size][j].getIsBorder()&&!sqr[i+size][j].getIsShip())
                {
                    sqr[i][j] = new Ship(i * 100, j * 100, (i * 100) + 100, (j * 100) + 100);
                    sqr[i][j].setIsShip(true);

                    while (length>=1)
                    {
                        sqr[i2+1][j] = new Ship((i2 + 1) * 100, j * 100, ((i2 + 1) * 100) + 100, (j * 100) + 100);
                        sqr[i2+1][j].setIsShip(true);
                        i2++;
                        length--;
                    }
                }
                if(vertical&&!sqr[i][j+size].getIsBorder()&&!sqr[i][j+size].getIsShip())
                {
                    while(length>=1)
                    {
                        sqr[i][j2+1] = new Ship(i * 100, (j2 + 1) * 100, (i2 * 100) + 100, ((j2 + 1) * 100) + 100);
                        sqr[i][j2 + 1].setIsShip(true);
                        j2++;
                        length--;
                    }
                    for (int k = 1; k<borderLength;k++)
                    {
                        SetBorders(sqr, i, j);
                        i++;
                    }
                }

                count++;
                if(size==3&&count==2)
                    size--;
                if(size==2&&count==4)
                    size--;
                if(size==1&&count==7)
                    size--;
            }
            i=0; j=0; i2=0; j2=0;
            horizontal = false; vertical = false;
        }
        for (int k=1; k<11; k++)
        {
            for(int l=1; l<11; l++)
            {
                if(sqr[k][l] instanceof Ship)
                {
                    if (sqr[0][l] instanceof BoardBorder)
                        ((BoardBorder) sqr[0][l]).addShip();
                    if (sqr[k][0] instanceof BoardBorder)
                        ((BoardBorder) sqr[k][0]).addShip();
                }
                sqr[k][l].setIsBorder(false);
            }
        }
    }

    public void SetBorders(Square[][] sqr, int i, int j){
        for (int k = 1; k < 11; k++){
            for (int l= 1; l < 11; l++){
                if(Math.abs(sqr[k][l].getX()-sqr[i][j].getX())==100 && Math.abs(sqr[k][l].getY()-sqr[i][j].getY())==100 && !(sqr[k][l] instanceof Ship)) {
                    sqr[k][l].setIsBorder(true);
                }
                if(Math.abs(sqr[k][l].getY()-sqr[i][j].getY())==100 && Math.abs(sqr[k][l].getX()-sqr[i][j].getX())==0 && !(sqr[k][l] instanceof Ship)){
                    sqr[k][l].setIsBorder(true);
                }
                if(Math.abs(sqr[k][l].getX()-sqr[i][j].getX())==100 && Math.abs(sqr[k][l].getY()-sqr[i][j].getY())==0 && !(sqr[k][l] instanceof Ship)) {
                    sqr[k][l].setIsBorder(true);
                }

            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawPaint(bgPaint);
        if(size1==4&&size2==3&&size3==2&&size4==1&&countWater==20)
        {
            paint.setStrokeWidth(4);
            paint.setTextSize(60);
            canvas.drawText("you won the game", 300, 300, paint);
        }
        else
        {
            for(int i=0; i<11; i++)
            {
                for(int j=0; j<11; j++)
                {
                    sqr[i][j].Draw(canvas,paint);
                    sqr[i][j].DrawBorder(canvas,paint);
                }
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(4);
            paint.setTextSize(60);
            canvas.drawText("Ships",200,1200,paint);
            canvas.drawText("1:",200,1300,paint);
            canvas.drawText("2:",200,1400,paint);
            canvas.drawText("3:",200,1500,paint);
            canvas.drawText("4:",200,1600,paint);
            canvas.drawText("Found",canvas.getWidth()-900,1200,paint);
            canvas.drawText(String.valueOf(size1),canvas.getWidth()-900,1300,paint);
            canvas.drawText(String.valueOf(size2),canvas.getWidth()-900,1400,paint);
            canvas.drawText(String.valueOf(size3),canvas.getWidth()-900,1500,paint);
            canvas.drawText(String.valueOf(size4),canvas.getWidth()-900,1600,paint);
        }
    }

    /*private void drawBoard(Canvas canvas) {
        h=canvas.getWidth()/size;
        w=canvas.getWidth()/size;
        for(int i=0; i<squares.length; i++){
            for(int j=0; j<squares.length; j++){
                squares[i][j] = new Square(j*w,i*h,w,h);
                squares[i][j].draw(canvas);
            }
        }
    }*/
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            int x=(int)event.getX(),y=(int)event.getY();
            for (int k = 1; k < 11; k++){
                for (int l= 1; l < 11; l++) {
                    if(sqr[k][l].getX()+100>x && sqr[k][l].getX()<x ){
                        if(sqr[k][l].getY()+100>y && sqr[k][l].getY()<y ){

                            if(sqr[k][l].getKind()==0){
                                sqr[k][l].setColor("water");
                                sqr[k][l].changeKind();
                                sqr[k-1][l-1].setIsBorder(false);
                                if(k+1!=11 && l+1!=11)
                                    sqr[k+1][l+1].setIsBorder(false);
                                if(l+1!=11)
                                    sqr[k-1][l+1].setIsBorder(false);
                                if(k+1!=11)
                                    sqr[k+1][l-1].setIsBorder(false);
                            }
                            else if(sqr[k][l].getKind()==1){
                                sqr[k][l].setColor("black");
                                sqr[k][l].changeKind();
                                sqr[k-1][l-1].setIsBorder(true);
                                if(k+1!=11 && l+1!=11)
                                    sqr[k+1][l+1].setIsBorder(true);
                                if(l+1!=11)
                                    sqr[k-1][l+1].setIsBorder(true);
                                if(k+1!=11)
                                    sqr[k+1][l-1].setIsBorder(true);
                                ((BoardBorder) sqr[0][l]).addGuess();
                                ((BoardBorder) sqr[k][0]).addGuess();
                            }
                            else if(sqr[k][l].getKind()==2){
                                sqr[k][l].setColor("white");
                                sqr[k][l].changeKind();
                                sqr[k-1][l-1].setIsBorder(false);
                                if(k+1!=11 && l+1!=11)
                                    sqr[k+1][l+1].setIsBorder(false);
                                if(l+1!=11)
                                    sqr[k-1][l+1].setIsBorder(false);
                                if(k+1!=11)
                                    sqr[k+1][l-1].setIsBorder(false);
                                ((BoardBorder) sqr[0][l]).removeGuess();
                                ((BoardBorder) sqr[k][0]).removeGuess();
                            }

                        }
                    }
                }
            }
            for (int k = 1; k < 11; k++)
            {
                for (int l = 1; l < 11; l++)
                {
                    if (sqr[k][l].getColor()=="black" && !sqr[k][l].getIsBorder())
                        sqr[k][l].setColor("red");
                    if(sqr[k][l].getColor()=="red" && !sqr[k][l].getIsBorder())
                        sqr[k][l].setColor("black");
                }
            }

            int count=0;
            int[] ship = new int[4];
            for (int l = 1; l < 11; l++)
            {
                for (int k = 1; k < 11; k++)
                {
                    if (sqr[k][l].getColor() == "black") {
                        while (sqr[k][l].getColor() == "black") {
                            count++;
                            if(k==10)
                                break;
                            k++;
                        }
                        if (count >= 5) {
                            for (int i=count; i>0;i--){
                                sqr[k-i][l].setColor("red");
                            }
                        }
                        if (1 < count && count <= 4)
                            ship[count - 1] += 1;
                        count = 0;
                    }
                    if(sqr[k][l].getColor() == "red"){
                        if(k!=10 && sqr[k+1][l].getColor() == "black")
                            sqr[k+1][l].setColor("red");
                        if(sqr[k-1][l].getColor() == "black")
                            sqr[k-1][l].setColor("red");
                        if(l!=10 && sqr[k][l+1].getColor() == "black")
                            sqr[k][l+1].setColor("red");
                        if(sqr[k][l-1].getColor() == "black")
                            sqr[k][l-1].setColor("red");
                    }
                }
            }
            for (int k = 1; k < 11; k++) {
                for (int l = 1; l < 11; l++) {
                    while(sqr[k][l].getColor()=="black") {
                        count++;
                        if(l==10)
                            break;
                        l++;

                    }
                    if (count >= 5) {
                        for (int i=count; i>0;i--){
                            sqr[k][l-i].setColor("red");
                        }
                    }
                    if(1<count && count<=4)
                        ship[count-1]+=1;


                    count=0;
                }
            }
            for (int k = 1; k < 11; k++) {
                for (int l = 1; l < 11; l++) {
                    if(sqr[k][l].getColor() == "black"){
                        if(k!=10 && l!=10){
                            if(sqr[k+1][l].getColor() != "black" && sqr[k-1][l].getColor() != "black" &&sqr[k][l+1].getColor() != "black" && sqr[k][l-1].getColor() != "black"){
                                ship[0]+=1;
                            }
                        }
                        else if(l!=10 && k==10){
                            if(sqr[k-1][l].getColor() != "black" &&sqr[k][l+1].getColor() != "black" && sqr[k][l-1].getColor() != "black"){
                                ship[0]+=1;
                            }
                        }
                        else if(l==10 && k!=10){
                            if(sqr[k+1][l].getColor() != "black" && sqr[k-1][l].getColor() != "black" && sqr[k][l-1].getColor() != "black"){
                                ship[0]+=1;
                            }
                        }
                        else if(l==10 && k==10){
                            if(sqr[k-1][l].getColor() != "black" && sqr[k][l-1].getColor() != "black"){
                                ship[0]+=1;
                            }
                        }

                    }
                }
            }

            size1=ship[0];
            size2=ship[1];
            size3=ship[2];
            size4=ship[3];
            countWater=0;
            for (int l = 1; l < 11; l++) {
                if(((BoardBorder)sqr[0][l]).isValid()){
                    countWater++;
                }
            }
            for(int i=1 ; i<11; i++){
                if(((BoardBorder)sqr[i][0]).isValid()){
                    countWater++;
                }
            }
            invalidate();
        }
        return true;
    }
}
