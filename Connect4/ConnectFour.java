/**
 * @(#)ConnectFour.java
 *
 *
 * @author 
 * @version 1.00 2016/5/26
 */

import java.applet.Applet;
import java.awt.*;

public class ConnectFour extends Applet {
  
  int [][] playVals ;    //0 ==> Empty
              //1 ==> yellow
              //2 ==> Red
  Rectangle [][] playSpace;
  Rectangle restart;
  int turn;
  int winner;
  int currntC;
  int currntR;
  boolean animate;
  int tied;
  public void init()
  {
    tied = 0;
    animate  = true;
    currntC = -1;
    currntR = -1;
    restart = new Rectangle(700,270,140,50);
    winner = 0;
    playVals = new int[7][6];
    playSpace = new Rectangle[7][6];
    turn = 1;
    int xPos=0;
    int yPos;
    for(int c = 0;c<7;c++ ) //each column c
    {
      xPos+=80;
      yPos = 100;
      for(int r = 0; r<6;r++) //for each row r
      {
        playSpace [c][r] = new Rectangle(xPos-35,yPos-35,70,70);
        yPos+=80;
        
      }
    }
    
  }
    
  public void paint(Graphics g)
  {
    
    Expo.setFont(g,"Arial",Font.BOLD,20);
    Expo.setColor(g,Expo.blue);
    Expo.fillRectangle(g,30,55,615,550);
    int xPos=0;
    int yPos;
    Expo.setColor(g,Expo.white);
    for(int c = 0;c<7;c++ ) //each column c
    {
      xPos+=80;
      yPos = 100;
      for(int r = 0; r<6;r++) //for each row r
      {
        if(playVals[c][r] == 1){Expo.setColor(g,Expo.yellow);}
        else if(playVals[c][r] == 2){Expo.setColor(g,Expo.red);}
        else{Expo.setColor(g,Expo.white);}
        if(currntC == c && currntR == r)
        {
          Expo.setColor(g,Expo.white);
        }
        Expo.fillCircle(g,xPos,yPos,35);
        yPos+=80;
        
      }
      
    } 
    xPos = 0;
    
    for(int c = 0;c<7;c++ ) //each column c
    {
      xPos+=80;
      yPos = 100;
      for(int r = 0; r<6;r++) //for each row r
      {   
        if(playVals[c][r] == 1)
        {
          Expo.setColor(g,Expo.yellow);
          if(currntC == c && currntR == r && r!=0  && animate)
          {
            dropChip(g,Expo.yellow,playSpace[c][r].x+35,playSpace[c][r].y+35);
          }
          
        }
        else if(playVals[c][r] == 2)
        {
          Expo.setColor(g,Expo.red);
          if(currntC == c && currntR == r && r!=0  && animate)
          {  
            dropChip(g,Expo.red,playSpace[c][r].x+35,playSpace[c][r].y+35);
          }  
          
        }
        else{Expo.setColor(g,Expo.white);}
        
        Expo.fillCircle(g,xPos,yPos,35);
        yPos+=80;
        
      }
      
    }
    
    
    if(winner!=0)
    {
      Expo.setColor(g,Expo.blue);
      Expo.fillRectangle(g,700,270,840,320);
      Expo.setColor(g,Expo.white);
      Expo.drawString(g,"PLAY AGAIN?",705,303);
      if(winner == 1)
      {
        Expo.setColor(g,Expo.darkYellow);
        Expo.drawString(g,"YELLOW WINS" ,690,250);
      }
      else if(winner == 2)
      {
        Expo.setColor(g,Expo.darkRed);
        Expo.drawString(g,"RED WINS" ,690,250);
      }
    }
    else
    {
      tied = 0;
      for(int c = 0; c<7;c++)
      {
        for(int r = 0; r<6;r++)
        {
          if(playVals[c][r]!=0)
          {
            tied++;
          }
        }
      }
      if(tied == 42)
      {
        Expo.setColor(g,Expo.blue);
        Expo.fillRectangle(g,700,270,840,320);
        Expo.setColor(g,Expo.white);
        Expo.drawString(g,"PLAY AGAIN?",705,303);
        Expo.setColor(g,Expo.blue);
        Expo.drawString(g,"STALEMATE" ,690,250); 
      }
      
    }
  }
    
