import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;


import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Graphics;
import java.util.*;
import java.io.*;

public class frame extends JFrame implements Runnable, KeyListener {

	public Inuit inuit = new Inuit(100,385,100);
	public static BufferedImage main;
	public static BufferedImage testimage;
	public Attack[] attacks = new Attack[5];
	public boolean [] activeAttacks = new boolean[5];
	public int[] combo = new int[5];
	public boolean[] freeCombo = new boolean[5];
	public long[] comboTime = new long[5];
	public int jump = 0;
	public int buf = 0;
	public int imgdir = 0;
	public int prime = 0;
	public int second = 0;
	public int ca = 0;
	public int[][] map;
	public int mapLoad = 0;
	public boolean ladder = false;
	public boolean ladderjump = false;
	public Enemy [] wolf;
	public int down = 0;
	public int up = 0;
	public int left = 0;
	public int right = 0;
	public int space = 0;
	public int wolfs;
	
	public static void loadImages()throws IOException{
		main = ImageIO.read(new File("inuit_main.png"));
		testimage = ImageIO.read(new File("inuit_warrior.png"));
	}
	
	public int[][] getMap(String filename) throws FileNotFoundException{
		File f = new File(filename);
		Scanner sc = new Scanner(new FileReader(f));
		
		StringTokenizer st = new StringTokenizer(sc.nextLine());
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		wolfs = Integer.parseInt(st.nextToken());
		wolf = new Enemy[wolfs];
		for (int p = 0; p < wolfs; p++){
			int xx = Integer.parseInt(st.nextToken());
			int yy = Integer.parseInt(st.nextToken());
			wolf[p] = new Enemy(xx,yy);
		}
		int[][] mapp = new int[x][y];
		int i = 0;
		try{
			while(sc.hasNextLine()){
				int j = 0;
				st = new StringTokenizer(sc.nextLine());
				while( st.hasMoreTokens()){
					mapp[i][j] = Integer.parseInt(st.nextToken());
					j++;
				}
				i++;
			}
		}
		finally{
			sc.close();
		}
		return mapp;
	}
	
	public void shift(){
		for (int i = 0; i < 4; i++){
			combo[i] = combo[i+1];
			freeCombo[i] = freeCombo[i+1];
			comboTime[i] = comboTime[i+1];
		}
		freeCombo[4] = false;
		combo[4] = 0;
	}
	
