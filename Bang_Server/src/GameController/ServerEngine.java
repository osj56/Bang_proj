package GameController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/**
 * 
 * ������ ������ ����ϴ� Ŭ����.
 * ����� �������� ��ü�� �����ϱ� ���� ��ǥ�� �����ϴ� �Լ��� ����ִ�.
 *
 */
public class ServerEngine {
	public static int[] GenerateXNY() { /*��ǥ�� �������� �����Ѵ�.*/
		Random rand = new Random();
		int x_rand = (rand.nextInt() % 900);
		x_rand = Math.abs(x_rand);
		int y_rand = (rand.nextInt() % 700);
		y_rand = Math.abs(y_rand);
		int[] res = new int[2];
		res[0] = x_rand;
		res[1] = y_rand;
		return res;
	}
	
	public static void getTime(){
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");
		System.out.print("["+sdf.format(dt).toString()+"] ");
	}
}