  public boolean mouseDown(Event e,int x,int y)
  {
    animate = false;
    if(winner == 0)
    {
      for(int c = 0; c<7;c++)
      {
        for(int r = 0; r<6;r++)
        {
          if(playSpace[c][r].inside(x,y) )
          {
            animate = true;
            for(int h = 5; h>=0;h--)
            {
              if(playVals[c][h]==0)
              {
                currntC = c;
                currntR = h;
                playVals[c][h]=turn;
                check4(c,h,turn);
                nextTurn();
                break;
              }
              
            }
            
          }
          
          
        }
      }
    }
    else 
    {
      
      if(restart.inside(x,y)){init();}
    } // end of if-else
    if(tied == 42)
    {
    	if(restart.inside(x,y)){init();}
    }
    repaint();
    return true;    
  }
    
  public void nextTurn()
  {
    if(turn == 1){turn = 2;}
    else if(turn == 2){turn = 1;}  
  }
    
  public void dropChip(Graphics g,Color c,int x,int y)
  {
    int yPos = 20;
    int xPos = x;
    
    while (yPos<y)
    {
      yPos+=80;
      Expo.delay(50);
      Expo.setColor(g,Expo.white);
      Expo.fillCircle(g,xPos,yPos-80,35); 
      Expo.setColor(g,c);
      Expo.fillCircle(g,xPos,yPos,35);
      
    }
    
  }
    
  public void check4(int yourC, int yourR, int player)
  {
    //HORIZONTAL 
    if(yourC >= 1 && yourC <=4)
    {
      if(player == playVals[yourC-1][yourR] && player == playVals[yourC+1][yourR] && player == playVals[yourC+2][yourR]){winner = player;}
    }
    if(yourC >= 2 && yourC <=5)
    {
      if(player == playVals[yourC+1][yourR] && player == playVals[yourC-1][yourR] && player == playVals[yourC-2][yourR]){winner = player;}
    }
    if(yourC < 4)
    {
      if(player == playVals[yourC+1][yourR] && player == playVals[yourC+2][yourR] && player == playVals[yourC+3][yourR]){winner = player;}
    }
    if(yourC > 2)
    {
      if(player == playVals[yourC-1][yourR] && player == playVals[yourC-2][yourR] && player == playVals[yourC-3][yourR]){winner = player;}
    }
    
    //VERTICLE
    if(yourR < 3)
    {
      if(player == playVals[yourC][yourR+1] && player == playVals[yourC][yourR+2] && player == playVals[yourC][yourR+3]){winner = player;}
    }
    
    
    //DIAGONAL
    if((yourC >= 1 && yourC <=4) && (yourR >= 1 && yourR <= 3))
    {
      if(player == playVals[yourC-1][yourR-1] && player == playVals[yourC+1][yourR+1] && player == playVals[yourC+2][yourR+2]){winner = player;}
    }
    if((yourC >= 2 && yourC <=5) && (yourR >= 1 && yourR <= 3))
    {
      if(player == playVals[yourC+1][yourR-1] && player == playVals[yourC-1][yourR+1] && player == playVals[yourC-2][yourR+2]){winner = player;}
    }
    
    if((yourC >= 1 && yourC <=4) && (yourR >= 2 && yourR <= 4))
    {
      if(player == playVals[yourC-1][yourR+1] && player == playVals[yourC+1][yourR-1] && player == playVals[yourC+2][yourR-2]){winner = player;}
    }
    if((yourC >= 2 && yourC <=5) && (yourR >= 2 && yourR <= 4))
    {
      if(player == playVals[yourC+1][yourR+1] && player == playVals[yourC-1][yourR-1] && player == playVals[yourC-2][yourR-2]){winner = player;}
    }
    
    if((yourC < 4) && (yourR < 3))
    {
      if(player == playVals[yourC+1][yourR+1] && player == playVals[yourC+2][yourR+2] && player == playVals[yourC+3][yourR+3]){winner = player;}
    }
    if((yourC > 2) && (yourR < 3))
    {
      if(player == playVals[yourC-1][yourR+1] && player == playVals[yourC-2][yourR+2] && player == playVals[yourC-3][yourR+3]){winner = player;}
    }
    
    if((yourC < 4) && (yourR > 2))
    {
      if(player == playVals[yourC+1][yourR-1] && player == playVals[yourC+2][yourR-2] && player == playVals[yourC+3][yourR-3]){winner = player;}
    }
    if((yourC > 2) && (yourR > 2))
    {
      if(player == playVals[yourC-1][yourR-1] && player == playVals[yourC-2][yourR-2] && player == playVals[yourC-3][yourR-3]){winner = player;}
    }
    
    
    
  }
    
}