//////////////////////////////////////
//Jalo
//
//By Cameron Crockrom (Camtendo)
//
//
//

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.applet.AudioClip;

public final class Jalo extends JPanel implements Runnable,MouseListener,MouseMotionListener,KeyListener
{
	File f=new File("Jalo.sav");
	public final int BLOCK_WIDTH=1;
	public final int BLOCK_HEIGHT=1;
	boolean keys[]=new boolean[4];
	int kills,deaths,shotsFired,shotsHit,gamesPlayed;
	int weaponUsage[]=new int[14];
	int highscores[]=new int [6];
	int score=0;
	int spree=0;
	int multikill=0;
	int bestSpree=0;
	int creditInt=0;
	Image medal;
	boolean watchedCredits=false;

	int achievements[]=new int [49];
	String achievementNames[]=new String[49];
	int achievementInt=0;

	int challenges[]=new int[25];
	String challengeNames[]=new String[25];
	int reloadsThisGame,shieldRechargesThisGame,consecNoDmgWaves,
		gruntsKilledThisGame,jackalsKilledThisGame,elitesKilledThisGame;
	int hudcolorR=0,hudcolorG=0,hudcolorB=255;

	int armoryInt[]=new int[8];
	int armoryIntIndex;
	String armoryItemNames[][]=new String[8][5];

	//User Vars
	String gamertag="YourGamertag";

	//Networking Vars
	public enum NetType{HOST,CLIENT}
	NetType net=NetType.HOST;
	File hostf=new File("ConnectToHost.txt");
	File gtf=new File("YourGamertag.txt");
	Spartan spartans[]=new Spartan[4];
	String mpGamertags[]=new String[4];
	String mpWeapons[]=new String[4];
	String mpRanks[]=new String[4];
	int mpLevels[]=new int[4];
	int mpHighestLevel[]=new int[4];
	int mpExp[]=new int[4];

	boolean mpConnected[]=new boolean[3];
	boolean mpTryingToConnect=false;
	boolean mpSent=false;
	int mpTotalPlayers=2;
	String hostIP="";
	int hostPort;
	int mpID;
	String stuff="";

	Scanner input;
	ServerSocket ss[]=new ServerSocket[3];
	Socket socket[]=new Socket[4];
	InputStream sin[]=new InputStream[3];
	OutputStream sout[]=new OutputStream[3];
	DataInputStream in[]=new DataInputStream[3];
	DataOutputStream out[]=new DataOutputStream[3];
	InetAddress ipAddress;

		//Security Vars
		String levelCode="5F18";
		int expCode=50000000;
		int unlockCode=13;

	String specialGTs[]=new String[10];
	boolean specialGT=false;

	String mpGametype[]=new String[3];
	int mpGametypeInt=0;
	int mpCurrentKills,mpCurrentDeaths;
	int mpKills,mpDeaths,mpShotsFired,mpShotsHit,mpGamesPlayed;
	int mpSpree, mpBestSpree;
	Image mpRankImg[]=new Image[4];
	Image mpImage;

	int set=1,round=1,wave=1;
	int lives;
	double multiplier=1.0;
	String difStr[]=new String[4];
	int difInt=1;

	int gruntSpawn;
	Grunt grunts[]=new Grunt[15];

	int eliteSpawn;
	Elite elites[]=new Elite[10];

	int jackalSpawn;
	Jackal jackals[]=new Jackal[10];

	String mainMessage="";
	int mainMessageInt=0;
	String unlockedMessage="";
	int unlockedMessageInt=0;
	boolean oneEnemyLeft=false;

	String wpnChoice[]=new String[14];
	int wpnChoiceInt=0;
	Weapon preview;
	int unlockInt=1;

	Spartan spartan;
	int menuInt=250;
	int titleRunInt=250, titleRunInt2=0;
	Point cometPt=new Point(0,0);
	boolean cometFall=false;
	boolean gameover=false;
	Image texture;
	String exclusiveGametype="";
	int exp=0;
	double expToNext=0;
	int level=1, highestLevel=1;

	String rank="Recruit";
	Image rankImg;
	String mode="title";
	String gametype[]=new String[6];
	int gametypeInt=0;
	String map[]=new String[5];
	int mapInt=0;
	Point mouse=new Point(0,0);
	AudioClip titleMusic,bgMusic;
	AudioClip yes,no,back,toggle;
	Image titleImg,singleImg,multiplayerImg,statisticsImg;
	Font smallerMenuFont=new Font("Sanserif", Font.BOLD, 14);
	Font menuFont=new Font("LucidaSans", Font.BOLD, 24);
	Font headerFont=new Font("LucidaSans", Font.BOLD, 72);

	Thread thread;
	boolean gameLoaded=false;
	Image icon, cyborg, cyborg2,mercury,jupiter,comet,venus,saturn;
	Image logo;
	String fps="1";
	long lastFPS;
	int frames, slp;

	Point randStars[]=new Point[300];

	public static void main(String[] args)
	{
		Jalo j=new Jalo();
		j.setupGUI();
		j.dataManager();
		j.beginThread();
	}

	public void setupGUI()
	{
		URL iconU=Jalo.class.getResource("icon.png");
    	icon=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=Jalo.class.getResource("camtendo.png");
    	logo=Toolkit.getDefaultToolkit().getImage(iconU);

		JFrame jf=new JFrame("Jalo");
		Container cp=jf.getContentPane();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(800,600);
		jf.setLayout(new GridLayout(1,1));
		cp.add(this);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setIconImage(icon);
		jf.setVisible(true);
		jf.addKeyListener(this);
		jf.addMouseListener(this);
		jf.addMouseMotionListener(this);
	}

	public void dataManager()
	{
    	URL iconU=Jalo.class.getResource("Backgrounds/cyborg.gif");
    	cyborg=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=Jalo.class.getResource("Backgrounds/cyborg2.gif");
    	cyborg2=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=Jalo.class.getResource("Backgrounds/saturn.gif");
    	saturn=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=Jalo.class.getResource("Backgrounds/mercury.gif");
    	mercury=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=Jalo.class.getResource("Backgrounds/jupiter.gif");
    	jupiter=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=Jalo.class.getResource("Backgrounds/comet.gif");
    	comet=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=Jalo.class.getResource("Backgrounds/venus.gif");
    	venus=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=Jalo.class.getResource("Backgrounds/singleplayer.jpeg");
    	singleImg=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=Jalo.class.getResource("Backgrounds/multiplayer.png");
    	multiplayerImg=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=Jalo.class.getResource("Backgrounds/statistics.jpg");
    	statisticsImg=Toolkit.getDefaultToolkit().getImage(iconU);

    	for(int i=0; i<4; i++)
    	keys[i]=false;

    	for(int i=0; i<15; i++)
		grunts[i]=new Grunt("Plasma Pistol",0,0,false);

		for(int i=0; i<10; i++)
		elites[i]=new Elite("Plasma Pistol","Minor",0,0,false);

		for(int i=0; i<10; i++)
		jackals[i]=new Jackal("Plasma Pistol",0,0,false);

		for(int i=0; i<4; i++)
		spartans[i]=new Spartan("Plasma Pistol",0,0,0);

    	wpnChoice[0]="Plasma Pistol";
    	wpnChoice[1]="Magnum";
    	wpnChoice[2]="Locked: Apprentice";
    	wpnChoice[3]="Locked: Private";
    	wpnChoice[4]="Locked: Corporal";
    	wpnChoice[5]="Locked: Sergeant";
    	wpnChoice[6]="Locked: Gunnery Sgt.";
    	wpnChoice[7]="Locked: Lieutenant";
    	wpnChoice[8]="Locked: Captain";
    	wpnChoice[9]="Locked: Major";
    	wpnChoice[10]="Locked: Commander";
    	wpnChoice[11]="Locked: Colonel";
    	wpnChoice[12]="Locked: Brig. Gen.";
    	wpnChoice[13]="Locked: General";

		gametype[0]="Standard";
		gametype[1]="Infinity";
		gametype[2]="Gruntastrophe";
		gametype[3]="1337";
		gametype[4]="7th Ring of Hell";
		gametype[5]="Level Up!";

		map[0]="Grassroots";
		map[1]="Rockface";
		map[2]="Desertion";
		map[3]="Frostbite";
		map[4]="Halo";

		difStr[0]="Noob";
		difStr[1]="Spartan";
		difStr[2]="Juggernaut";
		difStr[3]="1337-gendary";

		achievementNames[0]="I'm so 1337";
		achievementNames[1]="I Need a Weapon";
		achievementNames[2]="BFG";
		achievementNames[3]="Officer Spartan";
		achievementNames[4]="Newbie";
		achievementNames[5]="Nay, it was Heresy!";
		achievementNames[6]="Colt .45";
		achievementNames[7]="Orpheus";
		achievementNames[8]="Perfection";
		achievementNames[9]="Pawnch!";
		achievementNames[10]="Sharpshooter";
		achievementNames[11]="Round...Over";
		achievementNames[12]="I am the Juggernaut";
		achievementNames[13]="Splash Damage";
		achievementNames[14]="Versatility";
		achievementNames[15]="Killtacular";
		achievementNames[16]="Practice Makes Perfect";
		achievementNames[17]="I Love Jalo";
		achievementNames[18]="King of the Hill";
		achievementNames[19]="Playa playa";
		achievementNames[20]="C'mon, really?";
		achievementNames[21]="MLG";
		achievementNames[22]="Shoop da Woop";
		achievementNames[23]="Trigger Happy";
		achievementNames[24]="Score Attack";
		achievementNames[25]="Shotty 2 Hotty";
		achievementNames[26]="Noob Tube";
		achievementNames[27]="Elitist";
		achievementNames[28]="Metal Slug";
		achievementNames[29]="Small Arms";
		achievementNames[30]="Hunter";
		achievementNames[31]="They're too Strong!";
		achievementNames[32]="Gruntpocalypse";
		achievementNames[33]="No Grenades?!";
		achievementNames[34]="Rampage";
		
		achievementNames[35]="Double Trouble";
		achievementNames[36]="Are You Kidding?";
		achievementNames[37]="Climb the Mountain";
		achievementNames[38]="Boom! Headshot!";
		achievementNames[39]="Grunt Soup";
		achievementNames[40]="Jackal-ing off";
		achievementNames[41]="2 1337 4 U";
		achievementNames[42]="LSD";
		achievementNames[43]="The Skin You're In";
		achievementNames[44]="I Can't See!";
		achievementNames[45]="Eyes on the Prize";
		achievementNames[46]="Who is Camtendo?";
		achievementNames[47]="Hardly a Challenge";
		achievementNames[48]="Hyper Lethal";

		challengeNames[0]="Call of Jalo I";
		challengeNames[1]="Call of Jalo II";

		challengeNames[2]="Handy Dandy I";
		challengeNames[3]="Handy Dandy II";
		challengeNames[4]="Handy Dandy III";

		challengeNames[5]="Rambo I";
		challengeNames[6]="Rambo II";
		challengeNames[7]="Rambo III";

		challengeNames[8]="Juggernaut I";
		challengeNames[9]="Juggernaut II";
		challengeNames[10]="Juggernaut III";

		challengeNames[11]="Space Man I";
		challengeNames[12]="Space Man II";
		challengeNames[13]="Space Man III";
		challengeNames[14]="Space Man IV";

		challengeNames[15]="Crossdresser I";
		challengeNames[16]="Crossdresser II";
		challengeNames[17]="Crossdresser III";

		challengeNames[18]="Heads Up I";
		challengeNames[19]="Heads Up II";
		challengeNames[20]="Heads Up III";
		challengeNames[21]="Heads Up IV";

		challengeNames[22]="U for Uranium I";
		challengeNames[23]="U for Uranium II";
		challengeNames[24]="U for Uranium III";

		armoryItemNames[0][0]="None";
		armoryItemNames[0][1]="Locked";
		armoryItemNames[0][2]="Locked";
		armoryItemNames[0][3]="Locked";
		armoryItemNames[0][4]="";
		armoryItemNames[1][0]="1x";
		armoryItemNames[1][1]="Locked";
		armoryItemNames[1][2]="Locked";
		armoryItemNames[1][3]="Locked";
		armoryItemNames[1][4]="";
		armoryItemNames[2][0]="0";
		armoryItemNames[2][1]="Locked";
		armoryItemNames[2][2]="Locked";
		armoryItemNames[2][3]="Locked";
		armoryItemNames[2][4]="";
		armoryItemNames[3][0]="25";
		armoryItemNames[3][1]="Locked";
		armoryItemNames[3][2]="Locked";
		armoryItemNames[3][3]="Locked";
		armoryItemNames[3][4]="";
		armoryItemNames[4][0]="None";
		armoryItemNames[4][1]="Locked";
		armoryItemNames[4][2]="Locked";
		armoryItemNames[4][3]="Locked";
		armoryItemNames[4][4]="Locked";
		armoryItemNames[5][0]="Spartan";
		armoryItemNames[5][1]="Locked";
		armoryItemNames[5][2]="Locked";
		armoryItemNames[5][3]="Locked";
		armoryItemNames[5][4]="";
		armoryItemNames[6][0]="Blue";
		armoryItemNames[6][1]="Locked";
		armoryItemNames[6][2]="Locked";
		armoryItemNames[6][3]="Locked";
		armoryItemNames[6][4]="Locked";
		armoryItemNames[7][0]="Normal";
		armoryItemNames[7][1]="Locked";
		armoryItemNames[7][2]="Locked";
		armoryItemNames[7][3]="Locked";
		armoryItemNames[7][4]="";

		for(int i=0; i<8; i++)
			armoryInt[i]=0;

		for(int i=0; i<5; i++)
		highscores[i]=2500;

		highscores[5]=10000;

		URL introU=Jalo.class.getResource("Music/titlemusic.wav");
    	titleMusic=JApplet.newAudioClip(introU);

    	URL logoU=Jalo.class.getResource("title.png");
    	titleImg=Toolkit.getDefaultToolkit().getImage(logoU);

    	URL menuU=Jalo.class.getResource("SE/Menu/menu-ok.wav");
    	yes=JApplet.newAudioClip(menuU);

    	menuU=Jalo.class.getResource("SE/Menu/menu-no.wav");
    	no=JApplet.newAudioClip(menuU);

    	menuU=Jalo.class.getResource("SE/Menu/menu-toggle.wav");
    	toggle=JApplet.newAudioClip(menuU);

    	menuU=Jalo.class.getResource("SE/Menu/menu-back.wav");
    	back=JApplet.newAudioClip(menuU);

    	//Multiplayer Stuff
    	mpGametype[0]="Slayer";
		mpGametype[1]="Infinite Slayer";
		mpGametype[2]="One-Hit-KO";

		specialGTs[0]="Camtendo";
		specialGTs[1]="Walshy";
		specialGTs[2]="Eisermatic";
		specialGTs[3]="YummyJester";
		specialGTs[4]="ItzCloward";
		specialGTs[5]="kdog679";
		specialGTs[6]="Primetime";
		specialGTs[7]="Bungie";
		specialGTs[8]="Mr. Babb";
		specialGTs[9]="Master Chief";

		for(int i=0; i<4; i++)
			mpGamertags[i]="";
		for(int i=0; i<4; i++)
			mpWeapons[i]="";

		for(int i=0; i<300; i++)
		{
			randStars[i]=new Point(0,0);
			randStars[i].x=(int)(Math.random()*800);
			randStars[i].y=(int)(Math.random()*600);
		}

    	mpImage=Toolkit.getDefaultToolkit().getImage(iconU);

    	System.out.println("Data Loaded");
    	System.out.println("Calibrating Frame Rate...");

    	while(Integer.parseInt(fps)<80||Integer.parseInt(fps)>110)
    	{
    		gameLoaded=false;
    		repaint();
			frames++;
      		framerateManager();

      		try
      		{
      			Thread.sleep(slp);
      		}
      		catch(Exception e){}
    	}

    	gameLoaded=true;
    	System.out.println("Done.");
	}

