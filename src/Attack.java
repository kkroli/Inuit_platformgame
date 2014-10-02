
public class Attack {

	private int attackSpeed;
	public int Xposition;
	public int Yposition;
	int direction;
	long duration;
	public int damage;
	public boolean damagedone;
	
	public Attack(int x, int xpos, int ypos, int dir,int dam){
		attackSpeed = x;
		Xposition = xpos;
		Yposition = ypos;
		duration = System.currentTimeMillis();
		direction = dir;
		damage = dam;
		damagedone = false;
	}
	
	public void launch(){
		if (direction == 1) Xposition += attackSpeed;
		else Xposition -= attackSpeed;
	
	}
	public long checkTime(){
		return Math.abs(duration - System.currentTimeMillis());
	}
}
