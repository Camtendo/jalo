//////////////////////////////////////
//Jalo (Elite Class)
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

public class Elite extends JPanel
{
	public final int BLOCK_WIDTH=4;
	public final int BLOCK_HEIGHT=3;

	Weapon weapon;
	int EXP_YIELD;
	boolean active=false;
	boolean meleeing=false;
	String direction="S";
	Point location=new Point(0,0);
	int health;
	int actionDelay=0;
	AudioClip normal, die;
	Image eliteW,eliteN,eliteE,eliteS;
	Image eliteWS,eliteNS,eliteES,eliteSS;
	Image elitedead;

	public Elite(String wpn, String type, int x, int y, boolean boo)
	{
		weapon=new Weapon(wpn,0);
		location.x=x;
		location.y=y;
		direction="S";
		active=boo;

		if(type.equals("Minor"))
		{
			health=75;
			EXP_YIELD=50;

			URL eliteU=Jalo.class.getResource("Sprites/Elite/minorelitewest.png");
	    	eliteW=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/minorelitenorth.png");
	    	eliteN=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/minoreliteeast.png");
	    	eliteE=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/minorelitesouth.png");
	    	eliteS=Toolkit.getDefaultToolkit().getImage(eliteU);
		}
		else if(type.equals("Major"))
		{
			health=150;
			EXP_YIELD=100;

			URL eliteU=Jalo.class.getResource("Sprites/Elite/majorelitewest.png");
	    	eliteW=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/majorelitenorth.png");
	    	eliteN=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/majoreliteeast.png");
	    	eliteE=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/majorelitesouth.png");
	    	eliteS=Toolkit.getDefaultToolkit().getImage(eliteU);
		}
		else if(type.equals("Ultra"))
		{
			health=225;
			EXP_YIELD=250;

			URL eliteU=Jalo.class.getResource("Sprites/Elite/ultraelitewest.png");
	    	eliteW=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/ultraelitenorth.png");
	    	eliteN=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/ultraeliteeast.png");
	    	eliteE=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/ultraelitesouth.png");
	    	eliteS=Toolkit.getDefaultToolkit().getImage(eliteU);
		}
		else if(type.equals("Spec Ops"))
		{
			health=300;
			EXP_YIELD=500;

			URL eliteU=Jalo.class.getResource("Sprites/Elite/specopselitewest.png");
	    	eliteW=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/specopselitenorth.png");
	    	eliteN=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/specopseliteeast.png");
	    	eliteE=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/specopselitesouth.png");
	    	eliteS=Toolkit.getDefaultToolkit().getImage(eliteU);
		}
		else if(type.equals("Zealot"))
		{
			health=500;
			EXP_YIELD=1000;

			URL eliteU=Jalo.class.getResource("Sprites/Elite/zealotelitewest.png");
	    	eliteW=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/zealotelitenorth.png");
	    	eliteN=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/zealoteliteeast.png");
	    	eliteE=Toolkit.getDefaultToolkit().getImage(eliteU);

	    	eliteU=Jalo.class.getResource("Sprites/Elite/zealotelitesouth.png");
	    	eliteS=Toolkit.getDefaultToolkit().getImage(eliteU);
		}

		URL eliteU=Jalo.class.getResource("Sprites/Elite/elitedead.gif");
	    elitedead=Toolkit.getDefaultToolkit().getImage(eliteU);

    	URL soundU=Jalo.class.getResource("SE/Characters/elitespawn.wav");
	    normal=JApplet.newAudioClip(soundU);

    	soundU=Jalo.class.getResource("SE/Characters/elitedie.wav");
    	die=JApplet.newAudioClip(soundU);
	}

	public void runAI(String str, int x, int y)
	{
		if(x>location.x)
		{
			if(Math.abs(y-location.y)<=weapon.bulletSize)
			{
				direction="E";
				if(!weapon.shooting)
				{
					setEnemyProjCoordinates(location,direction);
					weapon.enemyFire();
				}
			}
			else
			{
				move("E");
			}
		}
		if(x<location.x)
		{
			if(Math.abs(y-location.y)<=weapon.bulletSize)
			{
				direction="W";
				if(!weapon.shooting)
				{
					setEnemyProjCoordinates(location,direction);
					weapon.enemyFire();
				}
			}
			else
			{
				move("W");
			}
		}
		if(y>location.y)
		{
			if(Math.abs(x-location.x)<=weapon.bulletSize)
			{
				direction="S";
				if(!weapon.shooting)
				{
					setEnemyProjCoordinates(location,direction);
					weapon.enemyFire();
				}
			}
			else
			{
				move("S");
			}
		}
		if(y<location.y)
		{
			if(Math.abs(x-location.x)<=weapon.bulletSize)
			{
				direction="N";
				if(!weapon.shooting)
				{
					setEnemyProjCoordinates(location,direction);
					weapon.enemyFire();
				}
			}
			else
			{
				move("N");
			}
		}
	}

	public void melee()
	{
		meleeing=true;
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

	public void drawElite(Graphics g)
	{
		if(!active)
		{
			g.drawImage(elitedead,location.x,location.y,this);
		}
		else if(direction.equals("N"))
		{
			g.drawImage(eliteN,location.x,location.y,this);
		}
		else if(direction.equals("S"))
		{
			g.drawImage(eliteS,location.x,location.y,this);
		}
		else if(direction.equals("E"))
		{
			g.drawImage(eliteE,location.x,location.y,this);
		}
		else if(direction.equals("W"))
		{
			g.drawImage(eliteW,location.x,location.y,this);
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