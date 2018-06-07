package game;

import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Random;

import mju.cn.common.RequestPacket;
import mju.cn.common.RequestPacket.SYNC_TYPE;

/**
 * 
 * �÷��̾��� ������ ��� �ִ� Ŭ����.
 *
 */
public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	Point p;
	int point; 							// �������� ���� ����
	int score;							// ����ġ ��
	int total;							// ����
	int boost;							// �ν�Ʈ ������
	int state = 0;
	int hp = 1;
	boolean isBullet = false;
	public boolean die = false;
	boolean win = false;
	boolean isHit = false;
	public boolean isAdult = false;
	public boolean exit = false;
	
	Exp exp;
	Game game;
	Socket socket;
	ObjectOutputStream outputStream;
	ObjectInputStream inputStream;
	String id;
	
	
	int [] initPlayer(){
		Random rand = new Random();
		int x_rand = (rand.nextInt() % 1000);
		x_rand = Math.abs(x_rand);
		int y_rand = (rand.nextInt() % 750);
		y_rand = Math.abs(y_rand);
		int[] res = new int[2];
		res[0] = x_rand;
		res[1] = y_rand;
		return res;
	}
	
	Player(Game g, Socket s, String id){
		game = g; 
		setId(id);
		init();
		int point[] = initPlayer();
		p = new Point(point[0],point[1]);
		exp = new Exp(5);
		socket = s;
		outputStream = game.getOutputStream();
	}
	
	public void init(){
		isBullet = false;
		isHit = false;
		win = false;
		state = 0;
		isAdult = false;
		hp = 1; 
		point = 0; 
		score = 0; 
		total = 0; 
		boost = 3;
		exit = false;
		@SuppressWarnings("unused")
		boolean die = false;
		exp = new Exp(5);
		int point[] = initPlayer();
		p = new Point(point[0],point[1]);
	}
	
	public void MovestdImage(Image dbImage, int x, int y, int width, int height) {/*�ڱ� �ڽ��� �̹����� �׷��ִ� �Լ�*/
		// ù��° ��ȭ.
		if(state==0){
			dbImage = GUI.egg;
			GUI.stdimg = dbImage;
		}
		if (total == 50 && exp.isFull()) {
			isAdult = true;
			game.engine.addHP();
			dbImage = GUI.nomal;
			GUI.stdimg = dbImage;
			score = 0; // ��ȭ�� �ʱ�ȭ
			exp.clear(); // ���� �ʱ�ȭ
			state= 1;
		}
		// �ι�° ��ȭ
		if (total == 100 && exp.isFull()) {
			game.engine.addHP();
			dbImage = GUI.rare;
			GUI.stdimg = dbImage;
			score = 0;
			exp.clear();
			state = 2;
		}
		// ����° ��ȭ
		if (total == 150 && exp.isFull()) {
			game.engine.addHP();
			dbImage = GUI.unique;
			GUI.stdimg = dbImage;
			score = 0;
			exp.clear();
			state = 3;
		}
		
		if (total > 150 && exp.isFull()) {
			game.engine.addHP();
			score = 0;
			exp.clear();
		}
		game.gui.drawPlayer(width,height);

	}
	public void sendXY(){
		try {		
//			System.out.println("write packet");
			RequestPacket packet = new RequestPacket();
			packet.setClassName("Player");
			packet.setMethodName("sendXY");
			packet.setSyncType(SYNC_TYPE.SYNCHRONOUS);
			packet.setArgs(new Object[] { id,p.x, p.y,state,isBullet,game.killer,die,hp,exit,game.mode});
//			System.out.println("sending..");
			isBullet = false;
			outputStream.writeObject(packet);
			outputStream.flush();
			game.engine.Reciever();
		} catch (IOException e) {
			e.printStackTrace();
		}	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	}
	
	public String makeID(){
		Random rand = new Random();
		int id = Math.abs(rand.nextInt()%10000);
		System.out.println("ID:"+id);
		return String.valueOf(id);	
	}
	
	public void DrawImg() {	/*�迭�� ����� ������ ȭ�鿡 �׷��ش�.*/
		game.gc.drawImage(GUI.stdimg, p.x, p.y, game);
	}
	
	public void setId(String id){
		this.id = id;
	}
}
