package GameController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ServerEnemy implements Serializable{
	protected static final long serialVersionUID = 1L;
	protected int position[] = new int[2]; // ��ֹ��� ��ġ�� �������� �޾ƿ��� ���� ����
	private int velocity; // ��ֹ��� �̵��ӵ��� �����ϱ� ���� ����
	private int dst[] = new int[2]; // �̵���θ� �����ϱ� ���� ����
	private int kind;
	
	int[] resetDst() { /* ��ǥ�� �ٽ� ����� ���Ͻ�Ų��. */
		return ServerEngine.GenerateXNY();
	}

	public ServerEnemy(int x, int y) {/* ��ֹ��� ������ */
		position[0] = x;
		position[1] = y;
		dst = ServerEngine.GenerateXNY(); // �������� �������� �޾ƿ´�
		Random rand = new Random();
		velocity = (rand.nextInt() % 3) + 1; // 2�� 4������ �ӵ��� �޾ƿ´�
		Random rand2 = new Random();
		kind = Math.abs(rand.nextInt()%4)+1;
	}
	
	public void move() { /* ��ֹ��� �����δ�. */
		if (position[0] >= dst[0] && position[0] >= 0) {
			position[0] = position[0] - velocity;
		}
		if (position[0] <= dst[0] && position[0] <= 1000) {
			position[0] = position[0] + velocity;
		}

		if (position[1] >= dst[1] && position[1] >= 0) {
			position[1] = position[1] - velocity;
		}
		if (position[1] <= dst[1] && position[1] <= 750) {
			position[1] = position[1] + velocity;
		} // ��ֹ��� ȭ�� ������ ���������� ���ϰ� �Ѵ�.

		if (position[0] > 1000) {
			dst = resetDst();
		}
		if (position[1] > 750) {
			dst = resetDst();
		}

		if (position[0] < 0) {
			dst = resetDst();
		}
		if (position[1] < 0) {
			dst = resetDst();
		} // ��ֹ��� �����ϳ� ȭ������� �Ѿ�� ��쿡 ȭ��ݴ������� �������ϰ�, �������� �缳���Ѵ�
		if (Math.abs(position[0] - dst[0]) <= velocity * 2) {
			dst = resetDst();
		}
		if (Math.abs(position[1] - dst[1]) <= velocity * 2) {
			dst = resetDst();
		}
		// ��ֹ��� ������ ��ó�� �����Ͽ��� ��� �������� �缳���Ѵ�
		if (position[0] > 1000 || position[0] < velocity) {
			dst = resetDst();
		}
		if (position[1] > 750 || position[1] < velocity) {
			dst = resetDst();
		} // ��ֹ��� ȭ������� �Ѿ ��� �������� �缳���Ѵ�
	}

	public int getX() {
		// TODO Auto-generated method stub
		return position[0];
	}
	public int getY() {
		// TODO Auto-generated method stub
		return position[1];
	}
	
	public int getKind(){
		return kind;
	}
}
