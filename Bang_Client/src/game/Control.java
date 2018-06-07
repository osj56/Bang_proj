package game;

/**
 * �� Ŭ������ Game Ŭ�������� KeyListener �� ���� ���� Ű ���� ���� Ű���� �Է��� �����ϴ� Ŭ�����Դϴ�.
 */
public class Control{
	boolean keyUp = false; 			// ���� ȭ��ǥ�� ������������ä���ִ�.
	boolean keyDown = false;        // �Ʒ��� ȭ��ǥ�� ������������ä���ִ�.
	boolean keyLeft = false;        // ���� ȭ��ǥ�� ������������ä���ִ�.
	boolean keyRight = false;       // ������ ȭ��ǥ�� ������������ä���ִ�.
	boolean space = false;          // �����̽� �ٰ� ������������ ä�� �ִ�.
	boolean shift = false;          // ����Ʈ
	boolean p = false;              // �Ͻ����� pause
	boolean et = false;             // ����
	boolean r = false;              // �����
	private Game game;              //Control �� ���� ���� ��ü
	
	Control(Game g){/*Control�� ������. ���� �������� Game�� ��ȣ���� �� �ش�.*/
		game = g;
	}
	public void arrowkey() {/*����Ű�� ���� ���� Ű ���� ���� �������� �����Ѵ�. ����Ű�� ���� �� ���� 4��ŭ�� �ӵ��� ���ϰ� �Ѵ�. ���� ���ȭ�鿡�� enterŰ�� ������ ������ �����Ѵ�.*/
		if (game.mode == 0) {

		} else {
			if (keyUp == true && game.pause == false) {
				if (game.player.p.y > 0){
					if (space == false) { game.player.p.y -= 7; }
				}
			}
			if (keyDown == true && game.pause == false) {
				if (game.player.p.y < 750) {
					if (space == false) { game.player.p.y += 7; }
				}
			}
			if (keyLeft == true && game.pause == false) {
				if (game.player.p.x > 0) {
					if (space == false) { game.player.p.x -= 7; }
				}
			}
			if (keyRight == true && game.pause == false) {
				if (game.player.p.x < 1000) {
					if (space == false) { game.player.p.x += 7; }
				}
			}
		}
	}
	
}
