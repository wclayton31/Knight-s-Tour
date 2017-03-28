/*
* William Clayton
*/
package knightstour;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class BoardDisplay extends JPanel
{
    private BufferedImage blackKnight, whiteKnight;
    private int previous_x=0, previous_y=0;
    private int SIZE = 5;
    
    public BoardDisplay()
    {
        try 
        {
            blackKnight = ImageIO.read(new File("src/knightstour/BlackKnight.jpg"));
            whiteKnight = ImageIO.read(new File("src/knightstour/WhiteKnight.png"));
        } catch (IOException e) {System.out.println("File not found");}
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        paintComponent(g,SIZE); 
    }
    
    public void paintComponent(Graphics g, int size)
    {
        if(getWidth()%size != 0)
        {
            SIZE = size;
            int new_panel_size = ((getWidth()/size)+1)*size;
            if(new_panel_size - size >= 400)
                new_panel_size = (getWidth()/size)*size;
                
            setSize(new_panel_size,new_panel_size);
        }
        
        int width = getWidth(), height = getHeight();
        int squareSize = width/size;
        double squareSized = width/(double)size;
        
        if(squareSized - squareSize >= .5)
            squareSize++;
        
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++)
            {
                if((i+j)%2 == 0)
                    g.setColor(Color.BLACK);
                else
                    g.setColor(Color.WHITE);
                g.fillRect(i*squareSize,j*squareSize,squareSize,squareSize);
            }
        
        g.setColor(Color.BLACK);
        g.drawLine(0,0,width,0);
    	g.drawLine(0,height-1, width, height-1);
    	g.drawLine(0,0,0,height);
    	g.drawLine(width-1,0,width-1,height);
        g.drawImage(whiteKnight,0,0,getWidth()/size,(getHeight()-1)/size, null);
        cleanUp(g,size);
        previous_x = 0;
        previous_y = 0;
    }
    
    public void paintOverPrevious(Graphics g, int size, int x, int y)
    {
        int squareSize = getWidth()/size;
        double squareSized = getWidth()/(double)size;
        if(squareSized - squareSize >= .5)
            squareSize++;
        
        if((x+y)%2 == 0)
            g.setColor(Color.BLACK);
        else
            g.setColor(Color.WHITE);
        
        g.fillRect(x*squareSize,y*squareSize,squareSize,squareSize);
                
        g.setColor(Color.BLACK);
        g.drawLine(0,0,getWidth(),0);
    	g.drawLine(0,getHeight()-1, getWidth(), getHeight()-1);
    	g.drawLine(0,0,0,getHeight());
    	g.drawLine(getWidth()-1,0,getWidth()-1,getHeight());
        cleanUp(g,size);
    }
    
    public void paintComponent(Graphics g, int size, int x, int y)
    {
        paintOverPrevious(g,size,previous_x,previous_y);
        if((x+y)%2 == 0)
            g.drawImage(whiteKnight,x*getWidth()/size,y*getHeight()/size,
                    getWidth()/size,(getHeight()-2)/size, null);
        else
            g.drawImage(blackKnight,x*getWidth()/size,y*getHeight()/size,
                    getWidth()/size,(getHeight()-2)/size, null);
        previous_x = x;
        previous_y = y;
    }
    
    public void cleanUp(Graphics g, int size)
    {
        int squareSize = getWidth()/size;
        double squareSized = getWidth()/(double)size;
        if(squareSized - squareSize >= .5)
            squareSize++;
        g.setColor(Color.WHITE);
        g.fillRect(0, (size)*squareSize, getWidth(), getHeight());
        g.fillRect((size)*squareSize, 0, getWidth(), getHeight());
    }
    
    public void numberedSquare(Graphics g, int size, int x, int y, int number, Color color)
    {
        int scale[] =    {50, 45, 40, 35, 30, 25, 20, 18, 15, 14, 14, 14,  14,  14,  14,  14};
        int offset_x[] = {26, 24, 21, 19, 17, 15, 12, 12, 10,  8,  8,  8,  8,  7,  6,  4};
        int offset_y[] = {55, 50, 42, 38, 33, 29, 25, 22, 22, 19, 19, 18, 18, 15, 15, 15};
        
        g.setFont(new Font("Ubuntu",0,scale[size-5]));
        g.setColor(color);
        g.drawString(number+"",offset_x[size-5] + (x*getWidth()/size),offset_y[size-5] + (y*getHeight()/size));
    }
    
    public void numberedSquare(Graphics g, int size, int x, int y, int number)
    {                    //5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20
        int scale[] =    {50, 45, 40, 35, 30, 25, 18, 16, 14, 12, 11, 10,  9,  9,  9,  9};
        int offset_x[] = {13,  8,  7,  6,  6,  6,  3,  3,  3,  2,  3,  2,  2,  2,  1,  0};
        int offset_y[] = {55, 50, 42, 38, 33, 29, 25, 22, 20, 18, 17, 16, 15, 15, 15, 15};
        String number_string;
        
        if(size*size > 100)
            number_string = number>=100?number+"":number>=10?"0"+number:"00"+number;
        else
            number_string = number>=10?number+"":"0"+number;
        
        g.setFont(new Font("Ubuntu",0,scale[size-5]));
        if((x+y)%2 == 0)
            g.setColor(Color.WHITE);
        else
            g.setColor(Color.BLACK);
        
        g.drawString(number_string,offset_x[size-5] + (x*getWidth()/size),offset_y[size-5] + (y*getHeight()/size));
    }
    
    /*public void number(Graphics g) //Debug
    {
        paintComponent(g,5); 
        g.setFont(new Font("Ubuntu",0,15));     
        g.setColor(Color.YELLOW);
        g.drawString("001", 0, 22);     // X: 13, Y: 55 for size: 5  50
    }    //*/                            //x: 8, y: 50 for size: 6   45 
}                                      //x: 7, y: 42 for size: 7    40
                                       //x: 6, y: 38 for size: 8    35
                                       //x: 6, y: 33 for size: 9    30
                                       //x: 6, y: 29 for size: 10   25
                                       //x: 2, y: 25 for size: 11   18
                                       //x: 2, y: 22 for size: 12   16
                                       //x: 2, y: 15 for size: 20
    