	public void medalImageManager()
	{
		if(spree==10)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/killingspree.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(spree==20)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/killingfrenzy.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(spree==30)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/runningriot.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(spree==40)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/rampage.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(spree==50)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/untouchable.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(spree==75)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/invincible.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(spree==100)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/extermination.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(spree==500)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/perfection.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(multikill==2)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/doublekill.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(multikill==3)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/triplekill.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(multikill==4)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/overkill.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(multikill==5)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/killtacular.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(multikill==6)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/killtrocity.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(multikill>=7)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/killimanjaro.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
		}
		else if(spartan.meleeing&&multikill==1)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/melee.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
    		mainMessage="Beat Down!";
		}
		else if(multikill==1&&(wpnChoiceInt==1||wpnChoiceInt==3||wpnChoiceInt==5))
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/headshot.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
    		mainMessage="Headshot!";
		}
		else if(wpnChoiceInt==4&&multikill==1)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/needler.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
    		mainMessage="Supercombine!";
		}
		else if(wpnChoiceInt==9&&multikill==1)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/shotgun.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
    		mainMessage="Shotgun!";
		}
		else if(wpnChoiceInt==10&&multikill==1)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/sniper.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
    		mainMessage="Sniped!";
		}
		else if(wpnChoiceInt==13&&multikill==1)
		{
			URL logoU=Jalo.class.getResource("Sprites/Medals/laser.png");
    		medal=Toolkit.getDefaultToolkit().getImage(logoU);
    		mainMessage="Lazer!";
		}

		else
			medal=null;
	}

	public void beginThread()
	{
		if (thread == null)
		{
      		thread = new Thread(this);
      		thread.start();
		}
	}

	public void	run()
	{
		titleMusic.play();
		saveFileManager("load");
		banhammer();
		saveFileManager("save");
		System.out.println("Starting Title Menu");
		stageManager();
		while(true)
		{
			if(mode.equals("mpBattle"))
				mpRunGame();
			else if(mode.equals("battle"))
				runGame();
			repaint();
			frames++;
      		framerateManager();

      		try
      		{
      			Thread.sleep(slp);
      		}
      		catch(Exception e){}
		}
	}

	public void framerateManager()
	{
		if( lastFPS <=System.currentTimeMillis() )
		{
			fps=""+frames;
			//System.out.println(fps);
			lastFPS = System.currentTimeMillis() + 1000;
			if(frames<80&&slp>0)
			{
				System.out.println("Low Frame Rate: "+fps);
				slp--;
			}
			else if(frames>110)
			{
				System.out.println("High Frame Rate: "+fps);
				slp++;
			}
			frames=0;
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.setFont(menuFont);

		if(mode.equals("title"))
		{
			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(),getHeight());
			g.setColor(Color.WHITE);
			if(!gameLoaded)
			{
				g.drawImage(cyborg,270,250,this);
				g.setColor(Color.WHITE);
				g.setFont(headerFont);
				if(frames%100<=25)
				g.drawString("Loading.",230,200);
				else if(frames%60<=50)
				g.drawString("Loading..",230,200);
				else
				g.drawString("Loading...",230,200);
			}
			else
			{
				for(int i=0; i<300; i++)
				{
					g.fillOval(randStars[i].x,randStars[i].y,4,4);
					if(titleRunInt%10==0)
						randStars[i].x--;
					if(randStars[i].x<0)
						randStars[i].x=800;
				}
				g.drawImage(jupiter,titleRunInt2+400,200,256,256,this);
				g.drawImage(saturn,titleRunInt2+800,200,this);
				g.drawImage(venus,titleRunInt2+50,200,this);
				g.drawImage(mercury,titleRunInt2+200,300,this);

				if(Math.abs(titleRunInt)%50>=10)
				g.drawImage(titleImg,130,50,this);
				titleRunInt-=2;
				if(titleRunInt%3==0)
				titleRunInt2--;
				if(titleRunInt<0)
				{
					titleRunInt=800;
					cometFall=true;
				}

				if(cometFall)
				{
					g.drawImage(comet,cometPt.x,cometPt.y,this);
					cometPt.x-=2;
					cometPt.y+=2;

					if(cometPt.x<0&&cometPt.y>600)
					{
						cometPt.x=(int)(Math.random()*400)+400;
						cometPt.y=-40;
						cometFall=false;
					}
				}

				if(titleRunInt2<-1300)
					titleRunInt2=800;

				g.setColor(Color.WHITE);
				g.setFont(menuFont);
				g.drawString("Start Game",310,250);
				g.drawString("The Armory",305,300);
				g.drawString("Statistics",315,350);
				g.drawString("Credits",325,400);
				g.drawString("Quit",340,450);

				g.drawImage(icon,250,menuInt-22,this);
				g.drawImage(icon,450,menuInt-22,this);
			}

			g.setColor(Color.WHITE);
			g.setFont(menuFont);
			if(gameLoaded)
			g.drawImage(logo,550,475,this);
		}
		else if(mode.equals("credits"))
		{
			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(),getHeight());
				
				g.setColor(Color.white);
				for(int i=0; i<300; i++)
				{
					g.fillOval(randStars[i].x,randStars[i].y,4,4);
					if(titleRunInt%10==0)
						randStars[i].x--;
					if(randStars[i].x<0)
						randStars[i].x=800;
				}
				g.drawImage(jupiter,titleRunInt2+400,200,256,256,this);
				g.drawImage(saturn,titleRunInt2+800,200,this);
				g.drawImage(venus,titleRunInt2+50,200,this);
				g.drawImage(mercury,titleRunInt2+200,300,this);
				
				titleRunInt-=2;
				if(titleRunInt%3==0)
				titleRunInt2--;
				if(titleRunInt<0)
				{
					titleRunInt=800;
					cometFall=true;
				}

				if(cometFall)
				{
					g.drawImage(comet,cometPt.x,cometPt.y,this);
					cometPt.x-=2;
					cometPt.y+=2;

					if(cometPt.x<0&&cometPt.y>600)
					{
						cometPt.x=(int)(Math.random()*400)+400;
						cometPt.y=-40;
						cometFall=false;
					}
				}

				if(titleRunInt2<-1300)
					titleRunInt2=800;
			
			g.setFont(smallerMenuFont);
			g.setColor(Color.WHITE);
			
			g.drawImage(logo,250,600-creditInt/3,this);
			g.drawString("Programmer/Director--------------Cameron Crockrom 'Camtendo'",150,800-creditInt/3);
			g.drawString("Mechanics Engineer---------------Trevor Jackson 'YummyJester'",150,900-creditInt/3);
			g.drawString("Achievement Design---------------Kyle Cloward 'ItzCloward'",150,1000-creditInt/3);
			g.drawString("Character Movement Theory--------Justin Begeman",150,1100-creditInt/3);
			g.drawString("Gameplay Consultants-------------Karl Smith and Sam Eiserman",150,1200-creditInt/3);
			g.drawString("Sprites---------------------------rambofox and XDarkeNX",150,1300-creditInt/3);
			g.drawString("Audio-----------------------------Buckethead, MW2, and Marty!",150,1400-creditInt/3);
			g.drawString("Special Thanks--------------------Mr. Babb",150,2000-creditInt/3);
			g.drawString("                                                  Mr. Babb Again",150,2050-creditInt/3);
			g.drawString("                                                  Bungie",150,2100-creditInt/3);
			g.drawString("                                                  Microsoft",150,2150-creditInt/3);
			g.drawImage(titleImg,130,2250-creditInt/3,this);
			
			creditInt++;
			
			if(creditInt==2200*3)
			{
				creditInt=0;
				mode="title";
			}
		}
		else if(mode.equals("single"))
		{
			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(),getHeight());
			g.drawImage(singleImg,0,0,800,600,this);

			g.setColor(Color.WHITE);
			g.setFont(headerFont);
			g.drawString("Firefight",25,75);

			g.setFont(smallerMenuFont);
			g.drawString("Gametype: <= "+gametype[gametypeInt]+" ("+highscores[gametypeInt]+") =>",50,450);
			g.drawString("Weapon: <="+wpnChoice[wpnChoiceInt]+" =>",50,470);
			if(gametypeInt==5)
				difInt=1;
			else
			g.drawString("Difficulty: <="+difStr[difInt]+" =>",50,490);
			g.drawString("Map: <="+map[mapInt]+" =>",50,510);
			g.drawString("Start Game",50,530);
			g.drawString("Back",50,550);
			g.setColor(Color.WHITE);
			g.fillRect(78,298,132,132);

			g.drawImage(texture,80,300,128,128,this);
			g.setColor(Color.MAGENTA);
			g.drawString("Bullet",125,315);

			if(wpnChoiceInt<=unlockInt)
			{
				g.setColor(preview.bulletColor);
				preview.projectile.x=144-preview.bulletSize/2;
				preview.projectile.y=364-preview.bulletSize/2;
				preview.drawProjectile(g);
			}

			g.drawImage(icon,15,menuInt-22,this);

			g.setColor(Color.RED);
			g.fillRect(500,200,225,40);
			g.setColor(Color.WHITE);

			for(int i=0; i<10; i++)
			{
				if(gamertag.equals(specialGTs[i]))
					specialGT=true;
			}
			if(specialGT)
				g.setColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
			specialGT=false;

			g.setFont(smallerMenuFont);
			g.drawString(gamertag,500,223);
			g.setFont(menuFont);
			g.drawString(""+level,615,225);
			g.drawImage(rankImg,690,200,30,40,this);
		}
		else if(mode.equals("armory"))
		{
			g.drawImage(multiplayerImg,0,0,800,600,this);
			g.setColor(Color.WHITE);
			g.setFont(headerFont);
			g.drawString("The Armory",25,75);

			g.setFont(smallerMenuFont);
			g.drawString("Weapon Attachments: <= "+armoryItemNames[0][armoryInt[0]]+" =>",50,300);
			g.drawString("Reload Speed: <="+armoryItemNames[1][armoryInt[1]]+" =>",50,320);
			g.drawString("Bandolier Clips: <="+armoryItemNames[2][armoryInt[2]]+" =>",50,340);
			g.drawString("Base Health: <="+armoryItemNames[3][armoryInt[3]]+" =>",50,360);
			g.drawString("Overshields: <="+armoryItemNames[4][armoryInt[4]]+" =>",50,380);
			g.drawString("Player Skin: <="+armoryItemNames[5][armoryInt[5]]+" =>",50,400);
			g.drawString("HUD Color: <="+armoryItemNames[6][armoryInt[6]]+" =>",50,420);
			g.drawString("Bonus Modes: <="+armoryItemNames[7][armoryInt[7]]+" =>",50,440);
			g.drawString("Back",50,460);

			g.setFont(menuFont);
			
			for(int i=0; i<10; i++)
			{
				if(gamertag.equals(specialGTs[i]))
					specialGT=true;
			}
			if(specialGT)
				g.setColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
			specialGT=false;
			
			g.drawString(gamertag,450,200);
			g.setColor(Color.white);
			g.drawString("Click to Change Gamertag",450,250);
			g.setColor(Color.RED);
			g.fillRect(550,300,100,100);
			g.setColor(Color.white);
			g.drawString("Click",570,355);

			g.drawImage(icon,15,menuInt-22,this);
		}
		else if(mode.equals("statistics"))
		{
			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(),getHeight());
			g.drawImage(statisticsImg,0,0,800,600,this);

			g.setColor(Color.WHITE);
			g.setFont(headerFont);
			g.drawString("Statistics",200,100);
			g.setFont(menuFont);
			g.drawString(gamertag,300,150);
			g.setFont(smallerMenuFont);
			g.drawString("Kills: "+kills+" Highest Spree: "+bestSpree,300,175);
			g.drawString("Deaths: "+deaths,300,200);
			g.drawString("Accuracy: "+(int)(100*(((double)(shotsHit))/shotsFired))+"%",300,225);
			g.drawString("Games: "+gamesPlayed,300,250);

			int temp=0;
			int choice=0;
			int sum=0;
			for(int i=0; i<14; i++)
			{
				if(weaponUsage[i]>temp)
				{
					temp=weaponUsage[i];
					choice=i;
				}
				sum+=weaponUsage[i];
			}

			g.drawString("Fave Wpn: "+wpnChoice[choice]+" "+(int)(100*(((double)(weaponUsage[choice]))/sum))+"%",300,275);

			g.drawString("Rank: "+rank,300,300);
			g.drawString("Exp: "+exp+" Level: "+level,300,325);
			if(achievements[achievementInt]==0)
				g.drawString("Achievements: "+"<="+(achievementInt+1)+". Locked=>",300,350);
			else
				g.drawString("Achievements: "+"<="+(achievementInt+1)+". "+achievementNames[achievementInt]+"=>",300,350);
			
			sum=0;
			for(int i=0; i<25; i++)
			{
				sum+=challenges[i];			
			}
			
			g.drawString("Challenges Completed: "+sum+"/"+"25",300,375);
			
			g.drawString("Back",350,450);

			g.drawImage(icon,300,450-22,this);
		}
		else if(mode.equals("battle"))
		{
			setBackground(Color.GRAY);
			
			if(armoryInt[7]==0||armoryInt[7]==2)
			{
				g.drawImage(texture,0,0,this);
				g.drawImage(texture,256,0,this);
				g.drawImage(texture,512,0,this);
				g.drawImage(texture,768,0,this);
				g.drawImage(texture,0,50,this);
				g.drawImage(texture,256,50,this);
				g.drawImage(texture,512,50,this);
				g.drawImage(texture,0,306,this);
				g.drawImage(texture,0,562,this);
				g.drawImage(texture,256,306,this);
				g.drawImage(texture,256,562,this);
				g.drawImage(texture,512,306,this);
				g.drawImage(texture,512,562,this);
				g.drawImage(texture,768,50,this);
				g.drawImage(texture,768,306,this);
				g.drawImage(texture,768,562,this);
			}
			else if(armoryInt[7]==1)
			{
				g.setColor(Color.black);
				g.fillRect(0,0,getWidth(),getHeight());
			}
			else if(armoryInt[7]==3)
			{
				g.setColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
				g.fillRect(0,0,getWidth(),getHeight());
			}

			//Grunts
			for(int i=0; i<gruntSpawn; i++)
			{
				if(exclusiveGametype.equals("Elites"))
				{
					if(grunts[i].active)
					{
						if(armoryInt[7]!=2)
						grunts[i].drawGrunt(g);
					}
				}
				else
				{
					if(armoryInt[7]!=2)
					grunts[i].drawGrunt(g);
				}

				if(grunts[i].weapon.isShooting())
				{
					g.setColor(grunts[i].weapon.bulletColor);
					if(armoryInt[7]==1)
						g.setColor(Color.white);

					grunts[i].weapon.drawProjectile(g);
				}
			}

			//Elites
			for(int i=0; i<eliteSpawn; i++)
			{
				if(exclusiveGametype.equals("Grunts"))
				{
					if(elites[i].active)
					{
						if(armoryInt[7]!=2)
						elites[i].drawElite(g);
					}
				}
				else
					if(armoryInt[7]!=2)
					elites[i].drawElite(g);

				if(elites[i].weapon.isShooting())
				{
					g.setColor(elites[i].weapon.bulletColor);
					if(armoryInt[7]==1)
						g.setColor(Color.white);
					elites[i].weapon.drawProjectile(g);
				}
			}
			//Jackals
			for(int i=0; i<jackalSpawn; i++)
			{
				if(!exclusiveGametype.equals(""))
				{
					if(jackals[i].active)
					{
						if(armoryInt[7]!=2)
						jackals[i].drawJackal(g);
					}
				}
				else
				{
					if(armoryInt[7]!=2)
					jackals[i].drawJackal(g);
				}

				if(jackals[i].weapon.isShooting())
				{
					g.setColor(jackals[i].weapon.bulletColor);
					if(armoryInt[7]==1)
						g.setColor(Color.white);
					jackals[i].weapon.drawProjectile(g);
				}
			}

			g.setColor(Color.WHITE);
			g.setFont(menuFont);
			if(mapInt==3||mapInt==2)
				g.setColor(Color.BLACK);

			for(int i=0; i<10; i++)
			{
				if(gamertag.equals(specialGTs[i]))
					specialGT=true;
			}
			if(specialGT)
				g.setColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));

			g.drawString(gamertag,spartan.location.x,spartan.location.y);
			
			if(armoryInt[7]!=2)
			spartan.drawSpartan(g);
			
			if(armoryInt[0]==2&&spartan.alive)
			{
				g.setColor(Color.RED);
				if(spartan.direction.equals("N"))
					g.fillRect(spartan.location.x+5,0,5,spartan.location.y);
				if(spartan.direction.equals("E"))
					g.fillRect(spartan.location.x+15,spartan.location.y+5,800-spartan.location.x,5);
				if(spartan.direction.equals("S"))
					g.fillRect(spartan.location.x+15,spartan.location.y+5,5,800-spartan.location.y);
				if(spartan.direction.equals("W"))
					g.fillRect(0,spartan.location.y+5,spartan.location.x,5);
			}

			if(spartan.weapon.isShooting())
			{
				g.setColor(spartan.weapon.bulletColor);
				if(armoryInt[7]==1)
						g.setColor(Color.white);
				spartan.weapon.drawProjectile(g);
			}
			
			if(armoryInt[6]>0)
				g.setColor(new Color(hudcolorR,hudcolorG,hudcolorB,180));
			else
			g.setColor(new Color(hudcolorR,hudcolorG,hudcolorB,80));
			
			g.fillRect(0,450,800,150);
			g.fillRect(0,0,800,50);
			g.drawImage(rankImg,0,450,60,80,this);
			g.setColor(Color.WHITE);
			g.setFont(smallerMenuFont);
			g.drawString(rank,75,465);
			g.drawString("Level: "+level,225,465);
			g.drawString("Exp: "+exp,75,485);
			g.drawString("To next:",75,505);
			g.drawString(""+(int)(expToNext-exp),75,525);
			g.setFont(menuFont);
			g.drawString("Score: "+score,520,30);
			//g.setColor(Color.BLACK);
			//g.fillRect(75,500,100,20);
			//g.setColor(Color.GREEN);
			//g.fillRect(75,500,(int)(100*(1-((expToNext-exp)/expToNext))),20);


			g.setColor(Color.BLACK);
			g.fillRect(295,15,200,20);
			g.setColor(Color.RED);
			g.fillRect(295,15,(int)(200*(spartan.health/(double)spartan.maxHealth)),20);
			g.setColor(Color.GREEN);
			g.fillRect(295,15,(int)(200*(spartan.shields/100.0)),20);

			if(spartan.overshield==Spartan.Overshield.x1)
			{
				g.setColor(Color.YELLOW);
				g.fillRect(295,15,(int)(200*(spartan.overshields[0]/100.0)),20);
			}
			if(spartan.overshield==Spartan.Overshield.x2)
			{
				g.setColor(Color.YELLOW);
				g.fillRect(295,15,(int)(200*(spartan.overshields[0]/100.0)),20);
				g.setColor(Color.ORANGE);
				g.fillRect(295,15,(int)(200*(spartan.overshields[1]/100.0)),20);
			}
			if(spartan.overshield==Spartan.Overshield.x3)
			{
				g.setColor(Color.YELLOW);
				g.fillRect(295,15,(int)(200*(spartan.overshields[0]/100.0)),20);
				g.setColor(Color.ORANGE);
				g.fillRect(295,15,(int)(200*(spartan.overshields[1]/100.0)),20);
				g.setColor(Color.CYAN);
				g.fillRect(295,15,(int)(200*(spartan.overshields[2]/100.0)),20);
			}
			if(spartan.overshield==Spartan.Overshield.x4)
			{
				g.setColor(Color.YELLOW);
				g.fillRect(295,15,(int)(200*(spartan.overshields[0]/100.0)),20);
				g.setColor(Color.ORANGE);
				g.fillRect(295,15,(int)(200*(spartan.overshields[1]/100.0)),20);
				g.setColor(Color.CYAN);
				g.fillRect(295,15,(int)(200*(spartan.overshields[2]/100.0)),20);
				g.setColor(Color.MAGENTA);
				g.fillRect(295,15,(int)(200*(spartan.overshields[3]/100.0)),20);
			}

			g.setFont(smallerMenuFont);
			g.setColor(Color.WHITE);
			if(spartan.recharging)
				g.drawString("Charging",365,30);
			else if(spartan.shields>0)
				g.drawString("Health",375,30);
			else
				g.drawString("Warning!",365,30);

			g.setColor(Color.WHITE);
			if(armoryInt[0]==0)
			{
				g.setFont(menuFont);
				g.drawString(spartan.weapon.wpnName,10,20);
			}
			else if(armoryInt[0]==1)
			{
				g.setFont(smallerMenuFont);
				g.drawString(spartan.weapon.wpnName+" + Combat Knife",10,20);
			}
			else if(armoryInt[0]==2)
			{
				g.setFont(smallerMenuFont);
				g.drawString(spartan.weapon.wpnName+" + Laser Sight",10,20);
			}
			
			g.setFont(smallerMenuFont);

			if(spartan.meleeInt>0)
			{
				g.setColor(Color.BLACK);
				g.fillRect(130,25,100,20);
				g.setColor(Color.ORANGE);
				g.fillRect(130,25,(int)(100*((double)(100-spartan.meleeInt)/100)),20);
				g.setColor(Color.WHITE);
				g.drawString("Fatigue",145,40);
			}

			if(spartan.weapon.reloading)
			{
				g.setColor(Color.BLACK);
				g.fillRect(5,25,100,20);
				g.setColor(Color.MAGENTA);
				g.fillRect(5,25,(int)(100*((double)(spartan.weapon.reloadInt)/spartan.weapon.reloadTime)),20);
				g.setColor(Color.WHITE);
				g.drawString("Reloading",20,40);
			}
			else
			{
				if(gametypeInt==1)
					g.drawString(""+spartan.weapon.ammoInClip+"/"+spartan.weapon.clipSize+" ("+(Double.POSITIVE_INFINITY)+")",30,40);
				else
					g.drawString(""+spartan.weapon.ammoInClip+"/"+spartan.weapon.clipSize+" ("+spartan.weapon.currentAmmo+")",30,40);
			}

			g.setColor(Color.WHITE);
			g.drawString(gametype[gametypeInt]+": "+difStr[difInt],550,465);
			if(gametypeInt!=5)
			{
				//g.drawString("Set: "+set,550,505);
				g.setFont(menuFont);
				g.drawString(""+set,530,505);
				//g.drawString("Round: "+round,550,525);
				g.setColor(Color.RED);
				switch(round)
				{
					case 3:
						g.fillOval(590,480,15,15);
					case 2:
						g.fillOval(570,480,15,15);
					case 1:
						g.fillOval(550,480,15,15);
				}

				//g.drawString("Wave: "+wave,550,545);
				g.setColor(Color.GREEN);
				switch(wave)
				{
					case 5:
						g.fillRect(630,500,15,15);
					case 4:
						g.fillRect(610,500,15,15);
					case 3:
						g.fillRect(590,500,15,15);
					case 2:
						g.fillRect(570,500,15,15);
					case 1:
						g.fillRect(550,500,15,15);

				}
			}

			g.setFont(menuFont);
			g.setColor(Color.WHITE);
			g.drawString("Lives: "+lives,550,550);

			if(!mainMessage.equals(""))
			{
				g.setFont(menuFont);
				g.setColor(new Color(255,255,200,200));
				if(mapInt==3||mapInt==2)
					g.setColor(Color.BLACK);
				g.drawString(mainMessage,10,420);
			}

			if(!unlockedMessage.equals(""))
			{
				g.setFont(headerFont);
				g.setColor(new Color(255,255,200,200));
				if(mapInt==3||mapInt==2)
					g.setColor(Color.BLACK);
				g.drawString(unlockedMessage,10,100);
			}

			if(medal!=null)
			g.drawImage(medal,10,300,this);
		}
	}

	public void stageManager()
	{
		if(mapInt==0)
		{
			URL textU=Jalo.class.getResource("Sprites/Textures/grass.png");
    		texture=Toolkit.getDefaultToolkit().getImage(textU);

    		URL bgU=Jalo.class.getResource("Music/grassroots.wav");
    		bgMusic=JApplet.newAudioClip(bgU);
		}
		else if(mapInt==1)
		{
			URL textU=Jalo.class.getResource("Sprites/Textures/rock.png");
    		texture=Toolkit.getDefaultToolkit().getImage(textU);

    		URL bgU=Jalo.class.getResource("Music/rockface.wav");
    		bgMusic=JApplet.newAudioClip(bgU);
		}
		else if(mapInt==2)
		{
			URL textU=Jalo.class.getResource("Sprites/Textures/desert.png");
    		texture=Toolkit.getDefaultToolkit().getImage(textU);

    		URL bgU=Jalo.class.getResource("Music/desertion.wav");
    		bgMusic=JApplet.newAudioClip(bgU);
		}
		else if(mapInt==3)
		{
			URL textU=Jalo.class.getResource("Sprites/Textures/snow.png");
    		texture=Toolkit.getDefaultToolkit().getImage(textU);

    		URL bgU=Jalo.class.getResource("Music/frostbite.wav");
    		bgMusic=JApplet.newAudioClip(bgU);
		}
		else if(mapInt==4)
		{
			URL textU=Jalo.class.getResource("Sprites/Textures/halo.png");
    		texture=Toolkit.getDefaultToolkit().getImage(textU);

    		URL bgU=Jalo.class.getResource("Music/halo.wav");
    		bgMusic=JApplet.newAudioClip(bgU);
		}
	}

	public void saveFileManager(String saveType)
  	{
  		if(saveType.equals("load"))
  		{
			try
			{
				BufferedReader in = new BufferedReader(new FileReader(f));
				rank=in.readLine();
				level=Integer.parseInt(in.readLine(),2);
				highestLevel=Integer.parseInt(in.readLine(),2);
				levelCode=in.readLine();
				exp=Integer.parseInt(in.readLine(),16);
				expCode=Integer.parseInt(in.readLine(),2);
				expToNext=(Double.parseDouble(in.readLine()));

				for(int i=0; i<14; i++)
					wpnChoice[i]=in.readLine();

				unlockInt=Integer.parseInt(in.readLine(),16);
				unlockCode=Integer.parseInt(in.readLine());

				kills=Integer.parseInt(in.readLine(),16);
				deaths=Integer.parseInt(in.readLine(),16);
				shotsFired=Integer.parseInt(in.readLine(),16);
				shotsHit=Integer.parseInt(in.readLine(),16);
				gamesPlayed=Integer.parseInt(in.readLine(),16);
				for(int i=0; i<14; i++)
				weaponUsage[i]=Integer.parseInt(in.readLine(),16);

				for(int i=0; i<49; i++)
					achievements[i]=Integer.parseInt(in.readLine(),16);
	
				for(int i=0; i<25; i++)
					challenges[i]=Integer.parseInt(in.readLine(),16);

				bestSpree=Integer.parseInt(in.readLine(),16);

				for(int i=0; i<5; i++)
					highscores[i]=Integer.parseInt(in.readLine(),16);
				
				for(int i=0; i<8; i++)
				{
					for(int j=0; j<5; j++)
						armoryItemNames[i][j]=in.readLine();
				}
				for(int i=0; i<8; i++)
					armoryInt[i]=Integer.parseInt(in.readLine());

				//BufferedReader in2 = new BufferedReader(new FileReader(hostf));
				//hostIP=in2.readLine();
				//hostPort=Integer.parseInt(in2.readLine());

				BufferedReader in3 = new BufferedReader(new FileReader(gtf));
				gamertag=in3.readLine();

				System.out.println("File loaded");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Error: New File Written");
				try
				{
					f.setWritable(true,true);
					PrintWriter fout = new PrintWriter(new FileWriter(f));
		        	fout.println(rank);
		        	fout.println(Integer.toBinaryString(level));
		        	fout.println(Integer.toBinaryString(highestLevel));
		        	fout.println(levelCode);
	        		fout.println(Integer.toHexString(exp));
	        		fout.println(Integer.toBinaryString(expCode));
	        		fout.println(expToNext);

	        		for(int i=0; i<14; i++)
	        		fout.println(wpnChoice[i]);

	        		fout.println(Integer.toHexString(unlockInt));
	        		fout.println(13-unlockInt);

	        		fout.println(Integer.toHexString(kills));
					fout.println(Integer.toHexString(deaths));
					fout.println(Integer.toHexString(shotsFired));
					fout.println(Integer.toHexString(shotsHit));
					fout.println(Integer.toHexString(gamesPlayed));
					for(int i=0; i<14; i++)
					fout.println(Integer.toHexString(weaponUsage[i]));

					for(int i=0; i<49; i++)
					fout.println(Integer.toHexString(achievements[i]));
					
					for(int i=0; i<25; i++)
					fout.println(Integer.toHexString(challenges[i]));

					fout.println(Integer.toHexString(bestSpree));

					for(int i=0; i<5; i++)
					fout.println(Integer.toHexString(highscores[i]));
					
					for(int i=0; i<8; i++)
					{
						for(int j=0; j<5; j++)
							fout.println(armoryItemNames[i][j]);
					}
					
					for(int i=0; i<8; i++)
					fout.println(armoryInt[i]);

		        	fout.close();
		        	System.out.println("File created");
		        	f.setReadOnly();
		        	f.setExecutable(false);

		        	//PrintWriter fout2 = new PrintWriter(new FileWriter(hostf));
			        //fout2.println("000.000.0.0");
			        //fout2.println("50001");
			        //fout2.close();

			        PrintWriter fout3 = new PrintWriter(new FileWriter(gtf));
			        fout3.println("YourGamertag");
			        fout3.close();
				}
				catch(Exception ex){e.printStackTrace();}
        	}
  		}
  		else if(saveType.equals("save"))
  		{
  			try
        	{
        		f.setWritable(true,true);
					PrintWriter fout = new PrintWriter(new FileWriter(f));
		        	fout.println(rank);
		        	fout.println(Integer.toBinaryString(level));
		        	fout.println(Integer.toBinaryString(highestLevel));
		        	fout.println(levelCode);
	        		fout.println(Integer.toHexString(exp));
	        		fout.println(Integer.toBinaryString(expCode));
	        		fout.println(expToNext);

	        		for(int i=0; i<14; i++)
	        		fout.println(wpnChoice[i]);

	        		fout.println(Integer.toHexString(unlockInt));
	        		fout.println(13-unlockInt);

	        		fout.println(Integer.toHexString(kills));
					fout.println(Integer.toHexString(deaths));
					fout.println(Integer.toHexString(shotsFired));
					fout.println(Integer.toHexString(shotsHit));
					fout.println(Integer.toHexString(gamesPlayed));
					for(int i=0; i<14; i++)
					fout.println(Integer.toHexString(weaponUsage[i]));

					for(int i=0; i<49; i++)
					fout.println(Integer.toHexString(achievements[i]));
					
					for(int i=0; i<25; i++)
					fout.println(Integer.toHexString(challenges[i]));

					fout.println(Integer.toHexString(bestSpree));

					for(int i=0; i<5; i++)
					fout.println(Integer.toHexString(highscores[i]));
					
					for(int i=0; i<8; i++)
					{
						for(int j=0; j<5; j++)
							fout.println(armoryItemNames[i][j]);
					}
					
					for(int i=0; i<8; i++)
					fout.println(armoryInt[i]);

		        	fout.close();
		        	System.out.println("File created");
		        	f.setReadOnly();
		        	f.setExecutable(false);

		        	//PrintWriter fout2 = new PrintWriter(new FileWriter(hostf));
			        //fout2.println("000.000.0.0");
			        //fout2.println("50001");
			        //fout2.close();

			        PrintWriter fout3 = new PrintWriter(new FileWriter(gtf));
			        fout3.println(gamertag);
			        fout3.close();
        	}
        	catch(Exception e){e.printStackTrace();}
  		}
  	}

  	public void hostInit()
  	{
		input = new Scanner(System.in);
		mpTryingToConnect=true;
		try
		{
			for(int i=1; i<mpTotalPlayers; i++)
			{
				ss[i] = new ServerSocket(50000+i); // create a server socket and bind it to the above port number.
				System.out.println("Waiting for a friend...");

				socket[i] = ss[i].accept(); // make the server listen for a connection, and let you know when it gets one.

				System.out.println("Connected!");
				System.out.println();
				mpConnected[i]=true;

				// Get the input and output streams of the socket, so that you can receive and send data to the client.
				sin[i] = socket[i].getInputStream();
				sout[i] = socket[i].getOutputStream();

				// Just converting them to different streams, so that string handling becomes easier.
				in[i] = new DataInputStream(sin[i]);
				out[i] = new DataOutputStream(sout[i]);
			}
		}
		catch(Exception e){System.exit(0);}

		mpTryingToConnect=false;
  	}

  	public void clientInit()
  	{
		input = new Scanner(System.in);
		mpTryingToConnect=true;
		hostPort=50001;

		try
		{
			ipAddress = InetAddress.getByName(hostIP); // create an object that represents the above IP address.
			System.out.println("Any of you heard of a socket with IP address " + hostIP + " and port " + hostPort + "?");

			socket[0] = new Socket(ipAddress, 50001); // create a socket with the server's IP address and server's port.
			System.out.println("Yes! I'm connected!");

			System.out.println();
			for(int i=0; i<3; i++)
			mpConnected[i]=true;

			// Get the input and output streams of the socket, so that you can receive and send data to the client.
			sin[0] = socket[0].getInputStream();
			sout[0] = socket[0].getOutputStream();

			// Just converting them to different streams, so that string handling becomes easier.
			in[0] = new DataInputStream(sin[0]);
			out[0] = new DataOutputStream(sout[0]);
		 }
		 catch(Exception e)
		 {
		 	hostPort=50002;
		 	try
		{
			ipAddress = InetAddress.getByName(hostIP); // create an object that represents the above IP address.
			System.out.println("Any of you heard of a socket with IP address " + hostIP + " and port " + hostPort + "?");

			socket[0] = new Socket(ipAddress, 50002); // create a socket with the server's IP address and server's port.
			System.out.println("Yes! I'm connected!");

			System.out.println();
			for(int i=0; i<3; i++)
			mpConnected[i]=true;

			// Get the input and output streams of the socket, so that you can receive and send data to the client.
			sin[0] = socket[0].getInputStream();
			sout[0] = socket[0].getOutputStream();

			// Just converting them to different streams, so that string handling becomes easier.
			in[0] = new DataInputStream(sin[0]);
			out[0] = new DataOutputStream(sout[0]);
		 }
		 catch(Exception ex)
		 {
		 	hostPort=50003;
		 	try
		{
			ipAddress = InetAddress.getByName(hostIP); // create an object that represents the above IP address.
			System.out.println("Any of you heard of a socket with IP address " + hostIP + " and port " + hostPort + "?");

			socket[0] = new Socket(ipAddress, 50003); // create a socket with the server's IP address and server's port.
			System.out.println("Yes! I'm connected!");

			System.out.println();
			for(int i=0; i<3; i++)
			mpConnected[i]=true;

			// Get the input and output streams of the socket, so that you can receive and send data to the client.
			sin[0] = socket[0].getInputStream();
			sout[0] = socket[0].getOutputStream();

			// Just converting them to different streams, so that string handling becomes easier.
			in[0] = new DataInputStream(sin[0]);
			out[0] = new DataOutputStream(sout[0]);
		 }
		 catch(Exception exc)
		 {

		 	//e.printStackTrace();
		 	System.out.println("I guess not...");
		 	mode="title";
			back.play();
			menuInt=250;
			titleMusic.stop();
			titleMusic.play();
			//System.exit(0);
		 }
		 }
		 }

		 mpTryingToConnect=false;
  	}

  	public void hostGetBasic()
  	{
  		mpExp[0]=exp;
  		mpGamertags[0]=gamertag;
  		mpHighestLevel[0]=highestLevel;
  		mpID=0;
  		mpLevels[0]=level;
  		mpWeapons[0]=wpnChoice[wpnChoiceInt];

  		for(int i=1; i<mpTotalPlayers; i++)
  		{
  			try
  			{
  				mpExp[i]=Integer.parseInt(in[i].readUTF());
  				System.out.println("Read Exp");
	  			mpGamertags[i]=in[i].readUTF();
	  			System.out.println("Read GTs");
	  			mpHighestLevel[i]=Integer.parseInt(in[i].readUTF());
	  			System.out.println("Read High Levels");
	  			mpLevels[i]=Integer.parseInt(in[i].readUTF());
	  			System.out.println("Read Levels");
	  			mpWeapons[i]=(in[i].readUTF());
	  			System.out.println("Read Weapons");
  			}
  			catch(Exception e){}
  		}

  		for(int i=1; i<mpTotalPlayers; i++)
  		{
  			try
  			{
  				mpExp[i]=Integer.parseInt(in[i].readUTF());
  				System.out.println("Read Exp");
  				out[i].writeUTF(""+mpExp[0]);
	  			out[i].flush();
	  			out[i].writeUTF(""+mpExp[1]);
	  			out[i].flush();
	  			out[i].writeUTF(""+mpExp[2]);
	  			out[i].flush();
	  			out[i].writeUTF(""+mpExp[3]);
	  			out[i].flush();
	  			System.out.println("Sent Exp");

	  			mpHighestLevel[i]=Integer.parseInt(in[i].readUTF());
	  			System.out.println("Read High Levels");
		  		out[i].writeUTF(""+mpHighestLevel[0]);
	  			out[i].flush();
	  			out[i].writeUTF(""+mpHighestLevel[1]);
	  			out[i].flush();
	  			out[i].writeUTF(""+mpHighestLevel[2]);
	  			out[i].flush();
	  			out[i].writeUTF(""+mpHighestLevel[3]);
	  			out[i].flush();
	  			System.out.println("Sent High Levels");

		  		mpLevels[i]=Integer.parseInt(in[i].readUTF());
	  			System.out.println("Read Levels");
		  		out[i].writeUTF(""+mpLevels[0]);
	  			out[i].flush();
	  			out[i].writeUTF(""+mpLevels[1]);
	  			out[i].flush();
	  			out[i].writeUTF(""+mpLevels[2]);
	  			out[i].flush();
	  			out[i].writeUTF(""+mpLevels[3]);
	  			out[i].flush();
	  			System.out.println("Sent Levels");

	  			mpGamertags[i]=in[i].readUTF();
	  			System.out.println("Read GTs");
	  			out[i].writeUTF(mpGamertags[0]);
	  			out[i].flush();
	  			out[i].writeUTF(mpGamertags[1]);
	  			out[i].flush();
	  			out[i].writeUTF(mpGamertags[2]);
	  			out[i].flush();
	  			out[i].writeUTF(mpGamertags[3]);
	  			out[i].flush();
	  			System.out.println("Sent Gamertags");

	  			out[i].writeUTF(mpWeapons[0]);
	  			out[i].flush();
	  			out[i].writeUTF(mpWeapons[1]);
	  			out[i].flush();
	  			out[i].writeUTF(mpWeapons[2]);
	  			out[i].flush();
	  			out[i].writeUTF(mpWeapons[3]);
	  			out[i].flush();
	  			System.out.println("Sent Weapons");

	  			out[i].writeUTF(""+mpGametypeInt);
	  			out[i].flush();
	  			System.out.println("Sent Gametype Integer");

	  			out[i].writeUTF(""+i);
	  			out[i].flush();
	  			System.out.println("Sent ID");

	  			System.out.println("Sent All Data");
  			}
  			catch(Exception e){}
  		}

  		mode="mpLobby";
  	}

  	public void clientGetBasic()
  	{
  		try
  		{
  			out[0].writeUTF(""+exp);
  			out[0].flush();
  			System.out.println("Sent Exp");

  			out[0].writeUTF(gamertag);
  			out[0].flush();
  			System.out.println("Sent Gamertag");

	  		out[0].writeUTF(""+highestLevel);
  			out[0].flush();
  			System.out.println("Sent High Level");

	  		out[0].writeUTF(""+level);
  			out[0].flush();
  			System.out.println("Sent Level");

  			out[0].writeUTF(""+wpnChoice[wpnChoiceInt]);
  			out[0].flush();
  			System.out.println("Sent Weapons");
  		}
  		catch(Exception e){}

  		try
  		{
  			//for(int i=1; i<mpTotalPlayers; i++)
  			//{
  				out[0].writeUTF(""+exp);
  				out[0].flush();
  				System.out.println("Sent Exp");
  				mpExp[0]=Integer.parseInt(in[0].readUTF());
  				mpExp[1]=Integer.parseInt(in[0].readUTF());
  				mpExp[2]=Integer.parseInt(in[0].readUTF());
  				mpExp[3]=Integer.parseInt(in[0].readUTF());
  				System.out.println("Read Exp");

  				out[0].writeUTF(""+highestLevel);
  				out[0].flush();
  				System.out.println("Sent High Level");
		  		mpHighestLevel[0]=Integer.parseInt(in[0].readUTF());
		  		mpHighestLevel[1]=Integer.parseInt(in[0].readUTF());
		  		mpHighestLevel[2]=Integer.parseInt(in[0].readUTF());
		  		mpHighestLevel[3]=Integer.parseInt(in[0].readUTF());
		  		System.out.println("Read Highest Level");

		  		out[0].writeUTF(""+level);
  				out[0].flush();
  				System.out.println("Sent Level");
		  		mpLevels[0]=Integer.parseInt(in[0].readUTF());
		  		mpLevels[1]=Integer.parseInt(in[0].readUTF());
		  		mpLevels[2]=Integer.parseInt(in[0].readUTF());
		  		mpLevels[3]=Integer.parseInt(in[0].readUTF());
		  		System.out.println("Read Levels");

		  		out[0].writeUTF(gamertag);
  				out[0].flush();
  				System.out.println("Sent Gamertag");
		  		mpGamertags[0]=in[0].readUTF();
			  	mpGamertags[1]=in[0].readUTF();
			  	mpGamertags[2]=in[0].readUTF();
			  	mpGamertags[3]=in[0].readUTF();
			  	System.out.println("Read Gamertags");

			  	mpWeapons[0]=in[0].readUTF();
			  	mpWeapons[1]=in[0].readUTF();
			  	mpWeapons[2]=in[0].readUTF();
			  	mpWeapons[3]=in[0].readUTF();
			  	System.out.println("Read Weapons");

			  	mpGametypeInt=Integer.parseInt(in[0].readUTF());
		  		System.out.println("Read Gametype Integer");

			  	mpID=Integer.parseInt(in[0].readUTF());
		  		System.out.println("Read ID");

		  		System.out.println("Read All Data");
  			//}
  		}
  		catch(Exception e){}

  		mode="mpLobby";
  	}

  	public void waitingInLobby()
  	{
  		int y=0;
  		try
  		{
  			while(y<2)
  			{
  				repaint();
  				Thread.sleep(5000);
  				y++;
  			}
  		}
  		catch(Exception e){}

  		for(int i=0; i<4; i++)
  		{
  			if(mpLevels[i]>1)
  			mpLevels[i]--;
  		}
  	}

  	public void mpRankManager()
  	{
  		for(int i=0; i<4; i++)
  		{
		if(mpExp[i]>=50000000)
		{
			mpRanks[i]="Reclaimer";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Reclaimer.jpg");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=40000000)
		{
			mpRanks[i]="Tritium";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Tritium.png");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=32000000)
		{
			mpRanks[i]="Deuterium";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Deuterium.png");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=26000000)
		{
			mpRanks[i]="Monotium";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Monotium.png");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=22000000)
		{
			mpRanks[i]="Jartan";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Jartan.png");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=20000000&&mpHighestLevel[i]==50)
		{
			mpRanks[i]="General-4";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/General_Grade_4.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=10000000&&mpHighestLevel[i]==50)
		{
			mpRanks[i]="General-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/General_Grade_3.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=4800000&&mpHighestLevel[i]==50)
		{
			mpRanks[i]="General-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/General_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=2400000&&mpHighestLevel[i]==50)
		{
			mpRanks[i]="General";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/General.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=16000000&&mpHighestLevel[i]>=45)
		{
			mpRanks[i]="Lt.General";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Brigadier_Grade_4.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=8000000&&mpHighestLevel[i]>=45)
		{
			mpRanks[i]="Brigadier-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Brigadier_Grade_3.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=4000000&&mpHighestLevel[i]>=45)
		{
			mpRanks[i]="Brigadier-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Brigadier_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=2000000&&mpHighestLevel[i]>=45)
		{
			mpRanks[i]="Brigadier";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Brigadier.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=9600000&&mpHighestLevel[i]>=40)
		{
			mpRanks[i]="Force Col.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Colonel_Grade_4.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=4800000&&mpHighestLevel[i]>=40)
		{
			mpRanks[i]="Colonel-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Colonel_Grade_3.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=2400000&&mpHighestLevel[i]>=40)
		{
			mpRanks[i]="Colonel-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Colonel_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=1600000&&mpHighestLevel[i]>=40)
		{
			mpRanks[i]="Colonel";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Colonel.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=7200000&&mpHighestLevel[i]>=35)
		{
			mpRanks[i]="Strike Comm.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Commander_Grade_4.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=3600000&&mpHighestLevel[i]>=35)
		{
			mpRanks[i]="Commander-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Commander_Grade_3.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=1800000&&mpHighestLevel[i]>=35)
		{
			mpRanks[i]="Commander-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Commander_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=1200000&&mpHighestLevel[i]>=35)
		{
			mpRanks[i]="Commander";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Commander.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=4800000&&mpHighestLevel[i]>=30)
		{
			mpRanks[i]="Field Maj.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Major_Grade_4.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=2400000&&mpHighestLevel[i]>=30)
		{
			mpRanks[i]="Major-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Major_Grade_3.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=1200000&&mpHighestLevel[i]>=30)
		{
			mpRanks[i]="Major-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Major_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=800000&&mpHighestLevel[i]>=30)
		{
			mpRanks[i]="Major";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Major.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=2400000&&mpHighestLevel[i]>=20)
		{
			mpRanks[i]="Staff Cpt.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Captain_Grade_4.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=1200000&&mpHighestLevel[i]>=20)
		{
			mpRanks[i]="Captain-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Captain_Grade_3.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=600000&&mpHighestLevel[i]>=20)
		{
			mpRanks[i]="Captain-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Captain_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=400000&&mpHighestLevel[i]>=20)
		{
			mpRanks[i]="Captain";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Captain.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=1600000&&mpHighestLevel[i]>=10)
		{
			mpRanks[i]="First Lt.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Lieutenant_Grade_4.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=800000&&mpHighestLevel[i]>=10)
		{
			mpRanks[i]="Lieutenant-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Lieutenant_Grade_3.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=340000&&mpHighestLevel[i]>=10)
		{
			mpRanks[i]="Lieutenant-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Lieutenant_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=280000&&mpHighestLevel[i]>=10)
		{
			mpRanks[i]="Lieutenant";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Lieutenant.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=1200000)
		{
			mpRanks[i]="Master Sgt.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Gunnery_Sergeant_Grade_4.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=600000)
		{
			mpRanks[i]="Gunnery Sgt-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Gunnery_Sergeant_Grade_3.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=240000)
		{
			mpRanks[i]="Gunnery Sgt-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Gunnery_Sergeant_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=200000)
		{
			mpRanks[i]="Gunnery Sgt.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Gunnery_Sergeant.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=160000)
		{
			mpRanks[i]="Sergeant-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Sergeant_Grade_3.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=120000)
		{
			mpRanks[i]="Sergeant-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Sergeant_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=80000)
		{
			mpRanks[i]="Sergeant";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Sergeant.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=60000)
		{
			mpRanks[i]="Corporal-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Corporal_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=40000)
		{
			mpRanks[i]="Corporal";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Corporal.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=28000)
		{
			mpRanks[i]="Private-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Private_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=20000)
		{
			mpRanks[i]="Private";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Private.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=12000)
		{
			mpRanks[i]="Apprentice-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Apprentice_Grade_2.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=8000)
		{
			mpRanks[i]="Apprentice";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Apprentice.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else if(mpExp[i]>=4000)
		{
			mpRanks[i]="Recruit";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Recruit.gif");
    		mpRankImg[i]=Toolkit.getDefaultToolkit().getImage(rankU);
		}
		else
		{
			mpRanks[i]="Noob";
    		mpRankImg[i]=null;
		}

  	}
  	}

	public void rankManager()
	{
		if(rank.equals("Banned"))
		{
			exp=0;
			expToNext=0;
			level=1;
			highestLevel=1;
			unlockInt=0;
			wpnChoice[0]="Plasma Pistol";
	    	wpnChoice[1]="Cheater!";
	    	wpnChoice[2]="Cheater!";
	    	wpnChoice[3]="Cheater!";
	    	wpnChoice[4]="Cheater!";
	    	wpnChoice[5]="Cheater!";
	    	wpnChoice[6]="Cheater!";
	    	wpnChoice[7]="Cheater!";
	    	wpnChoice[8]="Cheater!";
	    	wpnChoice[9]="Cheater!";
	    	wpnChoice[10]="Cheater!";
	    	wpnChoice[11]="Cheater!";
	    	wpnChoice[12]="Cheater!";
	    	wpnChoice[13]="Cheater!";

	    	rankImg=cyborg;
		}
		else if(exp>=50000000)
		{
			rank="Reclaimer";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Reclaimer.jpg");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=exp;
		}
		else if(exp>=40000000)
		{
			rank="Tritium";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Tritium.png");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=50000000;
		}
		else if(exp>=32000000)
		{
			rank="Deuterium";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Deuterium.png");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=40000000;
		}
		else if(exp>=26000000)
		{
			rank="Monotium";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Monotium.png");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=32000000;
		}
		else if(exp>=22000000)
		{
			rank="Jartan";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Jartan.png");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=26000000;
		}
		else if(exp>=20000000&&highestLevel==50)
		{
			rank="General-4";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/General_Grade_4.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=22000000;
    		unlockInt=13;
    		wpnChoice[13]="Spartan Laser";
    		wpnChoice[12]="Fuel Rod";
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=10000000&&highestLevel==50)
		{
			rank="General-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/General_Grade_3.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=20000000;
    		unlockInt=13;
    		wpnChoice[13]="Spartan Laser";
    		wpnChoice[12]="Fuel Rod";
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=4800000&&highestLevel==50)
		{
			rank="General-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/General_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=10000000;
    		unlockInt=13;
    		wpnChoice[13]="Spartan Laser";
    		wpnChoice[12]="Fuel Rod";
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=2400000&&highestLevel==50)
		{
			rank="General";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/General.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=4800000;
    		unlockInt=13;
    		wpnChoice[13]="Spartan Laser";
    		wpnChoice[12]="Fuel Rod";
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=16000000&&highestLevel>=45)
		{
			rank="Lt.General";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Brigadier_Grade_4.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=22000000;
    		unlockInt=12;
    		wpnChoice[12]="Fuel Rod";
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=8000000&&highestLevel>=45)
		{
			rank="Brigadier-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Brigadier_Grade_3.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=16000000;
    		unlockInt=12;
    		wpnChoice[12]="Fuel Rod";
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=4000000&&highestLevel>=45)
		{
			rank="Brigadier-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Brigadier_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=8000000;
    		unlockInt=12;
    		wpnChoice[12]="Fuel Rod";
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=2000000&&highestLevel>=45)
		{
			rank="Brigadier";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Brigadier.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=4000000;
    		unlockInt=12;
    		wpnChoice[12]="Fuel Rod";
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
    		if(highestLevel>=50)
    			expToNext=2400000;
		}
		else if(exp>=9600000&&highestLevel>=40)
		{
			rank="Force Col.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Colonel_Grade_4.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=22000000;
    		unlockInt=11;
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=4800000&&highestLevel>=40)
		{
			rank="Colonel-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Colonel_Grade_3.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=9600000;
    		unlockInt=11;
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=2400000&&highestLevel>=40)
		{
			rank="Colonel-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Colonel_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=4800000;
    		unlockInt=11;
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=1600000&&highestLevel>=40)
		{
			rank="Colonel";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Colonel.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=2400000;
    		//spartan.overshield=Spartan.Overshield.x3;
    		unlockInt=11;
    		wpnChoice[11]="Rockets";
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
    		if(highestLevel>=45)
    			expToNext=2000000;
		}
		else if(exp>=7200000&&highestLevel>=35)
		{
			rank="Strike Comm.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Commander_Grade_4.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=22000000;
    		unlockInt=10;
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=3600000&&highestLevel>=35)
		{
			rank="Commander-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Commander_Grade_3.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=7200000;
    		unlockInt=10;
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=1800000&&highestLevel>=35)
		{
			rank="Commander-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Commander_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=3600000;
    		unlockInt=10;
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=1200000&&highestLevel>=35)
		{
			rank="Commander";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Commander.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=1800000;
    		unlockInt=10;
    		wpnChoice[10]="Sniper";
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
    		if(highestLevel>=40)
    			expToNext=1600000;
		}
		else if(exp>=4800000&&highestLevel>=30)
		{
			rank="Field Maj.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Major_Grade_4.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=22000000;
    		unlockInt=9;
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=2400000&&highestLevel>=30)
		{
			rank="Major-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Major_Grade_3.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=4800000;
    		unlockInt=9;
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=1200000&&highestLevel>=30)
		{
			rank="Major-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Major_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=2400000;
    		unlockInt=9;
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=800000&&highestLevel>=30)
		{
			rank="Major";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Major.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=1200000;
    		unlockInt=9;
    		wpnChoice[9]="Shotgun";
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=2400000&&highestLevel>=20)
		{
			rank="Staff Cpt.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Captain_Grade_4.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=22000000;
    		unlockInt=8;
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=1200000&&highestLevel>=20)
		{
			rank="Captain-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Captain_Grade_3.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=2400000;
    		unlockInt=8;
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=600000&&highestLevel>=20)
		{
			rank="Captain-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Captain_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=1200000;
    		unlockInt=8;
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
    		if(highestLevel>=30)
    			expToNext=800000;
		}
		else if(exp>=400000&&highestLevel>=20)
		{
			rank="Captain";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Captain.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=600000;
    		unlockInt=8;
    		wpnChoice[8]="Mauler";
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=1600000&&highestLevel>=10)
		{
			rank="First Lt.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Lieutenant_Grade_4.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=22000000;
    		unlockInt=7;
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=800000&&highestLevel>=10)
		{
			rank="Lieutenant-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Lieutenant_Grade_3.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=1600000;
    		unlockInt=7;
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=340000&&highestLevel>=10)
		{
			rank="Lieutenant-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Lieutenant_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=800000;
    		unlockInt=7;
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
    		if(highestLevel>=20)
    			expToNext=400000;
		}
		else if(exp>=280000&&highestLevel>=10)
		{
			rank="Lieutenant";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Lieutenant.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=340000;
    		unlockInt=7;
    		wpnChoice[7]="G. Launcher";
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=1200000)
		{
			rank="Master Sgt.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Gunnery_Sergeant_Grade_4.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=22000000;
    		unlockInt=6;
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=600000)
		{
			rank="Gunnery Sgt-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Gunnery_Sergeant_Grade_3.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=1200000;
    		unlockInt=6;
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=240000)
		{
			rank="Gunnery Sgt-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Gunnery_Sergeant_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=600000;
    		unlockInt=6;
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
    		if(highestLevel>=10)
    			expToNext=280000;
		}
		else if(exp>=200000)
		{
			rank="Gunnery Sgt.";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Gunnery_Sergeant.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=240000;
    		unlockInt=6;
    		wpnChoice[6]="Brute Shot";
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=160000)
		{
			rank="Sergeant-3";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Sergeant_Grade_3.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=200000;
    		unlockInt=5;
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=120000)
		{
			rank="Sergeant-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Sergeant_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=160000;
    		unlockInt=5;
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=80000)
		{
			rank="Sergeant";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Sergeant.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=120000;
    		unlockInt=5;
    		wpnChoice[5]="DMR";
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=60000)
		{
			rank="Corporal-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Corporal_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=80000;
    		unlockInt=4;
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=40000)
		{
			rank="Corporal";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Corporal.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=60000;
    		unlockInt=4;
    		wpnChoice[4]="Needler";
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=28000)
		{
			rank="Private-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Private_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=40000;
    		unlockInt=3;
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=20000)
		{
			rank="Private";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Private.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=28000;
    		unlockInt=3;
    		wpnChoice[3]="Carbine";
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=12000)
		{
			rank="Apprentice-2";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Apprentice_Grade_2.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=20000;
    		unlockInt=2;
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=8000)
		{
			rank="Apprentice";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Apprentice.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=12000;
    		unlockInt=2;
    		wpnChoice[2]="Plasma Rifle";
		}
		else if(exp>=4000)
		{
			rank="Recruit";
			URL rankU=Jalo.class.getResource("Sprites/Ranks/Recruit.gif");
    		rankImg=Toolkit.getDefaultToolkit().getImage(rankU);
    		expToNext=8000;
		}
		else
		{
			rank="Noob";
    		rankImg=null;
    		expToNext=4000;
		}
	}

	public void keyPressed(KeyEvent e)
	{
		int key=e.getKeyCode();
		//System.out.println(spartan.location);
		
		/*if(key=='t'||key=='T')
		{
			gamesPlayed=90000;
			kills=9999999;
			multikill=10;
			spree=1000;
			consecNoDmgWaves=500;
			elitesKilledThisGame=1000;
			exp=10000000;
			gruntsKilledThisGame=1000;
			jackalsKilledThisGame=1000;
			shotsFired=500000;
			shieldRechargesThisGame=5000;
			
			level=50;
		}*/
		
		if(mode.equals("title"))
		{
			if(key==38&&menuInt>250)
			{
				menuInt-=50;
				toggle.play();
			}
			else if(key==40&&menuInt<450)
			{
				menuInt+=50;
				toggle.play();
			}
			else if(key==32)
			{
				if(menuInt==250)
				{
					bgMusic.stop();
					System.out.println("Starting Single Player Menu");
					bgMusic.stop();
					mode="single";
					yes.play();
					menuInt=450;
					preview=new Weapon(wpnChoice[wpnChoiceInt],0);
					rankManager();
					stageManager();
				}
				else if(menuInt==300)
				{
					mode="armory";
					armoryIntIndex=0;
					yes.play();
					menuInt=300;
					preview=new Weapon(wpnChoice[wpnChoiceInt],0);
					rankManager();
					stageManager();
				}
				else if(menuInt==350)
				{
					System.out.println("Starting Statistics Menu");
					mode="statistics";
					yes.play();
					menuInt=250;
				}
				else if(menuInt==400)
				{
					bgMusic.stop();
					watchedCredits=true;
					bgMusic.stop();
					System.out.println("Credits");
					mapInt=(int)(Math.random()*5);
					stageManager();
					mode="credits";
					yes.play();
					menuInt=250;
					titleMusic.stop();
					bgMusic.loop();
				}
				else if(menuInt==450)
				{
					System.exit(0);
				}
			}
		}
		else if(mode.equals("single"))
		{
			if(key==38&&menuInt>450)
			{
				menuInt-=20;
				toggle.play();
			}
			else if(key==40&&menuInt<550)
			{
				menuInt+=20;
				toggle.play();
			}
			else if(key==32)
			{
				if(menuInt==530)
				{
					if(wpnChoiceInt<=unlockInt)
					{
						bgMusic.stop();
						System.out.println("Loading Game...");
						yes.play();

						if(gametypeInt==0)
							lives=5;
						else if(gametypeInt==1)
							lives=3;
						else if(gametypeInt==2)
							lives=5;
						else if(gametypeInt==3)
							lives=7;
						else if(gametypeInt==4)
							lives=10;
						else if(gametypeInt==5)
						{
							lives=10;
							mainMessage="Level: "+level;
						}

						gametypeManager();
						System.out.println("Gametype Loaded");
						stageManager();
						System.out.println("Stage Selected");
						titleMusic.stop();
						mode="battle";
						bgMusic.loop();
						runGame();
						System.out.println("Game Started");
					}
					else
					{
						no.play();
					}

				}
				else if(menuInt==550)
				{
					System.out.println("Reverting to Title");
					mode="title";
					back.play();
					menuInt=250;
				}
			}
			else if(key==39)
			{
				if(menuInt==450&&gametypeInt<5)
				{
					gametypeInt++;
					toggle.play();
				}
				else if(menuInt==470&&wpnChoiceInt<13)
				{
					wpnChoiceInt++;
					toggle.play();
					preview=new Weapon(wpnChoice[wpnChoiceInt],0);
				}
				else if(menuInt==490&&difInt<3)
				{
					difInt++;
					toggle.play();
				}
				else if(menuInt==510&&mapInt<4)
				{
					mapInt++;
					toggle.play();
					stageManager();
				}
			}
			else if(key==37)
			{
				if(menuInt==450&&gametypeInt>0)
				{
					gametypeInt--;
					toggle.play();
				}
				else if(menuInt==470&&wpnChoiceInt>0)
				{
					wpnChoiceInt--;
					toggle.play();
					preview=new Weapon(wpnChoice[wpnChoiceInt],0);
				}
				else if(menuInt==490&&difInt>0)
				{
					difInt--;
					toggle.play();
				}
				else if(menuInt==510&&mapInt>0)
				{
					mapInt--;
					toggle.play();
					stageManager();
				}
			}
		}
		else if(mode.equals("armory"))
		{
			if(key==38&&menuInt>300)
			{
				menuInt-=20;
				armoryIntIndex--;
				toggle.play();

				if(menuInt==300)
					armoryIntIndex=0;
			}
			else if(key==40&&menuInt<460)
			{
				menuInt+=20;
				armoryIntIndex++;
				toggle.play();

				if(menuInt==460)
					armoryIntIndex=8;
			}
			else if(key==39)
			{
				toggle.play();
				if(menuInt==300&&armoryInt[armoryIntIndex]<2)
					armoryInt[armoryIntIndex]++;
				else if(menuInt==320&&armoryInt[armoryIntIndex]<3)
					armoryInt[armoryIntIndex]++;
				else if(menuInt==340&&armoryInt[armoryIntIndex]<3)
					armoryInt[armoryIntIndex]++;
				else if(menuInt==360&&armoryInt[armoryIntIndex]<3)
					armoryInt[armoryIntIndex]++;
				else if(menuInt==380&&armoryInt[armoryIntIndex]<4)
					armoryInt[armoryIntIndex]++;
				else if(menuInt==400&&armoryInt[armoryIntIndex]<3)
					armoryInt[armoryIntIndex]++;
				else if(menuInt==420&&armoryInt[armoryIntIndex]<4)
					armoryInt[armoryIntIndex]++;
				else if(menuInt==440&&armoryInt[armoryIntIndex]<3)
					armoryInt[armoryIntIndex]++;
			}
			else if(key==37&&menuInt!=460&&armoryInt[armoryIntIndex]>0)
			{
				toggle.play();
				armoryInt[armoryIntIndex]--;
			}
			else if(key==32&&menuInt==460)
			{
				boolean check=true;

				for(int i=0; i<8; i++)
				{
					if(armoryItemNames[i][armoryInt[i]].equals("Locked"))
					{
						check=false;
						break;
					}
				}

				if(check)
				{
					System.out.println("Reverting to Title");
					mode="title";
					back.play();
					menuInt=250;
				}
				else
				{
					no.play();
				}
			}
		}
		else if(mode.equals("statistics"))
		{
			if(key==32)
			{
				System.out.println("Reverting to Title");
				mode="title";
				back.play();
				menuInt=250;
			}
			else if(key==39&&achievementInt<48)
			{
				achievementInt++;
				toggle.play();
			}
			else if(key==37&&achievementInt>0)
			{
				achievementInt--;
				toggle.play();
			}
		}
		else if(mode.equals("battle")&&spartan.alive)
		{
			if(key==39)
			{
				keys[1]=true;
			}
			if(key==37)
			{
				keys[3]=true;
			}
			if(key==38)
			{
				keys[0]=true;
			}
			if(key==40)
			{
				keys[2]=true;
			}

			if(key=='A'||key=='a')
			{
				if(!spartan.weapon.reloading)
				{
					spartan.weapon.reload();
					System.out.println("Manual Reload Initiated");
					reloadsThisGame++;
				}
			}

			if((key=='f'||key=='F')&&!spartan.meleeing&&spartan.meleeInt==0)
			{
				spartan.meleeInt=100;
				spartan.melee();
				System.out.println("Melee Initiated");
			}

			if(key==32)
			{

				if(!spartan.weapon.isShooting())
				{
					spartan.setProjCoordinates(spartan.location,spartan.direction);
					spartan.weapon.fire();
					shotsFired++;
					System.out.println("Spartan Weapon Fired");
				}
			}
			if(key==27)
			{
				mainMessage="Game over";
				System.out.println("Manual Game Termination");
			}
		}
	}
	public void keyReleased(KeyEvent e)
	{
		int key=e.getKeyCode();

		if(key==38)
		{
			keys[0]=false;
		}
		if(key==39)
		{
			keys[1]=false;
		}
		if(key==40)
		{
			keys[2]=false;
		}
		if(key==37)
		{
			keys[3]=false;
		}

		if(mode.equals("battle")&&spartan.meleeing)
		{
			spartan.meleeing=false;
		}
	}
	public void keyTyped(KeyEvent e){}

	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseDragged(MouseEvent e)
	{
		System.out.println(mouse);
		//g.fillRect(550,300,100,100);
		
		boolean check=true;

				for(int i=0; i<8; i++)
				{
					if(armoryItemNames[i][armoryInt[i]].equals("Locked"))
					{
						check=false;
						break;
					}
				}
		if(mode.equals("armory")&&mouse.x>=550&&mouse.x<=650&&mouse.y>=300&&mouse.y<=400)
		{
			System.out.println("Editing Gamertag");
			gamertag = JOptionPane.showInputDialog(null, "Enter a Gamertag:", gamertag);
		}
		

		if(gamertag==null||gamertag.equals(""))
		{
			gamertag="YourGamertag";
		}
		
		if(gamertag.length()>15)
			gamertag=gamertag.substring(0,15);
			
		if(check)
		{
			saveFileManager("save");
		}

	}
	public void mouseClicked(MouseEvent e)
	{
	System.out.println(mouse);
		//g.fillRect(550,300,100,100);
		
		boolean check=true;

				for(int i=0; i<8; i++)
				{
					if(armoryItemNames[i][armoryInt[i]].equals("Locked"))
					{
						check=false;
						break;
					}
				}
		if(mode.equals("armory")&&mouse.x>=550&&mouse.x<=650&&mouse.y>=300&&mouse.y<=400)
		{
			System.out.println("Editing Gamertag");
			gamertag = JOptionPane.showInputDialog(null, "Enter a Gamertag:", gamertag);
		}
		

		if(gamertag==null||gamertag.equals(""))
		{
			gamertag="YourGamertag";
		}
		
		if(gamertag.length()>15)
			gamertag=gamertag.substring(0,15);
			
		if(check)
		{
			saveFileManager("save");
		}
	}
	public void mouseMoved(MouseEvent e)
	{
		mouse.setLocation(e.getX(),e.getY());
	}

	public void rechargeManager()
	{
		if(spartan.shields<=0)
		spartan.noshields.play();
		spartan.rechargeInt++;

		if(spartan.rechargeInt==600)
		{
			spartan.rechargeInt=0;
			spartan.recharge();
			shieldRechargesThisGame++;
		}

		if(spartan.recharging)
		{
			if(spartan.overshield==Spartan.Overshield.x4)
			{
				if(spartan.shields<100)
				spartan.shields++;
				if(spartan.shields>=100&&spartan.overshields[0]<100)
					spartan.overshields[0]++;
				if(spartan.overshields[0]>=100&&spartan.overshields[1]<100)
					spartan.overshields[1]++;
				if(spartan.overshields[1]>=100&&spartan.overshields[2]<100)
					spartan.overshields[2]++;
				if(spartan.overshields[2]>=100&&spartan.overshields[3]<100)
					spartan.overshields[3]++;
				if(spartan.overshields[3]>=100)
					spartan.recharging=false;
			}
			else if(spartan.overshield==Spartan.Overshield.x3)
			{
				if(spartan.shields<100)
				spartan.shields++;
				if(spartan.shields>=100&&spartan.overshields[0]<100)
					spartan.overshields[0]++;
				if(spartan.overshields[0]>=100&&spartan.overshields[1]<100)
					spartan.overshields[1]++;
				if(spartan.overshields[1]>=100&&spartan.overshields[2]<100)
					spartan.overshields[2]++;
				if(spartan.overshields[2]>=100)
					spartan.recharging=false;
			}
			else if(spartan.overshield==Spartan.Overshield.x2)
			{
				if(spartan.shields<100)
				spartan.shields++;
				if(spartan.shields>=100&&spartan.overshields[0]<100)
					spartan.overshields[0]++;
				if(spartan.overshields[0]>=100&&spartan.overshields[1]<100)
					spartan.overshields[1]++;
				if(spartan.overshields[1]>=100)
					spartan.recharging=false;
			}
			else if(spartan.overshield==Spartan.Overshield.x1)
			{
				if(spartan.shields<100)
				spartan.shields++;
				if(spartan.shields>=100)
					spartan.overshields[0]++;
				if(spartan.overshields[0]>=100)
					spartan.recharging=false;
			}
			else if(spartan.overshield==Spartan.Overshield.NONE)
			{
				if(spartan.shields<100)
				spartan.shields++;
				if(spartan.shields>=100)
					spartan.recharging=false;
			}
		}
	}

	public void mpRechargeManager()
	{
		if(spartans[mpID].shields<=0)
		spartans[mpID].noshields.play();
		spartans[mpID].rechargeInt++;

		if(spartans[mpID].rechargeInt==600)
		{
			spartans[mpID].rechargeInt=0;
			spartans[mpID].recharge();
		}

		if(spartans[mpID].recharging)
		{
			if(spartans[mpID].shields<100)
				spartans[mpID].shields++;
			if(spartans[mpID].shields>=100)
				spartans[mpID].recharging=false;
		}
	}

	public void reloadManager()
	{
		spartan.weapon.reloadInt++;

		if(spartan.weapon.reloadInt>=spartan.weapon.reloadTime)
		{
			spartan.weapon.reloadInt=0;
			spartan.weapon.reloading=false;
		}
	}

	public void mpReloadManager()
	{
		spartans[mpID].weapon.reloadInt++;

		if(spartans[mpID].weapon.reloadInt>=spartans[mpID].weapon.reloadTime)
		{
			spartans[mpID].weapon.reloadInt=0;
			spartans[mpID].weapon.reloading=false;
		}
	}

	public void runBots()
	{
		//Grunts
		for(int i=0; i<gruntSpawn; i++)
		{
			if(grunts[i].active)
			{
				grunts[i].actionDelay++;
				if(grunts[i].actionDelay>=25)
				{
					grunts[i].runAI();
					grunts[i].actionDelay=0;
				}
				if(grunts[i].weapon.shooting)
				{
					grunts[i].weapon.checkProjectile();
					grunts[i].weapon.bulletTravel();
				}
			}
		}

		//Elites
		for(int i=0; i<eliteSpawn; i++)
		{
			if(elites[i].active)
			{
				elites[i].actionDelay++;
				if(elites[i].actionDelay>=25)
				{
					elites[i].runAI(elites[i].direction,spartan.location.x,spartan.location.y);
					elites[i].actionDelay=0;
				}
				if(elites[i].weapon.shooting)
				{
					elites[i].weapon.checkProjectile();
					elites[i].weapon.bulletTravel();
				}
			}
		}
		//Jackals
		for(int i=0; i<jackalSpawn; i++)
		{
			if(jackals[i].active)
			{
				jackals[i].actionDelay++;
				if(jackals[i].actionDelay>=25)
				{
					jackals[i].runAI(spartan.location.x,spartan.location.y);
					jackals[i].actionDelay=0;
				}
				if(jackals[i].weapon.shooting)
				{
					jackals[i].weapon.checkProjectile();
					jackals[i].weapon.bulletTravel();
				}
			}
		}
	}

	public void mpCheckGunfire()
	{
		//Check Spartan Bullets
			for(int i=0; i<mpTotalPlayers; i++)
			{
				if(spartans[i].weapon.shooting)
				{
				for(int x=0; x<mpTotalPlayers; x++)
				{
					if(spartans[i].alive)
					{
						if(Math.abs(spartans[i].weapon.projectile.x-spartans[x].location.x)<=spartans[i].weapon.bulletSize&&
							Math.abs(spartans[i].weapon.projectile.y-spartans[x].location.y)<=spartans[i].weapon.bulletSize&&x!=i)
						{
							spartans[x].takeDamage(spartans[i].weapon.strength);
							mpShotsHit++;
							spartans[i].weapon.shooting=false;
							if(!spartans[x].alive)
							{
								mpExp[i]+=100;
								spartans[x].mpRespawn(mpGametypeInt);
								mainMessage=""+mpGamertags[i]+" beat down "+mpGamertags[x];
								mainMessageInt=0;
								//mpCurrentKills[i]+=1;
								//mpSpree[i]++;
							}
						}
					}
				}
				}
			}
	}

	public void checkGunfire()
	{
		//Check Spartan Bullets
		if(spartan.weapon.shooting)
		{
			//Grunts
			for(int i=0; i<gruntSpawn; i++)
			{
				if(grunts[i].active)
				{
					if(Math.abs(spartan.weapon.projectile.x-grunts[i].location.x)<=spartan.weapon.bulletSize&&
						Math.abs(spartan.weapon.projectile.y-grunts[i].location.y)<=spartan.weapon.bulletSize)
					{
						grunts[i].takeDamage(spartan.weapon.strength);
						System.out.println("Grunt Hit");
						shotsHit++;
						spartan.weapon.shooting=false;
						if(!grunts[i].active)
						{
							System.out.println("Grunt Killed");
							gruntsKilledThisGame++;
							exp+=grunts[i].EXP_YIELD;
							score+=grunts[i].EXP_YIELD;
							kills++;
							multikill++;
							spree++;
							if(spree==10)
							{
								mainMessage="Killing Spree! 10 Kills";
								exp+=100;
							}
							else if(spree==20)
							{
								mainMessage="Killing Frenzy! 20 Kills";
								exp+=150;
							}
							else if(spree==30)
							{
								mainMessage="Running Riot! 30 Kills";
								exp+=200;
							}
							else if(spree==40)
							{
								mainMessage="Rampancy! 40 Kills";
								exp+=300;
							}
							else if(spree==50)
							{
								mainMessage="Untouchable! 50 Kills";
								exp+=500;
							}
							else if(spree==75)
							{
								mainMessage="Invincible! 75 Kills";
								exp+=750;
							}
							else if(spree==100)
							{
								mainMessage="Ridiculous! 100 Kills";
								exp+=1000;
							}
							else if(spree==500)
							{
								mainMessage="Unfrigginbelievable! 500 Kills";
								exp+=5000;
							}

							else if(multikill==2)
							{
								mainMessage="Double Kill! +20 Exp";
								exp+=20;
							}
							else if(multikill==3)
							{
								mainMessage="Triple Kill! +40 Exp";
								exp+=40;
							}
							else if(multikill==4)
							{
								mainMessage="Overkill! +80 Exp";
								exp+=80;
							}
							else if(multikill==5)
							{
								mainMessage="Killtacular! +160 Exp";
								exp+=160;
							}
							else if(multikill==6)
							{
								mainMessage="Killtrocity! +320 Exp";
								exp+=320;
							}
							else if(multikill>=7)
							{
								mainMessage="Killimanjaro! +640 Exp";
								exp+=640;
							}
							mainMessageInt=0;
							medalImageManager();
						}
					}
				}
			}

			//Elites
			for(int i=0; i<eliteSpawn; i++)
			{
				if(elites[i].active)
				{
					if(Math.abs(spartan.weapon.projectile.x-elites[i].location.x)<=spartan.weapon.bulletSize&&
						Math.abs(spartan.weapon.projectile.y-elites[i].location.y)<=spartan.weapon.bulletSize)
					{
						System.out.println("Elite Hit");
						elites[i].takeDamage(spartan.weapon.strength);
						shotsHit++;
						spartan.weapon.shooting=false;
						if(!elites[i].active)
						{
							System.out.println("Elite Killed");
							elitesKilledThisGame++;
							exp+=elites[i].EXP_YIELD;
							score+=elites[i].EXP_YIELD;
							kills++;
							multikill++;
							spree++;
							if(spree==10)
							{
								mainMessage="Killing Spree! 10 Kills";
								exp+=100;
							}
							else if(spree==20)
							{
								mainMessage="Killing Frenzy! 20 Kills";
								exp+=150;
							}
							else if(spree==30)
							{
								mainMessage="Running Riot! 30 Kills";
								exp+=200;
							}
							else if(spree==40)
							{
								mainMessage="Rampancy! 40 Kills";
								exp+=300;
							}
							else if(spree==50)
							{
								mainMessage="Untouchable! 50 Kills";
								exp+=500;
							}
							else if(spree==75)
							{
								mainMessage="Invincible! 75 Kills";
								exp+=750;
							}
							else if(spree==100)
							{
								mainMessage="Ridiculous! 100 Kills";
								exp+=1000;
							}
							else if(spree==500)
							{
								mainMessage="Unfrigginbelievable! 500 Kills";
								exp+=5000;
							}

							else if(multikill==2)
							{
								mainMessage="Double Kill! +20 Exp";
								exp+=20;
							}
							else if(multikill==3)
							{
								mainMessage="Triple Kill! +40 Exp";
								exp+=40;
							}
							else if(multikill==4)
							{
								mainMessage="Overkill! +80 Exp";
								exp+=80;
							}
							else if(multikill==5)
							{
								mainMessage="Killtacular! +160 Exp";
								exp+=160;
							}
							else if(multikill==6)
							{
								mainMessage="Killtrocity! +320 Exp";
								exp+=320;
							}
							else if(multikill>=7)
							{
								mainMessage="Killimanjaro! +640 Exp";
								exp+=640;
							}
							mainMessageInt=0;
							medalImageManager();

							//Nay, it was Heresy!
							if(achievements[5]==0)
							{
								achievements[5]=1;
								unlockedMessage=achievementNames[5];
								exp+=1000;
							}
						}
					}
				}
			}
			//Jackals
			for(int i=0; i<jackalSpawn; i++)
			{
				if(jackals[i].active)
				{
					if(Math.abs(spartan.weapon.projectile.x-jackals[i].location.x)<=spartan.weapon.bulletSize&&
						Math.abs(spartan.weapon.projectile.y-jackals[i].location.y)<=spartan.weapon.bulletSize)
					{
						System.out.println("Jackal Hit");
						jackalsKilledThisGame++;
						jackals[i].takeDamage(spartan.weapon.strength);
						shotsHit++;
						spartan.weapon.shooting=false;
						if(!jackals[i].active)
						{
							System.out.println("Jackal Killed");
							exp+=jackals[i].EXP_YIELD;
							score+=jackals[i].EXP_YIELD;
							kills++;
							multikill++;
							spree++;
							if(spree==10)
							{
								mainMessage="Killing Spree! 10 Kills";
								exp+=100;
							}
							else if(spree==20)
							{
								mainMessage="Killing Frenzy! 20 Kills";
								exp+=150;
							}
							else if(spree==30)
							{
								mainMessage="Running Riot! 30 Kills";
								exp+=200;
							}
							else if(spree==40)
							{
								mainMessage="Rampancy! 40 Kills";
								exp+=300;
							}
							else if(spree==50)
							{
								mainMessage="Untouchable! 50 Kills";
								exp+=500;
							}
							else if(spree==75)
							{
								mainMessage="Invincible! 75 Kills";
								exp+=750;
							}
							else if(spree==100)
							{
								mainMessage="Ridiculous! 100 Kills";
								exp+=1000;
							}
							else if(spree==500)
							{
								mainMessage="Unfrigginbelievable! 500 Kills";
								exp+=5000;
							}

							else if(multikill==2)
							{
								mainMessage="Double Kill! +20 Exp";
								exp+=20;
							}
							else if(multikill==3)
							{
								mainMessage="Triple Kill! +40 Exp";
								exp+=40;
							}
							else if(multikill==4)
							{
								mainMessage="Overkill! +80 Exp";
								exp+=80;
							}
							else if(multikill==5)
							{
								mainMessage="Killtacular! +160 Exp";
								exp+=160;
							}
							else if(multikill==6)
							{
								mainMessage="Killtrocity! +320 Exp";
								exp+=320;
							}
							else if(multikill>=7)
							{
								mainMessage="Killimanjaro! +640 Exp";
								exp+=640;
							}
							mainMessageInt=0;
							medalImageManager();
						}
					}
				}
			}
		}

		//Check Grunt Bullets
		for(int i=0; i<gruntSpawn; i++)
		{
			if(grunts[i].active)
			{
				if(grunts[i].weapon.shooting)
				{

					if(Math.abs(spartan.location.x-grunts[i].weapon.projectile.x)<=grunts[i].weapon.bulletSize&&
						Math.abs(spartan.location.y-grunts[i].weapon.projectile.y)<=grunts[i].weapon.bulletSize)
					{
						if(spartan.alive)
						spartan.takeDamage(grunts[i].weapon.strength);
						grunts[i].weapon.shooting=false;
					}
				}
			}
		}

		//Check Elite Bullets
		for(int i=0; i<eliteSpawn; i++)
		{
			if(elites[i].active)
			{
				if(elites[i].weapon.shooting)
				{

					if(Math.abs(spartan.location.x-elites[i].weapon.projectile.x)<=elites[i].weapon.bulletSize&&
						Math.abs(spartan.location.y-elites[i].weapon.projectile.y)<=elites[i].weapon.bulletSize)
					{
						if(spartan.alive)
						spartan.takeDamage(elites[i].weapon.strength);
						elites[i].weapon.shooting=false;
					}
				}
			}
		}
		//Check Jackal Bullets
		for(int i=0; i<jackalSpawn; i++)
		{
			if(jackals[i].active)
			{
				if(jackals[i].weapon.shooting)
				{

					if(Math.abs(spartan.location.x-jackals[i].weapon.projectile.x)<=jackals[i].weapon.bulletSize&&
						Math.abs(spartan.location.y-jackals[i].weapon.projectile.y)<=jackals[i].weapon.bulletSize)
					{
						if(spartan.alive)
						spartan.takeDamage(jackals[i].weapon.strength);
						jackals[i].weapon.shooting=false;
					}
				}
			}
		}

		if(!spartan.alive&&mainMessage.equals(""))
		{
			deaths++;
			lives--;
			if(spree>bestSpree)
				bestSpree=spree;

			spree=0;

			//They're too strong!
			if(achievements[31]==0&&spartan.overshield==Spartan.Overshield.x4&&difInt==0)
			{
				achievements[31]=1;
				unlockedMessage=achievementNames[31];
				exp+=5000;
			}
			if(lives>0)
			mainMessage="Respawning...";
			else
			mainMessage="Game over";
		}
	}

	public void checkMovement()
	{
		if(keys[1]&&spartan.alive)
		{
			spartan.move("E");
		}

		if(keys[3]&&spartan.alive)
		{
			spartan.move("W");
		}

		if(keys[0]&&spartan.alive)
		{
			spartan.move("N");
		}

		if(keys[2]&&spartan.alive)
		{
			spartan.move("S");
		}
	}

	public void mpCheckMovement()
	{
		if(keys[1]&&spartans[mpID].alive)
		{
			spartans[mpID].move("E");
		}

		if(keys[3]&&spartans[mpID].alive)
		{
			spartans[mpID].move("W");
		}

		if(keys[0]&&spartans[mpID].alive)
		{
			spartans[mpID].move("N");
		}

		if(keys[2]&&spartans[mpID].alive)
		{
			spartans[mpID].move("S");
		}
	}

	public void mpCheckMelee()
	{
		for(int i=0; i<mpTotalPlayers; i++)
		{
			if(spartans[i].meleeing)
			{
				for(int x=0; x<mpTotalPlayers; x++)
				{
					if(spartans[i].alive)
					{
						if(Math.abs(spartans[i].location.x-spartans[x].location.x)<=30&&
							Math.abs(spartans[i].location.y-spartans[x].location.y)<=30&&x!=i)
						{
							spartans[x].takeDamage(100);
							spartans[i].weapon.shooting=false;
							if(!spartans[x].alive)
							{
								mpExp[i]+=100;
								spartans[x].mpRespawn(mpGametypeInt);
								mainMessage=""+mpGamertags[i]+" beat down "+mpGamertags[x];
								mainMessageInt=0;
								//mpCurrentKills[i]+=1;
								//mpSpree[i]++;
							}
						}
					}
				}
			}
		}
	}

	public void checkMelee()
	{
		if(spartan.meleeing&&spartan.meleeInt>=80)
		{
			//Grunts
			for(int i=0; i<gruntSpawn; i++)
			{
				if(grunts[i].active)
				{
					if(Math.abs(spartan.location.x-grunts[i].location.x)<=30&&
						Math.abs(spartan.location.y-grunts[i].location.y)<=30)
					{
						grunts[i].takeDamage(50);
						if(armoryInt[0]==1)
							grunts[i].takeDamage(5000);
						if(!grunts[i].active)
						{
							System.out.println("Grunt Beat Down");
							gruntsKilledThisGame++;
							exp+=grunts[i].EXP_YIELD;
							kills++;
							multikill++;
							spree++;
							if(spree==10)
							{
								mainMessage="Killing Spree! 10 Kills";
								exp+=100;
							}
							else if(spree==20)
							{
								mainMessage="Killing Frenzy! 20 Kills";
								exp+=150;
							}
							else if(spree==30)
							{
								mainMessage="Running Riot! 30 Kills";
								exp+=200;
							}
							else if(spree==40)
							{
								mainMessage="Rampancy! 40 Kills";
								exp+=300;
							}
							else if(spree==50)
							{
								mainMessage="Untouchable! 50 Kills";
								exp+=500;
							}
							else if(spree==75)
							{
								mainMessage="Invincible! 75 Kills";
								exp+=750;
							}
							else if(spree==100)
							{
								mainMessage="Ridiculous! 100 Kills";
								exp+=1000;
							}
							else if(spree==500)
							{
								mainMessage="Unfrigginbelievable! 500 Kills";
								exp+=5000;
							}

							else if(multikill==2)
							{
								mainMessage="Double Kill! +20 Exp";
								exp+=20;
							}
							else if(multikill==3)
							{
								mainMessage="Triple Kill! +40 Exp";
								exp+=40;
							}
							else if(multikill==4)
							{
								mainMessage="Overkill! +80 Exp";
								exp+=80;
							}
							else if(multikill==5)
							{
								mainMessage="Killtacular! +160 Exp";
								exp+=160;
							}
							else if(multikill==6)
							{
								mainMessage="Killtrocity! +320 Exp";
								exp+=320;
							}
							else if(multikill>=7)
							{
								mainMessage="Killimanjaro! +640 Exp";
								exp+=640;
							}
							mainMessageInt=0;
							medalImageManager();

							//Pawnch!
							if(achievements[9]==0)
							{
								achievements[9]=1;
								unlockedMessage=achievementNames[9];
								exp+=5000;
							}
							//No Grenades?!
							if(achievements[33]==0&&spartan.weapon.currentAmmo==0&&spartan.weapon.ammoInClip==0)
							{
								achievements[33]=1;
								unlockedMessage=achievementNames[33];
								exp+=15000;
							}
						}
					}
				}
			}

			//Elites
			for(int i=0; i<eliteSpawn; i++)
			{
				if(elites[i].active)
				{
					if(Math.abs(spartan.location.x-elites[i].location.x)<=30&&
						Math.abs(spartan.location.y-elites[i].location.y)<=30)
					{
						elites[i].takeDamage(50);
						if(armoryInt[0]==1)
							elites[i].takeDamage(5000);
							
						if(!elites[i].active)
						{
							System.out.println("Elite Beat Down");
							elitesKilledThisGame++;
							exp+=elites[i].EXP_YIELD;
							kills++;
							multikill++;
							spree++;
							if(spree==10)
							{
								mainMessage="Killing Spree! 10 Kills";
								exp+=100;
							}
							else if(spree==20)
							{
								mainMessage="Killing Frenzy! 20 Kills";
								exp+=150;
							}
							else if(spree==30)
							{
								mainMessage="Running Riot! 30 Kills";
								exp+=200;
							}
							else if(spree==40)
							{
								mainMessage="Rampancy! 40 Kills";
								exp+=300;
							}
							else if(spree==50)
							{
								mainMessage="Untouchable! 50 Kills";
								exp+=500;
							}
							else if(spree==75)
							{
								mainMessage="Invincible! 75 Kills";
								exp+=750;
							}
							else if(spree==100)
							{
								mainMessage="Ridiculous! 100 Kills";
								exp+=1000;
							}
							else if(spree==500)
							{
								mainMessage="Unfrigginbelievable! 500 Kills";
								exp+=5000;
							}

							else if(multikill==2)
							{
								mainMessage="Double Kill! +20 Exp";
								exp+=20;
							}
							else if(multikill==3)
							{
								mainMessage="Triple Kill! +40 Exp";
								exp+=40;
							}
							else if(multikill==4)
							{
								mainMessage="Overkill! +80 Exp";
								exp+=80;
							}
							else if(multikill==5)
							{
								mainMessage="Killtacular! +160 Exp";
								exp+=160;
							}
							else if(multikill==6)
							{
								mainMessage="Killtrocity! +320 Exp";
								exp+=320;
							}
							else if(multikill>=7)
							{
								mainMessage="Killimanjaro! +640 Exp";
								exp+=640;
							}
							mainMessageInt=0;
							medalImageManager();

							//Pawnch!
							if(achievements[9]==0)
							{
								achievements[9]=1;
								unlockedMessage=achievementNames[9];
								exp+=500;
							}
							//No Grenades?!
							if(achievements[33]==0&&spartan.weapon.currentAmmo==0&&spartan.weapon.ammoInClip==0)
							{
								achievements[33]=1;
								unlockedMessage=achievementNames[33];
								exp+=1500;
							}
						}
					}
				}
			}

			//Jackals
			for(int i=0; i<jackalSpawn; i++)
			{
				if(jackals[i].active)
				{
					if(Math.abs(spartan.location.x-jackals[i].location.x)<=30&&
						Math.abs(spartan.location.y-jackals[i].location.y)<=30)
					{
						jackals[i].takeDamage(50);
						
						if(armoryInt[0]==1)
							jackals[i].takeDamage(5000);
							
						if(!jackals[i].active)
						{
							System.out.println("Jackal Pummeled");
							jackalsKilledThisGame++;
							exp+=jackals[i].EXP_YIELD;
							kills++;
							multikill++;
							spree++;
							if(spree==10)
							{
								mainMessage="Killing Spree! 10 Kills";
								exp+=100;
							}
							else if(spree==20)
							{
								mainMessage="Killing Frenzy! 20 Kills";
								exp+=150;
							}
							else if(spree==30)
							{
								mainMessage="Running Riot! 30 Kills";
								exp+=200;
							}
							else if(spree==40)
							{
								mainMessage="Rampancy! 40 Kills";
								exp+=300;
							}
							else if(spree==50)
							{
								mainMessage="Untouchable! 50 Kills";
								exp+=500;
							}
							else if(spree==75)
							{
								mainMessage="Invincible! 75 Kills";
								exp+=750;
							}
							else if(spree==100)
							{
								mainMessage="Ridiculous! 100 Kills";
								exp+=1000;
							}
							else if(spree==500)
							{
								mainMessage="Unfrigginbelievable! 500 Kills";
								exp+=5000;
							}

							else if(multikill==2)
							{
								mainMessage="Double Kill! +20 Exp";
								exp+=20;
							}
							else if(multikill==3)
							{
								mainMessage="Triple Kill! +40 Exp";
								exp+=40;
							}
							else if(multikill==4)
							{
								mainMessage="Overkill! +80 Exp";
								exp+=80;
							}
							else if(multikill==5)
							{
								mainMessage="Killtacular! +160 Exp";
								exp+=160;
							}
							else if(multikill==6)
							{
								mainMessage="Killtrocity! +320 Exp";
								exp+=320;
							}
							else if(multikill>=7)
							{
								mainMessage="Killimanjaro! +640 Exp";
								exp+=640;
							}
							mainMessageInt=0;
							medalImageManager();

							//Pawnch!
							if(achievements[9]==0)
							{
								achievements[9]=1;
								unlockedMessage=achievementNames[9];
								exp+=500;
							}
							//No Grenades?!
							if(achievements[33]==0&&spartan.weapon.currentAmmo==0&&spartan.weapon.ammoInClip==0)
							{
								achievements[33]=1;
								unlockedMessage=achievementNames[33];
								exp+=1500;
							}
						}
					}
				}
			}
		}
		else
		{
			if(spartan.meleeInt<0)
			{
				spartan.meleeInt=0;
				spartan.meleeing=false;
			}
			else
				spartan.meleeInt--;
		}

		//Elites Melee
			for(int i=0; i<eliteSpawn; i++)
			{
				if(elites[i].active)
				{
					if(Math.abs(spartan.location.x-elites[i].location.x)<=10&&
						Math.abs(spartan.location.y-elites[i].location.y)<=10)
					{
						spartan.takeDamage(1);
						System.out.println("Elite Absorbing Health");
					}
				}
			}

		if(!spartan.alive&&mainMessage.equals(""))
		{
			deaths++;
			lives--;
			if(spree>bestSpree)
				bestSpree=spree;
			spree=0;

			if(lives>0)
			mainMessage="Respawning...";
			else
			mainMessage="Game over";
		}
	}

	public void mpMeleeManager()
	{
		if(!spartans[mpID].meleeing&&spartans[mpID].meleeInt>=80)
		if(spartans[mpID].meleeInt<0)
		{
			spartan.meleeInt=0;
			spartan.meleeing=false;
		}
		else
			spartan.meleeInt--;
	}

	public void firefightManager()
	{
		int check=0;

		//check grunts
		for(int i=0; i<gruntSpawn; i++)
		{
			if(!grunts[i].active)
				check++;
		}

		//check elites
		for(int i=0; i<eliteSpawn; i++)
		{
			if(!elites[i].active)
				check++;
		}

		//check jackals
		for(int i=0; i<jackalSpawn; i++)
		{
			if(!jackals[i].active)
				check++;
		}

		if(check>=gruntSpawn+eliteSpawn+jackalSpawn)
		{
			if(spartan.health==spartan.maxHealth)
				consecNoDmgWaves++;
			System.out.println("All Enemies Destroyed");
			if(gametypeInt==5)
			{
				level++;
				levelSigner();
				exp+=20*level;
				if(mainMessage.equals(""))
					mainMessage="Level: "+level;
				mainMessageInt=0;
			}
			else
			{
				wave++;
				exp+=20*wave;
				if(mainMessage.equals(""))
					mainMessage="Wave: "+wave+" +"+wave*20+" Exp";
				setRoundWave();
				mainMessageInt=0;
			}
			gametypeManager();
		}
		else if(!oneEnemyLeft&&check==gruntSpawn+eliteSpawn+jackalSpawn-1)
		{
			mainMessage="One Enemy Remaining";
			System.out.println("Single Enemy Remaining");
			mainMessageInt=0;
			oneEnemyLeft=true;
		}
	}

	public void addMultiplier()
	{
		switch(difInt)
		{
			case 0:
				multiplier=0.5;
				break;
			case 1:
				multiplier=1.0;
				break;
			case 2:
				multiplier=1.5;
				break;
			case 3:
				multiplier=2.0;
				break;
		}

		//Enemy Boosts
		for(int i=0; i<gruntSpawn; i++)
		{
			grunts[i].health=(int)(multiplier*grunts[i].health);
			grunts[i].weapon.strength=(int)(multiplier*grunts[i].weapon.strength);
			grunts[i].EXP_YIELD=(int)(multiplier*grunts[i].EXP_YIELD);
		}

		for(int i=0; i<eliteSpawn; i++)
		{
			elites[i].health=(int)(multiplier*elites[i].health);
			elites[i].weapon.strength=(int)(multiplier*elites[i].weapon.strength);
			elites[i].EXP_YIELD=(int)(multiplier*elites[i].EXP_YIELD);
		}

		for(int i=0; i<jackalSpawn; i++)
		{
			jackals[i].health=(int)(multiplier*jackals[i].health);
			jackals[i].weapon.strength=(int)(multiplier*jackals[i].weapon.strength);
			jackals[i].EXP_YIELD=(int)(multiplier*jackals[i].EXP_YIELD);
		}

		System.out.println("Difficulty Multiplier Applied");
	}
	
	public void armoryEffects()
	{
		switch(armoryInt[1])
		{
			case 1:
				spartan.weapon.reloadTime/=1.25;
				break;
			case 2:
				spartan.weapon.reloadTime/=1.5;
				break;
			case 3:
				spartan.weapon.reloadTime/=2;
				break;
			default:
				break;
		}
		
		if(gametypeInt!=1)
		{
			switch(armoryInt[2])
			{
				case 1:
					spartan.weapon.maxAmmo+=1*spartan.weapon.clipSize;
					spartan.weapon.currentAmmo=spartan.weapon.maxAmmo;
					break;
				case 2:
					spartan.weapon.maxAmmo+=2*spartan.weapon.clipSize;
					spartan.weapon.currentAmmo=spartan.weapon.maxAmmo;
					break;
				case 3:
					spartan.weapon.maxAmmo+=3*spartan.weapon.clipSize;
					spartan.weapon.currentAmmo=spartan.weapon.maxAmmo;
					break;
				default:
					break;
			}
		}
		
		switch(armoryInt[3])
		{
			case 1:
				spartan.maxHealth=50;
				spartan.health=spartan.maxHealth;
				break;
			case 2:
				spartan.maxHealth=100;
				spartan.health=spartan.maxHealth;
				break;
			case 3:
				spartan.maxHealth=300;
				spartan.health=spartan.maxHealth;
				break;
			default:
				break;
		}
		
		switch(armoryInt[4])
		{
			case 1:
				spartan.overshield=Spartan.Overshield.x1;
				break;
			case 2:
				spartan.overshield=Spartan.Overshield.x2;
				break;
			case 3:
				spartan.overshield=Spartan.Overshield.x3;
				break;
			case 4:
				spartan.overshield=Spartan.Overshield.x4;
				break;
			default:
				break;
		}
		
		switch(armoryInt[5])
		{
			case 1:
				spartan.sprite="Grunt";
				break;
			case 2:
				spartan.sprite="Jackal";
				break;
			case 3:
				spartan.sprite="Elite";
				break;
			default:
				break;
		}
		
		spartan.changeSprite();
		
		switch(armoryInt[6])
		{
			case 0:
				hudcolorR=0;
				hudcolorG=0;
				hudcolorB=255;
				break;
			case 1:
				hudcolorR=0;
				hudcolorG=255;
				hudcolorB=0;
				break;
			case 2:
				hudcolorR=255;
				hudcolorG=0;
				hudcolorB=0;
				break;
			case 3:
				hudcolorR=0;
				hudcolorG=0;
				hudcolorB=0;
				break;
			case 4:
				hudcolorR=255;
				hudcolorG=255;
				hudcolorB=0;
				break;
			default:
				break;
		}
	}

	public void levelSigner()
	{

		if(level==1)
			levelCode="5F18";
		else if(level==2)
			levelCode="019F";
		else if(level==3)
			levelCode="5F78";
		else if(level==4)
			levelCode="2E9C";
		else if(level==5)
			levelCode="1E1F";
		else if(level==6)
			levelCode="5DCD";
		else if(level==7)
			levelCode="CE9C";
		else if(level==8)
			levelCode="E2C7";
		else if(level==9)
			levelCode="4E78";
		else if(level==10)
			levelCode="ADD8";
		else if(level==11)
			levelCode="5599";
		else if(level==12)
			levelCode="EF8B";
		else if(level==13)
			levelCode="4AF7";
		else if(level==14)
			levelCode="6175";
		else if(level==15)
			levelCode="BAE7";
		else if(level==16)
			levelCode="F38D";
		else if(level==17)
			levelCode="017D";
		else if(level==18)
			levelCode="67AA";
		else if(level==19)
			levelCode="E2BB";
		else if(level==20)
			levelCode="8238";
		else if(level==21)
			levelCode="9DB3";
		else if(level==22)
			levelCode="0678";
		else if(level==23)
			levelCode="20D1";
		else if(level==24)
			levelCode="1354";
		else if(level==25)
			levelCode="E399";
		else if(level==26)
			levelCode="F080";
		else if(level==27)
			levelCode="686A";
		else if(level==28)
			levelCode="5BCF";
		else if(level==29)
			levelCode="5242";
		else if(level==30)
			levelCode="DB47";
		else if(level==31)
			levelCode="52BC";
		else if(level==32)
			levelCode="3705";
		else if(level==33)
			levelCode="76A2";
		else if(level==34)
			levelCode="5EF1";
		else if(level==35)
			levelCode="E378";
		else if(level==36)
			levelCode="35E1";
		else if(level==37)
			levelCode="3EFC";
		else if(level==38)
			levelCode="2D7B";
		else if(level==39)
			levelCode="AED2";
		else if(level==40)
			levelCode="94ED";
		else if(level==41)
			levelCode="79F2";
		else if(level==42)
			levelCode="34B5";
		else if(level==43)
			levelCode="411D";
		else if(level==44)
			levelCode="92FA";
		else if(level==45)
			levelCode="3FDA";
		else if(level==46)
			levelCode="8015";
		else if(level==47)
			levelCode="A76D";
		else if(level==48)
			levelCode="B1A7";
		else if(level==49)
			levelCode="9062";
		else if(level==50)
			levelCode="FC90";

		highestLevel=level;
		System.out.println("Level Signed Successfully");
	}

	public void banhammer()
	{
		boolean banned=false;

		if(exp+expCode!=50000000)
			banned=true;
		
		if(unlockCode+unlockInt!=13)
			banned=true;

		if(highestLevel>50)
			banned=true;

		if(level==1&&levelCode.equals("5F18"))
			banned=false;
		else if(level==2&&levelCode.equals("019F"))
			banned=false;
		else if(level==3&&levelCode.equals("5F78"))
			banned=false;
		else if(level==4&&levelCode.equals("2E9C"))
			banned=false;
		else if(level==5&&levelCode.equals("1E1F"))
			banned=false;
		else if(level==6&&levelCode.equals("5DCD"))
			banned=false;
		else if(level==7&&levelCode.equals("CE9C"))
			banned=false;
		else if(level==8&&levelCode.equals("E2C7"))
			banned=false;
		else if(level==9&&levelCode.equals("4E78"))
			banned=false;
		else if(level==10&&levelCode.equals("ADD8"))
			banned=false;
		else if(level==11&&levelCode.equals("5599"))
			banned=false;
		else if(level==12&&levelCode.equals("EF8B"))
			banned=false;
		else if(level==13&&levelCode.equals("4AF7"))
			banned=false;
		else if(level==14&&levelCode.equals("6175"))
			banned=false;
		else if(level==15&&levelCode.equals("BAE7"))
			banned=false;
		else if(level==16&&levelCode.equals("F38D"))
			banned=false;
		else if(level==17&&levelCode.equals("017D"))
			banned=false;
		else if(level==18&&levelCode.equals("67AA"))
			banned=false;
		else if(level==19&&levelCode.equals("E2BB"))
			banned=false;
		else if(level==20&&levelCode.equals("8238"))
			banned=false;
		else if(level==21&&levelCode.equals("9DB3"))
			banned=false;
		else if(level==22&&levelCode.equals("0678"))
			banned=false;
		else if(level==23&&levelCode.equals("20D1"))
			banned=false;
		else if(level==24&&levelCode.equals("1354"))
			banned=false;
		else if(level==25&&levelCode.equals("E399"))
			banned=false;
		else if(level==26&&levelCode.equals("F080"))
			banned=false;
		else if(level==27&&levelCode.equals("686A"))
			banned=false;
		else if(level==28&&levelCode.equals("5BCF"))
			banned=false;
		else if(level==29&&levelCode.equals("5242"))
			banned=false;
		else if(level==30&&levelCode.equals("DB47"))
			banned=false;
		else if(level==31&&levelCode.equals("52BC"))
			banned=false;
		else if(level==32&&levelCode.equals("3705"))
			banned=false;
		else if(level==33&&levelCode.equals("76A2"))
			banned=false;
		else if(level==34&&levelCode.equals("5EF1"))
			banned=false;
		else if(level==35&&levelCode.equals("E378"))
			banned=false;
		else if(level==36&&levelCode.equals("35E1"))
			banned=false;
		else if(level==37&&levelCode.equals("3EFC"))
			banned=false;
		else if(level==38&&levelCode.equals("2D7B"))
			banned=false;
		else if(level==39&&levelCode.equals("AED2"))
			banned=false;
		else if(level==40&&levelCode.equals("94ED"))
			banned=false;
		else if(level==41&&levelCode.equals("79F2"))
			banned=false;
		else if(level==42&&levelCode.equals("34B5"))
			banned=false;
		else if(level==43&&levelCode.equals("411D"))
			banned=false;
		else if(level==44&&levelCode.equals("92FA"))
			banned=false;
		else if(level==45&&levelCode.equals("3FDA"))
			banned=false;
		else if(level==46&&levelCode.equals("8015"))
			banned=false;
		else if(level==47&&levelCode.equals("A76D"))
			banned=false;
		else if(level==48&&levelCode.equals("B1A7"))
			banned=false;
		else if(level==49&&levelCode.equals("9062"))
			banned=false;
		else if(level==50&&levelCode.equals("FC90"))
			banned=false;
		else
			banned=true;

		if(banned)
		{
			rank="Banned";
			System.out.println("Data File Hacked");
			System.out.println("Banning File...Done.");
			System.out.println("Applying Ban...");
		}
		else
		{
			System.out.println("File Manipulation Undetected");
			System.out.print("Resaving File...");
		}
	}

	public void challengeManager()
	{
		//Call of Jalo I
		if(challenges[0]==0&&gamesPlayed>=49)
		{
			challenges[0]=1;
			unlockedMessage=challengeNames[0];
			armoryItemNames[0][1]="Combat Knife";
		}
		//Call of Jalo II
		if(challenges[1]==0&&gamesPlayed>=299)
		{
			challenges[1]=1;
			unlockedMessage=challengeNames[1];
			armoryItemNames[0][2]="Laser Sight";
		}
		//Handy Dandy I
		if(challenges[2]==0&&reloadsThisGame>=10)
		{
			challenges[2]=1;
			unlockedMessage=challengeNames[2];
			armoryItemNames[1][1]="1.25x";
			reloadsThisGame=0;
			
		}
		//Handy Dandy II
		if(challenges[3]==0&&reloadsThisGame>=50)
		{
			challenges[3]=1;
			unlockedMessage=challengeNames[3];
			armoryItemNames[1][2]="1.5x";
			reloadsThisGame=0;
		}
		//Handy Dandy III
		if(challenges[4]==0&&reloadsThisGame>=100)
		{
			challenges[4]=1;
			unlockedMessage=challengeNames[4];
			armoryItemNames[1][3]="2x";
			reloadsThisGame=0;
		}
		//Rambo I
		if(challenges[5]==0&&shotsFired>=2500)
		{
			challenges[5]=1;
			unlockedMessage=challengeNames[5];
			armoryItemNames[2][1]="1";
		}
		//Rambo II
		if(challenges[6]==0&&shotsFired>=10000)
		{
			challenges[6]=1;
			unlockedMessage=challengeNames[6];
			armoryItemNames[2][2]="2";
		}
		//Rambo III
		if(challenges[7]==0&&shotsFired>=25000)
		{
			challenges[7]=1;
			unlockedMessage=challengeNames[7];
			armoryItemNames[2][3]="3";
		}
		//Juggernaut I
		if(challenges[8]==0&&consecNoDmgWaves>=5)
		{
			challenges[8]=1;
			unlockedMessage=challengeNames[8];
			armoryItemNames[3][1]="50";
		}
		//Juggernaut II
		if(challenges[9]==0&&consecNoDmgWaves>=15)
		{
			challenges[9]=1;
			unlockedMessage=challengeNames[9];
			armoryItemNames[3][2]="100";
		}
		//Juggernaut III
		if(challenges[10]==0&&consecNoDmgWaves>=45)
		{
			challenges[10]=1;
			unlockedMessage=challengeNames[10];
			armoryItemNames[3][3]="300";
		}
		//Space Man I
		if(challenges[11]==0&&shieldRechargesThisGame>=25)
		{
			challenges[11]=1;
			unlockedMessage=challengeNames[11];
			armoryItemNames[4][1]="1x";
		}
		//Space Man II
		if(challenges[12]==0&&shieldRechargesThisGame>=50)
		{
			challenges[12]=1;
			unlockedMessage=challengeNames[12];
			armoryItemNames[4][2]="2x";
		}
		//Space Man III
		if(challenges[13]==0&&shieldRechargesThisGame>=100)
		{
			challenges[13]=1;
			unlockedMessage=challengeNames[13];
			armoryItemNames[4][3]="3x";
		}
		//Space Man IV
		if(challenges[14]==0&&shieldRechargesThisGame>=250)
		{
			challenges[14]=1;
			unlockedMessage=challengeNames[14];
			armoryItemNames[4][4]="4x";
		}
		//Crossdresser I
		if(challenges[15]==0&&gruntsKilledThisGame>=100)
		{
			challenges[15]=1;
			unlockedMessage=challengeNames[15];
			armoryItemNames[5][1]="Grunt";
		}
		//Crossdresser II
		if(challenges[16]==0&&jackalsKilledThisGame>=100)
		{
			challenges[16]=1;
			unlockedMessage=challengeNames[16];
			armoryItemNames[5][2]="Jackal";
		}
		//Crossdresser III
		if(challenges[17]==0&&elitesKilledThisGame>=100)
		{
			challenges[17]=1;
			unlockedMessage=challengeNames[17];
			armoryItemNames[5][3]="Elite";
		}
		//Heads Up! I
		if(challenges[18]==0&&kills>=100)
		{
			challenges[18]=1;
			unlockedMessage=challengeNames[18];
			armoryItemNames[6][1]="Green";
		}	
		//Heads Up! II
		if(challenges[19]==0&&kills>=500)
		{
			challenges[19]=1;
			unlockedMessage=challengeNames[19];
			armoryItemNames[6][2]="Red";
		}
		//Heads Up! III
		if(challenges[20]==0&&kills>=1000)
		{
			challenges[20]=1;
			unlockedMessage=challengeNames[20];
			armoryItemNames[6][3]="Black";
		}
		//Heads Up! IV
		if(challenges[21]==0&&kills>=10000)
		{
			challenges[21]=1;
			unlockedMessage=challengeNames[21];
			armoryItemNames[6][4]="Gold";
		}
		//U for Uranium I
		if(challenges[22]==0&&exp>=1000)
		{
			challenges[22]=1;
			unlockedMessage=challengeNames[22];
			armoryItemNames[7][1]="Black and White";
		}
		//U for Uranium II
		if(challenges[23]==0&&exp>=10000)
		{
			challenges[23]=1;
			unlockedMessage=challengeNames[23];
			armoryItemNames[7][2]="Active Camo";
		}
		//U for Uranium III
		if(challenges[24]==0&&exp>=100000)
		{
			challenges[24]=1;
			unlockedMessage=challengeNames[24];
			armoryItemNames[7][3]="Funky";
		}
	}

	public void achievementManager()
	{
		//I'm so 1337
		if(achievements[0]==0&&set>1&&difInt==3)
		{
			achievements[0]=1;
			unlockedMessage=achievementNames[0];
			exp+=75000;
		}
		//I Need a Weapon
		if(achievements[1]==0&&unlockInt>=13)
		{
			achievements[1]=1;
			unlockedMessage=achievementNames[1];
			exp+=10000;
		}
		//BFG
		if(achievements[2]==0&&weaponUsage[13]>=1)
		{
			achievements[2]=1;
			unlockedMessage=achievementNames[2];
			exp+=5000;
		}
		//Officer Spartan
		if(achievements[3]==0&&unlockInt>=7)
		{
			achievements[3]=1;
			unlockedMessage=achievementNames[3];
			exp+=15000;
		}
		//Newbie
		if(achievements[4]==0&&set>1&&difInt==0)
		{
			achievements[4]=1;
			unlockedMessage=achievementNames[4];
			exp+=10000;
		}
		//Colt .45
		if(achievements[6]==0&&set>1&&wpnChoiceInt==1)
		{
			achievements[6]=1;
			unlockedMessage=achievementNames[6];
			exp+=25000;
		}
		//Orpheus
		if(achievements[7]==0&&set>1&&gametypeInt==4&&difInt==3)
		{
			achievements[7]=1;
			unlockedMessage=achievementNames[7];
			exp+=250000;
		}
		//Perfection
		if(achievements[8]==0&&set>=3&&spartan.health==25&&gametypeInt==0)
		{
			achievements[8]=1;
			unlockedMessage=achievementNames[8];
			exp+=65000;
		}
		//Sharpshooter
		if(achievements[10]==0&&wpnChoiceInt==10&&spree>=10)
		{
			achievements[10]=1;
			unlockedMessage=achievementNames[10];
			exp+=15000;
		}
		//Round Over
		if(achievements[11]==0&&(round>1||set>1))
		{
			achievements[11]=1;
			unlockedMessage=achievementNames[11];
			exp+=10000;
		}
		//I am the Juggernaut
		if(achievements[12]==0&&set>1&&difInt==2)
		{
			achievements[12]=1;
			unlockedMessage=achievementNames[12];
			exp+=50000;
		}
		//Splash Damage
		if(achievements[13]==0&&weaponUsage[6]+weaponUsage[7]+weaponUsage[11]+weaponUsage[12]>=20)
		{
			achievements[13]=1;
			unlockedMessage=achievementNames[13];
			exp+=15000;
		}
		//Versatility
		if(achievements[14]==0&&(round>1||set>1)&&wpnChoiceInt==0)
		{
			achievements[14]=1;
			unlockedMessage=achievementNames[14];
			exp+=15000;
		}
		//Killtacular
		if(achievements[15]==0&&kills>=5000)
		{
			achievements[15]=1;
			unlockedMessage=achievementNames[15];
			exp+=35000;
		}
		//Practice Makes Perfect
		if(achievements[16]==0&&deaths>=500)
		{
			achievements[16]=1;
			unlockedMessage=achievementNames[16];
			exp+=20000;
		}
		//I Love Jalo
		if(achievements[17]==0)
		{
			achievements[17]=1;
			unlockedMessage=achievementNames[17];
		}
		//King of the Hill
		if(achievements[18]==0&&exp>=50000000)
		{
			achievements[18]=1;
			unlockedMessage=achievementNames[18];
		}
		//Playa playa
		if(achievements[19]==0&&gamesPlayed>=77)
		{
			achievements[19]=1;
			unlockedMessage=achievementNames[19];
			exp+=20000;
		}
		//C'mon, really?
		if(achievements[20]==0&&wpnChoiceInt==13&&difInt==0&&set>1)
		{
			achievements[20]=1;
			unlockedMessage=achievementNames[20];
			exp+=5000;
		}
		//MLG
		if(achievements[21]==0&&wpnChoiceInt==5&&set>1&&difInt>=2)
		{
			achievements[21]=1;
			unlockedMessage=achievementNames[21];
			exp+=75000;
		}
		//Shoop da Woop
		if(achievements[22]==0&&weaponUsage[13]==20)
		{
			achievements[22]=1;
			unlockedMessage=achievementNames[22];
			exp+=20000;
		}
		//Trigger Happy
		if(achievements[23]==0&&shotsFired>=2500)
		{
			achievements[23]=1;
			unlockedMessage=achievementNames[23];
			exp+=10000;
		}
		//Score Attack
		if(achievements[24]==0&&score>highscores[gametypeInt])
		{
			achievements[24]=1;
			unlockedMessage=achievementNames[24];
			exp+=50000;
		}
		//Shotty 2 Hotty
		if(achievements[25]==0&&weaponUsage[9]>=20)
		{
			achievements[25]=1;
			unlockedMessage=achievementNames[25];
			exp+=20000;
		}
		//Noob Tube
		if(achievements[26]==0&&weaponUsage[7]>=20)
		{
			achievements[26]=1;
			unlockedMessage=achievementNames[26];
			exp+=20000;
		}
		//Elitist
		if(achievements[27]==0&&weaponUsage[2]>=20)
		{
			achievements[27]=1;
			unlockedMessage=achievementNames[27];
			exp+=20000;
		}
		//Metal Slug
		if(achievements[28]==0&&weaponUsage[11]>=20)
		{
			achievements[28]=1;
			unlockedMessage=achievementNames[28];
			exp+=20000;
		}
		//Small Arms
		if(achievements[29]==0&&weaponUsage[8]>=20)
		{
			achievements[29]=1;
			unlockedMessage=achievementNames[29];
			exp+=20000;
		}
		//Hunter
		if(achievements[30]==0&&weaponUsage[12]>=20)
		{
			achievements[30]=1;
			unlockedMessage=achievementNames[30];
			exp+=20000;
		}
		//Gruntpocalypse
		if(achievements[32]==0&&set>1&&gametypeInt==2&&difInt==3)
		{
			achievements[32]=1;
			unlockedMessage=achievementNames[32];
			exp+=75000;
		}
		//Rampage
		if(achievements[34]==0&&spree>=50&&difInt>=2)
		{
			achievements[34]=1;
			unlockedMessage=achievementNames[34];
			exp+=25000;
		}
		//Double Trouble
		if(achievements[35]==0&&multikill==2)
		{
			achievements[35]=1;
			unlockedMessage=achievementNames[35];
			exp+=250;
		}
		//Are You Kidding?
		if(achievements[36]==0&&spree>=500)
		{
			achievements[36]=1;
			unlockedMessage=achievementNames[36];
			exp+=100000;
		}
		//Climb the Mountain
		if(achievements[37]==0&&multikill>=7)
		{
			achievements[37]=1;
			unlockedMessage=achievementNames[37];
			exp+=3000;
		}
		//Boom! Headshot!
		if(achievements[38]==0&&multikill==1&&(wpnChoiceInt==1||wpnChoiceInt==3||wpnChoiceInt==5))
		{
			achievements[38]=1;
			unlockedMessage=achievementNames[38];
			exp+=500;
		}
		//Grunt Soup
		if(achievements[39]==0&&gruntsKilledThisGame>=25)
		{
			achievements[39]=1;
			unlockedMessage=achievementNames[39];
			exp+=4000;
		}
		//Jackal-ing Off
		if(achievements[40]==0&&jackalsKilledThisGame>=25)
		{
			achievements[40]=1;
			unlockedMessage=achievementNames[40];
			exp+=8000;
		}
		//2 1337 4 U
		if(achievements[41]==0&&elitesKilledThisGame>=25)
		{
			achievements[41]=1;
			unlockedMessage=achievementNames[41];
			exp+=12000;
		}
		//LSD
		if(achievements[42]==0&&armoryInt[7]==3)
		{
			achievements[42]=1;
			unlockedMessage=achievementNames[42];
			exp+=1200;
		}
		//The Skin You're In
		if(achievements[43]==0&&armoryInt[5]>=3)
		{
			achievements[43]=1;
			unlockedMessage=achievementNames[43];
			exp+=1500;
		}
		//I Can't See!
		if(achievements[44]==0&&armoryInt[7]==2)
		{
			achievements[44]=1;
			unlockedMessage=achievementNames[44];
			exp+=2500;
		}
		//Eyes on the Prize
		if(achievements[45]==0&&armoryInt[6]>0)
		{
			achievements[45]=1;
			unlockedMessage=achievementNames[45];
			exp+=2500;
		}
		//Who is Camtendo?
		if(achievements[46]==0&&watchedCredits)
		{
			achievements[46]=1;
			unlockedMessage=achievementNames[46];
			exp+=1500;
		}
		//Hardly a Challenge?
		if(achievements[47]==0&&consecNoDmgWaves>=3)
		{
			achievements[47]=1;
			unlockedMessage=achievementNames[47];
			exp+=4500;
		}
		//Hyper Lethal
		if(achievements[48]==0&&kills>=50000)
		{
			achievements[48]=1;
			unlockedMessage=achievementNames[48];
			exp+=1000000;
		}
	}

	public void mpGametypeManager()
	{
		if(mpGametypeInt==0)
		{
			spartans[0]=new Spartan(mpWeapons[0],0,0,50);
			spartans[1]=new Spartan(mpWeapons[1],0,700,50);
			if(mpTotalPlayers>=3)
			spartans[2]=new Spartan(mpWeapons[2],0,0,450);
			if(mpTotalPlayers>=4)
			spartans[3]=new Spartan(mpWeapons[3],0,700,450);
		}
		if(mpGametypeInt==1)
		{
			spartans[0]=new Spartan(mpWeapons[0],0,0,50);
			spartans[1]=new Spartan(mpWeapons[1],0,700,50);
			if(mpTotalPlayers>=3)
			spartans[2]=new Spartan(mpWeapons[2],0,0,450);
			if(mpTotalPlayers>=4)
			spartans[3]=new Spartan(mpWeapons[3],0,700,450);

			for(int i=0; i<mpTotalPlayers; i++)
			{
				spartans[i].weapon.currentAmmo=Integer.MAX_VALUE;
			}
		}
		if(mpGametypeInt==2)
		{
			spartans[0]=new Spartan(mpWeapons[0],0,0,50);
			spartans[1]=new Spartan(mpWeapons[1],0,700,50);
			if(mpTotalPlayers>=3)
			spartans[2]=new Spartan(mpWeapons[2],0,0,450);
			if(mpTotalPlayers>=4)
			spartans[3]=new Spartan(mpWeapons[3],0,700,450);

			for(int i=0; i<mpTotalPlayers; i++)
			{
				spartans[i].weapon.strength=Integer.MAX_VALUE;
			}
		}
	}

	public void gametypeManager()
	{
		System.gc();
		oneEnemyLeft=false;

		//Standard
		if(gametypeInt==0)
		{
			exclusiveGametype="";

			if(set==1&&round==1&&wave==1)
			{
				System.out.println("Standard: Initial Wave");
				spartan=new Spartan(wpnChoice[wpnChoiceInt],0,375,325);
				armoryEffects();

				gruntSpawn=(int)(5*Math.random()+1);
				jackalSpawn=(int)(3*Math.random()+1);
				eliteSpawn=1;

				if(gruntSpawn>15)
					gruntSpawn=15;
				if(jackalSpawn>10)
					jackalSpawn=10;
				if(eliteSpawn>10)
					eliteSpawn=10;

				for(int i=0; i<gruntSpawn; i++)
				{
					grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				for(int i=0; i<jackalSpawn; i++)
				{
					int rand=(int)(Math.random()*2);
					if(rand==0)
					jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else
					jackals[i]=new Jackal("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				for(int i=0; i<eliteSpawn; i++)
				{
					int rand=(int)(Math.random()*10);
					if(difInt!=3)
					rand/=Math.abs(3-difInt);
					if(rand==0||rand==1||rand==2)
					elites[i]=new Elite("Plasma Rifle","Minor",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==3||rand==4)
					elites[i]=new Elite("Brute Shot","Major",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==5||rand==6)
					elites[i]=new Elite("Carbine","Ultra",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==7||rand==8)
					elites[i]=new Elite("Mauler","Spec Ops",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==9)
					elites[i]=new Elite("Sniper","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				addMultiplier();
			}
			else
			{
				System.out.println("Standard: New Wave");
				spartan.weapon.currentAmmo+=(int)(Math.random()*spartan.weapon.maxAmmo);
				if(spartan.weapon.currentAmmo>spartan.weapon.maxAmmo)
					spartan.weapon.currentAmmo=spartan.weapon.maxAmmo;

				gruntSpawn=(int)(5*Math.random()+wave);
				for(int x=0; x<gruntSpawn; x++)
				grunts[x]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);

				jackalSpawn=(int)(3*Math.random()+wave);
				for(int i=0; i<jackalSpawn; i++)
				{
					int rand=(int)(Math.random()*2);
					if(rand==0)
					jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else
					jackals[i]=new Jackal("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				eliteSpawn=(int)(Math.random()+wave);
				for(int i=0; i<eliteSpawn; i++)
				{
					int rand=(int)(Math.random()*10);
					if(difInt!=3)
					rand/=Math.abs(3-difInt);
					if(rand==0||rand==1||rand==2)
					elites[i]=new Elite("Plasma Rifle","Minor",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==3||rand==4)
					elites[i]=new Elite("Brute Shot","Major",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==5||rand==6)
					elites[i]=new Elite("Carbine","Ultra",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==7||rand==8)
					elites[i]=new Elite("Mauler","Spec Ops",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==9)
					elites[i]=new Elite("Sniper","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				addMultiplier();
			}
		}
		//Infinity

		else if(gametypeInt==1)
		{
			exclusiveGametype="";

			if(set==1&&round==1&&wave==1)
			{
				System.out.println("Infinity: Initial Wave");
				spartan=new Spartan(wpnChoice[wpnChoiceInt],Integer.MAX_VALUE,375,350);
				armoryEffects();

				gruntSpawn=(int)(5*Math.random()+1);
				jackalSpawn=(int)(3*Math.random()+1);
				eliteSpawn=1;

				if(gruntSpawn>15)
					gruntSpawn=15;
				if(jackalSpawn>10)
					jackalSpawn=10;
				if(eliteSpawn>10)
					eliteSpawn=10;

				for(int i=0; i<gruntSpawn; i++)
				{
					grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*500),(int)(Math.random()*100)+50,true);
				}

				for(int i=0; i<jackalSpawn; i++)
				{
					int rand=(int)(Math.random()*2);
					if(rand==0)
					jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else
					jackals[i]=new Jackal("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				for(int i=0; i<eliteSpawn; i++)
				{
					int rand=(int)(Math.random()*10);
					if(difInt!=3)
					rand/=Math.abs(3-difInt);
					if(rand==0||rand==1||rand==2)
					elites[i]=new Elite("Plasma Rifle","Minor",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==3||rand==4)
					elites[i]=new Elite("Brute Shot","Major",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==5||rand==6)
					elites[i]=new Elite("Carbine","Ultra",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==7||rand==8)
					elites[i]=new Elite("Mauler","Spec Ops",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==9)
					elites[i]=new Elite("Sniper","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				addMultiplier();
			}
			else
			{
				System.out.println("Infinity: New Wave");

				gruntSpawn=(int)(5*Math.random()+wave);
				for(int x=0; x<gruntSpawn; x++)
				grunts[x]=new Grunt("Plasma Pistol",(int)(Math.random()*500),(int)(Math.random()*100)+50,true);

				jackalSpawn=(int)(3*Math.random()+wave);
				for(int i=0; i<jackalSpawn; i++)
				{
					int rand=(int)(Math.random()*2);
					if(rand==0)
					jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else
					jackals[i]=new Jackal("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				eliteSpawn=(int)(Math.random()+wave);
				for(int i=0; i<eliteSpawn; i++)
				{
					int rand=(int)(Math.random()*10);
					if(difInt!=3)
					rand/=Math.abs(3-difInt);
					if(rand==0||rand==1||rand==2)
					elites[i]=new Elite("Plasma Rifle","Minor",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==3||rand==4)
					elites[i]=new Elite("Brute Shot","Major",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==5||rand==6)
					elites[i]=new Elite("Carbine","Ultra",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==7||rand==8)
					elites[i]=new Elite("Mauler","Spec Ops",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==9)
					elites[i]=new Elite("Sniper","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				addMultiplier();
			}
		}
		//Gruntastrophe

		else if(gametypeInt==2)
		{
			if(set==1&&round==1&&wave==1)
			{
				System.out.println("Gruntastrophe: Initial Wave");

				exclusiveGametype="Grunts";

				spartan=new Spartan(wpnChoice[wpnChoiceInt],0,375,350);
				armoryEffects();

				gruntSpawn=(int)(10*Math.random()+1);
				eliteSpawn=1;
				jackalSpawn=1;

				for(int i=0; i<gruntSpawn; i++)
				{
					int randy=(int)(Math.random()*4);

					if(randy==0)
					grunts[i]=new Grunt("Spartan Laser",(int)(Math.random()*500),(int)(Math.random()*100)+50,true);
					else if(randy==1)
					grunts[i]=new Grunt("Carbine",(int)(Math.random()*500),(int)(Math.random()*100)+50,true);
					else if(randy==2)
					grunts[i]=new Grunt("Mauler",(int)(Math.random()*500),(int)(Math.random()*100)+50,true);
					else if(randy==3)
					grunts[i]=new Grunt("Fuel Rod",(int)(Math.random()*500),(int)(Math.random()*100)+50,true);
				}

				for(int i=0; i<eliteSpawn; i++)
				{
					elites[i]=new Elite("Carbine","Minor",(int)(Math.random()*500),(int)(Math.random()*500)+50,false);
				}

				for(int i=0; i<jackalSpawn; i++)
				{
					jackals[i]=new Jackal("Carbine",(int)(Math.random()*500),(int)(Math.random()*500)+50,false);
				}

				addMultiplier();
			}
			else
			{
				System.out.println("Gruntastrophe: New Wave");

				spartan.weapon.currentAmmo+=(int)(Math.random()*spartan.weapon.maxAmmo);
				if(spartan.weapon.currentAmmo>spartan.weapon.maxAmmo)
					spartan.weapon.currentAmmo=spartan.weapon.maxAmmo;

				gruntSpawn=(int)(10*Math.random()+wave*2);
				if(gruntSpawn>15)
					gruntSpawn=15;

				for(int i=0; i<gruntSpawn; i++)
				{
					int randy=(int)(Math.random()*4);

					if(randy==0)
					grunts[i]=new Grunt("Spartan Laser",(int)(Math.random()*500),(int)(Math.random()*100)+50,true);
					else if(randy==1)
					grunts[i]=new Grunt("Carbine",(int)(Math.random()*500),(int)(Math.random()*100)+50,true);
					else if(randy==2)
					grunts[i]=new Grunt("Mauler",(int)(Math.random()*500),(int)(Math.random()*100)+50,true);
					else if(randy==3)
					grunts[i]=new Grunt("Fuel Rod",(int)(Math.random()*500),(int)(Math.random()*100)+50,true);
				}

				eliteSpawn=1;
				for(int x=0; x<eliteSpawn; x++)
				elites[x]=new Elite("Carbine","Minor",(int)(Math.random()*500),(int)(Math.random()*100)+50,false);

				jackalSpawn=1;
				for(int x=0; x<jackalSpawn; x++)
				jackals[x]=new Jackal("Carbine",(int)(Math.random()*500),(int)(Math.random()*100)+50,false);

				addMultiplier();
			}
		}
		//1337

		else if(gametypeInt==3)
		{
			if(set==1&&round==1&&wave==1)
			{
				System.out.println("1337: Initial Wave");

				exclusiveGametype="Elites";

				spartan=new Spartan(wpnChoice[wpnChoiceInt],0,375,350);
				armoryEffects();

				gruntSpawn=1;
				eliteSpawn=(int)(10*Math.random()+1);

				for(int i=0; i<gruntSpawn; i++)
				{
					grunts[i]=new Grunt("Carbine",(int)(Math.random()*500),(int)(Math.random()*500)+50,false);
				}

				jackalSpawn=1;
				for(int x=0; x<jackalSpawn; x++)
				jackals[x]=new Jackal("Carbine",(int)(Math.random()*500),(int)(Math.random()*100)+50,false);

				for(int i=0; i<eliteSpawn; i++)
				{
					int rand=(int)(Math.random()*10);
					if(rand==0||rand==1||rand==2)
					elites[i]=new Elite("Plasma Rifle","Minor",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==3||rand==4)
					elites[i]=new Elite("Brute Shot","Major",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==5||rand==6)
					elites[i]=new Elite("Carbine","Ultra",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==7||rand==8)
					elites[i]=new Elite("Mauler","Spec Ops",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==9)
					elites[i]=new Elite("Sniper","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				addMultiplier();
			}
			else
			{
				System.out.println("1337: New Wave");

				spartan.weapon.currentAmmo+=(int)(Math.random()*spartan.weapon.maxAmmo);
				if(spartan.weapon.currentAmmo>spartan.weapon.maxAmmo)
					spartan.weapon.currentAmmo=spartan.weapon.maxAmmo;

				gruntSpawn=1;

				for(int i=0; i<gruntSpawn; i++)
				{
					grunts[i]=new Grunt("Carbine",(int)(Math.random()*500),(int)(Math.random()*500)+50,false);
				}

				jackalSpawn=1;

				for(int i=0; i<jackalSpawn; i++)
				{
					jackals[i]=new Jackal("Carbine",(int)(Math.random()*500),(int)(Math.random()*500)+50,false);
				}

				eliteSpawn=(int)(10*Math.random()+1);

				for(int i=0; i<eliteSpawn; i++)
				{
					int rand=(int)(Math.random()*10);
					if(rand==0||rand==1||rand==2)
					elites[i]=new Elite("Plasma Rifle","Minor",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==3||rand==4)
					elites[i]=new Elite("Brute Shot","Major",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==5||rand==6)
					elites[i]=new Elite("Carbine","Ultra",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==7||rand==8)
					elites[i]=new Elite("Mauler","Spec Ops",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==9)
					elites[i]=new Elite("Sniper","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				addMultiplier();
			}
		}

		//7th Ring of Hell

		else if(gametypeInt==4)
		{
			if(set==1&&round==1&&wave==1)
			{
				System.out.println("7th Ring of Hell: Initial Wave");

				exclusiveGametype="Elites";

				spartan=new Spartan(wpnChoice[wpnChoiceInt],0,375,350);
				armoryEffects();

				gruntSpawn=1;
				eliteSpawn=(int)(Math.random()*wave+round+set);

				for(int i=0; i<gruntSpawn; i++)
				{
					grunts[i]=new Grunt("Carbine",(int)(Math.random()*500),(int)(Math.random()*500)+50,false);
				}

				jackalSpawn=1;
				for(int x=0; x<jackalSpawn; x++)
				jackals[x]=new Jackal("Carbine",(int)(Math.random()*500),(int)(Math.random()*100)+50,false);

				for(int i=0; i<eliteSpawn; i++)
				{
					int rand=(int)(Math.random()*10);
					if(rand==0||rand==1||rand==2)
					elites[i]=new Elite("Fuel Rod","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==3||rand==4)
					elites[i]=new Elite("DMR","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==5||rand==6)
					elites[i]=new Elite("Spartan Laser","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==7||rand==8)
					elites[i]=new Elite("Mauler","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==9)
					elites[i]=new Elite("Sniper","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				addMultiplier();
			}
			else
			{
				System.out.println("7th Ring of Hell: New Wave");

				spartan.weapon.currentAmmo+=(int)(Math.random()*spartan.weapon.maxAmmo);
				if(spartan.weapon.currentAmmo>spartan.weapon.maxAmmo)
					spartan.weapon.currentAmmo=spartan.weapon.maxAmmo;

				gruntSpawn=1;

				for(int i=0; i<gruntSpawn; i++)
				{
					grunts[i]=new Grunt("Carbine",(int)(Math.random()*500),(int)(Math.random()*500)+50,false);
				}

				jackalSpawn=1;

				for(int i=0; i<jackalSpawn; i++)
				{
					jackals[i]=new Jackal("Carbine",(int)(Math.random()*500),(int)(Math.random()*500)+50,false);
				}

				eliteSpawn=(int)(Math.random()*wave+round+set);

				for(int i=0; i<eliteSpawn; i++)
				{
					int rand=(int)(Math.random()*10);
					if(rand==0||rand==1||rand==2)
					elites[i]=new Elite("Fuel Rod","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==3||rand==4)
					elites[i]=new Elite("DMR","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==5||rand==6)
					elites[i]=new Elite("Spartan Laser","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==7||rand==8)
					elites[i]=new Elite("Plasma Rifle","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					else if(rand==9)
					elites[i]=new Elite("Shotgun","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
				}

				addMultiplier();
			}
		}
		//Level Up!
		else if(gametypeInt==5)
		{
			exclusiveGametype="";

			spartan=new Spartan(wpnChoice[wpnChoiceInt],0,375,350);
			armoryEffects();

			switch(level)
			{
				case 1:
					jackalSpawn=0;
					gruntSpawn=1;
					eliteSpawn=0;
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 2:
					gruntSpawn=3;
					jackalSpawn=0;
					eliteSpawn=0;
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 3:
					jackalSpawn=1;
					gruntSpawn=0;
					eliteSpawn=0;
					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 4:
					jackalSpawn=3;
					gruntSpawn=0;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 5:
					jackalSpawn=3;
					gruntSpawn=3;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 6:
					jackalSpawn=5;
					gruntSpawn=5;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 7:
					jackalSpawn=3;
					gruntSpawn=0;
					eliteSpawn=2;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Plasma Rifle","Minor",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 8:
					jackalSpawn=3;
					gruntSpawn=3;
					eliteSpawn=3;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Carbine","Minor",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 9:
					jackalSpawn=0;
					gruntSpawn=5;
					eliteSpawn=7;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Carbine","Minor",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 10:
					jackalSpawn=0;
					gruntSpawn=0;
					eliteSpawn=10;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Mauler","Minor",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 11:
					jackalSpawn=0;
					gruntSpawn=3;
					eliteSpawn=0;
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 12:
					gruntSpawn=5;
					jackalSpawn=0;
					eliteSpawn=0;
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Carbine",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 13:
					jackalSpawn=3;
					gruntSpawn=0;
					eliteSpawn=0;
					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 14:
					jackalSpawn=5;
					gruntSpawn=0;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 15:
					jackalSpawn=5;
					gruntSpawn=5;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 16:
					jackalSpawn=7;
					gruntSpawn=7;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 17:
					jackalSpawn=3;
					gruntSpawn=0;
					eliteSpawn=2;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Plasma Rifle","Major",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 18:
					jackalSpawn=3;
					gruntSpawn=3;
					eliteSpawn=3;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Carbine","Major",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 19:
					jackalSpawn=0;
					gruntSpawn=5;
					eliteSpawn=7;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Carbine","Major",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 20:
					jackalSpawn=0;
					gruntSpawn=0;
					eliteSpawn=10;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Brute Shot","Major",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 21:
					jackalSpawn=0;
					gruntSpawn=5;
					eliteSpawn=0;
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Mauler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 22:
					gruntSpawn=7;
					jackalSpawn=0;
					eliteSpawn=0;
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Mauler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 23:
					jackalSpawn=5;
					gruntSpawn=0;
					eliteSpawn=0;
					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Carbine",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 24:
					jackalSpawn=7;
					gruntSpawn=0;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 25:
					jackalSpawn=7;
					gruntSpawn=7;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 26:
					jackalSpawn=9;
					gruntSpawn=9;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Carbine",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Mauler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 27:
					jackalSpawn=3;
					gruntSpawn=0;
					eliteSpawn=2;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Sniper","Ultra",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 28:
					jackalSpawn=3;
					gruntSpawn=3;
					eliteSpawn=3;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Carbine","Ultra",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 29:
					jackalSpawn=0;
					gruntSpawn=5;
					eliteSpawn=7;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Carbine","Ultra",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 30:
					jackalSpawn=0;
					gruntSpawn=0;
					eliteSpawn=10;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Carbine","Ultra",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 31:
					jackalSpawn=0;
					gruntSpawn=7;
					eliteSpawn=0;
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Fuel Rod",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 32:
					gruntSpawn=9;
					jackalSpawn=0;
					eliteSpawn=0;
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Fuel Rod",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 33:
					jackalSpawn=7;
					gruntSpawn=0;
					eliteSpawn=0;
					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Carbine",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 34:
					jackalSpawn=9;
					gruntSpawn=0;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 35:
					jackalSpawn=8;
					gruntSpawn=8;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Carbine",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Fuel Rod",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 36:
					jackalSpawn=10;
					gruntSpawn=10;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Carbine",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Mauler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 37:
					jackalSpawn=3;
					gruntSpawn=0;
					eliteSpawn=3;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Shotgun","Spec Ops",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 38:
					jackalSpawn=3;
					gruntSpawn=3;
					eliteSpawn=3;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Carbine",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Fuel Rod",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Shotgun","Spec Ops",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 39:
					jackalSpawn=0;
					gruntSpawn=5;
					eliteSpawn=7;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Carbine","Spec Ops",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 40:
					jackalSpawn=0;
					gruntSpawn=0;
					eliteSpawn=10;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Fuel Rod","Spec Ops",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 41:
					jackalSpawn=0;
					gruntSpawn=10;
					eliteSpawn=0;
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Fuel Rod",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 42:
					gruntSpawn=10;
					jackalSpawn=0;
					eliteSpawn=0;
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Spartan Laser",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 43:
					jackalSpawn=10;
					gruntSpawn=0;
					eliteSpawn=0;
					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Mauler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 44:
					jackalSpawn=10;
					gruntSpawn=0;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Sniper",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 45:
					jackalSpawn=10;
					gruntSpawn=10;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Sniper",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Fuel Rod",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 46:
					jackalSpawn=10;
					gruntSpawn=10;
					eliteSpawn=0;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Mauler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Mauler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					break;
				case 47:
					jackalSpawn=5;
					gruntSpawn=5;
					eliteSpawn=3;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Needler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Fuel Rod",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Rockets","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 48:
					jackalSpawn=5;
					gruntSpawn=5;
					eliteSpawn=5;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Carbine",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Fuel Rod",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Fuel Rod","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 49:
					jackalSpawn=5;
					gruntSpawn=5;
					eliteSpawn=10;

					for(int i=0; i<jackalSpawn; i++)
					{
						jackals[i]=new Jackal("Mauler",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Spartan Laser",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					for(int x=0; x<eliteSpawn; x++)
					elites[x]=new Elite("Sniper","Zealot",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					break;
				case 50:
					level=50;
					gruntSpawn=10;
					for(int i=0; i<gruntSpawn; i++)
					{
						grunts[i]=new Grunt("Plasma Pistol",(int)(Math.random()*800),(int)(Math.random()*100)+50,true);
					}
					mainMessage="Game over";
					System.out.println("Game Terminated");
				if(score>highscores[gametypeInt])
				{
					highscores[gametypeInt]=score;
					System.out.println("New High Score!");
				}
				score=0;
				reloadsThisGame=0;
				shieldRechargesThisGame=0;
				consecNoDmgWaves=0;
				gruntsKilledThisGame=0;
				jackalsKilledThisGame=0;
				elitesKilledThisGame=0;
				gamesPlayed++;
				weaponUsage[wpnChoiceInt]++;
				round=1;
				set=1;
				wave=1;
				System.out.println("Rewritting FF Vars");
				expCode=50000000-exp;
				System.out.println("Generating New Exp Code");
				levelSigner();
				System.out.print("Saving File...");
				saveFileManager("save");
				System.out.println("Starting Single Player Menu");
				mode="single";
				menuInt=450;
				bgMusic.stop();
				titleMusic.play();
				default:
					mainMessage="Game over";
					break;
			}

			System.out.println("Level "+level+" Initiated");

			addMultiplier();
		}

		spartan.weapon.ammoInClip=spartan.weapon.clipSize;
	}

	public void setRoundWave()
	{
		if(wave>5)
		{
			wave=1;
			round++;
			System.out.println("Round Incremented");
			exp+=round*100;
			mainMessage="Round: "+round+" +"+(round*100)+" Exp";

			if(round>3)
			{
				round=1;
				set++;
				System.out.println("Set Incremented");
				exp+=500*set;
				mainMessage="Set: "+set+" +"+(set*500)+" Exp";
			}
		}
	}

	public void unlockedMessageManager()
	{
		if(!unlockedMessage.equals(""))
			unlockedMessageInt++;
		if(unlockedMessageInt>500)
		{
			unlockedMessage="";
			unlockedMessageInt=0;
		}
	}

	public void messageManager()
	{
		if(!mainMessage.equals(""))
			mainMessageInt++;
		if(mainMessageInt>=150)
		{
			if(mainMessage.equals("Respawning..."))
			{
				consecNoDmgWaves=0;
				spartan.respawn();
				System.out.println("Spartan Dead");
				System.out.println("Beginning Respawn");
				if(gametypeInt==1)
					spartan.weapon.currentAmmo=Integer.MAX_VALUE;
				gametypeManager();
			}
			else if(mainMessage.equals("Game over"))
			{
				System.out.println("Game Terminated");
				if(score>highscores[gametypeInt])
				{
					highscores[gametypeInt]=score;
					System.out.println("New High Score!");
				}
				score=0;
				reloadsThisGame=0;
				shieldRechargesThisGame=0;
				consecNoDmgWaves=0;
				gruntsKilledThisGame=0;
				jackalsKilledThisGame=0;
				elitesKilledThisGame=0;
				gamesPlayed++;
				weaponUsage[wpnChoiceInt]++;
				round=1;
				set=1;
				wave=1;
				System.out.println("Rewritting FF Vars");
				expCode=50000000-exp;
				System.out.println("Generating New Exp Code");
				System.out.print("Saving File...");
				saveFileManager("save");
				System.out.println("Starting Single Player Menu");
				mode="title";
				menuInt=250;
				bgMusic.stop();
				mapInt=(int)(Math.random()*5);
				stageManager();
				bgMusic.loop();
			}
			multikill=0;
			mainMessage="";
			mainMessageInt=0;
			medalImageManager();
		}
	}

	public void hostInfoExchange()
	{
		for(int i=1; i<mpTotalPlayers; i++)
		{
			try
			{
				spartans[i].location.x=Integer.parseInt(in[i].readUTF());
				spartans[i].location.y=Integer.parseInt(in[i].readUTF());
				spartans[i].direction=in[i].readUTF();
				spartans[i].alive=Boolean.parseBoolean(in[i].readUTF());
				spartans[i].weapon.shooting=Boolean.parseBoolean(in[i].readUTF());
				spartans[i].weapon.projectile.x=Integer.parseInt(in[i].readUTF());
				spartans[i].weapon.projectile.y=Integer.parseInt(in[i].readUTF());
				spartans[i].health=Integer.parseInt(in[i].readUTF());
				spartans[i].shields=Integer.parseInt(in[i].readUTF());
			}
			catch(Exception e){}
		}

	}

	public void clientInfoExchange()
	{
		try
			{
				out[0].writeUTF(""+spartans[mpID].location.x);
		  		out[0].flush();
		  		out[0].writeUTF(""+spartans[mpID].location.y);
		  		out[0].flush();
		  		out[0].writeUTF(spartans[mpID].direction);
		  		out[0].flush();
		  		out[0].writeUTF(""+spartans[mpID].alive);
		  		out[0].flush();
		  		out[0].writeUTF(""+spartans[mpID].weapon.shooting);
		  		out[0].flush();
		  		out[0].writeUTF(""+spartans[mpID].weapon.projectile.x);
		  		out[0].flush();
		  		out[0].writeUTF(""+spartans[mpID].weapon.projectile.y);
		  		out[0].flush();
		  		out[0].writeUTF(""+spartans[mpID].health);
		  		out[0].flush();
		  		out[0].writeUTF(""+spartans[mpID].shields);
		  		out[0].flush();
			}
			catch(Exception e){}
	}

	public void sendNewInfo()
	{
		if(net==NetType.HOST)
		{
			for(int i=1; i<mpTotalPlayers; i++)
		{
			try
			{
		  		out[i].writeUTF(""+spartans[0].alive);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[1].alive);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[2].alive);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[3].alive);
		  		out[i].flush();

		  		out[i].writeUTF(""+spartans[0].weapon.shooting);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[1].weapon.shooting);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[2].weapon.shooting);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[3].weapon.shooting);
		  		out[i].flush();

		  		out[i].writeUTF(""+spartans[0].weapon.projectile.x);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[0].weapon.projectile.y);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[1].weapon.projectile.x);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[1].weapon.projectile.y);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[2].weapon.projectile.x);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[2].weapon.projectile.y);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[3].weapon.projectile.x);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[3].weapon.projectile.y);
		  		out[i].flush();

		  		out[i].writeUTF(""+spartans[0].health);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[1].health);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[2].health);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[3].health);
		  		out[i].flush();

		  		out[i].writeUTF(""+spartans[0].shields);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[1].shields);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[2].shields);
		  		out[i].flush();
		  		out[i].writeUTF(""+spartans[3].shields);
		  		out[i].flush();
			}
			catch(Exception e){}
		}
		}
		else
		{
			try
			{

				spartans[0].alive=Boolean.parseBoolean(in[0].readUTF());
				spartans[1].alive=Boolean.parseBoolean(in[0].readUTF());
				spartans[2].alive=Boolean.parseBoolean(in[0].readUTF());
				spartans[3].alive=Boolean.parseBoolean(in[0].readUTF());

				spartans[0].weapon.shooting=Boolean.parseBoolean(in[0].readUTF());
				spartans[1].weapon.shooting=Boolean.parseBoolean(in[0].readUTF());
				spartans[2].weapon.shooting=Boolean.parseBoolean(in[0].readUTF());
				spartans[3].weapon.shooting=Boolean.parseBoolean(in[0].readUTF());

				spartans[0].weapon.projectile.x=Integer.parseInt(in[0].readUTF());
				spartans[0].weapon.projectile.y=Integer.parseInt(in[0].readUTF());
				spartans[1].weapon.projectile.x=Integer.parseInt(in[0].readUTF());
				spartans[1].weapon.projectile.y=Integer.parseInt(in[0].readUTF());
				spartans[2].weapon.projectile.x=Integer.parseInt(in[0].readUTF());
				spartans[2].weapon.projectile.y=Integer.parseInt(in[0].readUTF());
				spartans[3].weapon.projectile.x=Integer.parseInt(in[0].readUTF());
				spartans[3].weapon.projectile.y=Integer.parseInt(in[0].readUTF());

				spartans[0].health=Integer.parseInt(in[0].readUTF());
				spartans[1].health=Integer.parseInt(in[0].readUTF());
				spartans[2].health=Integer.parseInt(in[0].readUTF());
				spartans[3].health=Integer.parseInt(in[0].readUTF());

				spartans[0].shields=Integer.parseInt(in[0].readUTF());
				spartans[1].shields=Integer.parseInt(in[0].readUTF());
				spartans[2].shields=Integer.parseInt(in[0].readUTF());
				spartans[3].shields=Integer.parseInt(in[0].readUTF());
			}
			catch(Exception e){}
		}
	}

	public void getNewInfo()
	{
		if(net==NetType.HOST)
			hostInfoExchange();
		else
			clientInfoExchange();
	}

	public void runGame()
	{
		spartan.weapon.checkProjectile();
		spartan.weapon.bulletTravel();
		rankManager();
		rechargeManager();
		if(spartan.weapon.reloading)
			reloadManager();
		runBots();
		checkGunfire();
		checkMelee();
		firefightManager();
		messageManager();
		unlockedMessageManager();
		achievementManager();
		challengeManager();
		checkMovement();
	}

	public void mpRunGame()
	{
		getNewInfo();
		if(net==NetType.HOST)
		{
			for(int i=0; i<mpTotalPlayers; i++)
			{
				spartans[i].weapon.checkProjectile();
				spartans[i].weapon.bulletTravel();
			}

			mpCheckGunfire();
			mpCheckMelee();
		}
		sendNewInfo();
		mpCheckMovement();
		mpRechargeManager();
		mpMeleeManager();
		if(spartans[mpID].weapon.reloading)
			mpReloadManager();
	}
}