package game;

import java.awt.Image;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;

import GameController.ServerEnemy;



/**
 * �ڽ��� ĳ���Ͱ� ���� �� Space�� ������ 4�������� ���ư��� �Ѿ˿� ���� ������ ����ִ� Ŭ�����̴�.
 * 
 * */
public class Bullet {
	public int x, y, direction; // x,y�� �Ѿ��� ���� ��ǥ. direction�� �Ѿ��� ���� {1:����   2:������   3:��   4:�Ʒ�} 
	ArrayList<Bullet> arr = new ArrayList<Bullet>(); // �ڱ��ڽſ� ���� �迭�� ���� ����ؼ� �����Ǵ� �Ѿ��� ������ ��´�.
	Game game; // ���ӿ� ���� ������ ����
	Image weapon1 = new ImageIcon("Img/bullet1.png").getImage();
	Image weapon2 = new ImageIcon("Img/bullet2.png").getImage();
	Image weapon3 = new ImageIcon("Img/bullet3.png").getImage();
	Image weapon4 = new ImageIcon("Img/bullet4.png").getImage();
	
	public Bullet(Game g){// Bullet ������. ���� ��  �ϳ��� Ŭ������ �����ϰ� �������� �迭�� ����Ǵ� ���.
		game = g;
	}
	
	public Bullet(int x, int y, int a){ // Bullet ������.
		this.x = x;
		this.y = y;	
		this.direction = a;
		String s = "\"Img/bullet" + direction + ".png\"";
	}
	
	void create(int x,int y){ // �Ѿ��� ������ �迭�� �����Ѵ�.
		for(int i=1;i<=4;i++){
			Bullet bl = new Bullet(x,y,i);
			arr.add(bl);
	//		this.sendBullet(bl);
		}	
	}
	
	public void move(){ // �Ѿ��� 4�������� �̵��ϵ��� �Ѵ�.
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
	
	public void DrawImg(){ // �Ѿ��� �׸��� �׷��ִ� �Լ�
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
			
			double dist = Math.sqrt(dis1 + dis2); // ��ֹ��� �ڱ��ڽ��� �Ÿ��� ����Ѵ�.
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
				double dist = Math.sqrt(dis1 + dis2); // ��ֹ��� �ڱ��ڽ��� �Ÿ��� ����Ѵ�.
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
