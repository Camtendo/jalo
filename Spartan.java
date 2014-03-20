//////////////////////////////////////
//Jalo (Spartan Class)
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

public class Spartan extends JPanel
{
	public final int BLOCK_WIDTH=2;
	public final int BLOCK_HEIGHT=2;

	Weapon weapon;
	String direction="N";
	int health=25,shields=100;
	int maxHealth=25;
	Image spartanW,spartanN,spartanE,spartanS;
	Image spartanWS,spartanNS,spartanES,spartanSS;
	Image spartandead;
	Image fist;
	int rechargeInt=0;
	Point location=new Point(0,0);
	boolean meleeing=false;
	int meleeInt=0;
	boolean alive=true, recharging=false;
	int overshields[]=new int[4];
	public enum Overshield{NONE,x1,x2,x3,x4}
	Overshield overshield=Overshield.NONE;
	AudioClip noshields,hurt,die,meleeSound;
	String sprite="Spartan";

	public Spartan(String wpn,int w,int x,int y)
	{
		health=25; shields=100;
		maxHealth=25;
		weapon=new Weapon(wpn,w);
		alive=true;
		location.x=x;
		location.y=y;
		direction="N";
		overshields[0]=0;
		overshields[1]=0;
		overshields[2]=0;
		overshields[3]=0;

		URL spartanU=Jalo.class.getResource("Sprites/Spartan/spartanwest.png");
    	spartanW=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartannorth.png");
    	spartanN=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartaneast.png");
    	spartanE=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartansouth.png");
    	spartanS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartanshootwest.png");
    	spartanWS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartanshootnorth.png");
    	spartanNS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartanshooteast.png");
    	spartanES=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartanshootsouth.png");
    	spartanSS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartandead.gif");
    	spartandead=Toolkit.getDefaultToolkit().getImage(spartanU);

    	//spartanU=Jalo.class.getResource("Sprites/Spartan/fist.png");
    	//fist=Toolkit.getDefaultToolkit().getImage(spartanU);

    	URL nsU=Jalo.class.getResource("SE/Characters/beep-07.wav");
    	noshields=JApplet.newAudioClip(nsU);

    	nsU=Jalo.class.getResource("SE/Characters/spartanhurt.wav");
    	hurt=JApplet.newAudioClip(nsU);

    	nsU=Jalo.class.getResource("SE/Characters/spartandie.wav");
    	die=JApplet.newAudioClip(nsU);

    	nsU=Jalo.class.getResource("SE/Characters/punch.wav");
    	meleeSound=JApplet.newAudioClip(nsU);
	}
	
	public void changeSprite()
	{
		if(sprite.equals("Spartan"))
		{
			URL spartanU=Jalo.class.getResource("Sprites/Spartan/spartanwest.png");
    	spartanW=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartannorth.png");
    	spartanN=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartaneast.png");
    	spartanE=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartansouth.png");
    	spartanS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartanshootwest.png");
    	spartanWS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartanshootnorth.png");
    	spartanNS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartanshooteast.png");
    	spartanES=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartanshootsouth.png");
    	spartanSS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Spartan/spartandead.gif");
    	spartandead=Toolkit.getDefaultToolkit().getImage(spartanU);
		}
		else if(sprite.equals("Grunt"))
		{
			URL spartanU=Jalo.class.getResource("Sprites/Grunt/gruntwest.gif");
    	spartanW=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Grunt/gruntnorth.gif");
    	spartanN=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Grunt/grunteast.gif");
    	spartanE=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Grunt/gruntsouth.gif");
    	spartanS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Grunt/gruntshootwest.gif");
    	spartanWS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Grunt/gruntshootnorth.gif");
    	spartanNS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Grunt/gruntshooteast.gif");
    	spartanES=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Grunt/gruntshootsouth.gif");
    	spartanSS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Grunt/gruntdead.gif");
    	spartandead=Toolkit.getDefaultToolkit().getImage(spartanU);
		}
		else if(sprite.equals("Jackal"))
		{
			URL spartanU=Jalo.class.getResource("Sprites/Jackal/jackalwest.gif");
    	spartanW=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Jackal/jackalnorth.gif");
    	spartanN=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Jackal/jackaleast.gif");
    	spartanE=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Jackal/jackalsouth.gif");
    	spartanS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Jackal/jackalshootwest.gif");
    	spartanWS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Jackal/jackalshootnorth.gif");
    	spartanNS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Jackal/jackalshooteast.gif");
    	spartanES=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Jackal/jackalshootsouth.gif");
    	spartanSS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Jackal/jackaldead.gif");
    	spartandead=Toolkit.getDefaultToolkit().getImage(spartanU);
		}
		else if(sprite.equals("Elite"))
		{
			URL spartanU=Jalo.class.getResource("Sprites/Elite/zealotelitewest.png");
    	spartanW=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Elite/zealotelitenorth.png");
    	spartanN=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Elite/zealoteliteeast.png");
    	spartanE=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Elite/zealotelitesouth.png");
    	spartanS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Elite/zealotelitewest.png");
    	spartanWS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Elite/zealotelitenorth.png");
    	spartanNS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Elite/zealoteliteeast.png");
    	spartanES=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Elite/zealotelitesouth.png");
    	spartanSS=Toolkit.getDefaultToolkit().getImage(spartanU);

    	spartanU=Jalo.class.getResource("Sprites/Elite/elitedead.gif");
    	spartandead=Toolkit.getDefaultToolkit().getImage(spartanU);
		}
	}

