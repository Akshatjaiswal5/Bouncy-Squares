import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class BouncySquare extends JFrame
{
 int h,w;
 float x,y,a,b,tempX,tempY,r,g,bl;
 boolean selected;
 Canvas c;

 private void askToPoint()
 {
  setUndecorated(true);
  setSize(330,50);
  setLocation(((w+100)/2)-150,((h+100)/2)-25);
  JLabel label= new JLabel("Click anywhere to start");
  label.setFont(new Font("",Font.PLAIN,30));
  add(label);
  setVisible(true);
  addFocusListener(new FocusAdapter(){
   public void focusLost(FocusEvent e)
   {
    if(selected)
    return;

    x=MouseInfo.getPointerInfo().getLocation().x;
    y=MouseInfo.getPointerInfo().getLocation().y;

    if(x>w) x=w;
    if(y>h) y=h;

    if(x>=w/2&&y>=h/2){ a=x-1; b=y-1; }
    else if(x<w/2&&y>=h/2){ a=x+1; b=y-1; }
    else if(x>=w/2&&y<h/2){ a=x-1; b=y+1; }
    else if(x<w/2&&y<h/2){ a=x+1; b=y+1; }

    remove(label);
    BouncySquare.this.selected=true;
    BouncySquare.this.addFocusListener(null);
   }
  });

  while(true)
  {
   System.out.print("");
   if(selected)
   break;
  }

 }
 private void changeColor()
 {
  r*=255; r+=37; r=r%255; r/=256;
  g*=255; g+=57; g=g%255; g/=256;
  bl*=255; bl+=137; bl=bl%255; bl/=256;


  if(a>x)
  a++;
  else
  x++;

  if(b>y)
  b++;
  else
  y++;

  BouncySquare.this.c.setBackground(new Color(r,g,bl));
 }
 private void loop()
 {
  while(true)
  {
   try
   {
    java.util.concurrent.TimeUnit.MILLISECONDS.sleep(1);
   }
   catch(InterruptedException e)
   {
    break;
   }

   tempX=2*x-a;
   tempY=2*y-b;

   if(tempX>=w||tempX<=0)
   {
    changeColor();
    tempX=a;
   }
   if(tempY>=h||tempY<=0)
   {
    changeColor();
    tempY=b;
   }

   a=x;
   b=y;
   x=tempX;
   y=tempY;

   setLocation((int)x,(int)y);
  }
 }
 public BouncySquare()
 {
  Robot robot=null;
  try{
   robot = new Robot();
  }
  catch(Exception e)
  {}
  robot.keyPress(KeyEvent.VK_WINDOWS);
  robot.keyPress(KeyEvent.VK_D);
  robot.keyRelease(KeyEvent.VK_D);
  robot.keyRelease(KeyEvent.VK_WINDOWS);

  Rectangle d= GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
  h=(int)d.getHeight()-100;
  w=(int)d.getWidth()-100;

  r=1;g=0;b=0;

  askToPoint();

  c= new Canvas();
  c.setBackground(new Color(r,g,bl));
  add(c);
  c.addMouseListener(new MouseAdapter()
  {
   public void mouseEntered(MouseEvent e)
   {
    float mouseX=MouseInfo.getPointerInfo().getLocation().x;
    float mouseY=MouseInfo.getPointerInfo().getLocation().y;
    if(mouseX>x&&a<x||mouseX<=x+10&&a>x)
    {
     float g=x;
     x=a;
     a=g;
    }

    if(mouseY>y&&b<y||mouseY<y+10&&b>y)
    {
     float g=y;
     y=b;
     b=g;
    }
    c.requestFocus();
   }
  });

  setSize(100,100);
  setLocation((int)x,(int)y);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  loop();
 }
}
class Main 
{
 public static void main(String[] args)
 {
  BouncySquare k= new BouncySquare();
  k.setVisible(false);
 }
}