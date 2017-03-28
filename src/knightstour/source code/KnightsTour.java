/*
* William Clayton
*/
package knightstour;

import java.util.concurrent.TimeUnit;

public class KnightsTour 
{
    public boolean finnished = true, paused = false;
    protected volatile boolean running = true;
    protected BoardDisplay display;
    protected StackDisplay stackPanel;
    protected int time = 500;
    
    public KnightsTour(BoardDisplay display, StackDisplay stackPanel, int time)
    {
        this.display = display;
        this.stackPanel = stackPanel;
    }
    
    public void tour(int size, int x, int y)
    {}
    
    public void Terminate()
    {
        running = false;
    }
    
    public void run()
    {
        running = true;
    }
    
    public void updateTime(int time)
    {
        this.time = time;
    }
    
    protected void draw(int size, int oldx, int oldy, int newx, int newy, int move_count)
    {
        ((BoardDisplay)display).paintComponent(display.getGraphics(),size,newx,newy);
        ((BoardDisplay)display).numberedSquare(display.getGraphics(),size,oldx,oldy,move_count-1);
        
        delay(time);
    }
    
    protected void redraw(int size, int oldx, int oldy, int newx, int newy)
    {
        
        ((BoardDisplay)display).paintOverPrevious(display.getGraphics(), size, newx, newy);
        ((BoardDisplay)display).paintComponent(display.getGraphics(),size,newx,newy);
        ((BoardDisplay)display).paintOverPrevious(display.getGraphics(), size, oldx, oldy);
        delay(time);
    }
    
    protected void delay(int durration)
    {
        try
        {
            TimeUnit.MILLISECONDS.sleep(durration);
        } catch (InterruptedException e) {}
    }
    
    protected class KnightsMove
    {
        public int x,y,move;
        public KnightsMove(int x,int y,int move)
        {
            this.x=x;
            this.y=y;
            this.move=move;
        }
    }
    
    public void pause()
    {
        while(paused)
            delay(10);
    }
}
