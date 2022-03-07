package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class TicTacToeBoard extends View {

    private boolean winningLine=false;

    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;

    private final Paint paint=new Paint();//creating paint class object
    private final GameLogic game;

    private int cellSize=getWidth()/3;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game=new GameLogic();
        //Typed Array is a container for an array of values that are retrieved from resources
        //to style attributes of tic tac toe board according to the attrs.xml file
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs,R.styleable.TicTacToeBoard,
                0,0);
        try{ //to extract values from typed array
            boardColor=a.getInteger(R.styleable.TicTacToeBoard_boardColor,0);
            XColor=a.getInteger(R.styleable.TicTacToeBoard_XColor,0);
            OColor=a.getInteger(R.styleable.TicTacToeBoard_OColor,0);
            winningLineColor=a.getInteger(R.styleable.TicTacToeBoard_winningLineColor,0);
        }
        finally{
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width,int height)
    {
        //to define dimensions of tic tac toe board
        super.onMeasure(width,height);
        //we want the board to be perfect square irrespective of the screen of the user viewing on
        //to do that we need to find the min of height or width of the user screen
        int dimension=Math.min(getMeasuredWidth(),getMeasuredHeight());
        cellSize=dimension/3;
        //divide by 3 because we will have three boxes
        setMeasuredDimension(dimension,dimension);//defining dimensions of view
        // to draw the tic tac board we firstly need to import and create the object of paint class
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        //canvas defines the area of our view
        //setup the paint object
        paint.setStyle(Paint.Style.STROKE);//to draw a line
        paint.setAntiAlias(true);

        drawGameBoard(canvas); //draw game board will actually draw the game board

        drawMarkers(canvas);
        if(winningLine) {
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x=event.getX();//grab x and y position of user click
        float y=event.getY();

        int action=event.getAction();
        if(action==MotionEvent.ACTION_DOWN)
        {
            int row=(int)(Math.ceil(y/cellSize));
            //to find the position of row and column of user tap
            int col=(int)(Math.ceil(x/cellSize));
            if(!winningLine) {
                if (game.updateGameBoard(row, col)) {
                    invalidate();
                    if(game.winnerCheck())
                    {
                        winningLine=true;
                        invalidate();
                    }
                    if (game.getPlayer() % 2 == 0) {
                        game.setPlayer(game.getPlayer() - 1);//set next turn to player 1
                    } else {
                        game.setPlayer(game.getPlayer() + 1);//set next turn to player 2
                    }
                }
            }
            invalidate();//to re draw
            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas)
    {
        paint.setColor(boardColor);//set the paint color whatever is the color is of board
        //i.e.,the value extracted from the xml file
        paint.setStrokeWidth(16);
        //stroke width is defined in pixels and it can be float also
        for(int c=1;c<=2;c++)
        {
            canvas.drawLine(cellSize*c,0,cellSize*c,canvas.getWidth(),paint);
            //passing the start and end positions of x and y
            //column line will not start from 0
        }
        for(int r=1;r<=2;r++)
        {
            canvas.drawLine(0,cellSize*r,canvas.getWidth(),cellSize*r,paint);
            //row line will drawn from the starting extreme left,so start index will be 0
        }
    }

    private void drawMarkers(Canvas canvas)
    {
        for(int r=0;r<3;r++)
        {
            for(int c=0;c<3;c++)
            {
                if(game.getGameBoard()[r][c]!=0) //if the value is not equal to default value
                {              //then we either need to draw an X or O
                    if(game.getGameBoard()[r][c]==1) //value 1 denotes X
                    {         //if current game position is 1 then draw an X
                        drawX(canvas,r,c);
                    }
                    else  //otherwise draw an O
                    {
                        drawO(canvas,r,c);
                    }
                }
            }
        }
    }
    public void drawX(Canvas canvas,int row,int col)
    {
        paint.setColor(XColor);//set the color of x to what was set in the xml code
        canvas.drawLine((float)((col+1)*cellSize-cellSize*0.2),
                (float)(row*cellSize+cellSize*0.2),
                (float)(col*cellSize+cellSize*0.2),
                (float)((row+1)*cellSize-cellSize*0.2),paint);

        canvas.drawLine((float)(col*cellSize+cellSize*0.2),
                (float)(row*cellSize+cellSize*0.2),
                (float)((col+1)*cellSize-cellSize*0.2),
                (float)((row+1)*cellSize-cellSize*0.2),paint);
    }
    public void drawO(Canvas canvas,int row,int col)
    {
        paint.setColor(OColor);
        canvas.drawOval((float) (col*cellSize+cellSize*0.2),
                (float)(row*cellSize+cellSize*0.2),
                (float)((col*cellSize+cellSize)-cellSize*0.2)
                ,(float)((row*cellSize+cellSize)-cellSize*0.2),paint);
    }
/* set up game method is used because game logic class needs access to game_display.xml
* views play again button,home button,and text view up */
    private void drawHorizontalLine(Canvas canvas,int row,int col)
    {
        canvas.drawLine( col, (float)(row*cellSize+cellSize/2),
                cellSize*3,(float)(row*cellSize+cellSize/2),paint);
    }
    private void drawVerticalLine(Canvas canvas,int row,int col)
    {
        canvas.drawLine((float)(col*cellSize+cellSize/2),row,col*cellSize+cellSize/2,
                cellSize*3,paint);
    }
    private void drawDiagonalLinePositive(Canvas canvas)
    {
        canvas.drawLine(0,cellSize*3,cellSize*3,0,paint);
    }
    private void drawDiagonalLineNegative(Canvas canvas)
    {
        canvas.drawLine(0,0,cellSize*3,cellSize*3,paint);
    }
    private void drawWinningLine(Canvas canvas)
    {
        int row=game.getWinType()[0];
        int col=game.getWinType()[1];
        switch(game.getWinType()[2])
        {
            case 1:
                drawHorizontalLine(canvas,row,col);
                break;
            case 2:
                drawVerticalLine(canvas,row,col);
                break;
            case 3:
                drawDiagonalLineNegative(canvas);
                break;
            case 4:
                drawDiagonalLinePositive(canvas);
                break;
        }
    }
    public void setUpGame(Button playAgain, Button home,
                          TextView playerDisplay,String names[])
    {
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }
    public void resetGame()
    {
        game.resetGame();
        winningLine=false;
    }
}
