package Server;

import java.io.Serializable;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import GameController.ServerEnemy;
import GameController.ServerEngine;
import GameController.ServerFood;
import GameController.ServerPlayer;
import Generator.ServerGenerator;
import mju.cn.server.room.SSRoom;

public class Game implements Serializable {
	private static final long serialVersionUID = 1L;
	private Vector<ServerPlayer> player = null;
	private CopyOnWriteArrayList<ServerEnemy> enemy = null;
	private CopyOnWriteArrayList<ServerFood> food = null;
	private ServerGenerator generator;
	private boolean RunningGenerator = false;
	private int room = 999;
	public int MaxPlayer = 2;
	private SSRoom ssRoom;

	public Game(int room) {
		this.room = room;
		ServerEngine.getTime();
		System.out.println("room: " + room);
		player = new Vector<ServerPlayer>();
		enemy = new CopyOnWriteArrayList<ServerEnemy>();
		food = new CopyOnWriteArrayList<ServerFood>();
	}

	public Game(ServerManager server) {
		ServerEngine.getTime();
		System.out.println("initializing....");
		player = new Vector<ServerPlayer>();
		enemy = new CopyOnWriteArrayList<ServerEnemy>();
		food = new CopyOnWriteArrayList<ServerFood>();
	}

	public Game(ServerManager server, int MaxPlayer) {
		ServerEngine.getTime();
		System.out.println("initializing....");
		player = new Vector<ServerPlayer>();
		enemy = new CopyOnWriteArrayList<ServerEnemy>();
		food = new CopyOnWriteArrayList<ServerFood>();
		this.MaxPlayer = MaxPlayer;
	}

	public void addPlayer(SocketManager manager) {
		ServerEngine.getTime();
		System.out.println("added Player");
		if (player.size() <= MaxPlayer) {
			ServerPlayer p = new ServerPlayer();
			player.add(p);
			p.setXY(-100, -100);
			p.setState(0);
			p.setHP(1);
			p.setIsBullet(false);
			manager.setPlayer(p);
			if (player.size() == MaxPlayer) {
				generator = new ServerGenerator(this);
				generator.start();
			}
		}
	}

	public void updatePlayer(SocketManager manager, String id, int x, int y, int state, boolean isBullet, int kill,
			boolean die, int hp, boolean isExit) {
		synchronized (this) {
			if (isExit == true) {
				manager.exitSocket();
			}
			for (ServerPlayer p : player) {
				if (manager.getPlayer().equals(p)) {
					if (p.getID() == null) {
						p.setID(id);
					}
					p.setXY(x, y);
					p.setState(state);
					p.setIsBullet(isBullet);
					p.setHP(hp);
					if (die) {
						ServerEngine.getTime();
						p.setIsDie(true);
						p.setHP(0);
						p.setXY(-100, -100);
						for (ServerPlayer o : player) {
							if(!manager.getPlayer().equals(o)){
								o.setWin(true);
							}
						}
					}
				}
				if (kill != 999) {
					ServerEngine.getTime();
					System.out.println("hit Enemy!");
					removeEnemy(kill);
				}
			}
		}
	}

	public void removeEnemy(int index) {
		generator.delCount();
		enemy.remove(index);
	}

	public void removeBulletInfo() {
		for (ServerPlayer p : player) {
			p.setIsBullet(false);
		}
	}

	public void removePlayer(SocketManager manager) {
		synchronized (this) {
			for (int i = 0; i < player.size(); i++) {
				ServerPlayer p = player.get(i);
				if (manager.getPlayer().equals(p)) {
					player.remove(i);
					break;
				}
			}
		}
	}

	public void isEat() {
		synchronized (this) {
			for (ServerFood f : food) {
				synchronized (this) {
					for (ServerPlayer p : player) {
						int dis1 = (int) Math.pow((p.getX() + 25) - (f.getX() + 25), 2);
						int dis2 = (int) Math.pow((p.getY() + 25) - (f.getY() + 25), 2);
						double dist = Math.sqrt(dis1 + dis2);

						if (dist < 25) { // �Ÿ��� 25���Ϸ� �پ��ԵǸ� ������ ������� ����ġ�� �þ��.
							p.setIsEat(true);
							food.remove(f);
							return;
						}
					}
				}
			}
		}
	}

	public void isHit() {
		synchronized (this) {
			for (ServerEnemy e : enemy) {
				synchronized (this) {
					for (ServerPlayer p : player) {
						int dis1 = (int) Math.pow((p.getX() + 10) - (e.getX() + 10), 2);
						int dis2 = (int) Math.pow((p.getY() + 15) - (e.getY() + 15), 2);
						double dist = Math.sqrt(dis1 + dis2);
						if (dist < 25) {
							p.setIsHit(true);
						}
					}
				}
			}
		}
	}

	public CopyOnWriteArrayList<ServerEnemy> getEnemy() {
		return enemy;
	}

	public Vector<ServerPlayer> getPlayer() {
		return player;
	}

	public void initGenerator() {
		generator = new ServerGenerator(this);
		generator.start();
	}

	public CopyOnWriteArrayList<ServerFood> getFood() {
		return food;
	}

	public boolean isGenerator() {
		return RunningGenerator;
	}

	public int getRoom() {
		return room;
	}

	public SSRoom getSsRoom() {
		return ssRoom;
	}

	public void setSsRoom(SSRoom ssRoom) {
		this.ssRoom = ssRoom;
	}

}
