//////////////////////////////////////
//Jalo (Jackal Class)
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

public class Jackal extends JPanel
{
	public final int BLOCK_WIDTH=8;
	public final int BLOCK_HEIGHT=6;

	Weapon weapon;
	int EXP_YIELD=36;
	boolean active=false;
	String direction="S";
	Point location=new Point(0,0);
	int health=50;
	int actionDelay=0;
	AudioClip normal, die;
	Image jackalW,jackalN,jackalE,jackalS;
	Image jackalWS,jackalNS,jackalES,jackalSS;
	Image jackaldead;

	public Jackal(String wpn, int x, int y, boolean boo)
	{
		weapon=new Weapon(wpn,0);
		location.x=x;
		location.y=y;
		direction="S";
		active=boo;

		URL JackalU=Jalo.class.getResource("Sprites/Jackal/jackalwest.gif");
    	jackalW=Toolkit.getDefaultToolkit().getImage(JackalU);

    	JackalU=Jalo.class.getResource("Sprites/Jackal/jackalnorth.gif");
    	jackalN=Toolkit.getDefaultToolkit().getImage(JackalU);

    	JackalU=Jalo.class.getResource("Sprites/Jackal/jackaleast.gif");
    	jackalE=Toolkit.getDefaultToolkit().getImage(JackalU);

    	JackalU=Jalo.class.getResource("Sprites/Jackal/jackalsouth.gif");
    	jackalS=Toolkit.getDefaultToolkit().getImage(JackalU);

    	JackalU=Jalo.class.getResource("Sprites/Jackal/jackalshootwest.gif");
    	jackalWS=Toolkit.getDefaultToolkit().getImage(JackalU);

    	JackalU=Jalo.class.getResource("Sprites/Jackal/jackalshootnorth.gif");
    	jackalNS=Toolkit.getDefaultToolkit().getImage(JackalU);

    	JackalU=Jalo.class.getResource("Sprites/Jackal/jackalshooteast.gif");
    	jackalES=Toolkit.getDefaultToolkit().getImage(JackalU);

    	JackalU=Jalo.class.getResource("Sprites/Jackal/jackalshootsouth.gif");
    	jackalSS=Toolkit.getDefaultToolkit().getImage(JackalU);

    	JackalU=Jalo.class.getResource("Sprites/Jackal/jackaldead.gif");
    	jackaldead=Toolkit.getDefaultToolkit().getImage(JackalU);

    	URL soundU=Jalo.class.getResource("SE/Characters/jackalspawn.wav");
    	normal=JApplet.newAudioClip(soundU);

    	soundU=Jalo.class.getResource("SE/Characters/jackaldie.wav");
    	die=JApplet.newAudioClip(soundU);
	}

	public void runAI(int x, int y)
	{

		if(x>location.x)
		{
			if(Math.abs(y-location.y)<=10)
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
			if(Math.abs(y-location.y)<=10)
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
			if(Math.abs(x-location.x)<=10)
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
			if(Math.abs(x-location.x)<=10)
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

	public void drawJackal(Graphics g)
	{
		if(!active)
			g.drawImage(jackaldead,location.x,location.y,this);
		else if(direction.equals("N"))
		{
			if(weapon.isShooting())
				g.drawImage(jackalNS,location.x,location.y,this);
			else
				g.drawImage(jackalN,location.x,location.y,this);
		}
		else if(direction.equals("S"))
		{
			if(weapon.isShooting())
				g.drawImage(jackalSS,location.x,location.y,this);
			else
				g.drawImage(jackalS,location.x,location.y,this);
		}
		else if(direction.equals("E"))
		{
			if(weapon.isShooting())
				g.drawImage(jackalES,location.x,location.y,this);
			else
				g.drawImage(jackalE,location.x,location.y,this);
		}
		else if(direction.equals("W"))
		{
			if(weapon.isShooting())
				g.drawImage(jackalWS,location.x,location.y,this);
			else
				g.drawImage(jackalW,location.x,location.y,this);
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