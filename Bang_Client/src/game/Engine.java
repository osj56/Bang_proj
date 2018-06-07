package game;

import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

import GameController.ServerEnemy;
import GameController.ServerFood;
import GameController.ServerPlayer;
import mju.cn.common.RequestPacket;


/**
 * 
 * ������ ������ ����ϴ� Ŭ����.
 * ����� �������� ��ü�� �����ϱ� ���� ��ǥ�� �����ϴ� �Լ��� ����ִ�.
 *
 */
public class Engine {
	boolean gameOver = false;
	Game game;
	Engine(Game g){
		game = g;
	}
	
	public void plusScore(){
		game.player.score += 10;
		game.player.point++;
		game.player.total += 10;
		game.player.exp.push(10);
	}
	
	public void addHP(){
		game.player.hp ++;
		game.player.hp ++;
	}

	@SuppressWarnings("unchecked")
	void Reciever(){
		Object obj;
		try {
			obj = game.inputStream.readObject();
			RequestPacket requestPacket = (RequestPacket) obj;
			Object[] arg = requestPacket.getArgs();
			
			game.enemy = (CopyOnWriteArrayList<ServerEnemy>)arg[1];
			game.food = (CopyOnWriteArrayList<ServerFood>)arg[2];
			
			if (requestPacket.getMethodName().equals("sendPlayer")) {
				game.playerOther = (Vector<ServerPlayer>) arg[0];
				for (ServerPlayer p : game.playerOther) {
					if (game.player.id.equals(p.getID())) {
						if(p.getIsEat()){
							game.engine.plusScore();
						}
						if(p.getIsHit()){
							System.out.println("is hit!");
							game.player.isHit = true;
							game.player.hp--;
						}
						if(p.getWin()){
//							game.player.sendXY();
							gameOver = true;
						}
					} else {
						boolean isNew = true;
						for(int i=0;i<game.other.size();i++){
							OtherPlayer o = game.other.get(i);
							if(o.getID()!=null&&o.getID().equals(p.getID())){			
								isNew = false;
								o.x = p.getX();
								o.y = p.getY();
								o.hp = p.getHP();
								o.state = p.getState();
								if (p.getIsBullet() == true) {
									System.out.println("other is shooting!");
									game.otherBullet.create(o.x, o.y);
								}
								if(p.isDie()){
									o.x = -100;
									o.y = -100;
								}
							}
						}
						if(p.getID()!=null&&isNew){
							OtherPlayer o = new OtherPlayer(game);
							o.setID(p.getID());
							game.other.add(o);
							o.x = p.getX();
							o.y = p.getY();
							o.hp = p.getHP();
							o.state = p.getState();
							if (p.getIsBullet() == true) {
								System.out.println("other is shooting!");
								game.otherBullet.create(o.x, o.y);
							}
							if(p.isDie()){
								o.x = -100;
								o.y = -100;
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moveObject(){
		game.bullet.move();
		game.otherBullet.move();
	}
	
	public void addMagazine() { /* �Ѿ��� �����ϱ� ���� �޼ҵ� */
		if (game.control.space == false) {
			if (game.player.boost < 40&&game.player.state!=0) {
				if (game.cnt % 200 == 0) {
					game.player.boost++;
				}
			}
		} else {
			game.control.space = false;
		}
	}
}


