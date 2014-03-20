//////////////////////////////////////
//Jalo (Weapon Class)
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

public class Weapon extends JPanel
{
	Point projectile=new Point(500,500);
	String projDir="N";
	Color bulletColor;
	int strength;
	int ammoInClip,clipSize,currentAmmo,maxAmmo;
	int fireRate;
	int reloadTime;
	int reloadInt;
	int bulletSize;
	String wpnName;
	AudioClip shotSound;
	boolean shooting=false,reloading=false;
	
	public Weapon(String str,int i)
	{
		wpnName=str;
		declareWeapon();
		if(i>0)
			currentAmmo=i;
		else
			currentAmmo=maxAmmo;
	}
	
	public void declareWeapon()
	{
		bulletColor=Color.YELLOW;
		
		if(wpnName.equals("Magnum"))
		{
			strength=17;
			fireRate=7;
			maxAmmo=40;
			clipSize=8;
			ammoInClip=8;
			reloadTime=100;
			bulletSize=20;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/magnum.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("DMR"))
		{
			strength=25;
			fireRate=7;
			maxAmmo=60;
			clipSize=15;
			ammoInClip=15;
			reloadTime=200;
			bulletSize=20;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/dmr.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Shotgun"))
		{
			strength=100;
			fireRate=2;
			maxAmmo=24;
			clipSize=6;
			ammoInClip=6;
			reloadTime=500;
			bulletSize=40;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/shotgun.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Sniper"))
		{
			strength=120;
			fireRate=10;
			maxAmmo=20;
			clipSize=4;
			ammoInClip=4;
			reloadTime=150;
			bulletSize=10;
			bulletColor=Color.WHITE;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/sniper.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Plasma Pistol"))
		{
			strength=10;
			fireRate=4;
			maxAmmo=0;
			clipSize=100;
			ammoInClip=100;
			reloadTime=15;
			bulletSize=10;
			bulletColor=Color.GREEN;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/plasmapistol.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Carbine"))
		{
			strength=18;
			fireRate=7;
			maxAmmo=72;
			clipSize=18;
			ammoInClip=18;
			reloadTime=200;
			bulletSize=20;
			bulletColor=Color.GREEN;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/carbine.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Plasma Rifle"))
		{
			strength=15;
			fireRate=8;
			maxAmmo=100;
			clipSize=50;
			ammoInClip=50;
			reloadTime=15;
			bulletSize=15;
			bulletColor=Color.BLUE;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/plasmarifle.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Needler"))
		{
			strength=12;
			fireRate=12;
			maxAmmo=90;
			clipSize=30;
			ammoInClip=30;
			reloadTime=50;
			bulletSize=12;
			bulletColor=Color.MAGENTA;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/needler.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Brute Shot"))
		{
			strength=35;
			fireRate=9;
			maxAmmo=30;
			clipSize=6;
			ammoInClip=6;
			reloadTime=200;
			bulletSize=18;
			bulletColor=Color.LIGHT_GRAY;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/bruteshot.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("G. Launcher"))
		{
			strength=50;
			fireRate=7;
			maxAmmo=15;
			clipSize=1;
			ammoInClip=1;
			reloadTime=120;
			bulletSize=22;
			bulletColor=Color.GRAY;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/grenadelauncher.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Mauler"))
		{
			strength=70;
			fireRate=7;
			maxAmmo=25;
			clipSize=5;
			ammoInClip=5;
			reloadTime=100;
			bulletSize=15;
			bulletColor=Color.YELLOW;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/mauler.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Rockets"))
		{
			strength=150;
			fireRate=6;
			maxAmmo=10;
			clipSize=2;
			ammoInClip=2;
			reloadTime=350;
			bulletSize=35;
			bulletColor=Color.ORANGE;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/rocketlauncher.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Fuel Rod"))
		{
			strength=75;
			fireRate=3;
			maxAmmo=30;
			clipSize=5;
			ammoInClip=5;
			reloadTime=85;
			bulletSize=50;
			bulletColor=Color.GREEN;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/fuelrod.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Spartan Laser"))
		{
			strength=500;
			fireRate=10;
			maxAmmo=5;
			clipSize=1;
			ammoInClip=1;
			reloadTime=500;
			bulletSize=80;
			bulletColor=Color.RED;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/spartanlaser.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
		else if(wpnName.equals("Scarab Gun"))
		{
			strength=Integer.MAX_VALUE;
			fireRate=1;
			maxAmmo=0;
			clipSize=100;
			ammoInClip=100;
			reloadTime=250;
			bulletSize=200;
			bulletColor=Color.CYAN;
			
			URL shotU=Jalo.class.getResource("SE/Weapons/scarabgun.wav");
    			shotSound=JApplet.newAudioClip(shotU);
		}
	}
	
	public void drawProjectile(Graphics g)
	{
		g.fillOval(projectile.x,projectile.y,bulletSize,bulletSize);
	}
	
	public void bulletTravel()
	{
		if(projDir.equals("N"))
		{
			projectile.y-=fireRate;
		}
		else if(projDir.equals("S"))
		{
			projectile.y+=fireRate;
		}
		else if(projDir.equals("E"))
		{
			projectile.x+=fireRate;
		}
		else if(projDir.equals("W"))
		{
			projectile.x-=fireRate;
		}
	}
	
	public void fire()
	{
		if(ammoInClip>0&&!shooting&&!reloading)
		{
			shooting=true;
			ammoInClip--;
			shotSound.play();
			if(ammoInClip==0)
				reload();	
		}
		else
			reload();
	}
	
	public void enemyFire()
	{
		if(!shooting)
		{
			shooting=true;
			shotSound.play();
		}
	}
	
	public void reload()
	{
		if(!shooting&&!reloading&&currentAmmo>0&&ammoInClip<clipSize)
		{
			if(currentAmmo>=clipSize)
			{
				reloading=true;
				currentAmmo-=(clipSize-ammoInClip);
				ammoInClip=clipSize;	
			}
			else
			{
				reloading=true;
				ammoInClip=currentAmmo;
				currentAmmo=0;
			}
		}		
	}
	
	public int getReloadTime()
	{
		return reloadTime;
	}
	
	public boolean isShooting()
	{
		return shooting;
	}
	
	public void checkProjectile()
	{
		if(projectile.x>785||projectile.x<0||projectile.y>600||projectile.y<0)
			shooting=false;
	}
	
	public boolean isReloading()
	{
		return reloading;
	}
}