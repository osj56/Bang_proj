package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import GameController.ServerEnemy;
import GameController.ServerFood;
import mju.cn.client.gui.ContentPane;

/**
 * 
 * 게임 실행에 사용되는 UI나 기타 이미지들을 불러오는 클래스.
 *
 */
public class GUI {
	private Game game;
	private ContentPane contentPane;

	GUI(Game g) {
		game = g;
	}

	static Image dbImage; // 빈공간 이미지
	static Image background = new ImageIcon("Img/배경.jpg").getImage();
	static Image login_right = new ImageIcon("Img/login_right.png").getImage();
	
	static Image bullet = new ImageIcon("Img/bullet2.png").getImage();

	static Image enemy1 = Toolkit.getDefaultToolkit().createImage("Img/enemy.gif");
	static Image enemy2 = Toolkit.getDefaultToolkit().createImage("Img/enemy2.gif");
	static Image enemy4 = Toolkit.getDefaultToolkit().createImage("Img/enemy4.gif");
	static Image enemy3 = Toolkit.getDefaultToolkit().createImage("Img/enemy3.gif");

	static Image nomal = Toolkit.getDefaultToolkit().createImage("Img/normal2.gif");
	static Image rare = Toolkit.getDefaultToolkit().createImage("Img/rare2.gif");
	static Image unique = Toolkit.getDefaultToolkit().createImage("Img/unique2.gif");

	static Image nomal_enemy = Toolkit.getDefaultToolkit().createImage("Img/normal.gif");
	static Image rare_enemy = Toolkit.getDefaultToolkit().createImage("Img/rare.gif");
	static Image unique_enemy = Toolkit.getDefaultToolkit().createImage("Img/unique.gif");

	static Image buffimg = null; // 더블버퍼링을 사용하기위한 버퍼이미지를 정의한다
	static Image stdimg = Toolkit.getDefaultToolkit().createImage("Img/move_egg.gif");
	
	static Image egg = Toolkit.getDefaultToolkit().createImage("Img/move_egg.gif");

	static Image egg_enemy = Toolkit.getDefaultToolkit().createImage("Img/move_egg2.gif");
	static Image weapon = new ImageIcon("Img/bullet1.png").getImage();
	static Image title = new ImageIcon("Img/title.png").getImage();
	static Image food = new ImageIcon("Img/food2.gif").getImage();
	static Image info = new ImageIcon("Img/info.png").getImage();

	public void backgroundDrawImg() {
		game.gc.drawImage(GUI.background, 0, 0, game); // 가져온 배경이미지파일을 0,0에
														// 위치시킨다
	}

	public void setPannel() { /* 모드를 설정하는 함수 */
		if (game.mode == 0) {
			if (game.control.et == true) {
				game.mode = 1;
			}
		}
		if (game.control.p == true) { // p를 누르면 게임이 멈춥니다
			game.pause = !game.pause;
			game.control.p = false;
		}
	}

	public void drawEXP() {
		Graphics2D g2d = (Graphics2D) game.gc;
		int width = 30;

		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(width));
		game.gc.drawLine(0, 743, 1024, 743);

		g2d.setColor(Color.ORANGE);
		g2d.setStroke(new BasicStroke(width));
		game.gc.drawLine(0, 743, 102 * (game.player.score / 5), 743);

