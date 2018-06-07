package game;

import java.awt.Image;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;

import GameController.ServerEnemy;



/**
 * 자신의 캐릭터가 동작 중 Space를 누르면 4방향으로 날아가는 총알에 대한 정보가 담겨있는 클래스이다.
 * 
 * */
public class Bullet {
	public int x, y, direction; // x,y는 총알의 시작 좌표. direction은 총알의 방향 {1:왼쪽   2:오른쪽   3:위   4:아래} 
	ArrayList<Bullet> arr = new ArrayList<Bullet>(); // 자기자신에 대한 배열을 가져 계속해서 생성되는 총알의 정보를 담는다.
	Game game; // 게임에 대한 정보를 가짐
	Image weapon1 = new ImageIcon("Img/bullet1.png").getImage();
	Image weapon2 = new ImageIcon("Img/bullet2.png").getImage();
	Image weapon3 = new ImageIcon("Img/bullet3.png").getImage();
	Image weapon4 = new ImageIcon("Img/bullet4.png").getImage();
	
	public Bullet(Game g){// Bullet 생성자. 게임 당  하나의 클래스만 생성하고 나머지는 배열에 저장되는 방식.
		game = g;
	}
	
	public Bullet(int x, int y, int a){ // Bullet 생성자.
		this.x = x;
		this.y = y;	
		this.direction = a;
		String s = "\"Img/bullet" + direction + ".png\"";
	}
	
	void create(int x,int y){ // 총알을 생성해 배열에 저장한다.
		for(int i=1;i<=4;i++){
			Bullet bl = new Bullet(x,y,i);
			arr.add(bl);
	//		this.sendBullet(bl);
		}	
	}
	
	public void move(){ // 총알을 4방향으로 이동하도록 한다.
		for(int i=0;i<arr.size();i++){
			Bullet bl = (Bullet)(arr.get(i));
			if(bl.direction == 1){
				bl.y -= 7;
			}else if(bl.direction == 2){
				bl.y += 7;
			}else if(bl.direction == 3){
				bl.x -= 7;
			}else if(bl.direction == 4){
				bl.x += 7;
			}
		}
	}
	
	public void DrawImg(){ // 총알의 그림을 그려주는 함수
		for(int i=0;i<arr.size();i++){
			Bullet bl = (Bullet) (arr.get(i));
			if(bl.direction==1){
				game.gc.drawImage(weapon1, bl.x,bl.y,game);
			}else if(bl.direction==2){
				game.gc.drawImage(weapon2, bl.x,bl.y,game);
			}else if(bl.direction==3){
				game.gc.drawImage(weapon3, bl.x,bl.y,game);
			}else{
				game.gc.drawImage(weapon4, bl.x,bl.y,game);
			}
			
		}
	}
	
	public boolean isDie() {
		for (Bullet b : this.arr) {
			int dis1 = (int) Math.pow((game.player.p.x + 10) - (b.x + 10), 2);
			int dis2 = (int) Math.pow((game.player.p.y + 15) - (b.y + 15), 2);

			int dis3 = (int) Math.pow((game.player.p.y + 50) - (b.y + 15), 2);
			int dis4 = (int) Math.pow((game.player.p.x + 20) - (b.x + 10), 2);
			
			double dist = Math.sqrt(dis1 + dis2); // 장애물과 자기자신의 거리를 계산한다.
			double dist2 = Math.sqrt(dis4 + dis3);
			if (game.player.state == 3) {
				if (dist2 < 11) {
					arr.remove(b);
					return true;
				}
			} else {
				if (dist < 15) {
					arr.remove(b);
					return true;
				}
			}
		}
		return false;
	}
	
	public int killEnemy(CopyOnWriteArrayList<ServerEnemy> enemy) {
		for (Bullet b : this.arr) {
			for (int i = 0; i < enemy.size(); i++) {
				ServerEnemy e = enemy.get(i);
				int dis1 = (int) Math.pow((b.x + 10) - (e.getX() + 10), 2);
				int dis2 = (int) Math.pow((b.y + 15) - (e.getY() + 15), 2);
				double dist = Math.sqrt(dis1 + dis2); // 장애물과 자기자신의 거리를 계산한다.
				if (dist < 25) {
					arr.remove(b);
					System.out.println("Enemy kill");
					game.player.hp++;
					return i;
				}
			}
		}
		return 999;
	}
}
