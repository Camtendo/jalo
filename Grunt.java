//////////////////////////////////////
//Jalo (Grunt Class)
//
//By Cameron Crockrom (Camtendo)
//
//

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.applet.AudioClip;

public class Grunt extends JPanel
{
	public final int BLOCK_WIDTH=8;
	public final int BLOCK_HEIGHT=6;

	Weapon weapon;
	int EXP_YIELD=10;
	boolean active=false;
	String direction="S";
	Point location=new Point(0,0);
	int health=35;
	int actionDelay=0;
	AudioClip normal, die;
	Image gruntW,gruntN,gruntE,gruntS;
	Image gruntWS,gruntNS,gruntES,gruntSS;
	Image gruntdead;

	public Grunt(String wpn, int x, int y, boolean boo)
	{
		weapon=new Weapon(wpn,0);
		location.x=x;
		location.y=y;
		direction="S";
		active=boo;

		URL gruntU=Jalo.class.getResource("Sprites/Grunt/gruntwest.gif");
    	gruntW=Toolkit.getDefaultToolkit().getImage(gruntU);

    	gruntU=Jalo.class.getResource("Sprites/Grunt/gruntnorth.gif");
    	gruntN=Toolkit.getDefaultToolkit().getImage(gruntU);

    	gruntU=Jalo.class.getResource("Sprites/Grunt/grunteast.gif");
    	gruntE=Toolkit.getDefaultToolkit().getImage(gruntU);

    	gruntU=Jalo.class.getResource("Sprites/Grunt/gruntsouth.gif");
    	gruntS=Toolkit.getDefaultToolkit().getImage(gruntU);

    	gruntU=Jalo.class.getResource("Sprites/Grunt/gruntshootwest.gif");
    	gruntWS=Toolkit.getDefaultToolkit().getImage(gruntU);

    	gruntU=Jalo.class.getResource("Sprites/Grunt/gruntshootnorth.gif");
    	gruntNS=Toolkit.getDefaultToolkit().getImage(gruntU);

    	gruntU=Jalo.class.getResource("Sprites/Grunt/gruntshooteast.gif");
    	gruntES=Toolkit.getDefaultToolkit().getImage(gruntU);

    	gruntU=Jalo.class.getResource("Sprites/Grunt/gruntshootsouth.gif");
    	gruntSS=Toolkit.getDefaultToolkit().getImage(gruntU);

    	gruntU=Jalo.class.getResource("Sprites/Grunt/gruntdead.gif");
    	gruntdead=Toolkit.getDefaultToolkit().getImage(gruntU);

    	URL soundU=Jalo.class.getResource("SE/Characters/gruntspawn.wav");
    	normal=JApplet.newAudioClip(soundU);

    	soundU=Jalo.class.getResource("SE/Characters/gruntdie.wav");
    	die=JApplet.newAudioClip(soundU);
	}

	public void runAI()
	{
		int randy=(int)(Math.random()*10);

		if(randy==0)
		{
			move("N");
			normal.play();
		}
		else if(randy==1)
		{
			move("S");
		}
		else if(randy==2)
		{
			move("E");
		}
		else if(randy==3)
		{
			move("W");
		}
		else if(randy>=4&&!weapon.shooting)
		{
			setEnemyProjCoordinates(location,direction);
			weapon.enemyFire();
		}
	}

	public void move(String dir)
	{
		direction=dir;

		if(dir.equals("N")&&location.y>50+BLOCK_HEIGHT)
			location.y-=BLOCK_HEIGHT;
		else if(dir.equals("S")&&location.y<450-BLOCK_HEIGHT)
			location.y+=BLOCK_HEIGHT;
		else if(dir.equals("E")&&location.x<800-3*BLOCK_WIDTH)
			location.x+=BLOCK_WIDTH;
		else if(dir.equals("W")&&location.x>0+BLOCK_HEIGHT)
			location.x-=BLOCK_WIDTH;
	}

	public void drawGrunt(Graphics g)
	{
		if(!active)
			g.drawImage(gruntdead,location.x,location.y,this);
		else if(direction.equals("N"))
		{
			if(weapon.isShooting())
				g.drawImage(gruntNS,location.x,location.y,this);
			else
				g.drawImage(gruntN,location.x,location.y,this);
		}
		else if(direction.equals("S"))
		{
			if(weapon.isShooting())
				g.drawImage(gruntSS,location.x,location.y,this);
			else
				g.drawImage(gruntS,location.x,location.y,this);
		}
		else if(direction.equals("E"))
		{
			if(weapon.isShooting())
				g.drawImage(gruntES,location.x,location.y,this);
			else
				g.drawImage(gruntE,location.x,location.y,this);
		}
		else if(direction.equals("W"))
		{
			if(weapon.isShooting())
				g.drawImage(gruntWS,location.x,location.y,this);
			else
				g.drawImage(gruntW,location.x,location.y,this);
		}
	}

	public void takeDamage(int i)
	{
		health-=i;
		if(health<=0)
		{
			active=false;
			weapon.shooting=false;
			die.play();
		}
	}

	public void setEnemyProjCoordinates(Point p, String str)
	{
		weapon.projectile.x=p.x;
		weapon.projectile.y=p.y;
		weapon.projDir=str;
	}
}