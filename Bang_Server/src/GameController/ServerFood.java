package GameController;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class ServerFood implements Serializable{
	protected static final long serialVersionUID = 1L;
	private int position[] = new int[2];
	
	public ServerFood(int x, int y) {
		position[0] = x;
		position[1] = y;
	}
	
	public void move() {/*������ ��ġ�� �������ְ�, �ѹ� �����Ǹ� �ٲ��� �ʴ´�.*/

	}

	public int getX() {
		// TODO Auto-generated method stub
		return position[0];
	}
	
	public int getY() {
		// TODO Auto-generated method stub
		return position[1];
	}
}