	public frame() throws FileNotFoundException{
		super("Inuit");
		setSize(750,450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.black);
		setVisible(true);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		for (int i = 0; i < 5; i++){
			activeAttacks[i] = false;
			freeCombo[i] = false;
		}
	}
	public void keyPressed(KeyEvent e){
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_LEFT) {
			if (!ladder){
				buf = 1;
				imgdir = 1;
				left = 1;
			}
			int i;
			for (i = 0; i < 5; i++){
				if (!freeCombo[i]){
					combo[i] = 1;
					freeCombo[i] = true;
					comboTime[i] = System.currentTimeMillis();
					break;
				}
			} 
			if (i == 5){
				shift();
				freeCombo[4] = true;
				combo[4] = 1;
				comboTime[4] = System.currentTimeMillis();
			}
		}
		if (code == KeyEvent.VK_RIGHT) {
			if (!ladder){
				buf = 2;
				imgdir = 2;
				right = 1;
			}
			int i;
			for (i = 0; i < 5; i++){
				if (!freeCombo[i]){
					combo[i] = 2;
					freeCombo[i] = true;
					comboTime[i] = System.currentTimeMillis();
					break;
				}
			} 
			if (i == 5){
				shift();
				freeCombo[4] = true;
				combo[4] = 2;
				comboTime[4] = System.currentTimeMillis();
			}
		}
		if (code == KeyEvent.VK_DOWN) {
			int i;
			buf = 4;
			imgdir = 4;
			down = 1;
			for (i = 0; i < 5; i++){
				if (!freeCombo[i]){
					combo[i] = 3;
					freeCombo[i] = true;
					comboTime[i] = System.currentTimeMillis();
					break;
				}
			} 
			if (i == 5){
				shift();
				freeCombo[4] = true;
				combo[4] = 3;
				comboTime[4] = System.currentTimeMillis();
			}
		}
		if (code == KeyEvent.VK_UP){
			int i;
			buf = 3;
			imgdir = 3;
			up = 1;
			for (i = 0; i < 5; i++){
				if (!freeCombo[i]){
					combo[i] = 4;
					freeCombo[i] = true;
					comboTime[i] = System.currentTimeMillis();
					break;
				}
			} 
			if (i == 5){
				shift();
				freeCombo[4] = true;
				combo[4] = 4;
				comboTime[4] = System.currentTimeMillis();
			}
		}
		if (code == KeyEvent.VK_Z){
			int i;
			for (i = 0; i < 5; i++){
				if (!freeCombo[i]){
					combo[i] = 5;
					freeCombo[i] = true;
					comboTime[i] = System.currentTimeMillis();
					break;
				}
			} 
			if (i == 5){
				shift();
				freeCombo[4] = true;
				combo[4] = 5;
				comboTime[4] = System.currentTimeMillis();
			}
		}
		if (code == KeyEvent.VK_SPACE){
		   if (jump != 2) jump = 1;
		   ladderjump = true;
		   space = 1;
		}
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_LEFT){
			buf = 0;
			left = 0;
		}
		if (code == KeyEvent.VK_RIGHT){
			buf = 0;
			right = 0;
		}
		if (code == KeyEvent.VK_UP){
			buf = 0;
			up = 0;
		}
		if (code == KeyEvent.VK_DOWN){
			buf = 0;
			down = 0;
		}
		if (code == KeyEvent.VK_SPACE){
			jump = 2;
			ladderjump = false;
			space = 0;
		}
	}
	
	public void start()
	{
		Thread th = new Thread(this);
		th.start();
	}
	
	public void paint(Graphics g){
		
		try {
			g.drawImage(getFrame(), 0, 0, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private Image getFrame() throws IOException {
		BufferedImage img = new BufferedImage(750, 450, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = img.getGraphics();
		
		if (mapLoad == 1){
			for (int i = 0; i < map.length;i++){
				for (int j = 0; j < map[i].length; j++){
					if (map[i][j] == 1){
						g.drawImage(main, j*30+inuit.mapx, (i-9)*30+inuit.mapy-5, (j*30)+30+inuit.mapx, (i-8)*30+inuit.mapy-5, 101, 60, 130, 90, this);
					} 
					else if (map[i][j] == 2){
						g.drawImage(main,j*30+inuit.mapx, (i-9)*30+inuit.mapy-5, (j*30)+30+inuit.mapx, (i-8)*30+inuit.mapy-5, 163, 60,192,90, this);
					}
					else if (map[i][j] == 3){
						g.drawImage(main,j*30+inuit.mapx, (i-9)*30+inuit.mapy-5, (j*30)+30+inuit.mapx, (i-8)*30+inuit.mapy-5, 69, 60,99,90, this);
					}
					else if (map[i][j] == 4){
						g.drawImage(main,j*30+inuit.mapx, (i-9)*30+inuit.mapy-5, (j*30)+30+inuit.mapx, (i-8)*30+inuit.mapy-5, 132, 60,161,90, this);
					}
				}
			}
			// 0 = empty
			// 1 = floor 101 130
			// 2 = ladder
			// 3 = main floor 69 99
			// 4 = ladder floor
			
		}
		
		for (int i = 0; i < 5; i++){
			if (activeAttacks[i]){
				if (attacks[i].direction == 1) g.drawImage(main, attacks[i].Xposition, attacks[i].Yposition+5, attacks[i].Xposition+ 45, attacks[i].Yposition+15, 30,60,66,70,this);
				else if (attacks[i].direction == 2)g.drawImage(main, attacks[i].Xposition - 45, attacks[i].Yposition+5, attacks[i].Xposition, attacks[i].Yposition+15, 30,72,66,82,this);
				else if (attacks[i].direction == 3)g.drawImage(main, inuit.getX(), inuit.getY(), inuit.getX() + 60, inuit.getY() + 30, 90, 0, 150, 30, this);
				else if (attacks[i].direction == 4)g.drawImage(main, inuit.getX(), inuit.getY(), inuit.getX() + 60, inuit.getY() + 30, 90, 30, 150, 60, this);
				else if (attacks[i].direction == 5)g.drawImage(main, inuit.getX() - 29, inuit.getY(), inuit.getX() + 31, inuit.getY() + 30, 150, 0, 210, 30, this);
				else if (attacks[i].direction == 6)g.drawImage(main, inuit.getX() - 29, inuit.getY(), inuit.getX() + 31, inuit.getY() + 30, 150, 30, 210, 60, this);
			}
			if (freeCombo[i]){
				if (combo[i] == 1) g.drawImage(main, 30 + (i*30), 30, 60+ (i*30), 60, 60,30,90,60, this);
				else if (combo[i] == 2) g.drawImage(main, 30 + (i*30), 30 , 60+ (i*30), 60, 0,30,30,60, this);
				else if (combo[i] == 3) g.drawImage(main, 30 + (i*30), 30 , 60+ (i*30), 60, 60,0,90,30, this);
				else if (combo[i] == 4) g.drawImage(main, 30 + (i*30), 30, 60+ (i*30), 60, 30,30,60,60, this);
				else g.drawImage(main, 30 + (i*30), 30, 60+ (i*30), 60, 0,60,30,90, this);
			}
		}
		if (prime == 0){
			if (imgdir == 1 ) g.drawImage(main,inuit.getX(), inuit.getY(), inuit.getX()+30, inuit.getY()+30, 30,0,60,30, this);
			else if (imgdir == 3 && ladder)g.drawImage(main,inuit.getX(), inuit.getY(), inuit.getX()+30, inuit.getY()+30, 0,90,30,120, this);
			else if (imgdir == 4 && ladder)g.drawImage(main,inuit.getX(), inuit.getY(), inuit.getX()+30, inuit.getY()+30, 0,90,30,120, this);
			else g.drawImage(main,inuit.getX(), inuit.getY(), inuit.getX()+30, inuit.getY()+30, 0,0,30,30, this);
		}
		for (int p = 0; p < wolfs; p++){
		if (wolf[p].alive){
			if (wolf[p].attacking){
				if (wolf[p].direction == 1)g.drawImage(main, wolf[p].getX(), wolf[p].getY(), wolf[p].getX() + 90, wolf[p].getY()+30, 150, 90, 239, 120, this);
				else g.drawImage(main, wolf[p].getX()-30, wolf[p].getY(), wolf[p].getX() + 60, wolf[p].getY()+30, 0, 120, 89, 150, this);
			}
		else if (wolf[p].direction == 0) g.drawImage(main, wolf[p].getX(), wolf[p].getY(), wolf[p].getX() + 60, wolf[p].getY()+30, 30, 90, 90, 120, this);
		else g.drawImage(main, wolf[p].getX(), wolf[p].getY(), wolf[p].getX() + 60, wolf[p].getY()+30, 90, 90, 149, 120, this);
		if (wolf[p].bar){
			g.setColor(Color.red);
			double bar = ((double)wolf[p].getHP()/100.0) * 60.0;
			g.drawRect(wolf[p].getX(), wolf[p].getY() - 7, (int) bar, 5);
			g.fillRect(wolf[p].getX(), wolf[p].getY() - 7, (int) bar, 5);
		}
		}}
		g.setColor(Color.red);
		double mainbar = ((double) inuit.hp / 100.0) * 200;
		g.drawRect(535, 35, (int) mainbar, 20);
		g.fillRect(535, 35, (int) mainbar, 20);
		
		g.dispose();
		return img;
	}
	
	public void clearCombo(){
		while (freeCombo[0]){
			shift();
		}
	}
	
	public int checkCombo(){
		for (int i = 0; i < 5; i++){
			if(i < 3){
				if(combo[i] == 3){
					if (combo[i+1] == 2){
						if (combo[i+2] == 5) return 1;
					} else if (combo[i+1] == 1) {
						if (combo[i+2] == 5) return 2;
					}
				}
			} if (combo[i] == 5) {
				if (second == 0){
					second = 1;
					if (imgdir == 2)return 3;
					else return 5;
				} else {
					second = 0;
					if (imgdir == 2)return 4;
					else return 6;
				}
			}
		}
		return 0;
	}
	
	public void run(){
		while(true){
			if (mapLoad == 0){
				try {
					map = getMap("test_map.txt");
					mapLoad = 1;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
			jump = inuit.jump(jump,left,right);
			if (jump == 0){
				if (!ladder){
					if (left == 1 && inuit.getX() > 5) {
						if (inuit.mapx == 0 || inuit.mapx == -838)inuit.moveLeft();
						if (inuit.getX() == 376 || inuit.getX() == 377) inuit.mapx += 2;
					}
					else if (right == 1 && inuit.getX() < 715) {
						if (inuit.mapx == 0 || inuit.mapx == -838)inuit.moveRight();
						if (inuit.getX() == 376 || inuit.getX() == 377) inuit.mapx -= 2;
					}
				}
			}
			if (up == 1){
				if (ladder){
				left = 0;
				right = 0;
				jump = 0;
				}
				ladder = inuit.checkLadder(map);
				if (ladder && !((map[inuit.getYYgrid()][inuit.getXgrid()] == 0 && map[inuit.getYYYgrid()][inuit.getXgrid()] == 0))){
					if (inuit.getY() == 225) inuit.mapy += 2;
					if (inuit.mapy == 0 || inuit.mapy == 270)inuit.moveUp();
				} else ladder = false;
			}
			else if (down == 1){
				if (ladder){
				left = 0;
				right = 0;
				jump = 0;
				}
				ladder = inuit.checkLadder(map);
				if (ladder && (map[inuit.getYgrid()+1][inuit.getXgrid()] != 1 && map[inuit.getYgrid()+1][inuit.getXgrid()] != 3)){
					if (inuit.getY() == 225) inuit.mapy -= 2;
					if (inuit.mapy == 0 || inuit.mapy == 270) inuit.moveDown();
				} else ladder = false;
			}
			for (int i = 0; i < 5; i++){
				if (activeAttacks[i]){
					if (attacks[i].direction == 1 || attacks[i].direction == 2){
						if (attacks[i].checkTime() > 2000) activeAttacks[i] = false;
						else attacks[i].launch();
					} else if (attacks[i].direction == 3 || attacks[i].direction == 5){
						if (attacks[i].checkTime() > 300){
							activeAttacks[i] = false;
							second = 0;
							prime--;
						} else if (second == 0){
							activeAttacks[i] = false;
							prime--;
						}
					} else if (attacks[i].direction == 4 || attacks[i].direction == 6){
						if (attacks[i].checkTime() > 300 || second == 1){
							activeAttacks[i] = false;
							prime--;
						}
					}
				}
				if (freeCombo[i]){
					if (Math.abs(comboTime[i] - System.currentTimeMillis()) > 1000) shift();
				}
			}
			
			
			if ((jump == 0 || jump == 2) && !ladder) inuit.gravity(map);
			ca = checkCombo();
			if (ca != 0){
				for (int i = 0; i < 5; i++){
					if (!activeAttacks[i]){
						activeAttacks[i] = true;
						if (ca == 1 || ca == 2) attacks[i] = new Attack(5, inuit.getX() + 20, inuit.getY(), ca, 50);
						else if (ca == 3 || ca == 4) {
							attacks[i] = new Attack(5, inuit.getX()+30, inuit.getY(), ca, 20);
							prime++;
						} else if (ca == 5 || ca == 6){
							attacks[i] = new Attack(5, inuit.getX()-28, inuit.getY(), ca, 20);
							prime++;
						}
						clearCombo();
						break;
					}
				}
			}
			for (int i = 0; i < 5; i++){
				if (activeAttacks[i]){
					if (attacks[i].direction == 1 || attacks[i].direction == 2){
						for (int p = 0; p < wolfs; p++){
						if (wolf[p].alive && attacks[i].Yposition == wolf[p].getY() && ((attacks[i].Xposition >= wolf[p].getX() && attacks[i].Xposition <= wolf[p].getX() + 60) || (attacks[i].Xposition + 30 >= wolf[p].getX() && attacks[i].Xposition + 30 <= wolf[p].getX() + 60))){
							wolf[p].hurt(attacks[i].damage, attacks[i].direction);
							activeAttacks[i] = false;
						}}
					}
					else if (attacks[i].direction >= 3 && attacks[i].direction <= 6){
						if (!attacks[i].damagedone){
							for (int p = 0; p < wolfs; p++){
							if (wolf[p].alive && attacks[i].Yposition == wolf[p].getY() && ((attacks[i].Xposition >= wolf[p].getX() && attacks[i].Xposition <= wolf[p].getX() + 60) || (attacks[i].Xposition + 28 >= wolf[p].getX() && attacks[i].Xposition + 28 <= wolf[p].getX() + 60))){
								wolf[p].hurt(attacks[i].damage,attacks[i].direction);
								attacks[i].damagedone = true;
							}}
						}
					}
				}
				
			}
			for (int p = 0; p < wolfs; p++){
				wolf[p].update(inuit, map);
				wolf[p].gravity(map);
			}
			}
			repaint();
			try
			{
				
				Thread.sleep(8);
			}
			catch (InterruptedException ex)
			{
				//do nothing
			}
		}
	}
	public static void main(String[] args) throws IOException{
		frame f = new frame();
		loadImages();
		f.start();
	}
}
