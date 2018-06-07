package GameController;

import java.io.Serializable;

public class ServerPlayer implements Serializable {
	protected static final long serialVersionUID = 1L;
	private String id = null; // 플레이어 아이디
	private int x;
	private int y;
	private int state;
	private boolean is_bullet = false; // 총알 발사 여부
	private boolean is_die = false; // 죽은 여부
	private boolean is_eat = false;
	private boolean is_hit = false;
	private int hp;
	private boolean isWin = false;


	public ServerPlayer() {

	}

	public String getID() {
		return id;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getState() {
		return state;
	}

	public void setState(int n) {
		state = n;
	}

	public void setIsBullet(boolean b) {
		this.is_bullet = b;
	}

	public boolean getIsBullet() {
		return is_bullet;
	}

	public void setIsDie(boolean b) {
		is_die = b;
	}

	public boolean isDie() {
		return is_die;
	}

	public void setID(String id) {
		this.id = id;
	}

	public void setIsEat(boolean b) {
		is_eat = b;
	}

	public boolean getIsEat() {
		return is_eat;
	}

	public void setIsHit(boolean b) {
		is_hit = b;
	}

	public boolean getIsHit() {
		return is_hit;
	}

	public void setHP(int hp) {
		this.hp = hp;
		
	}

	public int getHP() {
		// TODO Auto-generated method stub
		return hp;
	}

	public void setWin(boolean b) {
		isWin = b;
	}
	
	public boolean getWin(){
		return isWin;
	}
}
