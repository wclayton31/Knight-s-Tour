/*
* William Clayton
*/
package knightstour;

import java.util.*;
import java.awt.*;

public class KnightsTourWarnsdorf extends KnightsTour
{
    public boolean display_moves = false;
    
    public KnightsTourWarnsdorf(BoardDisplay display, StackDisplay stackPanel, int time)
    {
        super(display,stackPanel,time);
    }
    
    private int knights_moves[][] = {{1,2,2,1,-1,-2,-2,-1}, {-2,-1,1,2,2,1,-1,-2}};
    @Override
    public void tour(int size, int start_x, int start_y)
    {
        finnished = false;
        Deque<warnsdorfMove> stack = new ArrayDeque<warnsdorfMove>(); //Java built in stack
        int board[][] = new int[size][size];
        for(int i=0; i<size; i++)               //initialized board to -1 
            for(int j=0; j<size; j++)
                board[i][j] = -1;
        board[start_x][start_y] = 1;
	int moves_used[] = {0,0,0,0,0,0,0,0};
        stack.push(new warnsdorfMove(start_x,start_y,moves_used,-1));
        
        int move_count = 1, x = start_x, y = start_y, move = 0;
	int move_x[] = knights_moves[0];
	int move_y[] = knights_moves[1];
        ((BoardDisplay)display).paintComponent(display.getGraphics(),size,x,y); //draw the first position
        
        ((StackDisplay)stackPanel).setSize(size);
        ((StackDisplay)stackPanel).push(stackPanel.getGraphics(),start_x,start_y,-1);
        delay(500); //pause for .5 seconds
      
        while(move_count < size*size && running)
        {
            if(paused)
                pause();
            int min_move = -1, move_options = 9;//more than is possible for a Knight
            int moves[][] = new int[8][2];
            for(int i=0; i<8; i++)
            {
                moves[i][0] = -1;
                moves[i][1] = -1;
            }
            
            for(int i=0; i< move_x.length; i++)
            {
                int pos_x = move_x[i] + x, pos_y = move_y[i] + y, options=0; 
                if(0<=pos_x && pos_x<board.length && 0<=pos_y && pos_y<board[0].length && 
                        board[pos_x][pos_y] == -1 &&
			moves_used[i] == 0)
		{
                    for(int j=0; j<move_x.length; j++)
                    {
                        if(0<=pos_x+move_x[j] && pos_x+move_x[j]<board.length && 
                           0<=pos_y+move_y[j] && pos_y+move_y[j]<board[0].length
                           && board[pos_x+move_x[j]][pos_y+move_y[j]] == -1)
                           options++;
                    }
                    if(options < move_options)
                    {
                        move_options = options;
                        min_move = i;
                    }
                    if(display_moves)
                    {
                        ((BoardDisplay)display).numberedSquare(display.getGraphics(),size,pos_x,pos_y,options,Color.BLUE);
                        moves[i][0] = pos_x;
                        moves[i][1] = pos_y;
                    }
                }
            }
            if(display_moves)
            {
                delay((int)(1.25*time));
                if(paused)
                    pause();
                for(int i=0; i<8;i++)
                    if(moves[i][0] != -1 && moves[i][1] != -1)
                        ((BoardDisplay)display).paintOverPrevious(display.getGraphics(),size,moves[i][0],moves[i][1]);
            }
            if(min_move == -1)
            {
		warnsdorfMove prev_move = stack.pop();
		board[prev_move.x][prev_move.y] = -1;
		move_count--;
		moves_used = prev_move.moves_used;
		x = stack.peek().x;
		y = stack.peek().y;
		redraw(size,prev_move.x,prev_move.y,x,y);
                ((StackDisplay)stackPanel).pop(stackPanel.getGraphics());
            }
	    else
	    {
		moves_used[min_move] = 1;
	        stack.push(new warnsdorfMove(x+move_x[min_move],y+move_y[min_move],moves_used, min_move));
		int temp[] = {0,0,0,0,0,0,0,0};
	        moves_used = temp;
		draw(size,x,y,x+move_x[min_move],y+move_y[min_move],++move_count);
	        x = x+move_x[min_move];
		y = y+move_y[min_move];
	        board[x][y] = move_count;
	    	((StackDisplay)stackPanel).push(stackPanel.getGraphics(),x,y,min_move);
	    }
	}
        finnished = true;
    }
    
    private class warnsdorfMove extends KnightsMove
    {
	public int moves_used[];
	public warnsdorfMove(int x, int y, int[] moves, int move)
	{
	    super(x,y,move);
	    moves_used = new int[8];
	    for(int i=0; i<8; i++)
	    {
		if(moves[i] != 0)
		    moves_used[i] = 1;
	    }
	}
    }
}
