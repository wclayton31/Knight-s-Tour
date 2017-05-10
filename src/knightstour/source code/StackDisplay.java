/*
* William Clayton
*/

import javax.swing.*;
import java.awt.*;
import java.util.Deque;
import java.text.*;
import java.util.ArrayDeque;

public class StackDisplay extends JPanel
{   
    private int size =5;
    private Deque<KnightsMove> stack;
    
    private int left_x, right_x, top_y, bottom_y;
    private int move_number = 1, offset_y = -4, offset_x = 6;
    private int scale;
    private DecimalFormat fmt;
 
    public StackDisplay()
    {
        stack = new ArrayDeque<KnightsMove>();
    }
    
    public void setSize(int size)
    {
        this.size = size;
        stack = new ArrayDeque<KnightsMove>();
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        left_x = getWidth()/4;
        right_x = getWidth()/4 + 80;
        top_y = 5;
        bottom_y = getHeight() - 5;
        
        offset_y = -4;
        move_number = 1;
        
        g.setColor(Color.BLACK);                //draws the stacks boarder
        g.fillRect(left_x, top_y, 2, bottom_y - top_y);  //left line
        g.fillRect(left_x, bottom_y, 82, 2);    //bottom line
        g.fillRect(right_x, top_y, 2, bottom_y - top_y); //right line
    }
    
    public void push(Graphics g, int x, int y, int move)
    {
        scale = size*size<100?14:10;
        if(size*size < 100)
            fmt = new DecimalFormat("00");
        else
            fmt = new DecimalFormat("000");
        if(bottom_y+offset_y <= 10)
            move_down(g);
        
        String str;
        str = fmt.format(move_number)+","+fmt.format(x)+","+fmt.format(y)+","+move;
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Ubuntu",0,scale));
        g.drawString(str, left_x+offset_x, bottom_y+offset_y);
        offset_y-=15;
        move_number++;
        stack.push(new KnightsMove(x,y,move));
    }
    
    public void pop(Graphics g)
    {
        stack.pop();
        if(offset_y + (move_number*15) > bottom_y)
            move_up(g);
        else
        {
            g.setColor(getBackground());
            offset_y += 15;
            g.fillRect(left_x+2,0,78,bottom_y+offset_y+2);
        }
        move_number--;
    }
    
    public void move_up(Graphics g)
    {
        clearStack(g);
        int temp = offset_y;
        KnightsMove array[] = new KnightsMove[stack.size()];
        array = stack.toArray(array);
        int count = 2;
        for(int i=0; i<stack.size(); i++,count++)
        {
            offset_y += 15;
            if(offset_y <= -4)
            {
                KnightsMove move = array[i];
                String str = fmt.format(move_number-count)+"," +fmt.format(move.x)+","+fmt.format(move.y)+","+move.move;
                
                g.setColor(Color.BLACK);
                g.setFont(new Font("Ubuntu",0,scale));
                g.drawString(str, left_x+offset_x, bottom_y+offset_y);
            }
        }
        offset_y = temp;
    }
    
    public void move_down(Graphics g)
    {
        clearStack(g);
        offset_y += 15;
        int temp = offset_y;
        KnightsMove array[] = new KnightsMove[stack.size()];
        array = stack.toArray(array);
        int count = 1;
        for(int i=0; i<stack.size();i++,count++)
        {
            offset_y += 15;
            if(offset_y <= -4)
            {
                KnightsMove move = array[i];
                String str = fmt.format(move_number-count)+"," +fmt.format(move.x)+","+fmt.format(move.y)+","+move.move;  
                
                g.setColor(Color.BLACK);
                g.setFont(new Font("Ubuntu",0,scale));
                g.drawString(str, left_x+offset_x, bottom_y+offset_y);
            }
        }
        offset_y = temp;
    }
        
    public void clearStack(Graphics g)
    {
        g.setColor(this.getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);                //draws the stacks boarder
        g.fillRect(left_x, top_y, 2, bottom_y - top_y);  //left line
        g.fillRect(left_x, bottom_y, 82, 2);    //bottom line
        g.fillRect(right_x, top_y, 2, bottom_y - top_y); //right line
        //offset_y = -4;
        //paintComponent(g);
    }
    
    public void clearAll(Graphics g)
    {
        g.setColor(this.getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.fillRect(right_x, top_y, 2, bottom_y - top_y); //right line
        move_number = 1;
        offset_y = -4;
        paintComponent(g);
    }
    
    private class KnightsMove
    {
        public int x,y,move;
        public KnightsMove(int x,int y,int move)
        {
            this.x=x;
            this.y=y;
            this.move=move;
        }
    }
}