	public void move(String dir)
	{
		direction=dir;

		if(dir.equals("N")&&location.y>50+BLOCK_HEIGHT)
			location.y-=BLOCK_HEIGHT;
		else if(dir.equals("S")&&location.y<445)
			location.y+=BLOCK_HEIGHT;
		else if(dir.equals("E")&&location.x<800-10*BLOCK_WIDTH)
			location.x+=BLOCK_WIDTH;
		else if(dir.equals("W")&&location.x>0+BLOCK_HEIGHT)
			location.x-=BLOCK_WIDTH;
	}

	public void melee()
	{
		meleeing=true;
		//meleeSound.play();
	}

	public void recharge()
	{
		recharging=true;
	}

	public void drawSpartan(Graphics g)
	{
		if(!alive)
		{
			g.drawImage(spartandead,location.x,location.y,this);
		}
		else if(direction.equals("N"))
		{
			if(weapon.isShooting())
				g.drawImage(spartanNS,location.x,location.y,this);
			else
				g.drawImage(spartanN,location.x,location.y,this);
		}
		else if(direction.equals("S"))
		{
			if(weapon.isShooting())
				g.drawImage(spartanSS,location.x,location.y,this);
			else
				g.drawImage(spartanS,location.x,location.y,this);
		}
		else if(direction.equals("E"))
		{
			if(weapon.isShooting())
				g.drawImage(spartanES,location.x,location.y,this);
			else
				g.drawImage(spartanE,location.x,location.y,this);
		}
		else if(direction.equals("W"))
		{
			if(weapon.isShooting())
				g.drawImage(spartanWS,location.x,location.y,this);
			else
				g.drawImage(spartanW,location.x,location.y,this);
		}
		if(meleeInt>0)
			g.drawImage(fist,location.x-10,location.y-5,this);
	}

	public void respawn()
	{
		alive=true;
		shields=100;
		health=maxHealth;
		location.x=375;
		location.y=325;

		weapon.currentAmmo=weapon.maxAmmo;
	}

	public void mpRespawn(int i)
	{
		alive=true;
		shields=100;
		health=25;
		location.x=(int)(Math.random()*800);
		location.y=(int)(Math.random()*400)+50;

		weapon.currentAmmo=weapon.maxAmmo;
		if(i==1)
			weapon.currentAmmo=Integer.MAX_VALUE;
		if(i==2)
			weapon.strength=Integer.MAX_VALUE;
	}

	public void takeDamage(int i)
	{
		recharging=false;
		rechargeInt=0;


		if(overshields[3]>0)
		{
			if(i>overshields[3])
				overshields[3]=0;
			else
			overshields[3]-=i;
		}
		else if(overshields[2]>0)
		{
			if(i>overshields[2])
				overshields[2]=0;
			else
			overshields[2]-=i;
		}
		else if(overshields[1]>0)
		{
			if(i>overshields[1])
				overshields[1]=0;
			else
			overshields[1]-=i;
		}
		else if(overshields[0]>0)
		{
			if(i>overshields[0])
				overshields[0]=0;
			else
			overshields[0]-=i;
		}
		else if(shields>0)
		{
			if(i>shields)
				shields=0;
			else
				shields-=i;
		}
		else
		{
			if(i>=health)
			{
				health=0;
				alive=false;
				die.play();
			}
			else
			{
				health-=i;
				hurt.play();
			}
		}
	}

	public void setProjCoordinates(Point p, String str)
	{
		weapon.projectile.x=p.x;
		weapon.projectile.y=p.y;
		weapon.projDir=str;
	}
}