import java.awt.*;
import java.applet.*;
 
public class MyHouse extends Applet
{
    public void paint (Graphics g)
    {
        background(g);
        house (g);
        roof1 (g);
        roof2 (g);
        windows (g);
        framing (g);
        setSize(700, 500);
    }
     
    public void background(Graphics g) 
    {
        setBackground (new Color(60,200,100));   //sky  
        g.setColor (new Color(225,140,0));   //sun
        g.fillOval (650,035,120,120);
        g.setColor (new Color(175,140,0));   //sun
        g.fillOval (650,035,110,110);
        g.setColor (new Color(175,120,0));   //sun
        g.fillOval (650,035,100,100);
        g.setColor (new Color(0,100,0));   //grass
        g.fillRect (000,370,800,800);
        g.fillOval (400,300,500,300);
        g.fillOval (150,320,500,300);
        g.fillOval (000,280,500,300);
        g.fillOval (000,320,300,110);   
    }
     
    public void house (Graphics g)
    {
        g.setColor (new Color(139,69,19));   //house and garage
        g.fillRect (100,250,400,200);
        g.fillRect (499,320,200,130);
        g.setColor(new Color(190,190,190));   //doors and chimney    
        g.fillRect (160,150,60,90);
        g.fillRect (245,380,110,70);
        g.fillRect (508,350,180,100);
        g.setColor (new Color(186,134,11));   //door knobs
        g.fillOval (282,412,10,10);
        g.fillOval (307,412,10,10);
         
    }
     
    public void roof1 (Graphics g)
    {
        g.setColor(new Color(190,190,190));   //house roof
        int x[] = {98,300,501};
        int y[] = {250,130,250};
        g.fillPolygon(x,y,3);
        
        
    }
     
    public void roof2 (Graphics g)
    {
        g.setColor (new Color(190,190,190));   //garage roof
        int x[] = {499,499,700};
        int y[] = {320,249,320};
        g.fillPolygon(x,y,3);
    
    }
     
     
    public void windows (Graphics g)
    {
        g.setColor (new Color(186,134,11));   //outer frame effect
        g.setColor (new Color(175,238,238));   //windows
        g.fillRect (125,265,70,70);
        g.fillRect (125,365,70,70);
        g.fillRect (405,265,70,70);
        g.fillRect (405,365,70,70);
        g.fillRect (245,265,110,70);
        g.fillRect (525,353,70,25);
        g.fillRect (610,353,70,25);
    }
     
    public void framing (Graphics g)
    {
        g.setColor (new Color(139,69,19));   //garage and door sections
        g.fillRect (298,380,2,70);
        g.fillRect (508,382,180,2);
        g.fillRect (508,417,180,2);
        g.setColor (new Color(186,134,11));   //inner frame effect
        g.fillRect (157,265,5,70);
        g.fillRect (157,365,5,70);
        g.fillRect (437,265,5,70);
        g.fillRect (438,365,5,70);
        g.fillRect (297,265,5,70);
        g.fillRect (125,298,70,5);
        g.fillRect (125,398,70,5);
        g.fillRect (405,298,70,5);
        g.fillRect (405,398,70,5);
        g.fillRect (245,298,110,5);
        g.fillRect (245,375,110,5);   //door and garage frame
        g.fillRect (240,375,5,75);
        g.fillRect (352,375,5,75);
        g.fillRect (508,345,180,5);
        g.fillRect (503,345,5,105);
        g.fillRect (688,345,5,105);
    }
     

}