		g2d.setColor(Color.WHITE);
		game.gc.drawString("EXP", 20, 730);
	}

	public void drawHP() {
		Graphics2D g2d = (Graphics2D) game.gc;
		int width = 30;

		width = 30;
		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(width));
		game.gc.drawLine(0, 47, 300, 47);

		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(width));
		game.gc.drawLine(0, 47, (game.player.hp * 10 - 10)-30, 47);

		g2d.setColor(Color.WHITE);
		game.gc.drawString("HP", 20, 54);
	}
	
	public void drawOtherHP() {
		int i = 0;
		for (OtherPlayer o : game.other) {
			Graphics2D g2d = (Graphics2D) game.gc;
			int width = 30;

			width = 30;
			g2d.setColor(Color.GRAY);
			g2d.setStroke(new BasicStroke(width));
			game.gc.drawLine(724, 147+i, 1000, 147+i);

			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(width));
			game.gc.drawLine(704, 147+i, 704 + (o.getHP() * 10 - 10), 147+i);

			g2d.setColor(Color.WHITE);
			game.gc.drawString(o.id + "'s HP", 720, 154+i);
			i+=50;
		}
	}

	public void drawMagazine() {
/*		Graphics2D g2d = (Graphics2D) game.gc;
		int width;
		width = 30;
		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(width));
		game.gc.drawLine(724, 47, 1000, 47);

		width = 30;
		g2d.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(width));
		game.gc.drawLine(724, 47, 724 + (5 * game.player.boost), 47);

		g2d.setColor(Color.WHITE);
		game.gc.drawString("Bullet", 744, 54);*/
		
		for(int i=game.player.boost;i>=0;i--){
			game.gc.drawImage(GUI.bullet, 1000-(i*28), 30, game);
		}
	}

	public void UIDrawImg() { /* UI를 그려주는 함수 */
		game.gc.setFont(new Font("Default", Font.ROMAN_BASELINE, 20)); // 폰트의모양과크기를정한다
		drawHP();
		drawEXP();
		drawMagazine();
		if(game.other.size()>=1){
			drawOtherHP();
		}
		if (game.pause == true) {
			game.gc.drawImage(GUI.info, (Game.width - info.getWidth(game)) / 2,
					(Game.height - info.getHeight(game)) / 2, game);
		}
		game.player.MovestdImage(GUI.stdimg, game.player.p.x, game.player.p.y, GUI.stdimg.getWidth(game),
				GUI.stdimg.getHeight(game));
	}

	public void drawLoading() {
		game.gc.drawImage(GUI.title, 0, 0, 1024, 768, game);
		game.setBackground(Color.black);
		game.gc.drawImage(GUI.login_right, 100, 450, 900, 300, game);
		game.gc.setColor(Color.red);
		game.gc.setFont(new Font("Default", Font.PLAIN, 50));
		String s = "Press Enter";
		game.gc.drawString(s, (game.width - s.length()) / 3, 480);

	}

	public void drawEnemy() {
		for (int i = 0; i < game.enemy.size(); i++) {
			ServerEnemy en = (ServerEnemy) (game.enemy.get(i));
			if (en.getKind() == 1) {
				game.gc.drawImage(GUI.enemy1, en.getX(), en.getY(), game);
			} else if(en.getKind()==2) {
				game.gc.drawImage(GUI.enemy2, en.getX(), en.getY(), game);
			}else if(en.getKind()==3) {
				game.gc.drawImage(GUI.enemy3, en.getX(), en.getY(), game);
			}else{
				game.gc.drawImage(GUI.enemy4, en.getX(), en.getY(), game);
			}

		}
	}

	public void drawFood() {
		for (int i = 0; i < game.food.size(); i++) {
			ServerFood f = (ServerFood) (game.food.get(i));
			game.gc.drawImage(GUI.food, f.getX(), f.getY(), game);
		}
	}

	public void drawPlayer(int width, int height) {
		game.gc.setClip(game.player.p.x, game.player.p.y, width, height); 
		game.gc.drawImage(GUI.stdimg, game.player.p.x, game.player.p.y, game);
		game.gc.drawString("Me", game.player.p.x - 10, game.player.p.y - 50);
	}

	public void drawOther() {
		for (OtherPlayer o : game.other) {
			o.drawing();
		}
	}

	public void drawBullet(Bullet b) { // 총알의 그림을 그려주는 함수
		for (int i = 0; i < b.arr.size(); i++) {
			Bullet bl = (Bullet) (b.arr.get(i));
			if(game.player.state==3){
				if(bl.direction==1){
					game.gc.drawImage(bl.weapon2, bl.x,bl.y+30,game);
				}else if(bl.direction==2){
					game.gc.drawImage(bl.weapon4, bl.x,bl.y+30,game);
				}else if(bl.direction==3){
					game.gc.drawImage(bl.weapon1, bl.x,bl.y+30,game);
				}else{
					game.gc.drawImage(bl.weapon3, bl.x,bl.y+30,game);
				}
			}
			else{
				if(bl.direction==1){
					game.gc.drawImage(bl.weapon2, bl.x,bl.y,game);
				}else if(bl.direction==2){
					game.gc.drawImage(bl.weapon4, bl.x,bl.y,game);
				}else if(bl.direction==3){
					game.gc.drawImage(bl.weapon1, bl.x,bl.y,game);
				}else{
					game.gc.drawImage(bl.weapon3, bl.x,bl.y,game);
				}
			}
		}
	}
}
