
public class Inuit {
	private int x;
	private int y;
	private int yjump;
	public int mapyjump;
	public int mapx;
	public int mapy;
	public int hp;
	
	public Inuit (int xx,int yy, int health){
		x = xx;
		y = yy;
		yjump = yy;
		mapx = 0;
		mapy = 0;
		mapyjump = mapy;
		hp = health;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	public int getXgrid(){
		int posx = x + 15 - mapx;
		int gridx = posx/30;
		return gridx;
	}
	public int getYgrid(){
		int posy = y + 5 - mapy;
		int gridy = posy/30 + 9;
		return gridy;
	}
	public int getYYgrid(){
		double posy = (y + 34.0 - mapy)/30.0;
		int gridy = (int) posy + 9;
		if (posy < 0 && posy != (int) posy) gridy--;
		return gridy;
	}
	public int getYYYgrid(){
		double posy = (y + 4.0 - mapy)/30.0;
		int gridy = (int) posy + 9;
		if (posy < 0 && posy != (int) posy) gridy--;
		return gridy;
	}
	public int getYjump(){
		return yjump;
	}
	public void moveRight(){
		x += 2;
	}
	public void moveLeft(){
		x -= 2;
	}
	public void moveUp(){
		y -= 2;
	}
	public void moveDown(){
		y += 2;
	}
	public void hurt(int damage){
		hp -= damage;
	}
	public int jump(int xx, int left, int right){
		if ((y != yjump || mapy != mapyjump) && left == 1 && x > 5){
			if (mapx == 0 || mapx == -838)x -= 2;
			if (x == 376 || x == 377) mapx += 2;
		}
		if ((yjump != y || mapy != mapyjump) && right == 1 && x < 715) {
			if (mapx == 0 || mapx == -838)x += 2;
			if (x == 376 || x == 377) mapx -= 2;
		}
		
		if (xx == 1 && (y >= yjump - 90 && mapy <= mapyjump + 90)){
			if (getY() == 225) mapy += 2;
			if (mapy == 0 || mapy == 270) y -= 2;
			return xx;
		} else if ( y == yjump && mapy == mapyjump){
			return 0;
		} else {
 			return 2;
		}
	}
	public boolean checkLadder(int [][] map){
		int posx = x + 15 - mapx;
		int gridx = posx/30;
		double posy = (y + 5.0- mapy)/30.0;
		int gridy = (int) posy + 9;
		if (posy < 0 && posy != (int) posy) gridy--;
		double posyy = (y + 34.0 - mapy)/30.0;
		int gridyy = (int) posyy + 9;
		if (posyy < 0 && posyy != (int) posyy) gridyy--;
		
		if ((map[gridy][gridx] == 2 || map[gridy][gridx] == 4) || (map[gridyy][gridx] == 2 || map[gridyy][gridx] == 4)) return true;
		else if (map[gridy+1][gridx] == 4) return true;
		else return false;
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
			yjump = y;
			mapyjump = mapy;
		}else {
			if (getY() == 225) mapy -= 2;
			if (mapy == 0 || mapy == 270) y+=2;
		}
	}
}
