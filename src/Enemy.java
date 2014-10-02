
public class Enemy {
	private int x;
	private int y;
	private int patrolx;
	private int patroly;
	public int mapx;
	public int mapy;
	public int direction;
	public int speed;
	private int hp;
	public int damage;
	public int stop;
	public long recover;
	public boolean alive;
	public boolean bar;
	public long attackDelay;
	public boolean ad;
	public boolean attacking;
	
	public Enemy(int xx, int yy){
		x = xx;
		y = yy;
		direction = 0;
		patrolx = xx;
		patroly = yy;
		speed = 1;
		hp = 100;
		mapx = 0;
		mapy = 0;
		damage = 10;
		alive = true;
		bar = false;
		ad = false;
		attacking = false;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public int getHP(){
		return hp;
	}
	public int attack(){
		return damage;
	}
	public void hurt(int dam, int dir){
		hp -= dam;
		stop = 1;
		recover = System.currentTimeMillis();
		if (dir % 2 == 0){
			x -= 2;
		} else x += 2;
		if (hp <= 0) alive = false;
		if (!bar) bar = true;
	}
	
	public void position(int mapxx, int mapyy){
		if (mapx != mapxx){
			if (mapx > mapxx){
				x -= 2;
				patrolx -= 2;
			}
			else {
				x += 2;
				patrolx += 2;
			}
			mapx = mapxx;
		}
		if (mapy != mapyy){
			if (mapy < mapyy){
				y += 2;
				patroly += mapy;
			}
			else {
				y -= 2;
				patroly -= mapy;
			}
			mapy = mapyy;
		}
	}
	
	public int chase(Inuit main){
		if (main.getY() != y) return 0;
		if (direction == 0){
			if (main.getX() <= x - 120 || main.getX() >= x + 150) return 0;
		} else if (direction == 1){
			if (main.getX() >= x + 150 || main.getX() <= x - 120) return 0;
		}
		return 1;
	}
	public void gravity(int [][] map){
		int posx = x - mapx;
		int gridx = posx/30;
		int posxx = x +29 - mapx;
		int gridxx = posxx/30;
		double posy = (y + 5.0 - mapy)/30.0;
		int gridy = (int)posy + 9;
		if (posy < 0 && (posy != (int) posy)) gridy--;
		double posyy = (y + 34.0 - mapy)/30.0;
		int gridyy = (int) posyy + 9;
		if (posyy < 0 && posyy != (int) posyy) gridyy--;
		if (((map[gridy+1][gridx] == 1 || map[gridy+1][gridx] == 3 || map[gridy+1][gridx] == 4) && (map[gridyy+1][gridx] == 1 || map[gridyy+1][gridx] == 3 || map[gridyy+1][gridx] == 4)) ||((map[gridy+1][gridxx] == 1 || map[gridy+1][gridxx] == 3 || map[gridy+1][gridxx] == 4) && (map[gridyy+1][gridxx] == 1 || map[gridyy+1][gridxx] == 3 || map[gridyy+1][gridxx] == 4)) ){
			// do nothing for now
		}else {
			if (getY() == 225) mapy -= 2;
			if (mapy == 0 || mapy == 270) y+=2;
		}
	}
	
	public void update(Inuit main, int [][] map){
		int posxl = x - mapx - 3;
		int gridxl = posxl/30;
		int posxr = x +57 - mapx;
		int gridxr = posxr/30;
		double posy = (y + 5.0 - mapy)/30.0;
		int gridy = (int)posy + 9;
		if (posy < 0 && (posy != (int) posy)) gridy--;
		if (alive){
			position(main.mapx, main.mapy);
			if (chase(main) == 0){
				attacking = false;
				if (stop == 0){
					if (direction == 0){
						if (x > patrolx - 100){
							if (map[gridy + 1][gridxl] == 0) direction = 1;
							else x -= speed;
						} else direction = 1;
					} else if (direction == 1){
						if (x < patrolx + 100){
							if (map[gridy + 1][gridxr] == 0) direction = 0;
							else x += speed;
						} else direction = 0;
					}
				} else {
					if (Math.abs(recover - System.currentTimeMillis()) > 300) stop = 0;
				}
			} else {
				if (stop == 0){
					if (main.getX() < x ) {
						direction = 0;
						if (x >= main.getX() + 32){
							attacking = false;
							x -= speed;
						} else if (!ad){
							main.hurt(attack());
							attackDelay = System.currentTimeMillis();
							attacking = true;
							ad = true;
						} else {
							if (Math.abs(attackDelay - System.currentTimeMillis()) > 400){
								ad = false;
							} else if (Math.abs(attackDelay - System.currentTimeMillis()) > 200)attacking = false;
						}
					}
					else {
						direction = 1;
						if (x + 30 <= main.getX() - 32){
							attacking = false;
							x += speed;
						} else if (!ad){
							main.hurt(attack());
							attackDelay = System.currentTimeMillis();
							attacking = true;
							ad = true;
						} else {
							if (Math.abs(attackDelay - System.currentTimeMillis()) > 400){
								ad = false;
							}else if (Math.abs(attackDelay - System.currentTimeMillis()) > 200)attacking = false;
						}
					}
					
				} else {
					if (Math.abs(recover - System.currentTimeMillis()) > 300) stop = 0;
				}
			}
		}
	}
}
