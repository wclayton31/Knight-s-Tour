/*
* William Clayton
*/
package knightstour;

import java.util.*;
import javax.swing.*;

public class KnightsTourBruteForce extends KnightsTour
{   
    public KnightsTourBruteForce(BoardDisplay display,StackDisplay stackPanel, int time)
    {
        super(display,stackPanel,time);
        this.stackPanel = stackPanel;
    }
    
    @Override
    public void tour(int size, int start_x, int start_y)
    {
        finnished = false;
        Deque<KnightsMove> stack = new ArrayDeque<KnightsMove>(); //Java built in stack
        int board[][] = new int[size][size];
        for(int i=0; i<size; i++)               //initialized board to -1 
            for(int j=0; j<size; j++)
                board[i][j] = -1;
        board[start_x][start_y] = 1;
        stack.push(new KnightsMove(start_x,start_y,-1));
        ((StackDisplay)stackPanel).setSize(size);
        ((StackDisplay)stackPanel).push(stackPanel.getGraphics(),start_x,start_y,-1);
        
        int move_count = 1, x = start_x, y = start_y, move = 0;
        int move_x[] = {1,2,2,1,-1,-2,-2,-1}, move_y[] = {-2,-1,1,2,2,1,-1,-2};
        ((BoardDisplay)display).paintComponent(display.getGraphics(),size,x,y); //draw the first position
        
        delay(500); //pause for .5 seconds
        
        while(move_count < size*size && running)
        {
            if(paused)
                pause();
            boolean placed = false;
            for(; move<move_x.length;move++)
            {   
                if( 0 <= (x+move_x[move]) && (x+move_x[move]) < size && 
                    0 <= (y+move_y[move]) && (y+move_y[move]) < size &&
                    board[x+move_x[move]][y+move_y[move]] == -1)
                {
                    placed = true;
                    board[x+move_x[move]][y+move_y[move]] = ++move_count;
                    stack.push(new KnightsMove(x+move_x[move],y+move_y[move],move));
                    ((StackDisplay)stackPanel).push(stackPanel.getGraphics(),x+move_x[move],y+move_y[move],move);
                    draw(size,x,y,x+move_x[move],y+move_y[move],move_count);
                    x = x+move_x[move];
                    y = y+move_y[move];
                    move = 0;
                    break;
                }
            }
            if(!placed)
            {
                KnightsMove prev_move = stack.pop();
                board[prev_move.x][prev_move.y] = -1;
                move_count--;
                move = prev_move.move;
                move++;
                x = stack.peek().x;
                y = stack.peek().y;
                redraw(size,prev_move.x,prev_move.y,x,y);
                ((StackDisplay)stackPanel).pop(stackPanel.getGraphics());
            }
           
        }
        finnished = true;
        if(!running)
            ((StackDisplay)stackPanel).clearAll(stackPanel.getGraphics());
        /*System.out.println("Finished"); Debug
        while(stack.peek()!=null)
        {
            KnightsMove move1 = stack.pop();
            System.out.println((move1.y+1) + "  " + (move1.x+1) + "  " + (move1.move+1));
        }
        for(int i=0; i<size;i++)
        {
            for(int j=0;j<size;j++)
                System.out.print(board[j][i] + "\t");
            System.out.println();
        }*/
    }
    
    public JTextArea stackTextbox;
}
